package sung04_exam2021_messageQueue_sub4_byYun;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ProcessHandler /*extends Thread*/ extends TimerTask {

    private static ProcessHandler inst;

    private LinkedBlockingQueue<Map> queue = new LinkedBlockingQueue<>();

    public static Map<String, Map<String, Object>> queueMap = new ConcurrentHashMap<>();

    public static Map<String, LinkedBlockingQueue<Map>> deQueueMap = new ConcurrentHashMap<>();

    public static Map<String, LinkedBlockingQueue<Map>> deadQueueMap = new ConcurrentHashMap<>();

    public static synchronized ProcessHandler getInstance() {
        if (inst == null) {
            inst = new ProcessHandler();
//            inst.setDaemon(true);
//            inst.start();
        }
        return inst;
    }

    public synchronized void add(Map<String, Object> map) {
        queue.add(map);
    }

    public synchronized void remove(Map<String, Object> mapForRemove) {
        Map<String, Object> removeMap = null;
        for (Map<String, Object> map : queue) {
            if (((String) map.get("MessageID")).equals(mapForRemove.get("MessageID"))) {
                removeMap = map;
                break;
            }
        }
        if (removeMap == null) {
            return;
        }
        System.out.println("remove" + ":"+removeMap.get("Message"));
        queue.remove(removeMap);
    }

    public synchronized void receiveProc(String uri, JsonObject result) {
        String[] uris = uri.split("/");
        Map<String, Object> queueNameMap = queueMap.get(uris[uris.length - 1]);
        if (queueNameMap != null /*&& !((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")).isEmpty()*/) {
            Map<String, String> pollMap = null;
            try {
                long waitTime = (long) queueNameMap.get("WaitTime");
                if ( waitTime == 0) {
                    pollMap = ((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")).poll();
                } else {
                    System.out.println("start : "+System.currentTimeMillis());
                    pollMap = ((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")).poll(waitTime, TimeUnit.SECONDS);
                    System.out.println("end : "+System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
                System.out.println("InterruptedException : "+System.currentTimeMillis());
                result.addProperty("Result", "No Message");
                return;
            }
            if (pollMap == null) {
                System.out.println("pollMap null : "+System.currentTimeMillis());
                result.addProperty("Result", "No Message");
            } else {
                Queue<Map> dequeue = deQueueMap.get(uris[uris.length - 1]);
                dequeue.offer(pollMap);
                result.addProperty("Result", "OK");
                result.addProperty("MessageID", (String) pollMap.get("MessageID"));
                result.addProperty("Message", (String) pollMap.get("Message"));

                int procTime = (int) queueNameMap.get("ProcessTimeout");
                if (procTime > 0) {
                    Map<String, Object> procMap = new ConcurrentHashMap<>();
                    procMap.putAll(pollMap);
                    long cuTime = System.currentTimeMillis();
                    procMap.put("FireTime", (cuTime - (cuTime%1000)) + Long.valueOf(procTime * 1000));
                    procMap.put("QueueName", uris[uris.length - 1]);
                    this.add(procMap);
                    System.out.println("RECEIVE" + ":" + uris[uris.length - 1] + ":" + pollMap.get("Message")+":"+"CUTime : "+(cuTime - (cuTime%1000))+"FireTime : " + procMap.get("FireTime"));
                }

                System.out.println("RECEIVE queue" + ":" + ((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")));
            }
        } else {
            result.addProperty("Result", "No Message");
        }
    }

    public synchronized void dlqProc(String uri, JsonObject result) {
        String[] uris = uri.split("/");
        LinkedBlockingQueue<Map> queueNameMap = deadQueueMap.get(uris[uris.length - 1]);
        if (queueNameMap != null && !((LinkedBlockingQueue<Map>)queueNameMap).isEmpty()) {
            Map<String, String> pollMap = ((LinkedBlockingQueue<Map>)queueNameMap).poll();
            if (pollMap == null) {
                result.addProperty("Result", "No Message");
            } else {
                result.addProperty("Result", "OK");
                result.addProperty("MessageID", (String) pollMap.get("MessageID"));
                result.addProperty("Message", (String) pollMap.get("Message"));
            }
        } else {
            result.addProperty("Result", "No Message");
        }
    }

    public synchronized void successProc(String uri, JsonObject result) {
        String[] uris = uri.split("/");
        LinkedBlockingQueue<Map> queue = deQueueMap.get(uris[uris.length - 2]);
        Map<String, Object> mapForRemove = null;
        for (Map<String, Object> map : queue) {
            if (map.get("MessageID").equals(uris[uris.length - 1])) {
                mapForRemove = map;
                break;
            }
        }
        if (mapForRemove == null) {
            result.addProperty("Result", "OK");
            return;
        }
        queue.remove(mapForRemove);
        this.remove(mapForRemove);
        System.out.println("ACK queueMap " + ":"+ queue);
        result.addProperty("Result", "OK");
    }

    public synchronized void sendProc(HttpServletRequest req, String uri, JsonObject result) throws IOException {
        String[] uris = uri.split("/");
        Map<String, Object> queueNameMap = queueMap.get(uris[uris.length - 1]);
        if (queueNameMap != null && ((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")).remainingCapacity() > 0) {
            JsonObject body = (JsonObject) JsonParser.parseReader(req.getReader());
            Map<String, Object> msgMap = new ConcurrentHashMap<>();
            msgMap.put("MessageID", String.valueOf(System.currentTimeMillis()));
            msgMap.put("Message", String.valueOf(body.get("Message").getAsString()));
            msgMap.put("FailCount", 0);
            ((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")).add(msgMap);
            System.out.println("SEND queue : "+ queueNameMap);
            result.addProperty("Result", "OK");
        } else if (queueNameMap != null && ((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")).remainingCapacity() == 0) {
            result.addProperty("Result", "Queue Full");
        }
    }

    public synchronized void createProc(HttpServletRequest req, String uri, JsonObject result) throws IOException {
        String[] uris = uri.split("/");
        if (queueMap.get(uris[uris.length - 1]) != null) {
            result.addProperty("Result", "Queue Exist");
        } else {
            JsonObject body = (JsonObject) JsonParser.parseReader(req.getReader());

            Map<String, Object> queueNameMap = new ConcurrentHashMap<>();
            queueNameMap.put("Queue", new LinkedBlockingQueue<>(Integer.valueOf(String.valueOf(body.get("QueueSize")))));
            queueNameMap.put("ProcessTimeout", Integer.valueOf(String.valueOf(body.get("ProcessTimeout"))));
            queueNameMap.put("MaxFailCount", Integer.valueOf(String.valueOf(body.get("MaxFailCount"))));
            queueNameMap.put("WaitTime", Long.valueOf(String.valueOf(body.get("WaitTime"))));
            queueMap.put(uris[uris.length - 1], queueNameMap);

            deQueueMap.put(uris[uris.length - 1], new LinkedBlockingQueue<>(Integer.valueOf(String.valueOf(body.get("QueueSize")))));
            result.addProperty("Result", "OK");
        }
    }

    public synchronized void failProc(String uri, JsonObject result) {
        String[] uris = uri.split("/");
        LinkedBlockingQueue<Map> dequeue = deQueueMap.get(uris[uris.length - 2]);
        Map<String, Object> queueNameMap = queueMap.get(uris[uris.length - 2]);
        LinkedBlockingQueue<Map> queue = (LinkedBlockingQueue<Map>)queueNameMap.get("Queue");
        List<Map> list = new LinkedList<>();
        Map<String, Object> mapForAdd = null;
        for (Map<String, Object> map : dequeue) {
            if (map.get("MessageID").equals(uris[uris.length - 1])) {
                mapForAdd = map;
                break;
            }
        }
        if (mapForAdd == null) {
            result.addProperty("Result", "OK");
            return;
        }
        mapForAdd.put("FailCount", (int)mapForAdd.get("FailCount") + 1);
        dequeue.remove(mapForAdd);
        this.remove(mapForAdd);

        if ((int)mapForAdd.get("FailCount") > (int)queueNameMap.get("MaxFailCount")) {
            LinkedBlockingQueue<Map> deadQueue = deadQueueMap.get(uris[uris.length - 2]);
            if (deadQueue == null) {
                deadQueue = new LinkedBlockingQueue<>();
                deadQueueMap.put(uris[uris.length - 2], deadQueue);
            }
            deadQueue.offer(mapForAdd);
        } else {
            long id = Long.valueOf((String) mapForAdd.get("MessageID"));
            boolean isInsert = false;
            if (queue.size() == 0) {
                list.add(mapForAdd);
            } else {
                for (Map<String, String> map : queue) {
                    if (!isInsert && id < Long.valueOf(map.get("MessageID"))) {
                        list.add(mapForAdd);
                        isInsert = true;
                    }
                    list.add(map);
                }
                if (!isInsert) {
                    list.add(mapForAdd);
                }
            }
            queue.clear();
            queue.addAll(list);
            System.out.println("FailProcess queueMap " + ":"+ queue);
        }
        result.addProperty("Result", "OK");
    }

    private synchronized boolean timeoutProc(Map<String, Object> map) {
        long cuTime = System.currentTimeMillis();
        boolean result = false;
        long gapTime = (cuTime - (cuTime%1000)) - (Long) map.get("FireTime");
        if (gapTime >= 0) {
            result = true;
            String queueName = (String) map.get("QueueName");
            LinkedBlockingQueue<Map> dequeue = deQueueMap.get(queueName);
            System.out.println("Fire :"+map.get("Message")+":CuTime:"+(cuTime - (cuTime%1000))+":FireTime:"+map.get("FireTime"));
            System.out.println("Fire deQueueMap " + ":"+dequeue);

            if (dequeue.isEmpty()) {
                return true;
            }

            Map<String, Object> queueNameMap = queueMap.get(queueName);
            LinkedBlockingQueue<Map> queue = (LinkedBlockingQueue<Map>)queueNameMap.get("Queue");
            List<Map> list = new LinkedList<>();
            Map<String, Object> mapForAdd = null;
            for (Map<String, Object> deMap : dequeue) {
                if (deMap.get("MessageID").equals(map.get("MessageID"))) {
                    mapForAdd = deMap;
                    break;
                }
            }
            if (mapForAdd == null) {
                return true;
            }
            mapForAdd.put("FailCount", (int)mapForAdd.get("FailCount") + 1);
            dequeue.remove(mapForAdd);
            if ((int)mapForAdd.get("FailCount") > (int)queueNameMap.get("MaxFailCount")) {
                LinkedBlockingQueue<Map> deadQueue = deadQueueMap.get(queueName);
                if (deadQueue == null) {
                    deadQueue = new LinkedBlockingQueue<>();
                    deadQueueMap.put(queueName, deadQueue);
                }
                deadQueue.offer(mapForAdd);
            } else {
                long id = Long.valueOf((String) mapForAdd.get("MessageID"));
                boolean isInsert = false;
                if (queue.size() == 0) {
                    list.add(mapForAdd);
                } else {
                    for (Map<String, String> queMap : queue) {
                        if (!isInsert && id < Long.valueOf(queMap.get("MessageID"))) {
                            list.add(mapForAdd);
                            isInsert = true;
                        }
                        list.add(queMap);
                    }
                    if (!isInsert) {
                        list.add(mapForAdd);
                    }
                }
                queue.clear();
                queue.addAll(list);
                System.out.println("Fire queueMap " + ":"+ queue);
            }
        }
        return result;
    }

    @Override
    public void run() {
//        while (true) {
//            long cuTime = System.currentTimeMillis();
//            System.out.println("run " + ":"+ (cuTime - (cuTime%1000)));
            List<Map> removeMapList = new ArrayList<>();
            for (Map<String, Object> map : queue) {
                if (timeoutProc(map)){
                    removeMapList.add(map);
                }
            }
            if (!removeMapList.isEmpty()) {
                for (Map<String, Object> map: removeMapList) {
                    this.remove(map);
                }
            }
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}

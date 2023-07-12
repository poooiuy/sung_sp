package sung04_exam2021_messageQueue_sub4_byYun;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ProcessHandlerNoTimer extends TimerTask {

    private static ProcessHandlerNoTimer inst;

    public static Map<String, Map<String, Object>> queueMap = new ConcurrentHashMap<>();

    public static Map<String, LinkedBlockingQueue<Map>> deadQueueMap = new ConcurrentHashMap<>();

    public static synchronized ProcessHandlerNoTimer getInstance() {
        if (inst == null) {
            inst = new ProcessHandlerNoTimer();
        }
        return inst;
    }

    public synchronized void receiveProc(String uri, JsonObject result) {
        String[] uris = uri.split("/");
        Map<String, Object> queueNameMap = queueMap.get(uris[uris.length - 1]);
        if (queueNameMap != null) {
            Map<String, Object> pollMap = null;
            long waitTime = (long) queueNameMap.get("WaitTime");
            LinkedBlockingQueue<Map> queue = (LinkedBlockingQueue<Map>)queueNameMap.get("Queue");
            pollMap = getPollMap(uris, queueNameMap, queue);
            if (pollMap == null) {
                if (waitTime == 0) {
                    result.addProperty("Result", "No Message");
                    return;
                } else {
                    long itrTime = 0;
                    while(itrTime < waitTime * 1000) {
                        pollMap = getPollMap(uris, queueNameMap, queue);
                        if (pollMap != null) {
                            break;
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        itrTime = itrTime + 100;
                        System.out.println("RECEIVE While" + ":" +itrTime);
                    }
                    if (pollMap == null) {
                        result.addProperty("Result", "No Message");
                    } else {
                        result.addProperty("Result", "OK");
                        result.addProperty("MessageID", (String) pollMap.get("MessageID"));
                        result.addProperty("Message", (String) pollMap.get("Message"));

                        System.out.println("RECEIVE queue" + ":" + ((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")));
                    }
                }
            } else {
                result.addProperty("Result", "OK");
                result.addProperty("MessageID", (String) pollMap.get("MessageID"));
                result.addProperty("Message", (String) pollMap.get("Message"));

                System.out.println("RECEIVE queue" + ":" + ((LinkedBlockingQueue<Map>)queueNameMap.get("Queue")));
            }
        } else {
            result.addProperty("Result", "No Message");
        }
    }

    private Map<String, Object> getPollMap(String[] uris, Map<String, Object> queueNameMap, LinkedBlockingQueue<Map> queue) {
        long cuTime = System.currentTimeMillis();
        Map<String, Object> pollMap = null;
        List<Map> removeList = new ArrayList<>();
        for (Map<String, Object> map: queue) {
            long sendTime = (long)map.get("SendTime");
            if (sendTime == 0L) {
                map.put("SendTime", (cuTime - (cuTime %1000)));
                pollMap = map;
                break;
            } else if ((long) queueNameMap.get("ProcessTimeout") != 0L && sendTime + ((long) queueNameMap.get("ProcessTimeout") * 1000) <= (cuTime - (cuTime %1000))) {
                System.out.println("RECEIVE map" + ":" +map);
                map.put("FailCount", (int)map.get("FailCount") + 1);
                if ((int)map.get("FailCount") <= (int) queueNameMap.get("MaxFailCount")) {
                    map.put("SendTime", (cuTime - (cuTime %1000)));
                    pollMap = map;
                    break;
                } else {
                    LinkedBlockingQueue<Map> deadQueue = deadQueueMap.get(uris[uris.length - 1]);
                    if (deadQueue == null) {
                        deadQueue = new LinkedBlockingQueue<>();
                        deadQueueMap.put(uris[uris.length - 1], deadQueue);
                    }
                    deadQueue.offer(map);
                    removeList.add(map);
                }
            }
        }
        if (!removeList.isEmpty()) {
            for (Map<String, Object> removeMap : removeList) {
                queue.remove(removeMap);
            }
        }
        return pollMap;
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
        Map<String, Object> queueNameMap = queueMap.get(uris[uris.length - 2]);
        LinkedBlockingQueue<Map> queue = (LinkedBlockingQueue<Map>) queueNameMap.get("Queue");
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
            msgMap.put("SendTime", 0L);
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
            queueNameMap.put("ProcessTimeout", Long.valueOf(String.valueOf(body.get("ProcessTimeout"))));
            queueNameMap.put("MaxFailCount", Integer.valueOf(String.valueOf(body.get("MaxFailCount"))));
            queueNameMap.put("WaitTime", Long.valueOf(String.valueOf(body.get("WaitTime"))));
            queueMap.put(uris[uris.length - 1], queueNameMap);

            result.addProperty("Result", "OK");
        }
    }

    public synchronized void failProc(String uri, JsonObject result) {
        String[] uris = uri.split("/");
        Map<String, Object> queueNameMap = queueMap.get(uris[uris.length - 2]);
        LinkedBlockingQueue<Map> queue = (LinkedBlockingQueue<Map>)queueNameMap.get("Queue");
        List<Map> list = new LinkedList<>();
        Map<String, Object> mapForAdd = null;
        for (Map<String, Object> map : queue) {
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

        if ((int)mapForAdd.get("FailCount") > (int)queueNameMap.get("MaxFailCount")) {
            LinkedBlockingQueue<Map> deadQueue = deadQueueMap.get(uris[uris.length - 2]);
            if (deadQueue == null) {
                deadQueue = new LinkedBlockingQueue<>();
                deadQueueMap.put(uris[uris.length - 2], deadQueue);
            }
            deadQueue.offer(mapForAdd);
            queue.remove(mapForAdd);
        } else {
            mapForAdd.put("SendTime", 0L);
        }
        System.out.println("FailProcess queueMap " + ":"+ queue);
        result.addProperty("Result", "OK");
    }

    @Override
    public void run() {
        long cuTime = System.currentTimeMillis();
        for (String queueName : queueMap.keySet()) {
            Map<String, Object> queueNameMap = queueMap.get(queueName);
            LinkedBlockingQueue<Map> queue = (LinkedBlockingQueue<Map>) queueNameMap.get("Queue");
            List<Map> removeList = new ArrayList<>();
            for (Map<String, Object> map: queue) {
                long sendTime = (long)map.get("SendTime");
                if (sendTime != 0L && (long) queueNameMap.get("ProcessTimeout") != 0L && (sendTime + ((long) queueNameMap.get("ProcessTimeout") * 1000)) <= (cuTime - (cuTime%1000))) {
                    map.put("FailCount", (int)map.get("FailCount") + 1);
                    System.out.println("Timeout queueMap " + ":"+ map + " " + "cuTime : "+(cuTime - (cuTime%1000)));
                    map.put("SendTime", 0L);
                }
                if ((int)map.get("FailCount") > (int)queueNameMap.get("MaxFailCount")) {
                    LinkedBlockingQueue<Map> deadQueue = deadQueueMap.get(queueName);
                    if (deadQueue == null) {
                        deadQueue = new LinkedBlockingQueue<>();
                        deadQueueMap.put(queueName, deadQueue);
                    }
                    deadQueue.offer(map);
                    removeList.add(map);
                }
            }
            if (!removeList.isEmpty()) {
                for (Map<String, Object> removeMap : removeList) {
                    queue.remove(removeMap);
                }
            }
        }
    }
}

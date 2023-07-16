package sung00_file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class FileWatcher {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		String dir = "./";
		WatchService service = FileSystems.getDefault().newWatchService();
		Path path = Paths.get(dir);
		path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		
		while(true) {
			WatchKey key = service.take();
			List<WatchEvent<?>> list = key.pollEvents();	//	이벤트 받을때까지 대기
			for(WatchEvent<?> event : list){
				Kind<?> kind = event.kind();
				Path pth = (Path) event.context();
				if(kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
					//	파일 생성 시 실행되는 코드
					System.out.println("생성: " + pth.getFileName());
				} else if(kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
					//	파일 삭제 시 실행되는 코드
					System.out.println("삭제: " + pth.getFileName());
				} else if(kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
					//	파일 수정 시 실행되는 코드
					System.out.println("수정: " + pth.getFileName());
				} else if(kind.equals(StandardWatchEventKinds.OVERFLOW)) {
					//	운영체제에서 이벤트가 소실되었거나 버려질 경우 실행되는 코드
					System.out.println("OVERFLOW");
				}
			}
			if(!key.reset())	break;	//	Key Reset (리셋을 해야 다시 돈다)
		}
		service.close();
	}

}

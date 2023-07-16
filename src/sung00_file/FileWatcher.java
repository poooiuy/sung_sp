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
			List<WatchEvent<?>> list = key.pollEvents();	//	�̺�Ʈ ���������� ���
			for(WatchEvent<?> event : list){
				Kind<?> kind = event.kind();
				Path pth = (Path) event.context();
				if(kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
					//	���� ���� �� ����Ǵ� �ڵ�
					System.out.println("����: " + pth.getFileName());
				} else if(kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
					//	���� ���� �� ����Ǵ� �ڵ�
					System.out.println("����: " + pth.getFileName());
				} else if(kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
					//	���� ���� �� ����Ǵ� �ڵ�
					System.out.println("����: " + pth.getFileName());
				} else if(kind.equals(StandardWatchEventKinds.OVERFLOW)) {
					//	�ü������ �̺�Ʈ�� �ҽǵǾ��ų� ������ ��� ����Ǵ� �ڵ�
					System.out.println("OVERFLOW");
				}
			}
			if(!key.reset())	break;	//	Key Reset (������ �ؾ� �ٽ� ����)
		}
		service.close();
	}

}

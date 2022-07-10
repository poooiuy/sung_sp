package sung00_execute_file;

public class ExecuteFileWithParameter {
	public static void main(String[] args) {

		try {
			System.out.println(System.getProperty("user.dir"));
//			Process p = Runtime.getRuntime().exec("exe .//src//sung00_execute_file//ALSee91.exe argument");
			
			ProcessBuilder pb = new ProcessBuilder("java -jar", ".//src//sung00_execute_file//runnableJar.jar", "argument");
			Process p = pb.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
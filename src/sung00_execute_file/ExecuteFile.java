package sung00_execute_file;

public class ExecuteFile {
	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();

		String file = ".//src//sung00_execute_file//runnableJar.jar";
		Process pro;

		try {
			System.out.println(System.getProperty("user.dir"));
			pro = rt.exec(file);
			pro.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
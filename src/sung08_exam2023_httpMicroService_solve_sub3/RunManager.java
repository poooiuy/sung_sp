package sung08_exam2023_httpMicroService_solve_sub3;

import sung08_exam2023_httpMicroService_solve_sub3.server.EngineServer;

public class RunManager {

	public static void main(String[] args) throws Exception {

		VariableManager.load();

		StateManager.load();

		EngineServer engineServer = new EngineServer();
		engineServer.start();
	}
}

package sung08_exam2023_httpMicroService_solve_sub5;

import sung08_exam2023_httpMicroService_solve_sub5.server.EngineServer;

public class RunManager {

	public static void main(String[] args) throws Exception {

		VariableManager.load();

		WorkflowManager.load();

		EngineServer workflowEngineServer = new EngineServer();
		workflowEngineServer.start();
	}
}

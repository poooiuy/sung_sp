package sung08_exam2023_httpMicroService_solve_sub4;

import sung08_exam2023_httpMicroService_solve_sub4.server.EngineServer;

public class RunManager {

	public static void main(String[] args) throws Exception {

		VariableManager.load();

		WorkflowManager.load();

		EngineServer workflowEngineServer = new EngineServer();
		workflowEngineServer.start();
	}
}

package sung08_exam2023_httpMicroService_solve_sub3;

public abstract class State {

	private String name;

	public State(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void run() throws Exception;

}

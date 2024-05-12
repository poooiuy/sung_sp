package sung08_exam2023_httpMicroService_solve_sub1;

public class State {

	private String name;
	private String type;
	private String url;

	public State(String name, String type, String url) {
		super();
		this.name = name;
		this.type = type;
		this.url = url;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}
}

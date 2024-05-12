package sung08_exam2023_httpMicroService_solve_sub2;

public class State {

	private String name;
	private String type;
	private String url;
	private String[] keys;

	public State(String name, String type, String url, String[] keys) {
		super();
		this.name = name;
		this.type = type;
		this.url = url;
		this.keys = keys;
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
	
	public String[] getKeys() {
		return keys;
	}
}

package sung00_gson;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GsonExample {
	
//	int º¯È¯
//	int count = (int) Math.round((double) jo.get("inputQueueCount"));

	public static void main(String[] args) {

		JsonElement jsonElement = JsonParser.parseString("{ \"key\":\"value\" }");
		System.out.println(jsonElement.toString());
		
		stringToObject();
		
		objectToString();

	}

	private static void stringToObject() {

		// Example for From Json to Object
		String jsonStr = "{'name' : 'augustine', 'address' : 'Seoul city', " + " 'level' : 'member'}";
		Gson gson = new Gson();
		Member member = gson.fromJson(jsonStr, Member.class);
		System.out.println("Name is == " + member.getName());
		System.out.println("Address is == " + member.getAddress());
		System.out.println("Level is == " + member.getLevel());

	}

	private static void objectToString() {

		Gson gson = new Gson();

		// Example for From Object to Json
		Member member2 = new Member();
		member2.setName("Rubin");
		member2.setAddress("NewYork");
		member2.setLevel("Manager");
		System.out.println("\n\n\n\n");
		System.out.println(gson.toJson(member2));

	}
	
	private static void test() {
		Gson gson = new Gson();
		JsonObject jObj = gson.fromJson("", JsonObject.class);
		
	}

}

class Member {
	String name;
	String address;
	String level;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
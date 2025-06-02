package sung00_gson;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonTest {
	
	public static void main(String[] args) {
		
		String line = "";
		//	1. File Read
		line = printFile("./src/main/java/gson/test/sung/object.json");
		//	2. Object 변환
//		Gson gson = new Gson();
//		gson = new GsonBuilder().registerTypeAdapter(Map.class, new MapDeserializer()).setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
//		TestObject o = gson.fromJson(line, TestObject.class);
//		int val = o.getVal();
//		System.out.println(val);
		
		//	3. Map 변환
		Map<String, Object> m = strJsonToHash(line);
		System.out.println(m.get("val"));
		System.out.println(m.get("val").getClass());
		int mapVal = Integer.parseInt( m.get("val").toString() );
		System.out.println(((ArrayList)m.get("arrayVal")).get(0));
		System.out.println(((ArrayList)m.get("arrayVal")).get(0).getClass());
		int mapInnerVal = Integer.parseInt(((ArrayList)m.get("arrayVal")).get(0).toString());
		
//		int mapInnerVal = ((ArrayList)m.get("mapVal")).get(0);
	}
		
	
	public static String printFile(String fileName) {
		String line = null;
		String lineTotal = "";

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				lineTotal = lineTotal + line;

			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineTotal;
	}
	
	public static Map<String, Object> strJsonToHash(String json) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Map.class, new MapDeserializer()).setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		return gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
	}

}

	


class TestObject{
	String name;
	int val;
	ArrayList<Integer> arrayVal;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public ArrayList getArrayVal() {
		return arrayVal;
	}
	public void setArrayVal(ArrayList arrayVal) {
		this.arrayVal = arrayVal;
	}
	
	
}

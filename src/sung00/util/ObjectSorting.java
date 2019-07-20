package sung00.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ObjectSorting {

public static void main(String[] args) throws NumberFormatException, IOException {
		
		String line = "";
		List<DataVo> list = new ArrayList<>();
		
		FileReader fileReader = new FileReader("./src/sung6/dataType/DS_Sample1.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		while((line = bufferedReader.readLine()) != null) {
			String[] data = line.split(" ");
			DataVo vo = new DataVo();
			vo.setName(data[0]);
			vo.setKorean(Integer.parseInt(data[1]));
			vo.setEnglish(Integer.parseInt(data[1]));
			vo.setMath(Integer.parseInt(data[1]));
			list.add(vo);
		}
		bufferedReader.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)
        {
        	String strInput = br.readLine();
        	if (strInput.equals("PRINT"))
        	{
        		Collections.sort(list, (g1, g2) -> g1.getName().compareTo(g2.getName()));
        	}
        	else if (strInput.equals("KOREAN"))
        	{
        		Collections.sort(list, (g1, g2) -> (g2.getKorean() - g1.getKorean()));
        	}
        	else if (strInput.equals("ENGLISH"))
        	{
        		Collections.sort(list, (g1, g2) -> (g2.getEnglish() - g1.getEnglish()));
        	}
        	else if (strInput.equals("MATH"))
        	{
        		Collections.sort(list, (g1, g2) -> (g2.getMath() - g1.getMath()));
        	}
        	else if (strInput.equals("QUIT"))
        	{
        		break;
        	}
        	
    		Iterator<DataVo> itr = list.iterator(); 
    		while (itr.hasNext()) {
    			DataVo val = itr.next();
    			System.out.println(String.format("%s %d %d %d",val.getName(), val.getKorean(), val.getEnglish(), val.getMath()));
    		}        	
        }  
		
	}
	
	

}


class DataVo {
	
	String name;
	int korean;
	int english;
	int math;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKorean() {
		return korean;
	}
	public void setKorean(int korean) {
		this.korean = korean;
	}
	public int getEnglish() {
		return english;
	}
	public void setEnglish(int english) {
		this.english = english;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	
}
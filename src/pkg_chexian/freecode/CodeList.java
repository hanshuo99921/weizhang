package pkg_chexian.freecode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CodeList {
	private List<Integer> list;
	private Integer[] userid = {1,2,3,4,6,8};
	public CodeList() {
		//list = new ArrayList(Arrays.asList(userid));
		list = new ArrayList<Integer>();
		Collections.addAll(list, userid);
	}
	public  List<String> genCodes(int num){
		List<String> results=new ArrayList<String>();
		int length = list.size();
		String charOrNum = null;
		Random random = new Random();
		String val = ""; 
		for (int i=0;i<num;i++) {
		  Collections.shuffle(list);
		  val = "";
		  for (int j = 0; j<length;j++) {
			   charOrNum = list.get(j) % 2 == 0 ? "num" : "char";
			   if("char".equalsIgnoreCase(charOrNum)) // ×Ö·û´®   
		        {   
		          val += (char) (97 + random.nextInt(26));   
		        }   
		        else if("num".equalsIgnoreCase(charOrNum)) // Êý×Ö   
		        {   
		          val += String.valueOf(random.nextInt(10));   
		        }   
		  }
		  if(results.contains(val)){
		        continue;
		      }else{
		        results.add(val);
		      }
		}
		return results;
	
	}
	public static void main(String[] args) {
		CodeList wx = new CodeList();
		List<String> results = wx.genCodes(5);
		for (String s :results) {
			System.out.println(s);
		}
	}
}

package pkg_chexian;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class Cls_readfile {
	public List<String> readTxtonly(String wjm) throws IOException, Cls_exception {
        wjm=wjm.replace(".xls", ".txt");
		List<String> list = new ArrayList<String>();
		java.io.File f  = null;
		BufferedReader br = null;
		try {
		 f = new java.io.File(wjm);
		 if(f.exists()){
		  br = new BufferedReader(new InputStreamReader(new FileInputStream(wjm),"GB2312"));			
		  String Line=br.readLine();
		  while(Line!=null){
			  if (!"".equals(Line.trim()))
				  list.add(Line.trim());
			        Line=br.readLine();//���ļ��м�����ȡһ������
			 }
		 br.close();//�ر�BufferedReader����
		 } else list.add("�ļ������ڡ�");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			f=null;
			if (br!=null)
				try {
					br.close();
				}catch (Exception e) {
					throw new Cls_exception(e.toString());
				}
				br = null;
		}		
		return list;
	}
}



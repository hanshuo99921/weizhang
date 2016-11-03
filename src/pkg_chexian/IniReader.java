package pkg_chexian;

/**
 * 此处插入类型说明。
 * 创建日期：(2011-6-7 11:14:23)
 * @author：Administrator
 inifile 
 [Section1]
 Key1=Value1
 Key2=Value2
 [Section2]
 Key=Value
 */
import java.util.*;
import java.io.*;


public class IniReader {
	public java.util.Hashtable iniHash;

	/**
	 * IniReader 构造子注解。
	 */
	public IniReader() {
		super();
	}

	public String getHashValue(String Section, String Key, String Inifile) throws Cls_exception {
		if (isEmpty())
			hashINI(Inifile);
		if (isEmpty())
			return "";
		String hashKey = Section + "." + Key;
		String Value = (String) iniHash.get(hashKey.toLowerCase());
		return (Value == null) ? "" : Value;

	}

	public void hashINI(String iniFilePath)throws Cls_exception {
		String Section = "", Key = "", Value = "";
		BufferedReader bufReader = null;
		String strLine = "";
		String hashKey = "";
		if (iniHash == null)
			iniHash = new Hashtable();
		if (!isEmpty())
			iniHash.clear();
		try {
			bufReader = new BufferedReader(new FileReader(
					iniFilePath));			
			int firstLeftSquareBrackets = 0, firstRightSquareBrackets = 0;
			while ((strLine = bufReader.readLine()) != null) {
				strLine = strLine.trim();
				firstLeftSquareBrackets = strLine.indexOf("[");
				firstRightSquareBrackets = strLine.indexOf("]");
				if (firstLeftSquareBrackets >= 0
						&& firstRightSquareBrackets >= 0
						&& firstLeftSquareBrackets < firstRightSquareBrackets) {
					Section = strLine.substring(firstLeftSquareBrackets + 1,
							firstRightSquareBrackets);
				} else {
					int index = 0;
					index = strLine.indexOf("=");
					Key = strLine.substring(0, index).trim();
					Value = strLine.substring(index + 1).trim();
					hashKey = Section + "." + Key;
					iniHash.put(hashKey.toLowerCase(), Value);
				}
			}
			bufReader.close();
		} catch (Exception e) {
			System.out.println("读取配置文件发生错误。" + e.getMessage());
		} finally {
			Section=null;Key=null;Value=null;
			strLine = null;hashKey = null;
			if (bufReader != null) {
				try {
					bufReader.close();
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}
			
		}
	}

	public boolean isEmpty() {
		if (iniHash == null)
			return true;
		try {
			return iniHash.isEmpty();
		} catch (NullPointerException e) {
			return true;
		}
	}
}
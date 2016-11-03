package pkg_chexian;

/**
 * �˴���������˵����
 * �������ڣ�(2011-5-26 16:49:42)
 * @author��Administrator
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Cls_jy {
/**
 * Cls_jy ������ע�⡣
 */
public Cls_jy() {
	super();
}

/**
 * �ڴ˴����뷽��˵����
 * �������ڣ�(2011-5-27 16:02:28)
 * @return java.lang.String
 * @param lshh java.lang.String
 * @param org_code java.lang.String
 * @exception pkg_dpiao.Cls_exception �쳣˵����
 * @exception java.net.UnknownHostException �쳣˵����
 * ����ֵ ��ͷ32λ ����122λ
 * ���� 32 ����ID�� 4 ������
 * 20 ����� 16 ��� 1 ������
 * 16 ������� 1 ����������
 * 32 �������
 */
public String zjgl_chx(String lshh, String org_code) throws Cls_exception, IOException {
	
	char c;
	String s ;
	String str =null;
	String serverResponse = null ;
	java.lang.String baotou="<<<<87  10031001                ";
	java.lang.String baoti="";
	
	//StringBuffer sbuff=new StringBuffer("                                1003                                                   ");

	StringBuffer sbuff1=new StringBuffer("                                1003");//����id �ӽ��״���32+4
	StringBuffer sbuff2=new StringBuffer("                    ");//�����̿��� ��20
	StringBuffer sbuff3=new StringBuffer("                 ");//�����ż����д���9+8
	StringBuffer sbuff4=new StringBuffer("              ");//�������ڼ�ʱ��8+6
	
	lshh=lshh.trim();
	org_code=org_code.trim();
	
	//sbuff.replace(0,lshh.length(),lshh);
	sbuff1.replace(0,lshh.length(),lshh);
	//sbuff.replace(56,(56+org_code.length()),org_code);
	sbuff3.replace(0,org_code.length(),org_code);
	java.text.DateFormat fm=new java.text.SimpleDateFormat("yyyyMMddhhmmss");
	//sbuff.replace(73,86,fm.format(new Date()));
	sbuff4.replace(0,14,fm.format(new Date()));
	//baoti=sbuff.toString();
	baoti=sbuff1.toString()+sbuff2.toString()+sbuff3.toString()+sbuff4.toString();
	
	str=baotou+baoti;

	Socket myClient =null;

	BufferedInputStream input =null;

	BufferedOutputStream output =null;

	DataOutputStream out=null;
	
	try
	{
	    //myClient = new Socket("10.154.2.66",3790);//����
	    myClient = new Socket("10.154.2.61",3790);//����
	    
		myClient.setSoTimeout(5000);

	    input = new BufferedInputStream(myClient.getInputStream(), 1024);
	    
	  	output = new BufferedOutputStream(myClient.getOutputStream(),1024);

	    ByteArrayOutputStream bytestream;
	    bytestream = new ByteArrayOutputStream(str.length());

	   
	    out = new DataOutputStream(bytestream);

	    for (int i=0; i<str.length(); i++)
		   out.write((byte) str.charAt(i));

	    output.write(bytestream.toByteArray(), 0, bytestream.size());
	    output.flush();

	    s = new String("");

	    
	    for (int k=0; k<(122+32-1);k++){
		   c=(char) input.read(); 
		   s = s + String.valueOf(c);
	    }

		//System.out.println(s);
	    
		if (s.substring(64,68).equals("0000"))
		{
	        serverResponse=s.substring(105,121);
		} else
		{
			serverResponse="-1";
		}

		//System.out.println(s.substring(105,121));
		
		myClient.close();

		
	} catch (IOException e) 
	       {
			   e.printStackTrace();
			   serverResponse="-1";
			} 
	finally 
	{
				   out.close();
				   input.close();
				   output.close(); 
				   myClient.close();

	}

	return serverResponse;
}
/**
 * ���������ǵ������򱾷�����ġ�������Ҫ�����Ŀۿ�׵Ļ������󡣲����������������
 * ����Ӧ��ʱʱ����ʱ����������׼ȷ֪�������Ƿ�ִ�н��׳ɹ���Ϊ���ⲻһ�µ��������������������Ҫ�򱾷������Զ�������
 * �������Ѿ��õ��ɹ�Ӧ�𣬵�������ֵ���������Ա��������ʱ����Ҫ�˹�����������˹�������Ӧ�ĵ��ճɹ��Ŀۿ�ף����ṩ�����˹������Ĺ��ܡ�
 * �������ڣ�(2011-5-29 11:39:11)
 * @return boolean
 * @param lshh java.lang.String
 * @param chzhlshh java.lang.String
 * @param org_code java.lang.String
 * @param jyje java.lang.String
 * @param jydate java.lang.String
 * @exception pkg_dpiao.Cls_exception �쳣˵����
 * @exception java.net.UnknownHostException �쳣˵����
 * lshh ָ�¼�¼��ˮ��
 * chzhlshh ָԭ��¼��ˮ��
 * org_code ָԭ������������
 * jyje ��ȷ����
 * jydate 8λԭ�����Ľ�������
 * ���ذ�ͷ 32λ ����135λ
 * 32 ���׺�  32 ���������׺�
 * 9 �����ĳ�����ˮ�� 4 ������0000�ɹ�
 * 20 �̿��� 32 �������
 */    
public  String  zjgl_chzh(String lshh, String chzhlshh, String org_code, String jyje, String jydate) throws Cls_exception, IOException {

	char c;
	String gbs = null ;
	String s ;
	String str =null;
	String serverResponse = "false" ;
	java.lang.String baotou="<<<<135 10051001                ";
	java.lang.String baoti="";
	
	//StringBuffer sbuff=new StringBuffer("                                1005                                                                                                   ");
	
	StringBuffer sbuff1=new StringBuffer("                                1005");//����id �ӽ��״���32+4
	StringBuffer sbuff2=new StringBuffer("                    ");//�����̿��� ��20
	StringBuffer sbuff3=new StringBuffer("         ");//ԭ���׻�����
	StringBuffer sbuff4=new StringBuffer("                                ");//���������׺�
	StringBuffer sbuff5=new StringBuffer("                ");//���׽��16
	StringBuffer sbuff6=new StringBuffer("        ");//��������������8
	StringBuffer sbuff7=new StringBuffer("              ");//ƽ̨���ڼ�ʱ��8+6
	
	lshh=lshh.trim();
	chzhlshh=chzhlshh.trim();
	org_code=org_code.trim();
	jyje=jyje.trim();
	jydate=jydate.trim();
	
	//sbuff.replace(0,lshh.length(),lshh);
	sbuff1.replace(0,lshh.length(),lshh);
	//sbuff.replace(65,(65+chzhlshh.length()),chzhlshh);
	sbuff4.replace(0,chzhlshh.length(),chzhlshh);
	//sbuff.replace(56,(56+org_code.length()),org_code);
	sbuff3.replace(0,org_code.length(),org_code);
	//sbuff.replace(97,(97+jyje.length()),jyje);
	sbuff5.replace(0,jyje.length(),jyje);
	//sbuff.replace(113,(113+jydate.length()),jydate);
	sbuff6.replace(0,jydate.length(),jydate);
	java.text.DateFormat fm=new java.text.SimpleDateFormat("yyyyMMddhhmmss");
	//sbuff.replace(121,135,fm.format(new Date()));
	sbuff7.replace(0,14,fm.format(new Date()));
	//baoti=sbuff.toString();
	baoti=sbuff1.toString()+sbuff2.toString()+sbuff3.toString()+sbuff4.toString()+sbuff5.toString()+sbuff6.toString()+sbuff7.toString();
	
	str=baotou+baoti;
	//System.out.println(str);
	Socket myClient =null;

	BufferedInputStream input =null;

	BufferedOutputStream output =null;

	DataOutputStream out=null;
	
	try
	{
		//myClient = new Socket("10.154.2.66",3790);//����
		myClient = new Socket("10.154.2.61",3790);//����
		myClient.setSoTimeout(5000);

	    input = new BufferedInputStream(myClient.getInputStream(), 1024);
	    
	  	output = new BufferedOutputStream(myClient.getOutputStream(),1024);

	    ByteArrayOutputStream bytestream;
	    bytestream = new ByteArrayOutputStream(str.length());

	   
	    out = new DataOutputStream(bytestream);

	    for (int i=0; i<str.length(); i++)
		   out.write((byte) str.charAt(i));

	    output.write(bytestream.toByteArray(), 0, bytestream.size());
	    output.flush();

	    s = new String("");

	    
	    for (int k=0; k<(129+32-1);k++){
		   c=(char) input.read(); 
		   s = s + String.valueOf(c);
	    }
	    gbs =new String(s.getBytes("ISO-8859-1"),"GB2312");
	   // System.out.println(s);

		if (s.substring(105,109).equals("0000"))
		{
	        serverResponse="true";
		}else
		{   errinfo=new String(gbs.substring(113));
			serverResponse="false";
		}

		// System.out.println(s.substring(96,106));
		
		myClient.close();

		
	} 
	 catch(SocketTimeoutException e)
	 {
		 e.printStackTrace();
		 serverResponse="timeout";
		 return serverResponse;
	 }
	catch (IOException e) 
	       {
			   e.printStackTrace();
			   serverResponse="false";
			   
			} 
	finally 
	{
				   out.close();
				   input.close();
				   output.close(); 
				   myClient.close();
	}


	
	return serverResponse;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2011-5-29 11:11:25)
 * @return boolean
 * @param lshh java.lang.String
 * @param org_code java.lang.String
 * @exception pkg_dpiao.Cls_exception �쳣˵����
 * @exception java.net.UnknownHostException �쳣˵����
 * lshh ָ�Ľ������
 * org_code ָ���̻���������
 * jyje ָ�ۿ��� *100 ��ȷ����
 * jydm ��Ʊ�Ĵ���
 * ���ذ�ͷ 32 λ ����145λ
 * �ۿ�ɹ�����true ���ɹ�����false;
 */          
public  String zjgl_kk(String lshh, String org_code,String jyje,String jydm) throws Cls_exception, IOException {

	
	char c;
	String s ;
	String gbs = null ;
	String str =null;	
	String serverResponse = null ;
	java.lang.String baotou="<<<<145 10041001                ";
	java.lang.String baoti="";
	
	//StringBuffer sbuff=new StringBuffer("                                1004                                                                                                             ");

	StringBuffer sbuff1=new StringBuffer("                                1004");//����id �ӽ��״���32+4
	StringBuffer sbuff2=new StringBuffer("                    ");//�����̿��� ��20
	StringBuffer sbuff3=new StringBuffer("                 ");//�����ż����д���9+8
	StringBuffer sbuff4=new StringBuffer("                ");//���׽��16
	StringBuffer sbuff5=new StringBuffer("          ");//��Ʒ����ӽ��뷽ʽ10
	StringBuffer sbuff6=new StringBuffer("              ");//�������ڼ�ʱ��8+6
	StringBuffer sbuff7=new StringBuffer("                                ");//32λ����
	
	lshh=lshh.trim();
	org_code=org_code.trim();
	jyje=jyje.trim();
	jydm=jydm.trim();
	
	//sbuff.replace(0,lshh.length(),lshh);
	sbuff1.replace(0,lshh.length(),lshh);
	//sbuff.replace(56,(56+org_code.length()),org_code);
	sbuff3.replace(0,org_code.length(),org_code);
	//sbuff.replace(73,(73+jyje.length()),jyje);
	sbuff4.replace(0,jyje.length(),jyje);
	//sbuff.replace(89,(89+jydm.length()),jydm);
	sbuff5.replace(0,jydm.length(),jydm);
	java.text.DateFormat fm=new java.text.SimpleDateFormat("yyyyMMddhhmmss");
	//sbuff.replace(99,113,fm.format(new Date()));
	sbuff6.replace(0,14,fm.format(new Date()));
	//baoti=sbuff.toString();
	baoti=sbuff1.toString()+sbuff2.toString()+sbuff3.toString()+sbuff4.toString()+sbuff5.toString()+sbuff6.toString()+sbuff7.toString();
	
	str=baotou+baoti;

	Socket myClient =null;

	BufferedInputStream input =null;

	BufferedOutputStream output =null;

	DataOutputStream out=null;
	
	try
	{
		//myClient = new Socket("10.154.2.66",3790);//����
		myClient = new Socket("10.154.2.61",3790);//����
		
		myClient.setSoTimeout(5000);

	    input = new BufferedInputStream(myClient.getInputStream(), 1024);
	    
	  	output = new BufferedOutputStream(myClient.getOutputStream(),1024);

	    ByteArrayOutputStream bytestream;
	    bytestream = new ByteArrayOutputStream(str.length());

	   
	    out = new DataOutputStream(bytestream);

	    for (int i=0; i<str.length(); i++)
		   out.write((byte) str.charAt(i));

	    output.write(bytestream.toByteArray(), 0, bytestream.size());
	    output.flush();

	    s = new String("");

	    
	    for (int k=0; k<(113+32-1);k++){
		   c=(char) input.read(); 
		   s = s + String.valueOf(c);
	    }
		gbs =new String(s.getBytes("ISO-8859-1"),"GB2312");
	    //System.out.println(gbs);

		if (s.substring(64,68).equals("0000"))
		{
	        serverResponse=s.substring(84,93);
		} else errinfo=new String(gbs.substring(113));

		
		//System.out.println(s.substring(84,93));
		myClient.close();

     }
	 catch(SocketTimeoutException e)
	 {
		 e.printStackTrace();
		 serverResponse="timeout";
		 return serverResponse;
	 }
     catch(IOException e)
     {
         e.printStackTrace();
     }
     finally
     {
                 out.close();
                 input.close();
                 output.close();
                 myClient.close();
     }
     return serverResponse;
    
}
public  java.lang.String errinfo=new java.lang.String();/**
 * �˴����뷽��˵����
 * �������ڣ�(2011-11-23 9:12:00)
 * @return java.lang.String
 */
public java.lang.String getErrinfo() {
	return errinfo;
}/**
 * �˴����뷽��˵����
 * �������ڣ�(2011-11-23 9:12:00)
 * @param newErrinfo java.lang.String
 */
public void setErrinfo(java.lang.String newErrinfo) {
	errinfo = newErrinfo;
}}
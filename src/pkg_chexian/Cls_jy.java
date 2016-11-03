package pkg_chexian;

/**
 * 此处插入类型说明。
 * 创建日期：(2011-5-26 16:49:42)
 * @author：Administrator
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Cls_jy {
/**
 * Cls_jy 构造子注解。
 */
public Cls_jy() {
	super();
}

/**
 * 在此处插入方法说明。
 * 创建日期：(2011-5-27 16:02:28)
 * @return java.lang.String
 * @param lshh java.lang.String
 * @param org_code java.lang.String
 * @exception pkg_dpiao.Cls_exception 异常说明。
 * @exception java.net.UnknownHostException 异常说明。
 * 返回值 包头32位 包体122位
 * 包体 32 交易ID号 4 返回码
 * 20 储蓄卡号 16 余额 1 余额符号
 * 16 可用余额 1 可用余额符号
 * 32 结果描述
 */
public String zjgl_chx(String lshh, String org_code) throws Cls_exception, IOException {
	
	char c;
	String s ;
	String str =null;
	String serverResponse = null ;
	java.lang.String baotou="<<<<87  10031001                ";
	java.lang.String baoti="";
	
	//StringBuffer sbuff=new StringBuffer("                                1003                                                   ");

	StringBuffer sbuff1=new StringBuffer("                                1003");//交易id 加交易代码32+4
	StringBuffer sbuff2=new StringBuffer("                    ");//储蓄绿卡号 空20
	StringBuffer sbuff3=new StringBuffer("                 ");//机构号加银行代码9+8
	StringBuffer sbuff4=new StringBuffer("              ");//请求日期加时间8+6
	
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
	    //myClient = new Socket("10.154.2.66",3790);//测试
	    myClient = new Socket("10.154.2.61",3790);//生产
	    
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
 * 冲正交易是第三方向本方发起的、对于需要冲正的扣款交易的回退请求。产生冲正的情况包括
 * 本方应答超时时，此时第三方不能准确知道本方是否执行交易成功，为避免不一致的情况发生，第三方就需要向本方发起自动冲正；
 * 第三方已经得到成功应答，但如果发现第三方操作员操作错误时就需要人工发起冲正，人工冲正对应的当日成功的扣款交易，不提供隔日人工冲正的功能。
 * 创建日期：(2011-5-29 11:39:11)
 * @return boolean
 * @param lshh java.lang.String
 * @param chzhlshh java.lang.String
 * @param org_code java.lang.String
 * @param jyje java.lang.String
 * @param jydate java.lang.String
 * @exception pkg_dpiao.Cls_exception 异常说明。
 * @exception java.net.UnknownHostException 异常说明。
 * lshh 指新记录流水号
 * chzhlshh 指原记录流水号
 * org_code 指原订单机构号码
 * jyje 精确到分
 * jydate 8位原订单的交易日期
 * 返回包头 32位 包体135位
 * 32 交易号  32 被充正交易号
 * 9 本方的冲正流水号 4 返回码0000成功
 * 20 绿卡号 32 结果描述
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
	
	StringBuffer sbuff1=new StringBuffer("                                1005");//交易id 加交易代码32+4
	StringBuffer sbuff2=new StringBuffer("                    ");//储蓄绿卡号 空20
	StringBuffer sbuff3=new StringBuffer("         ");//原交易机构号
	StringBuffer sbuff4=new StringBuffer("                                ");//被冲正交易号
	StringBuffer sbuff5=new StringBuffer("                ");//交易金额16
	StringBuffer sbuff6=new StringBuffer("        ");//被冲正交易日期8
	StringBuffer sbuff7=new StringBuffer("              ");//平台日期加时间8+6
	
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
		//myClient = new Socket("10.154.2.66",3790);//测试
		myClient = new Socket("10.154.2.61",3790);//生产
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
 * 此处插入方法说明。
 * 创建日期：(2011-5-29 11:11:25)
 * @return boolean
 * @param lshh java.lang.String
 * @param org_code java.lang.String
 * @exception pkg_dpiao.Cls_exception 异常说明。
 * @exception java.net.UnknownHostException 异常说明。
 * lshh 指的交易序号
 * org_code 指的商户机构号码
 * jyje 指扣款金额 *100 精确到分
 * jydm 火车票的代码
 * 返回包头 32 位 包体145位
 * 扣款成功返回true 不成功返回false;
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

	StringBuffer sbuff1=new StringBuffer("                                1004");//交易id 加交易代码32+4
	StringBuffer sbuff2=new StringBuffer("                    ");//储蓄绿卡号 空20
	StringBuffer sbuff3=new StringBuffer("                 ");//机构号加银行代码9+8
	StringBuffer sbuff4=new StringBuffer("                ");//交易金额16
	StringBuffer sbuff5=new StringBuffer("          ");//产品代码加接入方式10
	StringBuffer sbuff6=new StringBuffer("              ");//请求日期加时间8+6
	StringBuffer sbuff7=new StringBuffer("                                ");//32位空着
	
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
		//myClient = new Socket("10.154.2.66",3790);//测试
		myClient = new Socket("10.154.2.61",3790);//生产
		
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
 * 此处插入方法说明。
 * 创建日期：(2011-11-23 9:12:00)
 * @return java.lang.String
 */
public java.lang.String getErrinfo() {
	return errinfo;
}/**
 * 此处插入方法说明。
 * 创建日期：(2011-11-23 9:12:00)
 * @param newErrinfo java.lang.String
 */
public void setErrinfo(java.lang.String newErrinfo) {
	errinfo = newErrinfo;
}}
package pkg_chexian;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;
/**
 * @author Administrator
 *
 */
public class Wzchx_chx {
	String url3="http://222.134.129.34:10016/ziboZzjfWeb/bhl_jdc_write_A_Photo.action";
	String url4="http://222.134.129.34:10016/ziboZzjfWeb/xxcx_drvPersonQuery_1.action";
	String url5="http://222.134.129.34:10016/ziboZzjfWeb/xxcx_vehQuery.action";
	String url6="http://222.134.129.34:10016/ziboZzjfWeb/xxcx_vehQuery.action";
	String url7="http://222.134.129.34:10016/ziboZzjfWeb/jsp/zb_xxcx/view_sfzxx.jsp?flag=jdc";
	String url8="http://222.134.129.34:10016/ziboZzjfWeb/jsp/zb_wdjdc/view_sfzxx.jsp";
	String url9="http://222.134.129.34:10016/ziboZzjfWeb/bhl_jdc_write_A_Photo.action";	
	String url10="http://222.134.129.34:10016/ziboZzjfWeb/bhl_jdc_clsbdhQuery.action";
	String url11="http://222.134.129.34:10016/ziboZzjfWeb/bhl_jdc_clsbdhQuery.action";
	String url12="http://222.134.129.34:10016/ziboZzjfWeb/jsp/zb_wdjsz/wdjsz_showSfzxx.jsp";
	String url13="http://222.134.129.34:10016/ziboZzjfWeb/jszSfzPhotoWrite.action";
	String url14="http://222.134.129.34:10016/ziboZzjfWeb/nextPage.action";
	String url15="http://218.58.65.23/select/WZQuery.asp";//青岛
    public static void main(String[] args)
    {
          try {
        	Wzchx_chx reg = new Wzchx_chx();
        	System.out.println(reg.jshzh_img_zbga(""));
			//reg.regest9();
			//reg.fsdux();
        	Calendar cal = Calendar.getInstance();
        	   int t=cal.get(Calendar.DAY_OF_WEEK);
        	   System.out.println(t);
        	//int day=cal.get(Calendar.DATE);//获取日
        	//System.out.println(day);
        	//  String m = getmch("C:\\Documents and Settings\\435");
        	 // System.out.println(m);
			//String a="-1";
			//System.out.println(Long.parseLong(a));
			//System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        	  String aa="2013-01-10";
             System.out.println(Qmhz(aa));
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      }
	public static boolean Qmhz(String rq) {
		boolean tag = false;
		SimpleDateFormat qf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Calendar hrq = Calendar.getInstance();
		Calendar qrq = Calendar.getInstance();
		hrq.add(Calendar.DAY_OF_YEAR, 90);//90天后
		qrq.add(Calendar.YEAR, -1);//1年前日起
		try {
			cal.setTime(qf.parse(rq));
			if (cal.after(qrq)&&cal.before(hrq)) tag = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tag;
	}
    public boolean fsdux() throws Exception{
    	String surl="http://123.233.245.237:8080/ema/person/SendSms";
    	//String surl="http://10.154.14.4:8080/ema/person/GetBalance";
    	//String send_content=URLEncoder.encode(, "utf-8");//发送内容
    	String send_content=new String("测试短信".getBytes("GB2312"),"iso-8859-1");
    	boolean tag = false;
    	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    	    nvps.add(new BasicNameValuePair("EAccount", "3703"));
	        nvps.add(new BasicNameValuePair("PAccount", "wztx"));	     
	        nvps.add(new BasicNameValuePair("Password", "wztx1234"));
	        nvps.add(new BasicNameValuePair("Phone", "13853308806"));
	        nvps.add(new BasicNameValuePair("Content", send_content));          
	        nvps.add(new BasicNameValuePair("SubCode", "01"));          
	        nvps.add(new BasicNameValuePair("ProgramID", "1"));
	        nvps.add(new BasicNameValuePair("Priority", "9"));  	        
	        
	        
	    Getclient getconn = new Getclient();
	    HttpResponse response = getconn.get(surl, nvps);
	    HttpEntity entity = response.getEntity();
	    if (entity != null) { 
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			StringBuffer content = new StringBuffer();
			for (String line; (line = reader.readLine()) != null;) {
				content.append(line + "\r\n");
			}
			Document doc = Jsoup.parse(content.toString(),"", new Parser(new XmlTreeBuilder()));
            Elements  eles = doc.select("response");
            System.out.println(eles.text());
	    }
 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }
    	return tag;
    }
    public  boolean regest4() throws Exception { //驾驶人信息查询
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
       // nvps.add(new BasicNameValuePair("sfzmhm", "370303197710211023"));
        nvps.add(new BasicNameValuePair("drvPerson.sfzmhm", "370303197710211023"));
        nvps.add(new BasicNameValuePair("btn03", "下一步"));
	        HttpResponse response = Zizhuclient.httppost(url4, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   				System.out.println(content.toString());
	   			/*	Elements cxJg = doc.select("#clwfxx");
	   			 if (cxJg.size()==1) {
	   			    Elements links = cxJg.select("tr:contains(未处理)");
	   			    System.out.println(cxJg.toString());
	   			    }
	   			    for (Element link : links) {
					   Elements sj = link.getElementsByTag("td");
					   for (int i=0;i<sj.size();i++)
					    	System.out.println(sj.get(i).text());
					    System.out.println(sj.get(4).text().replace(".0", ""));
					    System.out.println(sj.get(4).text().substring(0,16));
	   			    }*/ 
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	} 
    public  boolean regest5() throws Exception { //驾驶人信息查询
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
       // nvps.add(new BasicNameValuePair("sfzmhm", "370303197710211023"));
        nvps.add(new BasicNameValuePair("drvPerson.sfzmhm", "370303197710211023"));
        nvps.add(new BasicNameValuePair("btn03", "下一步"));
	        HttpResponse response = Zizhuclient.httppost(url5, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString()); 				
	   				Elements cxJg = doc.select("#sfzxx");
	   				Elements links = cxJg.select("td[class=tabright]");
	   				System.out.println(cxJg.toString());
	   			/* if (cxJg.size()==1) {
	   			    Elements links = cxJg.select("tr:contains(未处理)");
	   			    System.out.println(cxJg.toString());
	   			    }
	   			    for (Element link : links) {
					   Elements sj = link.getElementsByTag("td");
					   for (int i=0;i<sj.size();i++)
					    	System.out.println(sj.get(i).text());
					    System.out.println(sj.get(4).text().replace(".0", ""));
					    System.out.println(sj.get(4).text().substring(0,16));
	   			    }*/ 
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	}
    public  boolean regest7() throws Exception { //驾驶人信息查询
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("csrq", "2012-01-01"));
        nvps.add(new BasicNameValuePair("xm", "姓名"));
        nvps.add(new BasicNameValuePair("sfzh", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("sfzmhm", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("base64", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("dzjg", "淄博公安"));
        nvps.add(new BasicNameValuePair("qs", "2012.01.01—2013.02.02"));
        nvps.add(new BasicNameValuePair("btn2", "下一步"));
        nvps.add(new BasicNameValuePair("ywbh", "null")); 
	        HttpResponse response = Zizhuclient.httppost(url7, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   				System.out.println(doc.toString());
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	} 
    public  boolean regest8() throws Exception { //行驶证
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("csrq", "2012-01-01"));
        nvps.add(new BasicNameValuePair("xm", "姓名"));
        nvps.add(new BasicNameValuePair("sfzh", "370303197606271712")); 
        nvps.add(new BasicNameValuePair("sfzmhm", "370303197606271712")); 
        nvps.add(new BasicNameValuePair("base64", "370303197606271712")); 
        nvps.add(new BasicNameValuePair("dzjg", "淄博公安"));
        nvps.add(new BasicNameValuePair("qs", "2012.01.01—2013.02.02"));
        nvps.add(new BasicNameValuePair("btn2", "下一步"));
        nvps.add(new BasicNameValuePair("ywbh", "1002")); 
	        HttpResponse response = Zizhuclient.httppost(url8, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   				System.out.println(doc.toString());
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	}
    public  boolean regest9() throws Exception { //行驶证2
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("drvPerson.sfzmhm", "370303196908276637")); 
        nvps.add(new BasicNameValuePair("photo.sfzmhm", "370303196908276637")); 
        nvps.add(new BasicNameValuePair("photo.tpBase64", "370303196908276637")); 
        nvps.add(new BasicNameValuePair("btn03", "下一步"));
	        HttpResponse response = Zizhuclient.httppost(url9, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   				System.out.println(doc.toString());
	   			     Elements cxJg = doc.select("#sfzxx");
	   				//System.out.println(cxJg.select("[id*=hpzl]").val());
	   				System.out.println(cxJg.toString());
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	} 
    public  boolean regest10() throws Exception { //行驶证2
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("hphm", "CXY677")); 
        nvps.add(new BasicNameValuePair("hpzl", "02")); 
        nvps.add(new BasicNameValuePair("cllx", "K31")); 
        nvps.add(new BasicNameValuePair("glbm", "370300000400")); 
        nvps.add(new BasicNameValuePair("xzqh", "370300"));
        nvps.add(new BasicNameValuePair("clxh", "CC6460RM01"));
        nvps.add(new BasicNameValuePair("sfzmmc", "A"));
        nvps.add(new BasicNameValuePair("sfzmhm", "370303196908276637"));
        nvps.add(new BasicNameValuePair("clsbdh", "LGWEF4A57DF262209"));
        nvps.add(new BasicNameValuePair("zt", "A"));
        nvps.add(new BasicNameValuePair("xm", "张宜山"));
        nvps.add(new BasicNameValuePair("sfhm", ""));
        nvps.add(new BasicNameValuePair("lsh", "2013123015383225211"));
        nvps.add(new BasicNameValuePair("firstGo", "0"));
	        HttpResponse response = Zizhuclient.httppost(url10, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   				System.out.println(doc.toString());
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	} 
    public  boolean regest11() throws Exception { //行驶证2
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("vehicle.hphm", "CYH319")); 
        nvps.add(new BasicNameValuePair("vehicle.hphm", "02"));
        
        nvps.add(new BasicNameValuePair("clsbdh", "2209"));
        nvps.add(new BasicNameValuePair("isFirst", "1"));
        nvps.add(new BasicNameValuePair("lsh", "2013123015383225211"));
        nvps.add(new BasicNameValuePair("firstGo", "1"));
	        HttpResponse response = Zizhuclient.httppost(url11, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   				System.out.println(doc.toString());
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	} 
	private static String getmch(String mch) {
		 int size=mch.length();
		 int index = mch.lastIndexOf("\\");
		 //if (index<size) return mch.substring(size);
		 return mch.substring(index + 1);
	}
    public static boolean isNumeric(String str){ 
	    	for (int i = str.length();--i>=0;){   
	    	   if (!Character.isDigit(str.charAt(i))){ 
	    	    return false; 
	    	   } 
	    	} 
	    	return true; 
	    	} 

    public  boolean regest12() throws Exception { //驾驶证信息查询
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("csrq", "2012-01-01"));
        nvps.add(new BasicNameValuePair("xm", "姓名"));
        nvps.add(new BasicNameValuePair("sfzh", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("sfzmhm", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("base64", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("dzjg", "淄博公安"));
        nvps.add(new BasicNameValuePair("qs", "2012.01.01—2013.02.02"));
        nvps.add(new BasicNameValuePair("btn2", "下一步"));
        nvps.add(new BasicNameValuePair("ywbh", "null")); 
	        HttpResponse response = Zizhuclient.httppost(url12, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   				System.out.println(doc.toString());
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	}
    public  boolean regest13() throws Exception { //驾驶证2
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ywbh", "")); 
        nvps.add(new BasicNameValuePair("photo.sfzmhm", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("photo.tpBase64", "3703031977102110237")); 
	        HttpResponse response = Zizhuclient.httppost(url13, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   			     Elements cxJg = doc.select("#sfzxx");
	   				//System.out.println(cxJg.select("[id*=hpzl]").val());
	   				System.out.println(cxJg.toString());
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	} 
    public  boolean regest14() throws Exception { //驾驶证3
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("jgSubInfo.ywbh", "")); 
        nvps.add(new BasicNameValuePair("jgSubInfo.sfzmhm", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("jgSubInfo.sfzmmc", "A")); 
        nvps.add(new BasicNameValuePair("jgSubInfo.fzjg", "鲁C"));
        nvps.add(new BasicNameValuePair("jgSubInfo.gmsl", "1"));
        nvps.add(new BasicNameValuePair("jgSubInfo.xm", "杨姝妹"));
        nvps.add(new BasicNameValuePair("jgSubInfo.lsh", "2014011010580202926"));

	        HttpResponse response = Zizhuclient.httppost(url14, nvps);   
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) {   
	           	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
	   			   StringBuffer content = new StringBuffer();
	   				for (String line; (line = reader.readLine()) != null;) {
	   					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
	   				    } 
	   				Document doc = Jsoup.parse(content.toString());
	   			     Elements cxJg = doc.select("#sfzxx");
	   				//System.out.println(cxJg.select("[id*=hpzl]").val());
	   				System.out.println(cxJg.toString());
	   		}
	 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }   
		return tag;
	} 
    public  String jshzh_img_zbga(String jgh) throws Exception { 
		String str="未找到数据",cooktmp="";
		String  filepath=Zbga_yzh.class.getResource("").toString();
		//filepath=filepath.substring(0,filepath.indexOf("WEB-INF"));
		//String wj=filepath+"yzh/"+jgh+".gif";
		//wj=wj.substring(5);
		HttpEntity entity = null;

		DefaultHttpClient client = new DefaultHttpClient();  	
		client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
		client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 
		HttpGet httpget = new HttpGet(url15); 
      try {
        HttpResponse response = client.execute(httpget);   
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        	str="网站请求失败。";
            throw new RuntimeException("请求失败");
        }
        entity = response.getEntity(); 
        if (entity != null) {   
        	List<Cookie> cookies=client.getCookieStore().getCookies();
        	for (Cookie cookie : cookies) { 
        		cooktmp=cooktmp+cookie.getName()+"|"+cookie.getValue();
        		
        	}
        
        	if (!"".equals(cooktmp)) str=cooktmp;
        	//InputStream  inputstream= entity.getContent();
			//OutputStream out = new FileOutputStream(wj);
			//byte[] buf = new byte[1024];
			//int len;
			//while ((len = inputstream.read(buf)) > 0) {
			//   out.write(buf, 0, len);
			 //    }
			//out.close();
 	         } 
      } catch (RuntimeException e) {
    	//  log.error(e.toString());
      } catch (Exception e) { // JSon解析出错
    	//  log.error(e.toString());
      } finally {
    	 	    if (entity != null)   
 	             entity.consumeContent(); 
      }
      return str;
	}   


}

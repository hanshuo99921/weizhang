package pkg_chexian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;
/**
 * @author Administrator
 *
 */
public class Dunxin {
	private static Logger log = Logger.getLogger(Dunxin.class);
	public String SendXx(String hm,String nr,String citycode) throws Exception{
		   String wj="/opt/weblogic/user_projects/domains/base_domain/servers/bmzww/chexian/sendmsg.ini";
		  //String wj="C:/jboss6/server/default/deploy/chexian.war/sendmsg.ini";
		   //String wj="/oracle/weblogic/user_projects/domains/base_domain/servers/AdminServer/uploads/chexian/sendmsg.ini";
		   long a=0;
		   String rets="";
		   HttpEntity entity = null;
		   IniReader rd = new IniReader();
		   String st=rd.getHashValue(citycode,"IP",wj);
		   HttpClient httpclient = Hclient.getHttpClient();
			if (!st.equals("")) {
		   String url = "http://"+st+"/ema/person/SendSms";
		   List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		       nvps.add(new BasicNameValuePair("EAccount", rd.getHashValue(citycode,"EAccount",wj)));
	           nvps.add(new BasicNameValuePair("PAccount", rd.getHashValue(citycode,"PAccount",wj)));	     
	           nvps.add(new BasicNameValuePair("Password", rd.getHashValue(citycode,"Password",wj)));
	           nvps.add(new BasicNameValuePair("Phone", hm));
	           nvps.add(new BasicNameValuePair("Content", new String(nr.getBytes("GBK"),"iso-8859-1")));          
	           nvps.add(new BasicNameValuePair("SubCode", rd.getHashValue(citycode,"SubCode",wj)));          
	           nvps.add(new BasicNameValuePair("ProgramID", rd.getHashValue(citycode,"ProgramID",wj)));
	           nvps.add(new BasicNameValuePair("Priority", rd.getHashValue(citycode,"Priority",wj)));  
	           
	           String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
	         //log.error(url+"?"+str) ;
		      try {
		    	    HttpGet httpget = new HttpGet(url+"?"+str);
		            HttpResponse response = httpclient.execute(httpget);
		           if (response == null) {
		        	   log.error("短信请求失败，请检查线路。");
		        	   rets="短信请求失败，请检查线路。";
		           } else {
		               if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		        	        log.error("短信请求失败。");
		        	        rets="短信请求失败。";
		                    throw new RuntimeException("请求失败");   
		                }
		                entity = response.getEntity(); 
		                if (entity != null) {   
		           	         BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		   			         StringBuffer content = new StringBuffer();
		   			      	 for (String line; (line = reader.readLine()) != null;) {
		   				    	content.append(line + "\r\n");
		   				     } 
		   				     reader.close();
		   			         Document doc = Jsoup.parse(content.toString(),"", new Parser(new XmlTreeBuilder()));
		   	                 Elements  eles = doc.select("response");
		   	                 if (eles.text()!=null)
		    			          a=Long.parseLong(eles.text());   
		   	                 if (a>0)
		   	                       rets="短信发送成功。";
		   	                  else if (a<0) rets="信发送失败,失败原因："+dxfhdm(a);
		   	                  else rets="短信发送失败：未取到系统返回值。";
		   	                 log.error(rets);
	     	           } 
		           }
		         } catch (RuntimeException e) {
		       	  log.error(e.toString());
		         } catch (Exception e) { // JSon解析出错
		       	  log.error(e.toString());
		         } finally {
		        	 httpclient.getConnectionManager().shutdown();
		         }    
	     	}      else rets="读配置文件错。";
	      return rets;     
	}
	private String dxfhdm(long a) {
		String s="";
		if (a== -1) s="帐号登陆失败";
		if (a== -2) s="错误的手机号";
		if (a== -3) s="手机号为黑名单用户";
		if (a== -4) s="短信内容不正确";
		if (a== -5) s="错误的子号码";
		if (a== -6) s="子号码超长";
		if (a== -7) s="账户余额不足";
		if (a== -8) s="请求参数错误";
		return s;
	}
}

package pkg_chexian;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 *运行在9服务器
 */
public class Zbga_yzh {
	private static Logger log = Logger.getLogger(Zbga_yzh.class);
	static String url="http://www.zbga.gov.cn/JTWeiFa/CreateCheckCode.aspx";
	static String url2="http://www.zbga.gov.cn/FWZX/JiaShiRenXinXi.aspx";
	public  String jshzh_img_zbga(String jgh) throws Exception { 
		String str="未找到数据",cooktmp="";
		String  filepath=Zbga_yzh.class.getResource("").toString();
		filepath=filepath.substring(0,filepath.indexOf("WEB-INF"));
		String wj=filepath+"yzh/"+jgh+".gif";
		wj=wj.substring(5);
		log.error("------------");
		log.error("驾驶证验证：验证码图片。");
		DefaultHttpClient client = Hclient_9.getHttpClient();
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 HttpResponse response = client.execute(httpget);  
		        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		        	str="网站请求失败。";
		            throw new RuntimeException("请求失败");
		        } 
		        HttpEntity entity = response.getEntity(); 
		        if (entity != null) {   
		        	List<Cookie> cookies=client.getCookieStore().getCookies();
		        	for (Cookie cookie : cookies) { 
		        		cooktmp=cooktmp+cookie.getName()+"|"+cookie.getValue();
		        		
		        	}
		        	if (!"".equals(cooktmp)) str=cooktmp;
		        	InputStream  inputstream= entity.getContent();
					OutputStream out = new FileOutputStream(wj);
					
					try {
					    byte[] buf = new byte[1024];
				     	int len;
				    	while ((len = inputstream.read(buf)) > 0) {
					         out.write(buf, 0, len);
					        }
					    out.close();
		 	         } catch (IOException ex) {
		 	        	 throw ex;
		 	         } catch (RuntimeException ex){
		 	        	httpget.abort();
	                    throw ex;
		 	         } finally {
		 	        	try {inputstream.close();
		 	        	     if (out!=null) out.close();    
		 	        	} catch (Exception ignore) {}
		 	        	}
		 	         }
	   } finally {
		client.getConnectionManager().shutdown();
	   }
		return str;	
		
		
	}
/*	public  String jshzh_img_zbga(String jgh) throws Exception { 
		String str="未找到数据",cooktmp="";
		String  filepath=Zbga_yzh.class.getResource("").toString();
		filepath=filepath.substring(0,filepath.indexOf("WEB-INF"));
		String wj=filepath+"yzh/"+jgh+".gif";
		wj=wj.substring(5);
		HttpEntity entity = null;
		log.error("------------");
		log.error("驾驶证验证：验证码图片。");
		DefaultHttpClient client = new DefaultHttpClient();  	
		client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
		client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 
		HttpGet httpget = new HttpGet(url); 
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
        	InputStream  inputstream= entity.getContent();
			OutputStream out = new FileOutputStream(wj);
			byte[] buf = new byte[1024];
			int len;
			while ((len = inputstream.read(buf)) > 0) {
			   out.write(buf, 0, len);
			     }
			out.close();
 	         } 
      } catch (RuntimeException e) {
    	  log.error(e.toString());
      } catch (Exception e) { // JSon解析出错
    	  log.error(e.toString());
      } finally {
    	 	    if (entity != null)   
 	             entity.consumeContent(); 
      }
      return str;
	}   */
	 public  String jshzh_zbga(String yzm,String jshz,String bh,String cook) throws Exception{
		   String str="未找到数据";
		   DefaultHttpClient client = Hclient_9.getHttpClient();
		   List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				    nvps.add(new BasicNameValuePair("code", yzm));
			        nvps.add(new BasicNameValuePair("sfz", jshz)); 
			        nvps.add(new BasicNameValuePair("bianhao", bh)); 
			 try {
				 HttpGet get = new HttpGet(url2); 
				 get.setHeader("Accept", "image/gif, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
					//get.setHeader("Accept-Encoding","gzip, deflate");
					get.setHeader("Accept-Language","zh-cn");
					get.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
					get.setHeader("Cookie",cook);
					get.setHeader("Referer", "http://www.zbga.gov.cn/FWZX/JiaShiRen.aspx");
					get.setHeader("Connection","Keep-Alive");
				 	
			        String temp = EntityUtils.toString(new UrlEncodedFormEntity(nvps));  
		            get.setURI(new URI(get.getURI().toString() + "?" + temp));  
				 HttpResponse response = client.execute(get);  
				 if (response == null) {
					 str="网站请求失败。";
				 } else {
					 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
						    str="网站请求失败。";
				            throw new RuntimeException("请求失败");	 
						}
					  HttpEntity entity = response.getEntity(); 
					  if (entity != null) {  
						  String content = null;
					try {
			                    content = EntityUtils.toString(entity, "UTF-8");
			                    if (content.contains("请输入正确的验证码")) 
			    					str="请输入正确的验证码";
			    				else  if(content.contains("error.jpg"))
			    					str="session过期，请刷新申请新的验证码。";
			    				else {
			    				     Document doc = Jsoup.parse(content);
			    				     if (doc.getElementById("DIV_NoDate")!=null) 
			    				    	 str="查询不到该驾驶人信息，请核对您的输入信息。";
			    				     else if (doc.getElementById("DIV_Data")!=null) {
			    				          Element cxJg = doc.getElementById("DIV_Data"); 
			    				          if (!cxJg.equals("")) str=cxJg.toString();
			    				          }
			    				     else str="解析错误。";
			    				}
			                    EntityUtils.consume(entity);

			                } catch (IOException ex) {
			                    throw ex;
			                } catch (RuntimeException ex) {
			                    get.abort();
			                    throw ex;
			                } finally {
			                    // Closing the input stream will trigger connection release
			                   content=null;
			                }
					 }
				 }
		  } catch (RuntimeException e) {
			  log.error(e.toString());
		  } catch (Exception e) { //
			  log.error(e.toString());
		   } finally {
			client.getConnectionManager().shutdown();
		   }
			return str;	 
		 
	 }
	public  String jshzh_zbga_old(String yzm,String jshz,String bh,String cook) throws Exception{
		 DefaultHttpClient client = new DefaultHttpClient();  
		 HttpGet get = new HttpGet(url2); 
		 HttpResponse response = null;
		 String str="未找到数据";
			HttpEntity entity =null;
			client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
			client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 
		try{
			get.setHeader("Accept", "image/gif, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
			//get.setHeader("Accept-Encoding","gzip, deflate");
			get.setHeader("Accept-Language","zh-cn");
			get.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
			get.setHeader("Cookie",cook);
			get.setHeader("Referer", "http://www.zbga.gov.cn/FWZX/JiaShiRen.aspx");
			get.setHeader("Connection","Keep-Alive");
		 	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		    nvps.add(new BasicNameValuePair("code", yzm));
	        nvps.add(new BasicNameValuePair("sfz", jshz)); 
	        nvps.add(new BasicNameValuePair("bianhao", bh)); 
	        String temp = EntityUtils.toString(new UrlEncodedFormEntity(nvps));  
            get.setURI(new URI(get.getURI().toString() + "?" + temp));  
		 	
		 	response = client.execute(get);
	        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
	        	str="网站请求失败。";
	            throw new RuntimeException("请求失败");
	        }
	        entity = response.getEntity();
	       if (entity!=null){
	    	   BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			   StringBuffer content = new StringBuffer();
				for (String line; (line = reader.readLine()) != null;) {
					content.append(line + "\r\n");
				    } 
				reader.close();
				if (content.toString().contains("请输入正确的验证码")) 
					str="请输入正确的验证码";
				else  if(content.toString().contains("error.jpg"))
					str="session过期，请刷新申请新的验证码。";
				else {
				     Document doc = Jsoup.parse(content.toString());
				     if (doc.getElementById("DIV_NoDate")!=null) 
				    	 str="查询不到该驾驶人信息，请核对您的输入信息。";
				     else if (doc.getElementById("DIV_Data")!=null) {
				          Element cxJg = doc.getElementById("DIV_Data"); 
				          if (!cxJg.equals("")) str=cxJg.toString();
				          }
				     else str="解析错误。";
				}
	           }
	      } catch (RuntimeException e) {
	    	  log.error(e.toString());
	      } catch (Exception e) { // JSon解析出错
	    	  log.error(e.toString());
	      } finally {
	    	 	    if (entity != null)   
	 	             entity.consumeContent(); 
	    	 	  
	      }
		return str;
	      	 
	 }
}

package pkg_chexian;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 *
 */
public class All_chx {
	private static Logger log = Logger.getLogger(All_chx.class);
	static String url="chaxun.wcar.net.cn";
	static String url2="chazibo.wcar.net.cn";
	static String url3="http://218.59.228.162:9080/wscgsxxcx/jdcwfcx.do";
	public static void main(String[] args)
    { 
		String cook="";
		try {
			/*Map<String, String> map = weiche(cook);
			if ("2".equals(map.get("job_status"))) {
				cook=map.get("job_id");
				map = weiche2(cook);
				if ("1".equals(map.get("job_status")))
				 cook=map.get("data");
			}
			System.out.println(cook);*/
			//cook=qingdao();
			qingdao2("aphl","ASPSESSIONIDQCTDDAQD=MNFJDJKAIBEPMAGCDPFFKCBL");
			System.out.println("str="+cook);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
	public static String qingdao() throws Exception { 
		HttpEntity entity = null;
		
		DefaultHttpClient client = new DefaultHttpClient();  	
		client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
		client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 	
		String cooktmp="";
		String wj="d:/123.gif";
      try {    
    	HttpPost httppost = new HttpPost("http://218.58.65.23/select/checkcode.asp");  
    	httppost.setHeader("Accept-Language","zh-cn");
        httppost.setHeader("Referer", "http://218.58.65.23/select/WZQuery.asp");
    	httppost.setHeader("Accept","*/*");
    	httppost.setHeader("Accept-Encoding","gzip, deflate");
    	httppost.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");   	
    	httppost.setHeader("Host","218.58.65.23");
    	httppost.setHeader("Connection","Keep-Alive");  

        HttpResponse response = client.execute(httppost); 

        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {    	
            throw new RuntimeException("请求失败");
        }       
        entity = response.getEntity(); 
        if (entity != null) {   
        	List<Cookie> cookies=client.getCookieStore().getCookies();
        	for (Cookie cookie : cookies) { 
        		cooktmp=cooktmp+cookie.getName()+"="+cookie.getValue();
        		
        	}
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
    	  //log.error(e.toString());
      } catch (Exception e) { // JSon解析出错
    	  //log.error(e.toString());
      } finally {
    	 	    if (entity != null)   
 	             entity.consumeContent(); 
      }
      return cooktmp;
	}
	public static String qingdao2(String yzm,String cook) throws Exception { 
		HttpEntity entity = null;
		
		DefaultHttpClient client = new DefaultHttpClient();  	
		client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
		client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 	
		String cooktmp="",str="";
      try {    
    	HttpPost httppost = new HttpPost("http://218.58.65.23/select/WZ.asp");  
    	httppost.setHeader("Accept-Language","zh-cn");
        httppost.setHeader("Referer", "http://218.58.65.23/select/WZQuery.asp");
    	httppost.setHeader("Accept","image/gif, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
    	httppost.setHeader("Accept-Encoding","gzip, deflate");
    	httppost.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");   	
    	httppost.setHeader("Content-Type","application/x-www-form-urlencoded");
    	httppost.setHeader("Host","218.58.65.23");
    	httppost.setHeader("Pragma","no-cache");
    	httppost.setHeader("Connection","Keep-Alive");  
    	httppost.setHeader("Cookie",cook);
    	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    	nvps.add(new BasicNameValuePair("stateid", "B"));
    	nvps.add(new BasicNameValuePair("hphm","001AQ"));
    	nvps.add(new BasicNameValuePair("hpzl","02"));
    	nvps.add(new BasicNameValuePair("jzh","8251"));
    	nvps.add(new BasicNameValuePair("yam",yzm));
    	nvps.add(new BasicNameValuePair("image.x","47"));
    	nvps.add(new BasicNameValuePair("image.y","19"));

    	httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        HttpResponse response = client.execute(httppost); 

        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {    	
            throw new RuntimeException("请求失败");
        }       
        entity = response.getEntity(); 
        if (entity != null) {   
       	 BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "gb2312"));
		   StringBuffer content = new StringBuffer();
			for (String line; (line = reader.readLine()) != null;) {
				content.append(line+ "\r\n");
			    } 
			if (content.toString().contains("请输入正确的验证码")) 
				str="请输入正确的验证码";
			else {
			     Document doc = Jsoup.parse(content.toString());
			     Elements cxJgs = doc.select("table[width=95%]:contains(鲁B001AQ)");//输出到9服务器
			     for (Element cxJg : cxJgs) {
			    	 Elements links = cxJg.select("td.back");
			    	 for (Element link:links){
			    	 System.out.println(link.text());
			    	 }
			     }

				}
			
 	         } 
      } catch (RuntimeException e) {
    	  //log.error(e.toString());
      } catch (Exception e) { // JSon解析出错
    	  //log.error(e.toString());
      } finally {
    	 	    if (entity != null)   
 	             entity.consumeContent(); 
      }
      return cooktmp;
	}
	public static Map<String, String> weiche(String cook) throws Exception { 
		HttpEntity entity = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Map<String, String> map1 = new HashMap<String, String>();
		DefaultHttpClient client = new DefaultHttpClient();  	
		client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
		client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 		 
      try {    
    	HttpPost httppost = new HttpPost("http://"+url+"/");  
    	httppost.setHeader("x-requested-with","XMLHttpRequest");
    	httppost.setHeader("Accept-Language","zh-cnt");
        httppost.setHeader("Referer", "http://"+url+"/");
    	httppost.setHeader("Accept","*/*");
    	httppost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    	httppost.setHeader("Accept-Encoding","gzip, deflate");
    	httppost.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");   	
    	httppost.setHeader("Host",url);
    	httppost.setHeader("Connection","Keep-Alive");  
     	httppost.setHeader("Pragma","no-cache");   	
        
        nvps.add(new BasicNameValuePair("province", "山东"));
        nvps.add(new BasicNameValuePair("city_pinyin", "zibo"));
        nvps.add(new BasicNameValuePair("car_province", "鲁"));
        nvps.add(new BasicNameValuePair("license_plate_num", "cmg512"));
        nvps.add(new BasicNameValuePair("body_num", "438510"));
        nvps.add(new BasicNameValuePair("engine_num", "选填"));
        nvps.add(new BasicNameValuePair("captcha", "验证码"));
       /* nvps.add(new BasicNameValuePair("province", "山东"));
        nvps.add(new BasicNameValuePair("city_pinyin", "linyi"));
        nvps.add(new BasicNameValuePair("car_province", "鲁"));
        nvps.add(new BasicNameValuePair("license_plate_num", "qkk288"));
        nvps.add(new BasicNameValuePair("body_num", "326938"));
        nvps.add(new BasicNameValuePair("engine_num", "选填"));
        nvps.add(new BasicNameValuePair("captcha", "验证码"));*/
        httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        HttpResponse response = client.execute(httppost); 

        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {    	
            throw new RuntimeException("请求失败");
        }       
        entity = response.getEntity(); 
        if (entity != null) {   
        	 BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			   StringBuffer content = new StringBuffer();
				for (String line; (line = reader.readLine()) != null;) {
					content.append(line);
				    } 
				reader.close();	 
             map1=toMap(content.toString());
 	         } 
      } catch (RuntimeException e) {
    	  //log.error(e.toString());
      } catch (Exception e) { // JSon解析出错
    	  //log.error(e.toString());
      } finally {
    	 	    if (entity != null)   
 	             entity.consumeContent(); 
      }
      return map1;
	}
	public static Map<String, String> weiche2(String cook) throws Exception { 
		Map<String, String> map1 = new HashMap<String, String>();
		HttpEntity entity = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		DefaultHttpClient client = new DefaultHttpClient();  	
		client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
		client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 		 
      try {    
    	HttpPost httppost = new HttpPost("http://chazibo.wcar.net.cn/");  
    	httppost.setHeader("x-requested-with","XMLHttpRequest");
    	httppost.setHeader("Accept-Language","zh-cnt");
        httppost.setHeader("Referer", "http://chazibo.wcar.net.cn/");
    	httppost.setHeader("Accept","*/*");
    	httppost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
    	httppost.setHeader("Accept-Encoding","gzip, deflate");
    	httppost.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");   	
    	httppost.setHeader("Host","chazibo.wcar.net.cn");
    	httppost.setHeader("Connection","Keep-Alive");  
     	httppost.setHeader("Pragma","no-cache");   	
       
        nvps.add(new BasicNameValuePair("job_id", cook));
        httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        HttpResponse response = client.execute(httppost); 
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
     	
            throw new RuntimeException("请求失败");
        }       
        entity = response.getEntity(); 
        if (entity != null) {   
        	 BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			   StringBuffer content = new StringBuffer();
				for (String line; (line = reader.readLine()) != null;) {
					content.append(line);
				    } 
				reader.close();
				System.out.println(content.toString());
				// map1=toMap(content.toString());
 	         } 
      } catch (RuntimeException e) {
    	  //log.error(e.toString());
      } catch (Exception e) { // JSon解析出错
    	  //log.error(e.toString());
      } finally {
    	 	    if (entity != null)   
 	             entity.consumeContent(); 
      }
      return  map1;
	}
	 /**
     * 将Json对象转换成Map
     * 
     * @param jsonObject
     *            json对象
     * @return Map对象
     * @throws JSONException
     */
    public static Map toMap(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        
        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        
        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);

        }
        return result;

    }


}

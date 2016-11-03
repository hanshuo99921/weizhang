/**
 * 
 */
package pkg_chexian;

import java.util.List;
import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.net.URI;  
import java.net.URISyntaxException;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.ParseException;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.util.EntityUtils;  
import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class Getclient {
	private static Logger log = Logger.getLogger(Getclient.class);
	 public static HttpResponse get(String url, List<NameValuePair> params) {  
		 
		        HttpResponse httpresponse = null;
		         try {  
		        	// DefaultHttpClient httpclient = new DefaultHttpClient();  
		        	 DefaultHttpClient httpclient = Hclient.getHttpClient();
    		         HttpGet httpget = new HttpGet(url);  
		            // 设置参数  
	                 String str = EntityUtils.toString(new UrlEncodedFormEntity(params));  
		             httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
		             // 发送请求  
		             //System.out.println(httpget.getURI().toString());
		            // log.error(httpget.getURI().toString());
		             httpresponse = httpclient.execute(httpget);  
		           
		              // 获取返回数据  
		          } catch (ParseException e) {  
		            e.printStackTrace();  
		          } catch (UnsupportedEncodingException e) {  
		             e.printStackTrace();  
		          } catch (IOException e) {  
	                e.printStackTrace();  
		          } catch (URISyntaxException e) {  
		              e.printStackTrace();  
		          }  
                  return httpresponse ;
		      }  

  

}

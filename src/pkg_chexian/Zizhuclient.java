/**
 * 
 */
package pkg_chexian;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class Zizhuclient {
	public static HttpResponse httppost(String url, List<NameValuePair> nvps)  throws Exception{
		HttpResponse response = null;
		 try {  
		// DefaultHttpClient httpclient = new DefaultHttpClient();   
            DefaultHttpClient httpclient = Hclient.getHttpClient();
		    // Ŀ���ַ   
		     HttpPost httppost = new HttpPost(url);  
		     httppost.setHeader("Referer", "http://222.134.129.34:10016/ziboZzjfWeb/jsp/zb_xxcx/clwfxx_srsbdh.jsp");
		     System.out.println("����: " + httppost.getRequestLine()); 
		    // ������򵥵��ַ�������   
		      httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		    // ִ��   
		      response = httpclient.execute(httppost);  
		 } catch (SocketTimeoutException e) {  
	            e.printStackTrace();  
	          } catch (UnsupportedEncodingException e) {  
	             e.printStackTrace();  
	          } catch (ConnectionPoolTimeoutException e) {  
                 e.printStackTrace();  
	          } catch (ClientProtocolException e) {
                 e.printStackTrace();
	          } catch (RuntimeException e) {
	        	  e.printStackTrace();
	          } catch (IOException e) {
	           e.printStackTrace();
         
	          } 
	    return response;   
	}
}

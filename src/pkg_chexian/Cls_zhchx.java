package pkg_chexian;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 *9服务器
 */
public class Cls_zhchx {
	static String url="http://222.134.129.34:10016/ziboZzjfWeb/bhl_jdc_write_A_Photo.action";
	private static Logger log = Logger.getLogger(Cls_zhchx.class);
	public static void main(String[] args)
    {
          try {
        	  //Chx_zbga("x0027","071841","02");
        	 // Chx_car_zizhu("370303197606271712","鲁CV7181");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      }
	public String Chx_car_zizhu(String sfz,String cphm) throws Exception{
		String str="<tr><td>未找到数据</td></tr>";
		DefaultHttpClient client = Hclient_9.getHttpClient();
		log.error("------------");
		log.error("行驶证信息："+sfz);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("drvPerson.sfzmhm", sfz)); 
        nvps.add(new BasicNameValuePair("photo.sfzmhm", sfz)); 
        nvps.add(new BasicNameValuePair("photo.tpBase64", sfz)); 
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + EntityUtils.toString(new UrlEncodedFormEntity(nvps))));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				 str="<tr><td>网站请求失败。</td></tr>";
		         
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					    str="<tr><td>网站请求失败。</td></tr>";
			            throw new RuntimeException("请求失败");
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"UTF-8");
		                    Document doc = Jsoup.parse(content);
		                    Elements cxJg = doc.select("#sfzxx");
							 if (cxJg.size()>0) {
				   			 Elements links = cxJg.select("tr:contains(鲁C"+cphm+")");
				   			 //System.out.println(links.toString());
							    if (links.size()==1)
		  		                  str=links.toString();
							    else str="<tr><td>无相关车辆信息</td></tr>";                
							 } else str="<tr><td>未找到相应的数据！</td></tr>";

		                    EntityUtils.consume(entity);
		                    doc=null;
		                } catch (IOException ex) {
		                    throw ex;
		                } catch (RuntimeException ex) {
		                    httpget.abort();
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
		   nvps=null;
		client.getConnectionManager().shutdown();
	   }
	   System.out.println(str);
	   return str;		
	}
/*	public static String Chx_car_zizhu(String sfz,String cphm) throws Exception{
		String str="<tr><td>未找到数据</td></tr>";
		HttpEntity entity = null;
		BufferedReader reader = null;
		String line = null;
		log.error("------------");
		log.error("行驶证信息："+sfz);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("drvPerson.sfzmhm", sfz)); 
        nvps.add(new BasicNameValuePair("photo.sfzmhm", sfz)); 
        nvps.add(new BasicNameValuePair("photo.tpBase64", sfz)); 
		 try {
		        HttpResponse response = Getclient.get(url, nvps);  
		        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		        	str="<tr><td>网站请求失败。</td></tr>";
		            throw new RuntimeException("请求失败");
		        }
		        entity = response.getEntity(); 
		        if (entity != null) {   
		        	   reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
					   StringBuffer content = new StringBuffer();
						for ( line=null; (line = reader.readLine()) != null;) {
							content.append(line + "\r\n");
						    } 
						reader.close();
					 Document doc = Jsoup.parse(content.toString());
					 Elements cxJg = doc.select("#sfzxx");
					 if (cxJg.size()>0) {
		   			 Elements links = cxJg.select("tr:contains(鲁C"+cphm+")");
		   			 //System.out.println(links.toString());
					    if (links.size()==1)
  		                  str=links.toString();
					    else str="<tr><td>无相关车辆信息</td></tr>";                
					 } else str="<tr><td>未找到相应的数据！</td></tr>";
		 	         } 
		      } catch (RuntimeException e) {
		    	  log.error(e.toString());
		      } catch (Exception e) { // JSon解析出错
		    	  log.error(e.toString());
		      } finally {
		    	  nvps=null;line=null;
		    	  if (reader !=null) {
		    		  try {
		    			  reader.close();		    			  
		    		  }catch (Exception e) {
		    			  throw new Exception ("Cls_wzchx.Chx_jshz_zizhu.bufferedreader"+e.toString());
		    		  }
		    	  }
		    	 	    if (entity != null)   
		 	             entity.consumeContent(); 
		    	 	  
		      }
		      return str;
	}*/
}

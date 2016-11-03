/**
 * 运行在9服务器
 */
package pkg_chexian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * @author Administrator
 * 
 */
public class Wx_Sd_wzchx {
	 private static Logger log = Logger.getLogger(Wx_Sd_wzchx.class);
     private static String shost = "120.192.86.21";
	public static void main(String[] args) throws Exception {
		//Getwz("");
		//jdc_check("\\u9c81CV7181","896a","MywzcxBLL","GEThpzl");
		//jdc_wfcx("鲁cv7181","896a");
	}
/*
 * 去微信后台验证车辆信息是否匹配，运行在9服务器。
 */
	public  String jdc_check(String cphm,String fdjh,String classname,String methodname) throws Exception {
		String str = "",tempjson="";
		DefaultHttpClient client = new DefaultHttpClient();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpEntity entity =null;
		BufferedReader br = null;
		String line = null;
		try {
			//tempjson="[\"\\\"\\"+cphm+"\\\"\",\"\\\""+fdjh+"\\\"\"]";//eclipse
			tempjson="[\"\\\"\\"+cphm.replace("\"", "")+"\\\"\",\"\\\""+fdjh.replace("\"", "")+"\\\"\"]";//应用
			//System.out.println(cphm);
			HttpPost httppost = new HttpPost("http://120.192.86.21/WEIXIN/SDTrafficCop/Hand/ExecuteNonQuery.ashx");	
			nvps.add(new BasicNameValuePair("className",classname));
			nvps.add(new BasicNameValuePair("methodName",methodname));
			nvps.add(new BasicNameValuePair("paraList",tempjson));
			String s = EntityUtils.toString(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			log.error(s);
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

		/**/HttpResponse response = client.execute(httppost);
			
			if (response == null) {                  
                   str = "\"\"";
			} else {
				 entity = response.getEntity();
				 br = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
		    	   StringBuffer content = new StringBuffer();
					for ( line=null; (line = br.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)
								+ "\r\n");
					}
		    	   Document doc = Jsoup.parse(content.toString());
		    	   Elements links = doc.select("body");
		    	   str = links.text();
		    	   log.error(links.text());

			}
		} catch (RuntimeException e) {
			 log.error(e.toString());
		} catch (Exception e) { // JSon解析出错
			 log.error(e.toString());
		} finally {
			  nvps=null;line=null;tempjson=null;
	    	  if (br !=null) {
	    		  try {
	    			  br.close();		    			  
	    		  }catch (Exception e) {
	    			  throw new Exception ("Cls_wzchx.Chx_jshz_zizhu.bufferedreader"+e.toString());
	    		  }
	    	  }
			if (entity != null)
				entity.consumeContent();
		}
		//System.out.println(str);
		return str;
	}
/*	public static String Getwz(String cphm)
			throws Exception {
		String url="http://120.192.86.21/WEIXIN/SDTrafficCop/WZZ/wzcxALL.aspx?hphm=鲁ch3381&fdjh=8534";
		DefaultHttpClient httpclient = Hclient.getHttpClient();
        HttpGet httpget = new HttpGet(url);  
		HttpResponse response = null;
			HttpEntity entity =null;
		try{
			response = httpclient.execute(httpget); 
	        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
	        	//str="网站请求失败。";
	            throw new RuntimeException("请求失败");
	        }
	        entity = response.getEntity();
		       if (entity!=null){
			       BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
		    	   StringBuffer content = new StringBuffer();
					for (String line; (line = br.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)
								+ "\r\n");
					}
		    	   Document doc = Jsoup.parse(content.toString());
		    	   Elements links = doc.select("ul:contains(未处理)");
		    	   System.out.println(links.text());

			
			}
    } catch (RuntimeException e) {
  	  //log.error(e.toString());
    } catch (Exception e) { // JSon解析出错
  	  //log.error(e.toString());
    } finally {
  	 	    if (entity != null)   
	             entity.consumeContent(); 
  	 	  
    }
	return "";
	}*/
	public  String jdc_wfcx(String cphm,String sbm) throws Exception {
		String str="<table class=\"ysm\" ><tr><td>未找到数据</td></tr></table>";
		String url="http://"+shost+"/WEIXIN/SDTrafficCop/WZZ/wzcxALL.aspx?hphm="+cphm+"&fdjh="+sbm;
		HttpEntity entity = null;
		BufferedReader reader = null;
		String line = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url); 
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时     
		log.error(url);
		try {
			HttpResponse response = client.execute(httpget);
			if (response == null) {
				log.error("查询请求失败，请检查线路。");
				str="<table class=\"ysm\" ><tr><td>查询请求失败，请检查线路。</td></tr></table>";
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					str="<table class=\"ysm\" ><tr><td>查询请求失败。</td></tr></table>";
					log.error("查询请求失败。");
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					reader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "UTF-8"));
					StringBuffer content = new StringBuffer();
					for ( line=null; (line = reader.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)+ "\r\n");
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements cxJgs  = doc.select("ul:contains(未处理)");					
					if (cxJgs.size() > 0) {
                         str=cxJgs.toString();
					} else {
						Element yclJgs = doc.getElementById("ycl");
						Elements ele = yclJgs.select(".top3");
						//System.out.println(ele.text());
						str="<table class=\"ysm\" ><tr><td>没有未处理的违章记录["+ele.text()+"]</td></tr></table>";
					}					
				}
			}
		} catch (RuntimeException e) {
			 log.error(e.toString());
		} catch (Exception e) { // JSon解析出错
			 log.error(e.toString());
		} finally {
			 line=null;
	    	  if (reader!=null) {
	    		  try {
	    			  reader.close();		    			  
	    		  }catch (Exception e) {
	    			  throw new Exception ("Cls_wzchx.Chx_jshz_zizhu.bufferedreader"+e.toString());
	    		  }
	    	  }
			if (entity != null)
				entity.consumeContent();
		}
		//log.error(str);
		//System.out.println(str);
		return str;
	}
}

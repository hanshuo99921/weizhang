package pkg_chexian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import org.apache.http.Consts;
import org.apache.http.HttpStatus;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.ResponseHandler;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.impl.client.BasicResponseHandler;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements; 
/**
 * @author Administrator
 *
 */
public class Text_chx {
	String url="http://www.zbga.gov.cn/FWZX/JiaShiRenXinXi.aspx";
	String url2="http://www.zbga.gov.cn/JTWeiFa/CreateCheckCode.aspx";
	 public static void main(String[] args)
	    {
	          try {
	        	  Text_chx reg = new Text_chx();
				reg.regest7();
            
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	public  boolean regest7() throws Exception { //驾驶人信息查询
		boolean tag = false;
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("code", "WIkb"));
        nvps.add(new BasicNameValuePair("sfz", "370303197710211023")); 
        nvps.add(new BasicNameValuePair("bianhao", "5255")); 
        String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
        DefaultHttpClient httpclient = Hclient.getHttpClient();
        HttpGet httpget = new HttpGet(url);  
        httpget.setHeader("Connection", "Keep-Alive");
        httpget.setHeader("Host", "www.zbga.gov.cn");   
        httpget.setHeader("Referer", "http://www.zbga.gov.cn/FWZX/JiaShiRen.aspx");
        httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
        // 发送请求  
        System.out.println(httpget.getURI().toString());
        HttpResponse httpresponse = httpclient.execute(httpget);  
	    HttpEntity entity = httpresponse.getEntity();
	    if (entity != null) { 
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			StringBuffer content = new StringBuffer();
			for (String line; (line = reader.readLine()) != null;) {
				content.append(line + "\r\n");
			}
			System.out.println(content.toString());
	    }
 	    if (entity != null) {   
	 	       entity.consumeContent();   
	 	     }
    	return tag; 
    	}
	private Map<String, String> form_map;
    private final HttpClient client;  
    private HttpPost post;  
	private HttpGet get;  
	private HttpResponse response;  
	private ResponseHandler<String> responseHandler;
	
	public Text_chx(){
		super();
		client = new DefaultHttpClient();  
		responseHandler = new BasicResponseHandler();  
		form_map = new HashMap<String, String>(); 
	}
	
	public String jshzh_img(String jshzh) throws Cls_exception {
		String filepath="../server/default/deploy/wzdb.war/yzh/"+jshzh+".jpg";
		InputStream inputstream=null; 

		String tag="";
		String url="http://www.zbga.gov.cn/JTWeiFa/CreateCheckCode.aspx?";
		//client = new DefaultHttpClient();  
		client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
		client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 
		get = new HttpGet(url);  
		
		try{
			response = client.execute(get);
			if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
			HttpEntity entity = response.getEntity();  

			if (entity != null) {
				inputstream= entity.getContent();
				byte[] input=getBytes(inputstream);
				
				//byte[] result = Base64.decodeBase64(input)  ;
				OutputStream out = new FileOutputStream(filepath);  
	            out.write(input);  
			    out.close();
			    tag=jshzh;
			}
			}
			
	  } catch(IOException e){
			return tag;
	  }
		catch(Exception e){
		throw new Cls_exception("Cls_l_yanzheng.shfzh_img  "+e.toString());
	  }	
	  finally{
			
		try{
				
			   }
			catch(Exception e){
				 throw new Cls_exception("Cls_l_yanzheng.shfzh_img"+e.toString());
			}
			
		}

		return tag;
	}
	
	public boolean jshzh_get(String jshzh) throws Cls_exception {
		boolean tag=false;
		int i=0;
		String url="http://www.zbga.gov.cn/JTWeiFa/JSZCX.aspx";
		form_map = new HashMap<String, String>(); 
		String[] jdname;
		try{
			System.out.println(url);
			Document doc1 = Jsoup.connect(url).timeout(15000).get();
			Elements els=doc1.getElementsByTag("input"); 
			
			jdname=new String[els.size()];
			//for(Element name : titleName){
			for(  Element e : els){
				
				form_map.put(e.attr("name"), e.attr("value"));
				//jdname[i]=e.attr("name")+";"+e.attr("value");
	            //System.out.println(jdname[i]);
	            //System.out.println("i="+i);
	            //i++;
	        } 
			if (!jshzh_img( jshzh).equals("")){
				tag=true;
			}

	  }	  catch(IOException e){
			return tag;
	  }	
		catch(Exception e){
		throw new Cls_exception("Cls_l_yanzheng.shfzh_yzh "+e.toString());
	  }	

	  finally{
			
		try{
				
			   }
			catch(Exception e){
				 throw new Cls_exception("Cls_l_yanzheng.shfzh_yzh "+e.toString());
			}
			
		}

		return tag;
	}	
	public String[] jshzh_post(String jshzh,String jszdabh,String yzhm) throws Cls_exception {
		//String tag="";
		String html;
		int i=0;
		String url="http://www.zbga.gov.cn/JTWeiFa/JSZCX.aspx";
		String[] jdname;
		post = new HttpPost(url);
		client.getParams().setParameter(  CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间  
		client.getParams().setParameter(  CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时 
		//post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");  
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("__EVENTTARGET", "eventTarget"));
		nvp.add(new BasicNameValuePair("__EVENTARGUMENT", "eventArgument"));
		nvp.add(new BasicNameValuePair("__VIEWSTATE", form_map.get("__VIEWSTATE")));
		nvp.add(new BasicNameValuePair("__VIEWSTATEENCRYPTED", form_map.get("__VIEWSTATEENCRYPTED")));
		nvp.add(new BasicNameValuePair("__EVENTVALIDATION", form_map.get("__EVENTVALIDATION")));
		nvp.add(new BasicNameValuePair("ctl00$ContentPlaceContent$txt_SFZNHM", jshzh));
		nvp.add(new BasicNameValuePair("ctl00$ContentPlaceContent$txt_DABH", jszdabh.substring(8,12)));
		nvp.add(new BasicNameValuePair("ctl00$ContentPlaceContent$txt_yzm", yzhm));
		nvp.add(new BasicNameValuePair("ctl00$ContentPlaceContent$btnSubmit", "yes"));
		post.setEntity(new UrlEncodedFormEntity(nvp, Consts.UTF_8));
		//System.out.println(jszdabh.substring(8,12));
		//System.out.println(form_map.get("__VIEWSTATE"));
		try{
			response = client.execute(post);
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity);
			//System.out.println(html);
			//jdname=new String[1];
			
			Document doc1 = Jsoup.parse(html);
			if (doc1.getElementById("ctl00_ContentPlaceContent_DIV_NoDate")!=null){
			Element els1=doc1.getElementById("ctl00_ContentPlaceContent_DIV_NoDate"); 
			
				jdname=new String[1];
				jdname[0]="查询不到该驾驶人信息，请核对您的输入信息！";
			}else{
			Element els=doc1.getElementById("ctl00_ContentPlaceContent_DIV_Data"); 
			if (!els.equals("")){
			Elements titleName=els.select("td"); 
			if (titleName.size()>0){
			jdname=new String[titleName.size()];
			
			for(  i=0;i<titleName.size();i++){
				//i=0;
				jdname[i]=titleName.eq(i).text();
				if ((i%2)==1){
				 jdname[i]=jdname[i].substring(1);
				}
				System.out.println(jdname[i]);
	            System.out.println("i="+i);
	            //i++;
	        } 
			}else{
				jdname=new String[1];
				jdname[0]="";	
			}
			}else{
				jdname=new String[1];
				jdname[0]="";
			}
			}	

	  } catch(IOException e){
		  return null;
	  }
		catch(Exception e){
		throw new Cls_exception("Cls_jshzh.jshzh_post "+e.toString());
	  }	
	  finally{
			
		try{
				
			   }
			catch(Exception e){
				 throw new Cls_exception("Cls_jshzh.jshzh_post "+e.toString());
			}
			
		}

		return jdname;
	}

	public static byte[] getBytes(InputStream is)
	throws Exception
	{
	    byte[] data = null;
	    
	    Collection chunks = new ArrayList();
	    byte[] buffer = new byte[1024*1000];
	    int read = -1;
	    int size = 0;
	    
	    while((read=is.read(buffer))!=-1)
	    {
	        if(read>0)
	        {
	            byte[] chunk = new byte[read];
	            System.arraycopy(buffer,0,chunk,0,read);
	            chunks.add(chunk);
	            size += chunk.length;
	        }
	    }       
	    
	    if(size>0)
	    {
	        ByteArrayOutputStream bos = null;
	        try
	        {
	            bos = new ByteArrayOutputStream(size);
	            for(Iterator itr=chunks.iterator();itr.hasNext();)
	            {
	                byte[] chunk = (byte[])itr.next();
	                bos.write(chunk);
	            }
	            data = bos.toByteArray();
	        }
	        finally
	        {
	            if(bos!=null)
	            {
	                bos.close();
	            }
	        }
	    }
	    return data;
	} 
	
}

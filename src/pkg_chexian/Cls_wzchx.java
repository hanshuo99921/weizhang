package pkg_chexian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
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
 *
 */
public class Cls_wzchx {
	String url= "http://222.134.129.34:10016/ziboZzjfWeb/clwf.action";
	static String url2="http://www.zbga.gov.cn/jtweifa/searchFrame.aspx";
	static String url3="http://222.134.129.34:10016/ziboZzjfWeb/xxcx_vehQuery.action";
	private static Logger log = Logger.getLogger(Cls_wzchx.class);
	public static void main(String[] args)
    {
          try {
        	  //Chx_zbga("x0027","071841","02");
        	  //Chx_jshz_zizhu("370303197710211023");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      }
	public  String Chx_jshz_zizhu(String sfz) throws Exception{
		String str="<td>未找到数据</td>";
		DefaultHttpClient client = Hclient_9.getHttpClient();
		log.error("------------");
		log.error("查询驾驶证信息："+sfz);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("drvPerson.sfzmhm",sfz));
        nvps.add(new BasicNameValuePair("btn03", "下一步"));
		 try {
			 HttpGet httpget = new HttpGet(url3); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + EntityUtils.toString(new UrlEncodedFormEntity(nvps))));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				 log.error(sfz+"的驾驶证信息："+"查询请求失败，请检查线路。");
				 str="<td>查询请求失败，请检查线路。</td>";
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					    str="<td>网站请求失败。</td>";
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
				   			 Elements links = cxJg.select("td[class=tabright]");
				   			//System.out.println(links.toString());
							    if (links.size()==6)
		  		                  str=links.toString();
							    else str="<td>无相关驾驶人信息</td>";                
							 } else str="<td>未找到相应的数据！</td>";

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
	   return str;		
	}
/*	@SuppressWarnings("deprecation")
	public  String Chx_jshz_zizhu(String sfz) throws Exception{
		BufferedReader reader = null;
		String line = null;
		String str="<td>未找到数据</td>";
		HttpEntity entity = null;
		log.error("------------");
		log.error("查询驾驶证信息："+sfz);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("drvPerson.sfzmhm",sfz));
        nvps.add(new BasicNameValuePair("btn03", "下一步"));
		 try {
		        HttpResponse response = Getclient.get(url3, nvps);  
		        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		        	str="<td>网站请求失败。</td>";
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
		   			 Elements links = cxJg.select("td[class=tabright]");
		   			//System.out.println(links.toString());
					    if (links.size()==6)
  		                  str=links.toString();
					    else str="<td>无相关驾驶人信息</td>";                
					 } else str="<td>未找到相应的数据！</td>";
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
	public  String Chx_zbga(String cphm,String sbm,String zl) throws Exception { 
		String str="<td>未找到数据</td>";
		DefaultHttpClient client = Hclient_9.getHttpClient();
		log.error("------------");
		log.error("查询违章信息：鲁c"+cphm+"/"+sbm+"/"+zl);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("select", "C"));
        nvps.add(new BasicNameValuePair("ZL", zl));	     
        nvps.add(new BasicNameValuePair("CH", cphm));
        nvps.add(new BasicNameValuePair("SBM", sbm));
		 try {
			 HttpGet httpget = new HttpGet(url2); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + EntityUtils.toString(new UrlEncodedFormEntity(nvps)))); 
			 //System.out.println(url2+"?"+EntityUtils.toString(new UrlEncodedFormEntity(nvps)));
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				 log.error("查询违章信息："+"查询请求失败，请检查线路。");
				 str="<td>查询请求失败，请检查线路。</td>";
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					    str="<td>网站请求失败。</td>";
			            throw new RuntimeException("请求失败");
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"UTF-8");
		                   
		                    Document doc = Jsoup.parse(content);
		                    Elements cxJg = doc.select("#cxJg");
		       			 if (cxJg.size()>0) {
		       				 //System.out.println(cxJg.toString());
		       			     log.error(cxJg.text());
		                        str=cxJg.toString();
		       			 } else str="未找到相应的违法数据！";

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
	   
	   return str;	
		
	}
/*	public  String Chx_zbga(String cphm,String sbm,String zl) throws Exception { 
		String str="未找到数据";
		HttpEntity entity = null;
		BufferedReader reader = null;
		String line = null;
		log.error("------------");
		log.error("查询违章信息：鲁c"+cphm+"/"+sbm+"/"+zl);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("select", "C"));
        nvps.add(new BasicNameValuePair("ZL", zl));	     
        nvps.add(new BasicNameValuePair("CH", cphm));
        nvps.add(new BasicNameValuePair("SBM", sbm));
      try {
        HttpResponse response = Getclient.get(url2, nvps);  
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        	str="网站请求失败。";
            throw new RuntimeException("请求失败");
        }
        entity = response.getEntity(); 
        if (entity != null) {   
        	   reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			   StringBuffer content = new StringBuffer();
				for (line=null; (line = reader.readLine()) != null;) {
					content.append(line + "\r\n");
				    } 
				reader.close();
			 Document doc = Jsoup.parse(content.toString());
			 Elements cxJg = doc.select("#cxJg");
			 if (cxJg.size()>0) {
				 //System.out.println(cxJg.toString());
			     log.error(cxJg.text());
                 str=cxJg.toString();
			 } else str="未找到相应的违法数据！";
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
	}   */
	public String Chx_zizhu(String cphm,String sbm,String zl) throws Exception { 
		String str="<td>未找到数据</td>";
		DefaultHttpClient client = Hclient_9.getHttpClient();
		log.error("------------");
		log.error("查询违章信息：鲁c"+cphm+"/"+sbm+"/"+zl);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("cllx", zl));	     
        nvps.add(new BasicNameValuePair("hphm", "鲁C"+cphm));
        nvps.add(new BasicNameValuePair("clsbdh", sbm));
        nvps.add(new BasicNameValuePair("btn", "确定"));
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + EntityUtils.toString(new UrlEncodedFormEntity(nvps))));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				 log.error("查询违章信息："+"查询请求失败，请检查线路。");
				 str="<td>查询请求失败，请检查线路。</td>";
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					    str="<td>网站请求失败。</td>";
			            throw new RuntimeException("请求失败");
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"UTF-8");
		                    content = StringEscapeUtils.unescapeHtml4(content);
		                    Document doc = Jsoup.parse(content);
		                    Elements cxJg = doc.select("#clwfxx");
		       			 if (cxJg.size()==1) {
		       			     //Elements links = cxJg.select("tr:contains(未处理)");
		       			     log.error(cxJg.text());
		                        str=cxJg.toString();
		       			 } else str="未找到相应的违法数据！";

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
	   return str;		
		
	}
/*	@SuppressWarnings("deprecation")
	public String Chx_zizhu(String cphm,String sbm,String zl) throws Exception { 
		String str="未找到数据";
		HttpEntity entity = null;
		BufferedReader reader = null;
		String line = null;
		log.error("------------");
		log.error("查询违章信息：鲁c"+cphm+"/"+sbm+"/"+zl);
	    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	    nvps.add(new BasicNameValuePair("cllx", zl));	     
        nvps.add(new BasicNameValuePair("hphm", "鲁C"+cphm));
        nvps.add(new BasicNameValuePair("clsbdh", sbm));
        nvps.add(new BasicNameValuePair("btn", "确定"));
      try {
     	HttpResponse response = Zizhuclient.httppost(url, nvps);  
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        	str="网站请求失败。";
            throw new RuntimeException("请求失败"); 
        }
        entity = response.getEntity(); 
        if (entity != null) {   
        	   reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			   StringBuffer content = new StringBuffer();
				for (line=null; (line = reader.readLine()) != null;) {
					content.append(StringEscapeUtils.unescapeHtml4(line) + "\r\n");
				    } 
				reader.close();
			 Document doc = Jsoup.parse(content.toString());
			 Elements cxJg = doc.select("#clwfxx");
			 if (cxJg.size()==1) {
			     //Elements links = cxJg.select("tr:contains(未处理)");
			     log.error(cxJg.text());
                 str=cxJg.toString();
			 } else str="未找到相应的违法数据！";
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

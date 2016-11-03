/*
 * 运行在生产服务器，去9服务器发送数据查询请求。
 */
package pkg_chexian;

//import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//import org.jsoup.parser.Parser;
//import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 * 运行在生产服务器，去9服务器发送数据查询请求。
 */
public class XxChx_9 {
	private static Logger log = Logger.getLogger(XxChx_9.class);
	//String dzh = "http://localhost:8080";
	String dzh = "http://10.157.0.9:7001";
	public String str_format(String s1,int mark) throws Exception {
		String temp="";
		int ibegin=0,iend=0;
		ibegin=s1.indexOf(" ")+1;
		iend=s1.indexOf(" ",ibegin);	
		if (ibegin!=-1&&iend!=-1) {
		if (mark==1) {
		   temp=s1.substring(ibegin, iend);
		   temp=temp.replaceAll("年","-");
		   temp=temp.replaceAll("月", "-");
		   temp=temp.replaceAll("日", "");
		}
		if (mark==2)
		   temp=s1.substring(ibegin,iend);
		}
		return temp;
	}
	public String Getjshzimg_from9(String jgh) throws Exception { 
		String url = dzh + "/chexian/yzh/"+jgh+".gif";
		String str="未找到数据";
		String  filepath=XxChx_9.class.getResource("").toString();
		filepath=filepath.substring(0,filepath.indexOf("WEB-INF"));
		String wj=filepath+"yzh/"+jgh+".gif";
		//wj="C:/jboss6/server/default/deploy/chexian.war/yzh/"+jgh+".gif";
		wj=wj.substring(5);
		//log.error(wj);	
		log.error("------------Getjshzimg_from9(jgh)--------");
		log.error("驾驶证验证：验证码图片。"+url);
		DefaultHttpClient client = Hclient.getHttpClient();
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 HttpResponse response = client.execute(httpget);  
		        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		        	str="网站请求失败。";
		            throw new RuntimeException("请求失败");
		        } 
		        HttpEntity entity = response.getEntity(); 
		        if (entity != null) {   
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
/*	public String Getjshzimg_from9(String jgh) throws Exception { 
		String url = dzh + "/chexian/yzh/"+jgh+".gif";
		String str="未找到数据";
		String  filepath=XxChx_9.class.getResource("").toString();
		filepath=filepath.substring(0,filepath.indexOf("WEB-INF"));
		String wj=filepath+"yzh/"+jgh+".gif";
		//wj="C:/jboss6/server/default/deploy/chexian.war/yzh/"+jgh+".gif";
		wj=wj.substring(5);
		//log.error(wj);
		HttpEntity entity = null;
		log.error("------------Getjshzimg_from9(jgh)--------");
		log.error("驾驶证验证：验证码图片。"+url);
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
    	  log.error("驾驶证验证：验证码图片。"+e.toString());
      } catch (Exception e) { // JSon解析出错
    	  log.error("驾驶证验证：验证码图片。"+e.toString());
      } finally {
    	 	    if (entity != null)   
 	             entity.consumeContent(); 
      }
      return str;		
		
	}
	*/
	public String Getjshzimg_from9_zbga(String jgh) throws Exception{
		String cook="";
		String url = dzh + "/chexian/server_9/jszh_img_zbga.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("jgh", jgh));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error("----------Getjshzimg_from9_zbga----------");
		log.error(jgh+"取zbga验证码cook："+url + "?" + str);
		DefaultHttpClient client = Hclient.getHttpClient();
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
					log.error(jgh+"取zbga验证码cook："+"查询请求失败，请检查线路。");
					cook="查询请求失败，请检查线路。";
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
						log.error(jgh+"取zbga验证码cook："+"查询请求失败。");
	                    cook="查询请求失败.";
						throw new RuntimeException("请求失败");
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"gb2312");
		                    Document doc = Jsoup.parse(content);
							Element ele = doc.getElementById("ysm");
							cook=ele.text();
							log.error(ele.text());
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
				log.error(jgh+"取zbga验证码cook："+e.toString());
	  } catch (Exception e) { // JSon解析出错
				log.error(jgh+"取zbga验证码cook："+e.toString());		 
	   } finally {
		client.getConnectionManager().shutdown();
	   }
		return cook;			
}	
/*	public String Getjshzimg_from9_zbga(String jgh) throws Exception{
		String cook="";
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/jszh_img_zbga.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("jgh", jgh));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error("----------Getjshzimg_from9_zbga----------");
		log.error(jgh+"取zbga验证码cook："+url + "?" + str);
		try {
			HttpResponse response = Getclient.get(url, nvps);
			if (response == null) {
				log.error(jgh+"取zbga验证码cook："+"查询请求失败，请检查线路。");
				cook="查询请求失败，请检查线路。";
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					log.error(jgh+"取zbga验证码cook："+"查询请求失败。");
                    cook="查询请求失败.";
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "gb2312"));
					StringBuffer content = new StringBuffer();
					for (String line; (line = reader.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)+ "\r\n");
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Element ele = doc.getElementById("ysm");
					
					//System.out.println(links.toString());
					cook=ele.text();
					log.error(ele.text());
				}
			}
		} catch (RuntimeException e) {
			log.error(jgh+"取zbga验证码cook："+e.toString());
		} catch (Exception e) { // JSon解析出错
			log.error(jgh+"取zbga验证码cook："+e.toString());
		} finally {
			if (entity != null)
				entity.consumeContent();
		}
		return cook;
	}
	*/
	public Cls_e_tmp Getjshz_from9_zbga(String sfz,String yzm,String scook,String bh) throws Exception {
		Cls_e_tmp tmp_jl= new Cls_e_tmp();
		String url = dzh + "/chexian/server_9/jszh_zbga.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("jshz", sfz));
		nvps.add(new BasicNameValuePair("yzhm", yzm));
		nvps.add(new BasicNameValuePair("scook", scook));
		nvps.add(new BasicNameValuePair("jszdabh", bh));		
		
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error("-------Getjshz_from9_zbga(sfz,yzm,scook,bh)-------");
		log.error(sfz+"的驾驶证信息："+url + "?" + str);
		DefaultHttpClient client = Hclient.getHttpClient();
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				 log.error(sfz+"的驾驶证信息："+"查询请求失败，请检查线路。");
					tmp_jl.setNum1(0);
					tmp_jl.setS1("查询请求失败，请检查线路。");
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					 log.error(sfz+"的驾驶证信息："+"查询请求失败。");
						tmp_jl.setNum1(0);
						tmp_jl.setS1("查询请求失败。");
						throw new RuntimeException("请求失败");
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"gb2312");
		                    content = StringEscapeUtils.unescapeHtml4(content);
		                    Document doc = Jsoup.parse(content);
							if (doc.getElementById("DIV_Data")!=null){
								tmp_jl.setNum1(1);
								tmp_jl.setS1(doc.getElementById("lblNo").text());
								tmp_jl.setS2(doc.getElementById("lblZJCX").text());
								tmp_jl.setS3(str_format(doc.getElementById("lblYXQZ").text(),1));
								tmp_jl.setS4(doc.getElementById("lblZT").text());
								tmp_jl.setS5(str_format(doc.getElementById("lblJF").text(),2));
							} else {
								tmp_jl.setNum1(0);
								Elements links = doc.select("td");
								tmp_jl.setS1(links.text());
							}

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
		  log.error(sfz+"的驾驶证信息："+e.toString());
	  } catch (Exception e) { // JSon解析出错
		  log.error(sfz+"的驾驶证信息："+e.toString());
	   } finally {
		client.getConnectionManager().shutdown();
	   }
	   return tmp_jl;		
	}
/*	public Cls_e_tmp Getjshz_from9_zbga(String sfz,String yzm,String scook,String bh) throws Exception {
		Cls_e_tmp tmp_jl= new Cls_e_tmp();
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/jszh_zbga.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("jshz", sfz));
		nvps.add(new BasicNameValuePair("yzhm", yzm));
		nvps.add(new BasicNameValuePair("scook", scook));
		nvps.add(new BasicNameValuePair("jszdabh", bh));		
		
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error("-------Getjshz_from9_zbga(sfz,yzm,scook,bh)-------");
		log.error(sfz+"的驾驶证信息："+url + "?" + str);
		try {
			HttpResponse response = Getclient.get(url, nvps);
			if (response == null) {
				log.error(sfz+"的驾驶证信息："+"查询请求失败，请检查线路。");
				tmp_jl.setNum1(0);
				tmp_jl.setS1("查询请求失败，请检查线路。");
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					log.error(sfz+"的驾驶证信息："+"查询请求失败。");
					tmp_jl.setNum1(0);
					tmp_jl.setS1("查询请求失败。");
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "gb2312"));
					StringBuffer content = new StringBuffer();
					for (String line; (line = reader.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)+ "\r\n");
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					if (doc.getElementById("DIV_Data")!=null){
						tmp_jl.setNum1(1);
						tmp_jl.setS1(doc.getElementById("lblNo").text());
						tmp_jl.setS2(doc.getElementById("lblZJCX").text());
						tmp_jl.setS3(str_format(doc.getElementById("lblYXQZ").text(),1));
						tmp_jl.setS4(doc.getElementById("lblZT").text());
						tmp_jl.setS5(str_format(doc.getElementById("lblJF").text(),2));
					} else {
						tmp_jl.setNum1(0);
						Elements links = doc.select("td");
						tmp_jl.setS1(links.text());
					}
				}
			}
		} catch (RuntimeException e) {
			log.error(sfz+"的驾驶证信息："+e.toString());
		} catch (Exception e) { // JSon解析出错
			log.error(sfz+"的驾驶证信息："+e.toString());
		} finally {
			if (entity != null)
				entity.consumeContent();
		}
		return tmp_jl;
	}*/
	public Cls_e_tmp Getjshz_from9_zizhu(String sfz) throws Exception {
		Cls_e_tmp tmp_jl= new Cls_e_tmp();
		String url = dzh + "/chexian/server_9/zizhu_jshz.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sfz", sfz));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error("-------Getjshz_from9_zizhu(sfz)--------");
		log.error(sfz+"的驾驶证信息："+url + "?" + str);
		DefaultHttpClient client = Hclient.getHttpClient();
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				 log.error(sfz+"的驾驶证信息："+"查询请求失败，请检查线路。");
					tmp_jl.setNum1(0);
					tmp_jl.setS1("查询请求失败，请检查线路。");
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
						log.error(sfz+"的驾驶证信息："+"查询请求失败。");
						tmp_jl.setNum1(0);
						tmp_jl.setS1("查询请求失败。");
						throw new RuntimeException("请求失败");
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"gb2312");
		                    content = StringEscapeUtils.unescapeHtml4(content);
		                    Document doc = Jsoup.parse(content);
		                    Elements links = doc.select("td");
							//System.out.println(links.toString());
							//log.error(sfz+"的驾驶证信息："+links.text());
								if (links.size() ==6) {
									tmp_jl.setNum1(1);
									tmp_jl.setS1(links.get(0).text());
									tmp_jl.setS2(links.get(2).text());
									tmp_jl.setS3(links.get(3).text());
									tmp_jl.setS4(links.get(4).text());
									tmp_jl.setS5(links.get(5).text());
								} else if (links.size() ==1) {
									tmp_jl.setNum1(0);
									tmp_jl.setS1(links.text());
								}  else {
									tmp_jl.setNum1(0);
								    tmp_jl.setS1("未找到返回数据！");
							    }

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
		  log.error(sfz+"的驾驶证信息："+e.toString());
	  } catch (Exception e) { // JSon解析出错
		  log.error(sfz+"的驾驶证信息："+e.toString());
	   } finally {
		client.getConnectionManager().shutdown();
	   }
	   return tmp_jl;		
	}
/*  public Cls_e_tmp Getjshz_from9_zizhu(String sfz) throws Exception {
		Cls_e_tmp tmp_jl= new Cls_e_tmp();
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/zizhu_jshz.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sfz", sfz));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error("-------Getjshz_from9_zizhu(sfz)--------");
		log.error(sfz+"的驾驶证信息："+url + "?" + str);
		try {
			HttpResponse response = Getclient.get(url, nvps);
			if (response == null) {
				log.error(sfz+"的驾驶证信息："+"查询请求失败，请检查线路。");
				tmp_jl.setNum1(0);
				tmp_jl.setS1("查询请求失败，请检查线路。");
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					log.error(sfz+"的驾驶证信息："+"查询请求失败。");
					tmp_jl.setNum1(0);
					tmp_jl.setS1("查询请求失败。");
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "gb2312"));
					StringBuffer content = new StringBuffer();
					for (String line; (line = reader.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)+ "\r\n");
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements links = doc.select("td");
					//System.out.println(links.toString());
					//log.error(sfz+"的驾驶证信息："+links.text());
						if (links.size() ==6) {
							tmp_jl.setNum1(1);
							tmp_jl.setS1(links.get(0).text());
							tmp_jl.setS2(links.get(2).text());
							tmp_jl.setS3(links.get(3).text());
							tmp_jl.setS4(links.get(4).text());
							tmp_jl.setS5(links.get(5).text());
						} else if (links.size() ==1) {
							tmp_jl.setNum1(0);
							tmp_jl.setS1(links.text());
						}  else {
							tmp_jl.setNum1(0);
						    tmp_jl.setS1("未找到返回数据！");
					    }
				}
			}
		} catch (RuntimeException e) {
			log.error(sfz+"的驾驶证信息："+e.toString());
		} catch (Exception e) { // JSon解析出错
			log.error(sfz+"的驾驶证信息："+e.toString());
		} finally {
			if (entity != null)
				entity.consumeContent();
		}
		return tmp_jl;
	}*/
	public List<Wfjl> Getwz_from9_zbga(String cphm, String sbm, String zl) throws Exception {
		ArrayList<Wfjl> rtnAL = new ArrayList<Wfjl>();
		Wfjl tmp_jl;
		String dm = "", act = "";
		String url = dzh + "/chexian/server_9/zbgachx.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cphm", cphm));
		nvps.add(new BasicNameValuePair("sbm", sbm));
		nvps.add(new BasicNameValuePair("zl", zl));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error("--------Getwz_from9_zbga(cphm,sbm,zl)--------------");
		log.error("查询"+cphm+"违章信息："+url + "?" + str);
		DefaultHttpClient client = Hclient.getHttpClient();
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				 log.error("查询"+cphm+"违章信息："+"查询请求失败，请检查线路。");
					tmp_jl = new Wfjl();
					tmp_jl.setName("查询请求失败，请检查线路。");
					rtnAL.add(tmp_jl);
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					    log.error("查询"+cphm+"违章信息："+"查询请求失败。");
						tmp_jl = new Wfjl();
						tmp_jl.setName("查询请求失败。");
						rtnAL.add(tmp_jl);
						throw new RuntimeException("请求失败"); 
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"gb2312");
		                    content = StringEscapeUtils.unescapeHtml4(content);
		                    Document doc = Jsoup.parse(content);
		                    Elements cxJg = doc.select("#cxJg");
							Elements links = cxJg.select("tr:contains(未处理)");
							Elements links2 = cxJg.select("td[colspan=7]");
							if (cxJg.size() == 1) {
								if (links.size() > 0) {
									tmp_jl = new Wfjl();
									tmp_jl.setWfdm("违法代码");
									tmp_jl.setCphm("号牌号码");
									tmp_jl.setWfxw("违法行为");
									tmp_jl.setWfaddr("违法地点");
									tmp_jl.setWfrq("违法时间");
									tmp_jl.setWfcl("处理标记");
									rtnAL.add(tmp_jl);
									for (Element link : links) {
										Elements sj = link.getElementsByTag("td");
										if (sj.size() == 7) {
											if (isNumeric(sj.get(4).text())) {
												dm = sj.get(4).text();
												act = Wfact(dm);
											} else {
												act = sj.get(4).text();
												dm = Wfdm(act);
											}
											tmp_jl = new Wfjl();
											tmp_jl.setWfdm(dm);
											tmp_jl.setCphm(sj.get(2).text());
											tmp_jl.setWfxw(act);
											tmp_jl.setWfaddr(sj.get(1).text());
											tmp_jl.setWfrq(sj.get(0).text());
											tmp_jl.setWfcl(sj.get(5).text());
											rtnAL.add(tmp_jl);
											tmp_jl = null;
										}
									}
								} else if (links2.size() > 0) {
									tmp_jl = new Wfjl();
									tmp_jl.setWfdm(links2.text());
									rtnAL.add(tmp_jl);
								}
							} else {
								tmp_jl = new Wfjl();
								tmp_jl.setWfdm("未找到返回数据！");
								rtnAL.add(tmp_jl);
							}

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
		  log.error("查询"+cphm+"违章信息："+e.toString());
	  } catch (Exception e) { 
		  log.error("查询"+cphm+"违章信息："+e.toString()); 
	   } finally {
		client.getConnectionManager().shutdown();
	   }
	   return rtnAL;	
	}
/*	public List<Wfjl> Getwz_from9_zbga(String cphm, String sbm, String zl)
			throws Exception {
		ArrayList<Wfjl> rtnAL = new ArrayList<Wfjl>();
		Wfjl tmp_jl;
		String dm = "", act = "";
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/zbgachx.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cphm", cphm));
		nvps.add(new BasicNameValuePair("sbm", sbm));
		nvps.add(new BasicNameValuePair("zl", zl));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error("--------Getwz_from9_zbga(cphm,sbm,zl)--------------");
		log.error("查询"+cphm+"违章信息："+url + "?" + str);
		try {
			HttpResponse response = Getclient.get(url, nvps);
			if (response == null) {
				log.error("查询"+cphm+"违章信息："+"查询请求失败，请检查线路。");
				tmp_jl = new Wfjl();
				tmp_jl.setName("查询请求失败，请检查线路。");
				rtnAL.add(tmp_jl);
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					log.error("查询"+cphm+"违章信息："+"查询请求失败。");
					tmp_jl = new Wfjl();
					tmp_jl.setName("查询请求失败。");
					rtnAL.add(tmp_jl);
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "gb2312"));
					StringBuffer content = new StringBuffer();
					for (String line; (line = reader.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)
								+ "\r\n");
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements cxJg = doc.select("#cxJg");
					Elements links = cxJg.select("tr:contains(未处理)");
					Elements links2 = cxJg.select("td[colspan=7]");
					if (cxJg.size() == 1) {
						if (links.size() > 0) {
							tmp_jl = new Wfjl();
							tmp_jl.setWfdm("违法代码");
							tmp_jl.setCphm("号牌号码");
							tmp_jl.setWfxw("违法行为");
							tmp_jl.setWfaddr("违法地点");
							tmp_jl.setWfrq("违法时间");
							tmp_jl.setWfcl("处理标记");
							rtnAL.add(tmp_jl);
							for (Element link : links) {
								Elements sj = link.getElementsByTag("td");
								if (sj.size() == 7) {
									if (isNumeric(sj.get(4).text())) {
										dm = sj.get(4).text();
										act = Wfact(dm);
									} else {
										act = sj.get(4).text();
										dm = Wfdm(act);
									}
									tmp_jl = new Wfjl();
									tmp_jl.setWfdm(dm);
									tmp_jl.setCphm(sj.get(2).text());
									tmp_jl.setWfxw(act);
									tmp_jl.setWfaddr(sj.get(1).text());
									tmp_jl.setWfrq(sj.get(0).text());
									tmp_jl.setWfcl(sj.get(5).text());
									rtnAL.add(tmp_jl);
									tmp_jl = null;
								}
							}
						} else if (links2.size() > 0) {
							tmp_jl = new Wfjl();
							tmp_jl.setWfdm(links2.text());
							rtnAL.add(tmp_jl);
						}
					} else {
						tmp_jl = new Wfjl();
						tmp_jl.setWfdm("未找到返回数据！");
						rtnAL.add(tmp_jl);
					}
				}
			}
		} catch (RuntimeException e) {
			log.error("查询"+cphm+"违章信息："+e.toString());
		} catch (Exception e) { // JSon解析出错
			log.error("查询"+cphm+"违章信息："+e.toString());
		} finally {
			if (entity != null)
				entity.consumeContent();
		}
		return rtnAL;
	}*/
	public List<Wfjl> Getwz_from9_zizhu(String cphm, String sbm, String zl)	throws Exception {
		ArrayList<Wfjl> rtnAL = new ArrayList<Wfjl>();
		Wfjl tmp_jl;
		String url = dzh + "/chexian/server_9/zizhuchx.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cphm", cphm));
		nvps.add(new BasicNameValuePair("sbm", sbm));
		nvps.add(new BasicNameValuePair("zl", zl));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error(url + "?" + str);
		DefaultHttpClient client = Hclient.getHttpClient();
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				 log.error("查询请求失败，请检查线路。");
					tmp_jl = new Wfjl();
					tmp_jl.setName("查询请求失败，请检查线路。");
					rtnAL.add(tmp_jl);
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					    log.error("查询请求失败。");
						tmp_jl = new Wfjl();
						tmp_jl.setName("查询请求失败。");
						rtnAL.add(tmp_jl);
						throw new RuntimeException("请求失败");
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"gb2312");
		                    content = StringEscapeUtils.unescapeHtml4(content);
		                    Document doc = Jsoup.parse(content);
		                    log.error(doc.toString());
							Elements cxJg = doc.select("#clwfxx");
							if (cxJg.size() == 1) {
								tmp_jl = new Wfjl();
								tmp_jl.setName("机动车所有人");
								tmp_jl.setCphm("号牌号码");
								tmp_jl.setWfxw("违法行为");
								tmp_jl.setWfaddr("违法地点");
								tmp_jl.setWfrq("违法时间");
								tmp_jl.setWfcl("处理标记");
								rtnAL.add(tmp_jl);
								Elements links = cxJg.select("tr:contains(未处理)");
								for (Element link : links) {
									Elements sj = link.getElementsByTag("td");
									if (sj.size() == 6) {
										tmp_jl = new Wfjl();
										tmp_jl.setName(sj.get(0).text());
										tmp_jl.setCphm(sj.get(1).text());
										tmp_jl.setWfxw(sj.get(2).text());
										tmp_jl.setWfaddr(sj.get(3).text());
										tmp_jl.setWfrq(sj.get(4).text());
										tmp_jl.setWfcl(sj.get(5).text());
										rtnAL.add(tmp_jl);
										tmp_jl = null;
									}
								}
								log.error(cxJg.text());
							} else {
								tmp_jl = new Wfjl();
								tmp_jl.setName("未找到相应的违法数据！");
								rtnAL.add(tmp_jl);
							}
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
		client.getConnectionManager().shutdown();
	   }
	   return rtnAL;	
	}
/*	public List<Wfjl> Getwz_from9_zizhu(String cphm, String sbm, String zl)
			throws Exception {
		ArrayList<Wfjl> rtnAL = new ArrayList<Wfjl>();
		Wfjl tmp_jl;
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/zizhuchx.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cphm", cphm));
		nvps.add(new BasicNameValuePair("sbm", sbm));
		nvps.add(new BasicNameValuePair("zl", zl));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error(url + "?" + str);
		try {
			HttpResponse response = Getclient.get(url, nvps);
			if (response == null) {
				log.error("查询请求失败，请检查线路。");
				tmp_jl = new Wfjl();
				tmp_jl.setName("查询请求失败，请检查线路。");
				rtnAL.add(tmp_jl);
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					log.error("查询请求失败。");
					tmp_jl = new Wfjl();
					tmp_jl.setName("查询请求失败。");
					rtnAL.add(tmp_jl);
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "gb2312"));
					StringBuffer content = new StringBuffer();
					for (String line; (line = reader.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)
								+ "\r\n");
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					log.error(doc.toString());
					Elements cxJg = doc.select("#clwfxx");
					if (cxJg.size() == 1) {
						tmp_jl = new Wfjl();
						tmp_jl.setName("机动车所有人");
						tmp_jl.setCphm("号牌号码");
						tmp_jl.setWfxw("违法行为");
						tmp_jl.setWfaddr("违法地点");
						tmp_jl.setWfrq("违法时间");
						tmp_jl.setWfcl("处理标记");
						rtnAL.add(tmp_jl);
						Elements links = cxJg.select("tr:contains(未处理)");
						for (Element link : links) {
							Elements sj = link.getElementsByTag("td");
							if (sj.size() == 6) {
								tmp_jl = new Wfjl();
								tmp_jl.setName(sj.get(0).text());
								tmp_jl.setCphm(sj.get(1).text());
								tmp_jl.setWfxw(sj.get(2).text());
								tmp_jl.setWfaddr(sj.get(3).text());
								tmp_jl.setWfrq(sj.get(4).text());
								tmp_jl.setWfcl(sj.get(5).text());
								rtnAL.add(tmp_jl);
								tmp_jl = null;
							}
						}
						log.error(cxJg.text());
					} else {
						tmp_jl = new Wfjl();
						tmp_jl.setName("未找到相应的违法数据！");
						rtnAL.add(tmp_jl);
					}
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
		return rtnAL;
	}*/
	public Cls_e_tmp Getcar_from9_zizhu(String sfz,String cphm) throws Exception {
		Cls_e_tmp tmp_jl= new Cls_e_tmp();
		String url = dzh + "/chexian/server_9/zizhu_car.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sfz", sfz));
		nvps.add(new BasicNameValuePair("cphm", cphm));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error(url + "?" + str);
		DefaultHttpClient client = Hclient.getHttpClient();
		 try {
			 HttpGet httpget = new HttpGet(url); 
			 httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
			 HttpResponse response = client.execute(httpget);  
			 if (response == null) {
				    log.error("查询请求失败，请检查线路。");
					tmp_jl.setNum1(0);
					tmp_jl.setS1("查询请求失败，请检查线路。");
			 } else {
				 if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
						log.error("查询请求失败。");
						tmp_jl.setNum1(0);
						tmp_jl.setS1("查询请求失败。");
						throw new RuntimeException("请求失败");						 
					}
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) {  
					  String content = null;
					  try {
		                    content = EntityUtils.toString(entity,"gb2312");
		                    content = StringEscapeUtils.unescapeHtml4(content);
		                    Document doc = Jsoup.parse(content);
		                    Elements links = doc.select("td");
							//System.out.println(links.toString());
							log.error(links.text());
								if (links.size() ==7) {
									if (links.get(6).text().contains("不允许办理")) {
										tmp_jl.setNum1(0);
										tmp_jl.setS1("机动车状态:"+links.get(5).text()+",不允许办理。");
									} else {
									tmp_jl.setNum1(1);
									tmp_jl.setS1(links.get(3).text());
									tmp_jl.setS2(links.get(4).text());
									tmp_jl.setS3(doc.select("[id*=hpzl]").val());
									tmp_jl.setS4(doc.select("[id*=clsbdh]").val());
									tmp_jl.setS5(links.get(5).text());
									}
								} else if (links.size() ==1) {
									tmp_jl.setNum1(0);
									tmp_jl.setS1(links.text());
								}  else {
									tmp_jl.setNum1(0);
								    tmp_jl.setS1("未找到返回数据！");
							    }

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
		client.getConnectionManager().shutdown();
	   }
	   return tmp_jl;	
	}
/*	public Cls_e_tmp Getcar_from9_zizhu(String sfz,String cphm) throws Exception {
		Cls_e_tmp tmp_jl= new Cls_e_tmp();
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/zizhu_car.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("sfz", sfz));
		nvps.add(new BasicNameValuePair("cphm", cphm));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error(url + "?" + str);
		try {
			HttpResponse response = Getclient.get(url, nvps);
			if (response == null) {
				log.error("查询请求失败，请检查线路。");
				tmp_jl.setNum1(0);
				tmp_jl.setS1("查询请求失败，请检查线路。");
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					log.error("查询请求失败。");
					tmp_jl.setNum1(0);
					tmp_jl.setS1("查询请求失败。");
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "gb2312"));
					StringBuffer content = new StringBuffer();
					for (String line; (line = reader.readLine()) != null;) {
						content.append(StringEscapeUtils.unescapeHtml4(line)+ "\r\n");
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements links = doc.select("td");
					//System.out.println(links.toString());
					log.error(links.text());
						if (links.size() ==7) {
							if (links.get(6).text().contains("不允许办理")) {
								tmp_jl.setNum1(0);
								tmp_jl.setS1("机动车状态:"+links.get(5).text()+",不允许办理。");
							} else {
							tmp_jl.setNum1(1);
							tmp_jl.setS1(links.get(3).text());
							tmp_jl.setS2(links.get(4).text());
							tmp_jl.setS3(doc.select("[id*=hpzl]").val());
							tmp_jl.setS4(doc.select("[id*=clsbdh]").val());
							tmp_jl.setS5(links.get(5).text());
							}
						} else if (links.size() ==1) {
							tmp_jl.setNum1(0);
							tmp_jl.setS1(links.text());
						}  else {
							tmp_jl.setNum1(0);
						    tmp_jl.setS1("未找到返回数据！");
					    }
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
		return tmp_jl;
	}
*/
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public String Wfact(String dm) throws Cls_exception {
		String a = dm;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			String tsql = "SELECT wfjc FROM  KCS_FKWZDM where wfdm='" + dm
					+ "'";
			pstmt = conn.prepareStatement(tsql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				a = rs.getString(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Xxchx_9.Wfact()" + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Xxchx_9.Wfact() finally"
						+ e.toString());
			}
		}
		return a;
	}

	public String Wfdm(String act) throws Cls_exception {
		String a = "";
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			String tsql = "SELECT wfdm FROM  KCS_FKWZDM where wfjc like '%"
					+ act + "%'";
			pstmt = conn.prepareStatement(tsql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				a = rs.getString(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Xxchx_9.Wfact()" + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Xxchx_9.Wfact() finally"
						+ e.toString());
			}
		}
		return a;
	}
}

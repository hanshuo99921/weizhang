package pkg_chexian;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
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
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 *运行在生产服务器，去9服务器发送查询请求
 */
public class Zbga_wzchx_ajax {
	private static Logger log = Logger.getLogger(Zbga_wzchx_ajax.class);
	String dzh = "http://10.157.0.9:7001";
	//String dzh = "http://localhost:8080";
	public String Get_from9_zbga_wzchx(String cphm, String sbm,String yzm,String zl,String cook) throws Exception {
		
		String  res = "", s_json = "", tab_s = "<table cellspacing=\\\"0\\\" cellpadding=\\\"0\\\" width=\\\"100%\\\">", tab_d = "</table>";
		String dm = "", act = "";
		int flog = 1, i = 0;
		tab_s = tab_s
				+ "<input type=\\\"hidden\\\" name=\\\"mark\\\" value=\\\"wxsdjg\\\">";
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/zbga_wzchx.jsp";
		log.error("get_from9_zbga_wzchx.java--"+cphm);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("hp", URLEncoder.encode(cphm, "gb2312")));
		nvps.add(new BasicNameValuePair("sbm", sbm));
		nvps.add(new BasicNameValuePair("yzm", yzm));		
		nvps.add(new BasicNameValuePair("zl", zl));
		nvps.add(new BasicNameValuePair("scook", cook));		
		
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps),"gb2312");
		log.error(url + "?" + str);
		DefaultHttpClient client = Hclient.getHttpClient();
		try {
			HttpGet httpget = new HttpGet(url); 
			httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));  
			HttpResponse response = client.execute(httpget);  
			if (response == null) {
				log.error("查询请求失败，请检查线路。");
				res = "<tr><td>查询请求失败，请检查线路。</td></tr>";
				flog = 1;
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					log.error("查询请求失败。");
					res = "<tr><td>查询请求失败。</td></tr>";
					flog = 1;
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					 String content = null;
					 try {
		                    content = EntityUtils.toString(entity,"gb2312");
		                    content = StringEscapeUtils.unescapeHtml4(content);
		                    Document doc = Jsoup.parse(content);
		                    Elements cxJg = doc.select("#cxJg");							
							if (cxJg.size() == 1) {
								Elements links = cxJg.select("tr:contains(未处理)");
							    Elements links2 = cxJg.select("td[colspan=7]");
							    if (links.size() > 0) {
							    	flog = 2;
									res = "<tr bgcolor=\\\"#D8D8D8\\\"><td width=\\\"15px\\\"><input type=\\\"checkbox\\\" name=\\\"allchk\\\" id=\\\"allchk\\\" onClick=test(this)></td><td width=\\\"60px\\\">违法代码</td><td width=\\\"70px\\\">号牌号码</td><td>违法行为</td><td>违法地点</td><td width=\\\"80px\\\">违法时间</td><td width=\\\"60px\\\">处理标记</td></tr>";
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
											res = res
											+ "<tr><td><input type=\\\"checkbox\\\" name=\\\"schk\\\" value=\\\""
											+ i
											+ "\\\"></td><td><input type=\\\"hidden\\\" name=\\\"wfdm\\\" value=\\\""
											+ dm
											+ "\\\">"
											+ dm
											+ "</td><td><input type=\\\"hidden\\\" name=\\\"cp\\\" value=\\\""
											+ sj.get(2).text()
											+ "\\\">"
											+ sj.get(2).text()
											+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfxw\\\" value=\\\""
											+ act
											+ "\\\">"
											+ act
											+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfaddr\\\" value=\\\""
											+ sj.get(1).text()
											+ "\\\">"
											+ sj.get(1).text()
											+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfrq\\\" value=\\\""
											+ sj.get(0).text()
											+ "\\\">"
											+ sj.get(0).text()
											+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfcl\\\" value=\\\""
											+ "未处理"
											+ "\\\">"
											+ "未处理"
											+ "</td></tr>";
									    i++;																															
										}//if = 7
									}//for
							    } else if (links2.size() > 0) 
							    	res = "<tr><td>" + links2.text() + "</td></tr>";
							 } else {							    	
							    	Elements links3 = doc.select("td[colspan=3][bgcolor=#ffffff]");
							    	if (links3.size() > 0)
							    	  res = "<tr><td>"+ links3.text()+"</td></tr>";
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
			  log.error(cphm+"的违章信息："+e.toString());
		  } catch (Exception e) { // JSon解析出错
			  log.error(cphm+"的违章信息："+e.toString());
		   } finally {
			client.getConnectionManager().shutdown();
		}
		s_json = "{\"flog\":" + flog + ",\"xxjl\":\"" + tab_s + res + tab_d
				+ "\"}";
		log.error(s_json);
		//System.out.println(res);
		return s_json;
		
	}
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

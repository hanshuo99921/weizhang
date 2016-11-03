/**
 * 运行在本服务器
 */
package pkg_chexian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wx_Xxchx_ajax {
	private static Logger log = Logger.getLogger(Wx_Xxchx_ajax.class);
	//String dzh = "http://localhost:8080";
	 String dzh = "http://10.157.0.9:7001";
	public static void main(String args[]) {
		/*	String str = "abc12jlskdf4a";
	Pattern p = Pattern.compile("[0-9\\.]+");
		Matcher m = p.matcher(str);
		ArrayList<Integer> rtnAL = new ArrayList<Integer>();
		while (m.find()) {
			rtnAL.add(Integer.parseInt(m.group()));
		}
		System.out.print(rtnAL.size());*/	
	}
   private List<Integer> GetFkDet(String str) {
	   ArrayList<Integer> rtnAL = new ArrayList<Integer>();
	   Pattern p = Pattern.compile("[0-9\\.]+");
	   Matcher m = p.matcher(str);
	   while (m.find()) {
			rtnAL.add(Integer.parseInt(m.group()));
		}
	   return rtnAL;
   }
	/**
	 * @author Administrator 去9服务器验证车辆信息是否匹配，运行在本服务器。
	 */
	public String Get_from9_wx_yzh(String cphm, String fdjh) throws Exception {
		String res = "";
		HttpEntity entity = null;
		BufferedReader reader = null;
		String line = null;
		String url = dzh + "/chexian/server_9/wx_sd_yzh.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("hp", cphm));
		nvps.add(new BasicNameValuePair("fdjh", fdjh));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
		log.error(url + "?" + str);
		try {
			HttpResponse response = Getclient.get(url, nvps);
			if (response == null) {
				log.error("查询请求失败，请检查线路。");
				res = "\"\"";
			} else {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					log.error("查询请求失败。");
					res = "\"\"";
					throw new RuntimeException("请求失败");
				}
				entity = response.getEntity();
				if (entity != null) {
					reader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "utf-8"));
					StringBuffer content = new StringBuffer();
					for (line=null; (line = reader.readLine()) != null;) {
						content.append(line);
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements links = doc.select("body");
					res = links.text();
				}
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
		return res;
	}

	public String Get_from9_wx_wzchx(String cphm, String fdjh) throws Exception {
		String  res = "", s_json = "", tab_s = "<p><table cellspacing=\\\"0\\\" cellpadding=\\\"0\\\" width=\\\"100%\\\">", tab_d = "</table>";
		String wfdd = "", nr = "";
		String[] wfnr = null;
		BufferedReader reader = null;
		String line = null;
		String wfsj = "";
		List<Integer> Lfs = null;
		int flog = 1, i = 0;
		tab_s = tab_s
				+ "<input type=\\\"hidden\\\" name=\\\"mark\\\" value=\\\"wxsdjg\\\">";
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/wx_sd_wzchx.jsp";
		log.error("getfrom9wxwzchx.java--"+cphm);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("hp", URLEncoder.encode(cphm, "utf-8")));
		nvps.add(new BasicNameValuePair("fdjh", fdjh));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps),"utf-8");
		log.error(url + "?" + str);
		try {
			HttpResponse response = Getclient.get(url, nvps);
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
					reader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "utf-8"));
					StringBuffer content = new StringBuffer();
					for (line=null; (line = reader.readLine()) != null;) {
						content.append(line);
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements cxJgs = doc.select("div.wzxq:contains(未处理)");
					Elements errJg = doc.select("table.ysm");
					if (cxJgs.size() > 0) {
						flog = 2;
						res = "<tr><td width=\\\"15px\\\"><input type=\\\"checkbox\\\" name=\\\"allchk\\\" id=\\\"allchk\\\" onClick=test(this)></td><td width=\\\"50px\\\">违法代码</td><td width=\\\"70px\\\">号牌号码</td><td>违法行为</td><td>违法地点</td><td width=\\\"80px\\\">违法时间</td><td width=\\\"50px\\\">处理标记</td></tr>";
						for (Element cxJg : cxJgs) {
							wfdd = cxJg.select("span.wzld").text();
							nr = cxJg.select("span.wznr").text();
							if (nr.contains(","))
								wfnr = cxJg.select("span.wznr").text()
										.split(",");
							if (wfnr.length==2)
								Lfs = GetFkDet(wfnr[1]);
							wfsj = cxJg.select("div.wzsj").text()
									.replace("未处理", "");
							if (Lfs.size()==2) {
								res = res
								+ "<tr><td><input type=\\\"checkbox\\\" name=\\\"schk\\\" value=\\\""
								+ i
								+ "\\\"></td><td><input type=\\\"hidden\\\" name=\\\"wfdm\\\" value=\\\""
								+ ""
								+ "\\\">"
								+ ""
								+ "</td><td><input type=\\\"hidden\\\" name=\\\"cp\\\" value=\\\""
								+ cphm
								+ "\\\">"
								+ cphm
								+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfxw\\\" value=\\\""
								+ wfnr[0]
								+ "\\\">"
								+ wfnr[0]
								+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfaddr\\\" value=\\\""
								+ wfdd
								+ "\\\">"
								+ wfdd
								+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfrq\\\" value=\\\""
								+ wfsj
								+ "\\\">"
								+ wfsj
								+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfcl\\\" value=\\\""
								+ "未处理"
								+ "\\\">"
								+ "未处理"
								+ "</td><input type=\\\"hidden\\\" name=\\\"fen\\\" value=\\\""
								+ Lfs.get(1)
								+ "\\\"><input type=\\\"hidden\\\" name=\\\"je\\\" value=\\\""
								+ Lfs.get(0) + "\\\"></tr>";
						i++;								
							}						
						}

					} else if (errJg.size() == 1) {
						res = "<tr><td>" + errJg.text() + "</td></tr>";
						flog = 1;
					} else {
						res = "<tr><td>未找到返回数据！</td></tr>";
						flog = 1;
					}
				}
			}
		} catch (RuntimeException e) {
			log.error(e.toString());
		} catch (Exception e) { // JSon解析出错
			log.error(e.toString());
		} finally {
			  nvps=null;line=null;Lfs=null;wfnr=null;wfsj=null;wfdd=null;nr=null;
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
		s_json = "{\"flog\":" + flog + ",\"xxjl\":\"" + tab_s + res + tab_d
				+ "\"}";
		log.error(s_json);
		//System.out.println(res);
		return s_json;
	}
}

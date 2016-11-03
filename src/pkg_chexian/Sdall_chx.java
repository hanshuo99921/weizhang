package pkg_chexian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 * 
 */
public class Sdall_chx {
	private static Logger log = Logger.getLogger(Sdall_chx.class);
	//static String url = "218.59.228.162:9080/wscgsxxcx/jdcwfcx.do";

	public static void main(String[] args) {
		try {
			jdcwfcx("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Wfjl> jdcwfcx(String cook) throws Exception {
		HttpEntity entity = null;
		BufferedReader reader = null;
		String line = null;
		String dm="";
		int fen=0;
		String je="";
		Cls_gg gg = new Cls_gg();
		Wfjl tmp_jl;
		ArrayList<Wfjl> rtnAL = new ArrayList<Wfjl>();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Map<String, String> map1 = new HashMap<String, String>();
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时
		try {
			HttpPost httppost = new HttpPost("http://www.wfcgs.com:9080/wscgsxxcx/jdcwfcx.do");
			httppost.setHeader(
					"Accept",
					"image/gif, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
			httppost.setHeader("Referer",
					"http://www.wfcgs.com:9080/wscgsxxcx/jdcwfcx.do");
			httppost.setHeader("Accept-Language", "zh-cn");
			httppost.setHeader(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
			httppost.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
			httppost.setHeader("Accept-Encoding", "gzip, deflate");
			httppost.setHeader("Host", "www.wfcgs.com:9080");
			httppost.setHeader("Connection", "Keep-Alive");
			httppost.setHeader("Pragma", "no-cache");
			httppost.setHeader("Cookie", "AntiLeech=2672287178");

			nvps.add(new BasicNameValuePair("hpzl", "02"));
			nvps.add(new BasicNameValuePair("fzjg", "G"));//D 枣庄
			nvps.add(new BasicNameValuePair("hphm", "99183"));
			nvps.add(new BasicNameValuePair("clsbdh", "LBEXDAEBXAX976084"));
			nvps.add(new BasicNameValuePair("type", "wfcx"));
			nvps.add(new BasicNameValuePair("state", ""));
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = client.execute(httppost);
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
					reader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "UTF-8"));
					StringBuffer content = new StringBuffer();
					for (line=null; (line = reader.readLine()) != null;) {
						content.append(line);
					}
					System.out.print(content.toString());
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements cxJgs = doc.select("table.wfresult:contains(未处罚)");
					if (cxJgs.size() > 0) {
						tmp_jl = new Wfjl();
						tmp_jl.setWfdm("违法代码");
						tmp_jl.setCphm("号牌号码");
						tmp_jl.setWfxw("违法行为");
						tmp_jl.setWfaddr("违法地点");
						tmp_jl.setWfrq("违法时间");
						tmp_jl.setWfcl("处理标记");
						rtnAL.add(tmp_jl);
						for (Element cxJg : cxJgs) {
							Elements links = cxJg.getElementsByTag("td");
							if (links.size() == 18) {
								//dm = Wfdm(links.get(13).text());
								fen = Integer.parseInt(gg.replace(links.get(17).text(),"分", ""));
								je =  gg.replace(links.get(11).text(),"元", "");
								tmp_jl = new Wfjl();
								tmp_jl.setWfdm(dm);
								tmp_jl.setCphm(links.get(3).text());
								tmp_jl.setWfxw(links.get(13).text());
								tmp_jl.setWfaddr(links.get(15).text());
								tmp_jl.setWfrq(links.get(5).text()+" "+links.get(7).text());
								tmp_jl.setWfcl(links.get(9).text());
								tmp_jl.setFen(fen);
								tmp_jl.setFkje(je);
								rtnAL.add(tmp_jl);
								tmp_jl = null;								
							}
							System.out.println(cxJg.toString());							
						}
					} else {
						Elements errJg = doc.select("[name=state]");
						if (errJg.size()==1) {
							tmp_jl = new Wfjl();
							tmp_jl.setName(errJg.attr("value"));
						}
						//System.out.println(errJg.toString());
						//System.out.println(errJg.val());
						System.out.println(errJg.attr("value"));
					}					
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
		return rtnAL;
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

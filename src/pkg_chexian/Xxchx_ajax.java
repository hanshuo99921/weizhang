package pkg_chexian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 * 运行在生产服务器
 */
public class Xxchx_ajax {
	private static Logger log = Logger.getLogger(Xxchx_ajax.class);
	//String dzh = "http://localhost:8080";
	 String dzh = "http://10.157.0.9:7001";

	public String Getwz_from9_zbga(String cphm, String sbm, String zl)
			throws Exception {
		String dm = "", act = "", res = "", s_json = "", tab_s = "<p><table cellspacing=\\\"0\\\" cellpadding=\\\"0\\\" width=\\\"100%\\\">", tab_d = "</table>";
		int flog = 1, i = 0;
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/zbgachx.jsp";
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
							flog = 2;
							res = "<tr><td width=\\\"15px\\\"><input type=\\\"checkbox\\\" name=\\\"allchk\\\" id=\\\"allchk\\\" onClick=test(this)></td><td width=\\\"50px\\\">违法代码</td><td width=\\\"70px\\\">号牌号码</td><td>违法行为</td><td>违法地点</td><td width=\\\"80px\\\">违法时间</td><td width=\\\"50px\\\">处理标记</td></tr>";
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
											+ sj.get(5).text() + "\\\">"
											+ sj.get(5).text() + "</td></tr>";
									i++;
								}
							}
						} else if (links2.size() > 0) {
							res = "<tr><td>" + links2.text() + "</td></tr>";
							flog = 1;
						}
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
			if (entity != null)
				entity.consumeContent();
		}
		s_json = "{\"flog\":" + flog + ",\"xxjl\":\"" + tab_s + res + tab_d
				+ "\"}";
		log.error(s_json);
		return s_json;
	}

	public String Getwz_from9_sdwscgs(String cphm, String sbm, String zl,
			String car_two, int city) throws Exception {
		String dm = "", res = "", s_json = "", tab_s = "<p><table cellspacing=\\\"0\\\" cellpadding=\\\"0\\\" width=\\\"100%\\\">", tab_d = "</table>";
		int flog = 1, i = 0;
		int fen = 0;
		String je = "";
		tab_s = tab_s
				+ "<input type=\\\"hidden\\\" name=\\\"mark\\\" value=\\\"sdjg\\\">";
		Cls_gg gg = new Cls_gg();
		HttpEntity entity = null;
		String url = dzh + "/chexian/server_9/sd_car.jsp";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("cphm", cphm));
		nvps.add(new BasicNameValuePair("sbm", sbm));
		nvps.add(new BasicNameValuePair("zl", zl));
		nvps.add(new BasicNameValuePair("car_two", car_two));
		nvps.add(new BasicNameValuePair("city", city + ""));
		String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
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
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "utf-8"));
					StringBuffer content = new StringBuffer();
					for (String line; (line = reader.readLine()) != null;) {
						content.append(line);
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements cxJgs = doc.select("table.wfresult:contains(未处罚)");
					Elements errJg = doc.select("table.ysm");
					if (cxJgs.size() > 0) {
						flog = 2;
						res = "<tr><td width=\\\"15px\\\"><input type=\\\"checkbox\\\" name=\\\"allchk\\\" id=\\\"allchk\\\" onClick=test(this)></td><td width=\\\"50px\\\">违法代码</td><td width=\\\"70px\\\">号牌号码</td><td>违法行为</td><td>违法地点</td><td width=\\\"80px\\\">违法时间</td><td width=\\\"50px\\\">处理标记</td></tr>";
						for (Element cxJg : cxJgs) {
							Elements links = cxJg.getElementsByTag("td");
							if (links.size() == 18) {
								fen = Integer.parseInt(gg.replace(links.get(17)
										.text(), "分", ""));
								je = gg.replace(links.get(11).text(), "元", "");
								res = res
										+ "<tr><td><input type=\\\"checkbox\\\" name=\\\"schk\\\" value=\\\""
										+ i
										+ "\\\"></td><td><input type=\\\"hidden\\\" name=\\\"wfdm\\\" value=\\\""
										+ Wfdm(links.get(13).text())
										+ "\\\">"
										+ Wfdm(links.get(13).text())
										+ "</td><td><input type=\\\"hidden\\\" name=\\\"cp\\\" value=\\\""
										+ links.get(3).text()
										+ "\\\">"
										+ links.get(3).text()
										+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfxw\\\" value=\\\""
										+ links.get(13).text()
										+ "\\\">"
										+ links.get(13).text()
										+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfaddr\\\" value=\\\""
										+ links.get(15).text()
										+ "\\\">"
										+ links.get(15).text()
										+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfrq\\\" value=\\\""
										+ links.get(5).text()
										+ " "
										+ links.get(7).text()
										+ "\\\">"
										+ links.get(5).text()
										+ " "
										+ links.get(7).text()
										+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfcl\\\" value=\\\""
										+ links.get(9).text()
										+ "\\\">"
										+ links.get(9).text()
										+ "</td><input type=\\\"hidden\\\" name=\\\"fen\\\" value=\\\""
										+ fen
										+ "\\\"><input type=\\\"hidden\\\" name=\\\"je\\\" value=\\\""
										+ je + "\\\"></tr>";
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
			if (entity != null)
				entity.consumeContent();
		}
		s_json = "{\"flog\":" + flog + ",\"xxjl\":\"" + tab_s + res + tab_d
				+ "\"}";
		log.error(s_json);
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

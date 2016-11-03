package pkg_chexian;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;
/**
 * @author Administrator
 * 
 */
public class Dunxin_tp {
	//private static String wj="C:/jboss6/server/default/deploy/hcp.war/sendmsg_tp.ini";
	private static String wj="/opt/weblogic/user_projects/domains/base_domain/servers/bmzww/chexian/sendmsg_tp.ini";
	private static Logger log = Logger.getLogger(Dunxin_tp.class);

	public  String SendXx(String hm, String nr, String citycode)
			throws Exception {
		String rets = "0", a = "0";
		IniReader rd = new IniReader();
		String st = rd.getHashValue(citycode, "IP", wj);
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);// 连接时间
		httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,8000);// 数据传输时间
		if (!st.equals("")) {
			String url = "http://" + st + "/smproxy/YXproxy_new.jsp";
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("action", "send"));
			nvps.add(new BasicNameValuePair("userid", rd.getHashValue(citycode,
					"Userid", wj)));
			nvps.add(new BasicNameValuePair("account", rd.getHashValue(
					citycode, "PAccount", wj)));
			nvps.add(new BasicNameValuePair("password", rd.getHashValue(
					citycode, "Password", wj)));
			nvps.add(new BasicNameValuePair("mobile", hm));
			nvps.add(new BasicNameValuePair("content", nr + "【山东邮政】"));
			nvps.add(new BasicNameValuePair("sendTime", ""));
			nvps.add(new BasicNameValuePair("extno", ""));
			String str = URLEncodedUtils.format(nvps, "utf-8");
			System.out.println(url + "?" + str);
			try {
				HttpGet httpget = new HttpGet(url + "?" + str);
				HttpResponse response = httpclient.execute(httpget);
				if (response == null) {
					log.error("短信请求失败，请检查线路。");
					rets = "短信请求失败，请检查线路";
				} else {
					if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
						log.error("短信请求失败。");
						rets = "短信请求失败。";
						throw new RuntimeException("请求失败");
					}
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String content = null;
						try {
							content = EntityUtils.toString(entity, "utf-8");
							Document doc = Jsoup.parse(content, "", new Parser(
									new XmlTreeBuilder()));
							Elements eles = doc.select("message");
							if (eles.text() != null)
								rets = eles.text();
							Elements yu = doc.select("remainpoint");
							 if (yu.text()!=null)
							    a = yu.text();
							log.error("remainpoin="+a);
							if (Integer.parseInt(a) < 10) {
								log.error("warn message");
								sendWarn(citycode);
								}
							EntityUtils.consume(entity);
							doc = null;

						} catch (IOException ex) {
							ex.printStackTrace();
						} catch (RuntimeException ex) {
							httpget.abort();
							ex.printStackTrace();
						} finally {
							content = null;
							httpget.abort();
							
						}
					}
				}
			} catch (ConnectException se) {
				se.printStackTrace();
				return "短信接口连接失败，暂时不能发送短信";
			} catch (ConnectTimeoutException ex) {
				ex.printStackTrace();
				return "短信接口请求超时，暂时不能发送短信";
			} catch (SocketTimeoutException ex) {
				ex.printStackTrace();
				return "短信接口请求超时，暂时不能发送短信";
			} finally {
				httpclient.getConnectionManager().shutdown();
				
			}
		} else
			rets = "读配置文件错";
		if ("ok".equals(rets))
			rets = "短信发送成功。[" + rets + "]";
		else
			rets = "短信发送失败。[" + rets + "]";
		//log.error(rets);
		return rets;
	}

	private  void sendWarn(String citycode) throws Exception {
		log.error("warn message begib");
		IniReader rd = new IniReader();
		String st = rd.getHashValue(citycode, "IP", wj);
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);// 连接时间
		httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
				3000);// 数据传输时间
		if (!st.equals("")) {
			String url = "http://" + st + "/smproxy/YXproxy_new.jsp";
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("action", "send"));
			nvps.add(new BasicNameValuePair("userid", rd.getHashValue(citycode,
					"Userid", wj)));
			nvps.add(new BasicNameValuePair("account", rd.getHashValue(
					citycode, "PAccount", wj)));
			nvps.add(new BasicNameValuePair("password", rd.getHashValue(
					citycode, "Password", wj)));
			nvps.add(new BasicNameValuePair("mobile", rd.getHashValue(citycode,
					"Wtel", wj)));
			nvps.add(new BasicNameValuePair("content", "账号"
					+ rd.getHashValue(citycode, "PAccount", wj) + "发出余额警戒提醒 。"+ "【山东邮政】"));
			nvps.add(new BasicNameValuePair("sendTime", ""));
			nvps.add(new BasicNameValuePair("extno", ""));
			String str = URLEncodedUtils.format(nvps, "utf-8");
			try {
				HttpGet httpget = new HttpGet(url + "?" + str);
				HttpResponse response = httpclient.execute(httpget);
				if (response == null) {
					log.error("短信请求失败，请检查线路。");
				} else {
					if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
						log.error("短信请求失败。");
						throw new RuntimeException("请求失败");
					}

				}

			} finally {

				httpclient.getConnectionManager().shutdown();
			}
		}
	}
}

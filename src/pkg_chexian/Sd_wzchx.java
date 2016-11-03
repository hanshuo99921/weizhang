package pkg_chexian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.jsoup.select.Elements;

/**
 * @author Administrator
 *
 */
public class Sd_wzchx {
	private static Logger log = Logger.getLogger(Sd_wzchx.class);
	static String url63 = "218.59.228.162:9080";//枣庄
	static String url80 = "221.2.145.164:9080";//威海
	static String url85 = "223.99.198.194:22";//德州
	static String url87 = "123.131.131.94:9080";//临沂
	static String url88 = "123.130.246.26:9080";//菏泽
	static String url83 = "222.134.43.251:9080";//滨州
	static String url66 = "cgs.ytjj.gov.cn:9061";//烟台
	static String url86 = "218.56.165.82";//聊城
	static String url69 = "60.213.185.51:9080";//泰安
	static String url68= "60.211.179.22:9080";//济宁
	static String url65 = "www.dygajj.gov.cn:9080";//东营
	

	public static void main(String[] args) {
		try {
			jdcwfcx("99183","LBEXDAEBXAX976084","02","G",67);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String jdcwfcx(String cphm,String sbm,String zl,String car_two,int city) throws Exception {
		String str="<table class=\"ysm\" ><tr><td>未找到数据</td></tr></table>",url="";
		HttpEntity entity = null;
		BufferedReader reader = null;
		String line = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000); // 读取超时
		if (city==63) url=url63;
		if (city==80) url=url80;
		if (city==85) url=url85;
		if (city==65) url=url65;
		try {
			HttpPost httppost = new HttpPost("http://" + url+"/wscgsxxcx/jdcwfcx.do");
			httppost.setHeader(
					"Accept",
					"image/gif, image/jpeg, image/pjpeg, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
			httppost.setHeader("Referer",
					"http://"+url+"/wscgsxxcx/jdcwfcx.do");
			httppost.setHeader("Accept-Language", "zh-cn");
			httppost.setHeader(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
			httppost.setHeader("Content-Type",
					"application/x-www-form-urlencoded");
			httppost.setHeader("Accept-Encoding", "gzip, deflate");
			httppost.setHeader("Host", url);
			httppost.setHeader("Connection", "Keep-Alive");
			httppost.setHeader("Pragma", "no-cache");
            //log.error("weche="+cphm);
			nvps.add(new BasicNameValuePair("hpzl", zl));
			nvps.add(new BasicNameValuePair("fzjg", car_two));//D 枣庄
			nvps.add(new BasicNameValuePair("hphm", cphm));
			nvps.add(new BasicNameValuePair("clsbdh", sbm));
			nvps.add(new BasicNameValuePair("type", "wfcx"));
			nvps.add(new BasicNameValuePair("state", ""));
			httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = client.execute(httppost);
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
					for (line=null; (line = reader.readLine()) != null;) {
						content.append(line);
					}
					reader.close();
					Document doc = Jsoup.parse(content.toString());
					Elements cxJgs = doc.select("table.wfresult:contains(未处罚)");
					if (cxJgs.size() > 0) {
                         str=cxJgs.toString();
					} else {
						Elements errJg = doc.select("[name=state]");
						if (errJg.size()==1) {
							str="<table class=\"ysm\" ><tr><td>"+errJg.attr("value")+"</td></tr></table>";
						}
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
		System.out.println(str);
		return str;
	}
}

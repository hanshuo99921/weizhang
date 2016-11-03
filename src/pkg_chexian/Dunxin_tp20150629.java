/**
 * 
 */
package pkg_chexian;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 *
 */
public class Dunxin_tp20150629 {
	//private static Logger log = Logger.getLogger(Dunxin_tp.class);
	//private static String wj="C:/jboss6/server/default/deploy/hcp.war/sendmsg_tp.ini";
	private static String wj="/opt/weblogic/user_projects/domains/base_domain/servers/bmzww/chexian/sendmsg_tp.ini";
	public String SendXx(String hm,String nr,String citycode) throws Exception{
		 String rets="0";
		   IniReader rd = new IniReader();
		   String st=rd.getHashValue(citycode,"IP",wj);
		   HttpClient httpclient = Hclient.getHttpClient();
		   //HttpClient httpclient = new DefaultHttpClient();
		   //httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,2000);//连接时间 
		  // httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,3000);//数据传输时间 
		   if (!st.equals("")) {
			   String url = "http://"+st+"/smproxy/YXproxy.jsp";
			   List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		           nvps.add(new BasicNameValuePair("userCode", rd.getHashValue(citycode,"PAccount",wj)));	     
		           nvps.add(new BasicNameValuePair("userPass", rd.getHashValue(citycode,"Password",wj)));
		           nvps.add(new BasicNameValuePair("DesNo", hm));
		           nvps.add(new BasicNameValuePair("Msg", nr+"【山东邮政】"));          
		           nvps.add(new BasicNameValuePair("Channel", "37"));          
		           String str =  URLEncodedUtils.format(nvps,"utf-8");
	        try {
	            HttpGet httpget = new HttpGet(url+"?"+str);
	            HttpResponse response = httpclient.execute(httpget);
	            if (response == null) {
		        	 //  log.error("短信请求失败，请检查线路。");
		        	   rets="-21";
		           } else {
		               if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		        	      //  log.error("短信请求失败。");
		        	        rets="-22";
		                    throw new RuntimeException("请求失败");   
		                }
		             HttpEntity entity = response.getEntity();   
	            if (entity != null) {
	            	String content = null;
	                try {
	                    content = EntityUtils.toString(entity,"utf-8");
	                    Document doc = Jsoup.parse(content,"", new Parser(new XmlTreeBuilder()));
	                    Elements  eles = doc.select("string");
	   	                 if (eles.text()!=null)
	   	                	 rets=eles.text();
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

	        } finally {

	            httpclient.getConnectionManager().shutdown();
	        }
		   } else
				if (rets.startsWith("-")||"0".equals(rets)) rets = this.dxfhdm(rets);
				else rets="短信发送成功。["+rets+"]";
				
		      return rets;     
	}
	private  String dxfhdm(String aa) {
		String s="";
		if (aa.startsWith("-6:")){
			s="含有关键字["+aa.replace("-6:", "")+"]";
		} else {
		long a=Long.parseLong(aa);
		if (a== -1) s="应用程序异常";
		if (a== -3) s="用户名密码错误或者用户无效";
		if (a== -5) s="签名不正确";
		if (a== -7) s="账户余额不足";
		if (a== -8) s="没有可用通道，或不在时间范围内";
		if (a== -9) s="发送号码一次不能超过1000 个";
		if (a== -10) s="号码数量大于允许上限";
		if (a== -11) s="号码数量小于允许下限";
		if (a== -12) s="模板不匹配";
		if (a== -13) s="Invalid Ip";
		if (a== -14) s="用户黑名单";
		if (a== -15) s="系统黑名单";
		if (a== -16) s="号码格式错误";
		if (a== -17) s="无效号码";
		if (a== -18) s="没有设置用户的固定下发扩展号，不能自定义扩展";
		if (a== -19) s="强制模板通道，不能使用个性化接口";		
		if (a== -21) s="短信服务器无返回,请检查线路";
		if (a== -22) s="短信请求失败";
		if (a== -23) s="读配置文件错";
		if (a==0) s="未取到系统返回值";
		}
		return s;
	}		
}

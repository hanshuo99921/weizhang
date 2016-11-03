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
		   //httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,2000);//����ʱ�� 
		  // httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,3000);//���ݴ���ʱ�� 
		   if (!st.equals("")) {
			   String url = "http://"+st+"/smproxy/YXproxy.jsp";
			   List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		           nvps.add(new BasicNameValuePair("userCode", rd.getHashValue(citycode,"PAccount",wj)));	     
		           nvps.add(new BasicNameValuePair("userPass", rd.getHashValue(citycode,"Password",wj)));
		           nvps.add(new BasicNameValuePair("DesNo", hm));
		           nvps.add(new BasicNameValuePair("Msg", nr+"��ɽ��������"));          
		           nvps.add(new BasicNameValuePair("Channel", "37"));          
		           String str =  URLEncodedUtils.format(nvps,"utf-8");
	        try {
	            HttpGet httpget = new HttpGet(url+"?"+str);
	            HttpResponse response = httpclient.execute(httpget);
	            if (response == null) {
		        	 //  log.error("��������ʧ�ܣ�������·��");
		        	   rets="-21";
		           } else {
		               if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		        	      //  log.error("��������ʧ�ܡ�");
		        	        rets="-22";
		                    throw new RuntimeException("����ʧ��");   
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
				else rets="���ŷ��ͳɹ���["+rets+"]";
				
		      return rets;     
	}
	private  String dxfhdm(String aa) {
		String s="";
		if (aa.startsWith("-6:")){
			s="���йؼ���["+aa.replace("-6:", "")+"]";
		} else {
		long a=Long.parseLong(aa);
		if (a== -1) s="Ӧ�ó����쳣";
		if (a== -3) s="�û��������������û���Ч";
		if (a== -5) s="ǩ������ȷ";
		if (a== -7) s="�˻�����";
		if (a== -8) s="û�п���ͨ��������ʱ�䷶Χ��";
		if (a== -9) s="���ͺ���һ�β��ܳ���1000 ��";
		if (a== -10) s="��������������������";
		if (a== -11) s="��������С����������";
		if (a== -12) s="ģ�岻ƥ��";
		if (a== -13) s="Invalid Ip";
		if (a== -14) s="�û�������";
		if (a== -15) s="ϵͳ������";
		if (a== -16) s="�����ʽ����";
		if (a== -17) s="��Ч����";
		if (a== -18) s="û�������û��Ĺ̶��·���չ�ţ������Զ�����չ";
		if (a== -19) s="ǿ��ģ��ͨ��������ʹ�ø��Ի��ӿ�";		
		if (a== -21) s="���ŷ������޷���,������·";
		if (a== -22) s="��������ʧ��";
		if (a== -23) s="�������ļ���";
		if (a==0) s="δȡ��ϵͳ����ֵ";
		}
		return s;
	}		
}

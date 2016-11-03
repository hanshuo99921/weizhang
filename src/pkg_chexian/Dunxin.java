package pkg_chexian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
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
public class Dunxin {
	private static Logger log = Logger.getLogger(Dunxin.class);
	public String SendXx(String hm,String nr,String citycode) throws Exception{
		   String wj="/opt/weblogic/user_projects/domains/base_domain/servers/bmzww/chexian/sendmsg.ini";
		  //String wj="C:/jboss6/server/default/deploy/chexian.war/sendmsg.ini";
		   //String wj="/oracle/weblogic/user_projects/domains/base_domain/servers/AdminServer/uploads/chexian/sendmsg.ini";
		   long a=0;
		   String rets="";
		   HttpEntity entity = null;
		   IniReader rd = new IniReader();
		   String st=rd.getHashValue(citycode,"IP",wj);
		   HttpClient httpclient = Hclient.getHttpClient();
			if (!st.equals("")) {
		   String url = "http://"+st+"/ema/person/SendSms";
		   List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		       nvps.add(new BasicNameValuePair("EAccount", rd.getHashValue(citycode,"EAccount",wj)));
	           nvps.add(new BasicNameValuePair("PAccount", rd.getHashValue(citycode,"PAccount",wj)));	     
	           nvps.add(new BasicNameValuePair("Password", rd.getHashValue(citycode,"Password",wj)));
	           nvps.add(new BasicNameValuePair("Phone", hm));
	           nvps.add(new BasicNameValuePair("Content", new String(nr.getBytes("GBK"),"iso-8859-1")));          
	           nvps.add(new BasicNameValuePair("SubCode", rd.getHashValue(citycode,"SubCode",wj)));          
	           nvps.add(new BasicNameValuePair("ProgramID", rd.getHashValue(citycode,"ProgramID",wj)));
	           nvps.add(new BasicNameValuePair("Priority", rd.getHashValue(citycode,"Priority",wj)));  
	           
	           String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps));
	         //log.error(url+"?"+str) ;
		      try {
		    	    HttpGet httpget = new HttpGet(url+"?"+str);
		            HttpResponse response = httpclient.execute(httpget);
		           if (response == null) {
		        	   log.error("��������ʧ�ܣ�������·��");
		        	   rets="��������ʧ�ܣ�������·��";
		           } else {
		               if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		        	        log.error("��������ʧ�ܡ�");
		        	        rets="��������ʧ�ܡ�";
		                    throw new RuntimeException("����ʧ��");   
		                }
		                entity = response.getEntity(); 
		                if (entity != null) {   
		           	         BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		   			         StringBuffer content = new StringBuffer();
		   			      	 for (String line; (line = reader.readLine()) != null;) {
		   				    	content.append(line + "\r\n");
		   				     } 
		   				     reader.close();
		   			         Document doc = Jsoup.parse(content.toString(),"", new Parser(new XmlTreeBuilder()));
		   	                 Elements  eles = doc.select("response");
		   	                 if (eles.text()!=null)
		    			          a=Long.parseLong(eles.text());   
		   	                 if (a>0)
		   	                       rets="���ŷ��ͳɹ���";
		   	                  else if (a<0) rets="�ŷ���ʧ��,ʧ��ԭ��"+dxfhdm(a);
		   	                  else rets="���ŷ���ʧ�ܣ�δȡ��ϵͳ����ֵ��";
		   	                 log.error(rets);
	     	           } 
		           }
		         } catch (RuntimeException e) {
		       	  log.error(e.toString());
		         } catch (Exception e) { // JSon��������
		       	  log.error(e.toString());
		         } finally {
		        	 httpclient.getConnectionManager().shutdown();
		         }    
	     	}      else rets="�������ļ���";
	      return rets;     
	}
	private String dxfhdm(long a) {
		String s="";
		if (a== -1) s="�ʺŵ�½ʧ��";
		if (a== -2) s="������ֻ���";
		if (a== -3) s="�ֻ���Ϊ�������û�";
		if (a== -4) s="�������ݲ���ȷ";
		if (a== -5) s="������Ӻ���";
		if (a== -6) s="�Ӻ��볬��";
		if (a== -7) s="�˻�����";
		if (a== -8) s="�����������";
		return s;
	}
}

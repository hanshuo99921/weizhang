/**
 * ������9������
 */
package pkg_chexian;

import org.apache.http.HttpHost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

/**
 * @author Administrator
 *
 */
public class Hclient_9 {
	public static  DefaultHttpClient getHttpClient(){
		 int TIMEOUT = 5000;//���ӳ�ʱʱ��
		 int SO_TIMEOUT = 10000;//���ݴ��䳬ʱ
		//�����˵���ClientConnectionManager�м���ManagedClientConnectionʵ��ʱʹ�õĺ��뼶�ĳ�ʱʱ��
		//������������õ�һ��java.lang.Long���͵�ֵ������������û�б����ã�Ĭ�ϵ���CONNECTION_TIMEOUT�����һ��Ҫ����
		Long CONN_MANAGER_TIMEOUT = 500L; //��ֵ�������Ӳ����õ�ʱ��ȴ���ʱʱ�䣬һ��Ҫ���ã����Ҳ���̫�� () 


		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http",80,PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

		
		HttpParams params = new BasicHttpParams();
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,TIMEOUT);
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
		params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
		//���ύ����֮ǰ ���������Ƿ���� 
		params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);



      PoolingClientConnectionManager  cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(80);
		cm.setDefaultMaxPerRoute(10);
		HttpHost wdx = new HttpHost("222.134.129.34", 80);//������ѯ��
		HttpHost sdx = new HttpHost("www.zbga.gov.cn", 80);
	    cm.setMaxPerRoute(new HttpRoute(wdx), 10);
	    cm.setMaxPerRoute(new HttpRoute(sdx), 70);
		DefaultHttpClient client = new DefaultHttpClient(cm,params);
		return client;
		
	}
}

/**
 * 运行在9服务器
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
		 int TIMEOUT = 5000;//连接超时时间
		 int SO_TIMEOUT = 10000;//数据传输超时
		//定义了当从ClientConnectionManager中检索ManagedClientConnection实例时使用的毫秒级的超时时间
		//这个参数期望得到一个java.lang.Long类型的值。如果这个参数没有被设置，默认等于CONNECTION_TIMEOUT，因此一定要设置
		Long CONN_MANAGER_TIMEOUT = 500L; //该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大 () 


		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http",80,PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

		
		HttpParams params = new BasicHttpParams();
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,TIMEOUT);
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
		params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
		//在提交请求之前 测试连接是否可用 
		params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);



      PoolingClientConnectionManager  cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(80);
		cm.setDefaultMaxPerRoute(10);
		HttpHost wdx = new HttpHost("222.134.129.34", 80);//自助查询机
		HttpHost sdx = new HttpHost("www.zbga.gov.cn", 80);
	    cm.setMaxPerRoute(new HttpRoute(wdx), 10);
	    cm.setMaxPerRoute(new HttpRoute(sdx), 70);
		DefaultHttpClient client = new DefaultHttpClient(cm,params);
		return client;
		
	}
}

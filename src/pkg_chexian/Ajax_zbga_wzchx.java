/**
 * 
 */
package pkg_chexian;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class Ajax_zbga_wzchx  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Ajax_zbga_wzchx.class);
	public Ajax_zbga_wzchx() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String retu="";

		String hp=(String)request.getParameter("hp");
		String sbm=(String)request.getParameter("sbm");
		String yzm=(String)request.getParameter("yzm");
		String zl=(String)request.getParameter("zl");
		String cook=(String)request.getParameter("zbgacook");
		log.error(hp+"/"+sbm+"/"+yzm+"/"+cook);
		//System.out.println(hp);
		//System.out.println(engine);
		Zbga_wzchx_ajax wzchx = new Zbga_wzchx_ajax();
		if ("12345".equals(hp)) {
				StringBuffer strb = new StringBuffer();
				strb.append("<table cellspacing=\\\"0\\\" cellpadding=\\\"0\\\" width=\\\"100%\\\">");
				strb.append("<input type=\\\"hidden\\\" name=\\\"mark\\\" value=\\\"wxsdjg\\\">");
				strb.append("<tr bgcolor=\\\"#D8D8D8\\\"><td width=\\\"15px\\\"><input type=\\\"checkbox\\\" name=\\\"allchk\\\" id=\\\"allchk\\\" onClick=test(this)></td><td width=\\\"60px\\\">违法代码</td><td width=\\\"70px\\\">号牌号码</td><td>违法行为</td><td>违法地点</td><td width=\\\"80px\\\">违法时间</td><td width=\\\"60px\\\">处理标记</td></tr>");
				strb.append("<tr><td><input type=\\\"checkbox\\\" name=\\\"schk\\\" value=\\\""
						+ 0
						+ "\\\"></td><td><input type=\\\"hidden\\\" name=\\\"wfdm\\\" value=\\\""
						+ 12080
						+ "\\\">"
						+ 12080
						+ "</td><td><input type=\\\"hidden\\\" name=\\\"cp\\\" value=\\\""
						+ "鲁C0320M"
						+ "\\\">"
						+ "鲁C0320M"
						+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfxw\\\" value=\\\""
						+ "不按导向车道行驶"
						+ "\\\">"
						+ "不按导向车道行驶"
						+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfaddr\\\" value=\\\""
						+ "金稀路路口000米"
						+ "\\\">"
						+ "金稀路路口000米"
						+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfrq\\\" value=\\\""
						+ "2015-08-16 12:12"
						+ "\\\">"
						+ "2015-08-16 12:12"
						+ "</td><td><input type=\\\"hidden\\\" name=\\\"wfcl\\\" value=\\\""
						+ "未处理"
						+ "\\\">"
						+ "未处理"
						+ "</td></tr>");
                  strb.append("</table>");
				
				retu = "{\"flog\":\"2\",\"xxjl\":\"" +strb.toString()
						+ "\"}";
				log.error(retu);
		} else {		
		if (!"".equals(hp)&&!"".equals(sbm)&&!"".equals(yzm)&&!"".equals(cook)) {						
			   try {
			   	log.error("Ajax_zbga_wzchx.java--"+hp);
				retu=wzchx.Get_from9_zbga_wzchx(hp, sbm,yzm,zl,cook);
				
			   } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		    	}	
			}
		}
		response.setContentType("text/x-json;charset=UTF-8");//返回json格式
		response.getWriter().write(retu); 
		response.getWriter().flush();
		response.getWriter().close(); 
	}
}

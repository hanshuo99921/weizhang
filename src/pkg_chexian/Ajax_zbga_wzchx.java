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
		if (!"".equals(hp)&&!"".equals(sbm)&&!"".equals(yzm)&&!"".equals(cook)) {
			try {
				log.error("Ajax_zbga_wzchx.java--"+hp);
				retu=wzchx.Get_from9_zbga_wzchx(hp, sbm,yzm,zl,cook);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		response.setContentType("text/x-json;charset=UTF-8");//∑µªÿjson∏Ò Ω
		response.getWriter().write(retu); 
		response.getWriter().flush();
		response.getWriter().close(); 
	}
}

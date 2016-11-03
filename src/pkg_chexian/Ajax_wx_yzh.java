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
public class Ajax_wx_yzh extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Ajax_wx_yzh.class);
	 public Ajax_wx_yzh() {
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
			String str="",hp="",fdjh="";
		    JSEscape ucode = new JSEscape();
			hp=(String)request.getParameter("hp");			
			fdjh=(String)request.getParameter("fdjh");
			//log.error(hp+"/"+fdjh);
			hp = ucode.escape(hp);
			fdjh = ucode.escape(fdjh);
			Wx_Xxchx_ajax  wzchx = new Wx_Xxchx_ajax();
			String retu="";
			//log.error(hp+"/"+fdjh);
			//System.out.println(hp);
			//System.out.println(engine);
			if (!"".equals(hp)&&!"".equals(fdjh)) {
				try {
					log.error("Ajax_wx_yzh.java--"+hp);
					str=wzchx.Get_from9_wx_yzh(hp,fdjh);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			retu = "{\"fa\":" + str+ "}";
			log.error(retu);
			response.setContentType("text/x-json;charset=gb2312");//∑µªÿjson∏Ò Ω
			response.getWriter().write(retu); 
			response.getWriter().flush();
			response.getWriter().close(); 
		}
}

package pkg_chexian;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class Ajax_wx_wzchx
 */
public class Ajax_wx_wzchx extends HttpServlet {
	private static Logger log = Logger.getLogger(Ajax_wx_wzchx.class);
	//private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajax_wx_wzchx() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		Wx_Xxchx_ajax  wzchx = new Wx_Xxchx_ajax();
		String car_province=(String)request.getParameter("car_province");
		String engine=(String)request.getParameter("engine");
		String license_plate_num=(String)request.getParameter("license_plate_num");
		String hp = car_province+license_plate_num;
		log.error(hp+"/"+engine);
		//System.out.println(hp);
		//System.out.println(engine);
		if (!"".equals(hp)&&!"".equals(engine)) {
			try {
				log.error("Ajax_wx_wzchx.java--"+hp);
				retu=wzchx.Get_from9_wx_wzchx(hp, engine);
				
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

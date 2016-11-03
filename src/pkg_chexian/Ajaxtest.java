package pkg_chexian;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Ajaxtest
 */

public class Ajaxtest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajaxtest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String retu="";
		Xxchx_ajax  wzchx = new Xxchx_ajax();
		String car_province=(String)request.getParameter("car_province");
		String car_two=(String)request.getParameter("car_two");
		String license_plate_num=(String)request.getParameter("license_plate_num");
		String province=(String)request.getParameter("province");
		String city_pinyin=(String)request.getParameter("city_pinyin");
		String body_num=(String)request.getParameter("body_num");
		String engine_num=(String)request.getParameter("engine_num");
		String zl=(String)request.getParameter("zl");
		Cls_e_tmp  che_xx = new Cls_e_tmp();
		che_xx.setS1(car_two);
		che_xx.setS2(license_plate_num);
		che_xx.setS3(body_num);
		che_xx.setS4(zl);
		try {
		if ("62".equals(city_pinyin))	
				retu=wzchx.Getwz_from9_zbga(license_plate_num, body_num, zl);
	    if (city_check(city_pinyin))
	    	    retu=wzchx.Getwz_from9_sdwscgs(license_plate_num, body_num,  zl, car_two, Integer.parseInt(city_pinyin));
	    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				che_xx = null;
			}
		//response.setContentType("text/plain;charset=utf-8"); //
		response.setContentType("text/x-json;charset=UTF-8");//∑µªÿjson∏Ò Ω
		response.getWriter().write(retu); 
		response.getWriter().flush();
		response.getWriter().close(); 

	}
	private boolean city_check(String citycode){
		boolean tag = false;
		String str="63,80,85,87,88,83,66,86,69,68,65,";
		tag = str.contains(citycode);
		return tag;
		
	}
}

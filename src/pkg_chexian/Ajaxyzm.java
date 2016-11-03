/**
 * 
 */
package pkg_chexian;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public class Ajaxyzm extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public  Ajaxyzm() {
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
		String cook="";
		String jgh=(String)request.getParameter("jgh");	
		XxChx_9 From9 = new XxChx_9();
		try {
			DES des = new DES();
			jgh = des.decrypt(jgh);
			//System.out.println(jgh);	
			cook=From9.Getjshzimg_from9_zbga(jgh);
			//System.out.println(cook);
			if (cook.contains("ASP.NET_SessionId|")) { 
				cook=cook.split("\\|")[1];
				From9.Getjshzimg_from9(jgh);
				//System.out.println(cook);

				retu= "{\"flog\":\"ok\",\"xxjl\":\"" + cook	+ "\"}";
				//System.out.println(retu);
			}  else retu= "{\"flog\":\"no\",\"xxjl\":\"" + cook	+ "\"}";
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			jgh=null;cook=null;
		}
		//response.setContentType("text/plain;charset=utf-8"); //
		response.setContentType("text/x-json;charset=UTF-8");//∑µªÿjson∏Ò Ω
		response.getWriter().write(retu); 
		response.getWriter().flush();
		response.getWriter().close(); 
	}
}

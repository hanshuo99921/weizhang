/**
 * 
 */
package pkg_chexian.freecode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * ��֤΢�������
 */
public class Ajax_WxCode extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Ajax_WxCode.class);
	  /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajax_WxCode() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String retu="";
		boolean flog = false;
		String errInfoString = "";
		String carnob=(String)request.getParameter("carnob");
		String strWxCode=(String)request.getParameter("wxcode");
		log.error(carnob+"/"+strWxCode);

		if (!"".equals(carnob)&&!"".equals(strWxCode)) {
			try {
				log.error("Ajax_WxCode.java--"+strWxCode);
				WxCode wxCode = new WxCode(strWxCode);
				if (wxCode.isPrepare()) {
					flog = true;
				} else {
					int code = wxCode.getUse_mark(strWxCode);
					switch (code) {
					case 0:
						errInfoString = "������Ч��";
						break;
					case 2:
						errInfoString = "�����ѱ�������";
						break;
					case 3:
						errInfoString = "���� �����������Ժ����Ի���ϵ����Ա��";
						break;
					default:
						errInfoString = "δ֪״̬["+code+"]������ϵ����Ա";
						break;
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			errInfoString = "δ�õ���ȷ�ĳ��ƺ�������롣";
		}
		retu= "{\"flog\":\""+flog+"\",\"info\":\"" + errInfoString	+ "\"}";
		response.setContentType("text/x-json;charset=UTF-8");//����json��ʽ
		response.getWriter().write(retu); 
		response.getWriter().flush();
		response.getWriter().close(); 
	}

}

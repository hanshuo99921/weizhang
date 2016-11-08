package pkg_chexian.freecode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import pkg_chexian.Cls_exception;

/**
 * Servlet implementation class ImgWxCode
 */
public class ImgWxCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ImgWxCode.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImgWxCode() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String org_code = request.getParameter("org_code");
		String filePath = request.getRealPath("/wximg/");
		Map<String, String> map = new HashMap<String, String>();
		boolean tag = false;
		String info = "";
		try {
			map = upload(request,org_code,filePath);
			tag = new Boolean(map.get("tag").toString());
			info = map.get("info").toString();
			log.error(org_code+"�ϴ������"+tag);
			map = null;
			if (tag) {
				File img = new File(filePath+File.separator+org_code);
				//log.error(filePath+File.separator+org_code);
				DecodeImgZxing imgZxing = new DecodeImgZxing();
				info = imgZxing.decodeImg(img);  
				if (info == null)
					tag = false;
				log.error("code="+info);
			}/**/
		} catch (Cls_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");//�����ı���ʽ
		response.getWriter().write("tag="+tag+"&info="+info); 
		response.getWriter().flush();
		response.getWriter().close(); 
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public Map<String, String> upload(HttpServletRequest request,String filename,String uploadPath )
			throws Cls_exception {
		Map<String, String> map = new HashMap<String, String>();
		String info = "";
		File fullFile = null;
		File savedFile = null;
		boolean flag = true;
		// ������������Ƿ�Ϊmultipart�����ݡ�
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// �����ǵĻ�
		if (isMultipart) {
			/** Ϊ�����󴴽�һ��DiskFileItemFactory����ͨ��������������ִ�н��������еı���Ŀ��������һ��List�С� **/
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");// ����ļ���������
				@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> itr = items.iterator();// ���еı���
					// �����ļ�
					while (itr.hasNext()) {
						FileItem item = (FileItem) itr.next();// ѭ�����ÿ������
						if (!item.isFormField()) {// ������ļ�����
							if (item.getSize() > 10) {
								String name = item.getName();// ����ļ��� ����·����
								if (name != null) {
									fullFile = new File(item.getName());
									savedFile = new File(uploadPath, filename);
									item.write(savedFile);
									info = filename;
								}
								name = null;
							} else {
								flag = false;
								info = "�������ļ�";
							}
						}
						item.delete();
					}
				
			} catch (FileUploadException e) {
				flag = false;
				info = "�ļ��ϴ�����.";
				e.printStackTrace();
			} catch (Exception e) {
				flag = false;
				info = "�ļ��ϴ�����.";
				e.printStackTrace();
			} finally {
				uploadPath = null;
				if (fullFile != null) {
					try {
						fullFile = null;
					} catch (Exception e) {
						throw new Cls_exception(
								"FileUpload.upload.fullFile.isnull()"
										+ e.toString());
					}
				}
				if (savedFile != null) {
					try {
						savedFile = null;
					} catch (Exception e) {
						throw new Cls_exception(
								"FileUpload.upload.fullFile.isnull()"
										+ e.toString());
					}
				}
			}
		} else {
			flag = false;
			info = "�����������";
			System.out.println("the enctype must be multipart/form-data");
		}
		map.put("tag", flag + "");
		map.put("info", info);
		info = null;
		return map;
	}
}

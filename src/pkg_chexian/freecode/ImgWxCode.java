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
			log.error(org_code+"上传结果："+tag);
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
		response.setContentType("text/html;charset=UTF-8");//返回文本格式
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
		// 检查输入请求是否为multipart表单数据。
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// 若果是的话
		if (isMultipart) {
			/** 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。 **/
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");// 解决文件乱码问题
				@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> itr = items.iterator();// 所有的表单项
					// 保存文件
					while (itr.hasNext()) {
						FileItem item = (FileItem) itr.next();// 循环获得每个表单项
						if (!item.isFormField()) {// 如果是文件类型
							if (item.getSize() > 10) {
								String name = item.getName();// 获得文件名 包括路径啊
								if (name != null) {
									fullFile = new File(item.getName());
									savedFile = new File(uploadPath, filename);
									item.write(savedFile);
									info = filename;
								}
								name = null;
							} else {
								flag = false;
								info = "非正常文件";
							}
						}
						item.delete();
					}
				
			} catch (FileUploadException e) {
				flag = false;
				info = "文件上传错误.";
				e.printStackTrace();
			} catch (Exception e) {
				flag = false;
				info = "文件上传错误.";
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
			info = "输入请求错误";
			System.out.println("the enctype must be multipart/form-data");
		}
		map.put("tag", flag + "");
		map.put("info", info);
		info = null;
		return map;
	}
}

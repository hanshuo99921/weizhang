package pkg_chexian;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class FileUpload {
	 private  Logger log = Logger.getLogger(FileUpload.class);
	 private static String uploadPath = null;// 上传文件的目录
	 private static int sizeMax = 1048576;
	 //初始化
	/**
	 * 上传文件 
	 * @param request
	 * @return true 上传成功 false上传失败
	 * @throws Cls_exception 
	 */
	@SuppressWarnings("unchecked")
	public Map upload(HttpServletRequest request,int xh) throws Cls_exception{
		Map map = new HashMap(); 
		uploadPath=request.getRealPath("/wzhimg");
		String ext="";
		String info="";
		File fullFile = null;
		File savedFile = null;
    	String id = request.getParameter("id");
    	String lb = request.getParameter("lb");
    	boolean flag = true;
		//检查输入请求是否为multipart表单数据。
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		//若果是的话
		if(isMultipart){
			/**为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。**/
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");//解决文件乱码问题
				upload.setSizeMax(sizeMax);// 设置最大文件尺寸
				List<FileItem> items = upload.parseRequest(request);
				// 检查是否符合上传的类型
				if(!checkFileType(items))  {
					flag=false; info="文件类型不符。";
				} else {
				Iterator<FileItem> itr = items.iterator();//所有的表单项
				//保存文件
				while (itr.hasNext()){
					 FileItem item = (FileItem) itr.next();//循环获得每个表单项
					 if (!item.isFormField()){//如果是文件类型
						  if (item.getSize()>10) {
							 String name = item.getName();//获得文件名 包括路径啊
							 if(name!=null){
								 fullFile=new File(item.getName());
								 ext = fullFile.getName().substring(fullFile.getName().lastIndexOf(".")+1).toLowerCase();
								 savedFile=new File(uploadPath, xh+"_"+id+"_"+lb+"."+ext);
								 item.write(savedFile);
								 info=xh+"_"+id+"_"+lb+"."+ext;
								 log.error(info);
							 }
						  } else {flag = false;info="非正常文件";}
					 }
					 item.delete();
				}
				}
			}catch (SizeLimitExceededException e) {
			   flag = false;info="文件大小超过限制的1M.";
			   //e.printStackTrace();		
			} catch (FileUploadException e) {
				flag = false;info="文件上传错误.";
				e.printStackTrace();
			}catch (Exception e) {
				flag = false;info="文件上传错误.";
				e.printStackTrace();
	        }finally{
	        	uploadPath=null;ext=null;
	        	if (fullFile != null) {
					try {
						fullFile = null;
					}catch (Exception e){
						throw new Cls_exception("FileUpload.upload.fullFile.isnull()" + e.toString());
					}
				}
	        	if (savedFile != null) {
					try {
						savedFile = null;
					}catch (Exception e){
						throw new Cls_exception(" FileUpload.upload.fullFile.isnull()" + e.toString());
					}
				}	        	        	
	        }
		}else{
			flag = false;info="输入请求错误";
			System.out.println("the enctype must be multipart/form-data");
		}
		map.put("tag", flag);
		map.put("info", info);
		return map;
	}
	/**
	 * 判断上传文件类型
	 * @param items
	 * @return
	 */
	private static Boolean checkFileType(List<FileItem> items){
		String allowfiletype="jpg,png"; 
		boolean isType = true;
		Iterator<FileItem> itr = items.iterator();//所有的表单项
		while (itr.hasNext()){
			 FileItem item = (FileItem) itr.next();//循环获得每个表单项
			 if (!item.isFormField()){//如果是文件类型
					 String name = item.getName();//获得文件名 包括路径啊
					 if(name!=null){
						 File fullFile=new File(item.getName());
						 String ext = fullFile.getName().substring(fullFile.getName().lastIndexOf(".")+1).toLowerCase(); 
						 if ("".equals(ext) || ext==null) 
							 isType= false; 
						 else 
						    isType =allowfiletype.contains(ext); 
						 if(!isType) return false;
						 break;
					 }
			 }
		}
		
		return true;
	}
	
}

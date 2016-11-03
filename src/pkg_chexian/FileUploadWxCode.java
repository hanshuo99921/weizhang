/**
 * 
 */
package pkg_chexian;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class FileUploadWxCode {
	 private  Logger log = Logger.getLogger(FileUploadWxCode.class);
	 private static String uploadPath = null;// �ϴ��ļ���Ŀ¼
	 private static int sizeMax = 1048576;
	 
	 /**
		 * �ж��ϴ��ļ�����
		 * @param items
		 * @return
		 */
		private static Boolean checkFileType(List<FileItem> items){
			String allowfiletype="xls"; 
			boolean isType = true;
			Iterator<FileItem> itr = items.iterator();//���еı���
			while (itr.hasNext()){
				 FileItem item = (FileItem) itr.next();//ѭ�����ÿ������
				 if (!item.isFormField()){//������ļ�����
						 String name = item.getName();//����ļ��� ����·����
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
		 //��ʼ��
		/**
		 * �ϴ��ļ� 
		 * @param request
		 * @return true �ϴ��ɹ� false�ϴ�ʧ��
		 * @throws Cls_exception 
		 */
		@SuppressWarnings("unchecked")
		public Map upload(HttpServletRequest request) throws Cls_exception{
			Map map = new HashMap(); 
			uploadPath=request.getRealPath("/wx_code/file");
			String ext="";
			String info="";
			File fullFile = null;
			File savedFile = null;
	    	String org_code = request.getParameter("org_code");
	    	//String opercode = request.getParameter("opercode");
	    	boolean flag = true;
			//������������Ƿ�Ϊmultipart�����ݡ�
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			//�����ǵĻ�
			if(isMultipart){
				/**Ϊ�����󴴽�һ��DiskFileItemFactory����ͨ��������������ִ�н��������еı���Ŀ��������һ��List�С�**/
				try {
					DiskFileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setHeaderEncoding("UTF-8");//����ļ���������
					upload.setSizeMax(sizeMax);// ��������ļ��ߴ�
					List<FileItem> items = upload.parseRequest(request);
					// ����Ƿ�����ϴ�������
					if(!checkFileType(items))  {
						flag=false; info="�ļ����Ͳ�����";
					} else {
					Iterator<FileItem> itr = items.iterator();//���еı���
					//�����ļ�
					while (itr.hasNext()){
						 FileItem item = (FileItem) itr.next();//ѭ�����ÿ������
						 if (!item.isFormField()){//������ļ�����
							  if (item.getSize()>10) {
								 String name = item.getName();//����ļ��� ����·����
								 if(name!=null){
									 fullFile=new File(item.getName());
									 ext = fullFile.getName().substring(fullFile.getName().lastIndexOf(".")+1).toLowerCase();
									 savedFile=new File(uploadPath, org_code+getSysDatetime()+"."+ext);
									 item.write(savedFile);
									 info=org_code+getSysDatetime()+"."+ext;
									 log.error(info);
								 }
							  } else {flag = false;info="�������ļ�";}
						 }
						 item.delete();
					}
					}
				}catch (SizeLimitExceededException e) {
				   flag = false;info="�ļ���С�������Ƶ�1M.";
				   //e.printStackTrace();		
				} catch (FileUploadException e) {
					flag = false;info="�ļ��ϴ�����.";
					e.printStackTrace();
				}catch (Exception e) {
					flag = false;info="�ļ��ϴ�����.";
					e.printStackTrace();
		        }finally{
		        	uploadPath=null;ext=null;
		        	if (fullFile != null) {
						try {
							fullFile = null;
						}catch (Exception e){
							throw new Cls_exception("FileUploadWxCode.upload.fullFile.isnull()" + e.toString());
						}
					}
		        	if (savedFile != null) {
						try {
							savedFile = null;
						}catch (Exception e){
							throw new Cls_exception(" FileUploadWxCode.upload.fullFile.isnull()" + e.toString());
						}
					}	        	        	
		        }
			}else{
				flag = false;info="�����������";
				System.out.println("the enctype must be multipart/form-data");
			}
			map.put("tag", flag);
			map.put("info", info);
			return map;
		}
		private String getSysDatetime() {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		    String mytime = sdf.format(new Date());
		     return mytime;
		  }
}

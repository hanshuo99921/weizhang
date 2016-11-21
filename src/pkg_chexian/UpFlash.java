/**
 * 接收flash上传图片，保存，加水印。
 */
package pkg_chexian;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class UpFlash extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UpFlash.class);
	private static int sizeMax = 512000;

	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String org_code = "", id = "", filePath = "",mark="",ddid="",lb="",filename="",info="";
		boolean tag = false,tmptag = false;
		int xh = 0;
		Cls_gg gg = new Cls_gg();
		photo pho = new photo();
		BufferedInputStream inputStream = null;
		FileOutputStream outputStream = null;
		org_code = request.getParameter("org_code");
		id = request.getParameter("id");
		mark = request.getParameter("mark");
		lb = request.getParameter("lb");
		ddid = request.getParameter("ddid");
		if (org_code == null && "".equals(org_code)) {
			log.error("UpFlash.doGet().org_code is null");
			throw new NullPointerException("UpFlash.doGet().org_code");
		} else {
			try {			
			xh=gg.getint("select ZBWZ_ID.nextval from dual");
			if ("all".equals(mark)) {
					filePath = request.getRealPath("/wzhimg/");
			        filename = xh+"_"+id+"_"+lb+".jpg";
			}
			if ("wxfk".equals(mark)) {
				    filePath = request.getRealPath("/wximg/");
				    filename = ddid+"_"+id+"_"+lb+".jpg";
			}
			if ("half".equals(mark)) {
				    filePath=request.getRealPath("/halfimg/"); 
				if ("3".equals(lb)) {
				     filename=ddid+"_"+lb+".jpg";
				  } else {
				     filename=ddid+"_"+id+"_"+lb+".jpg";
				   }
			}
			//citycode = Integer.parseInt(org_code.substring(0, 2));
				Map<String, String> map = new HashMap<String, String>();
				map = upload(request,filename,filePath);
				tag = new Boolean(map.get("tag").toString());
				info = map.get("info").toString();
				log.error(filename+"上传结果："+tag);
				map = null;
				if (tag) {
					//System.out.println(filePath+filename);
					pressText(org_code, filePath+"/"+filename, "黑体", 36,  55, 0, 60);
					gg.update("delete from kcs_fkphoto where dd_id="+ddid+" and fk_serial="+id+" and org_code='"+org_code+"' and mark="+lb);
					tmptag = pho.insert_lj(xh,id,org_code,filename,ddid,lb);
					if (!tmptag) {
						tag = false;
						info = info + ";数据库插入失败";
						log.error("数据库插入失败");
					}
				} else log.error(info);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new ServletException("UpFlash.doGet()"
						+ e1.toString());
			} finally {
				org_code = null;
				id = null;
				filePath = null;
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (Exception e) {
						throw new ServletException("UpFlash.doGet()"
								+ e.toString());
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (Exception e) {
						throw new ServletException("UpFlash.doGet()"
								+ e.toString());
					}
				}
			}
		}
		response.setContentType("text/html;charset=UTF-8");//返回文本格式
		response.getWriter().write("tag="+tag+"&info="+info); 
		//response.getWriter().write("tag=false&info=测试返回错误");
		response.getWriter().flush();
		response.getWriter().close(); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public Map<String, String> upload(HttpServletRequest request,String filename,String uploadPath )
			throws Cls_exception {
		Map<String, String> map = new HashMap<String, String>();
		//String uploadPath = request.getRealPath("/wzhimg");
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
				// factory.setSizeThreshold(sizeThreshold); // 设置缓冲区大小，这里是4kb
				// factory.setRepository(tempPathFile);// 设置缓冲区目录
				// factory.setRepository(new File(uploadPath+"/tmp"+org_code));
				// factory.setSizeThreshold(10240);
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");// 解决文件乱码问题
				upload.setSizeMax(sizeMax);// 设置最大文件尺寸
				@SuppressWarnings("unchecked")
				List<FileItem> items = upload.parseRequest(request);
				// 检查是否符合上传的类型
				if (!checkFileType(items)) {
					flag = false;
					info = "文件类型不符。";
				} else {
					Iterator<FileItem> itr = items.iterator();// 所有的表单项
					// 保存文件
					while (itr.hasNext()) {
						FileItem item = (FileItem) itr.next();// 循环获得每个表单项
						if (!item.isFormField()) {// 如果是文件类型
							if (item.getSize() > 10) {
								String name = item.getName();// 获得文件名 包括路径啊
								if (name != null) {
									fullFile = new File(item.getName());
									// ext =
									// fullFile.getName().substring(fullFile.getName().lastIndexOf(".")+1).toLowerCase();
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
				}
			} catch (SizeLimitExceededException e) {
				flag = false;
				info = "文件大小超过限制的500k.";
				// e.printStackTrace();
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

	/**
	 * 判断上传文件类型
	 * 
	 * @param items
	 * @return
	 */
	private Boolean checkFileType(List<FileItem> items) {
		String allowfiletype = "jpg";
		boolean isType = true;
		Iterator<FileItem> itr = items.iterator();// 所有的表单项
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();// 循环获得每个表单项
			if (!item.isFormField()) {// 如果是文件类型
				String name = item.getName();// 获得文件名 包括路径啊
				if (name != null) {
					File fullFile = new File(item.getName());
					String ext = fullFile.getName()
							.substring(fullFile.getName().lastIndexOf(".") + 1)
							.toLowerCase();
					fullFile = null;
					if ("".equals(ext) || ext == null)
						isType = false;
					else
						isType = allowfiletype.contains(ext);
					if (!isType)
						return false;
					break;
				}
			}
		}

		return true;
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param targetImg
	 *            目标图片
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度
	 * @throws Cls_exception 
	 */
	public void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, int fontSize, int x, int y) throws Cls_exception,IOException {
		pressText = "仅限中国邮政代办违章罚款";
		File img = null;
		Image src = null;
		BufferedImage image = null;
		Graphics2D g = null;
		try {
			img = new File(targetImg);
			if (img.exists() && img.isFile()) {
				if (img.length() > 0) {
					src = ImageIO.read(img);
					int width = src.getWidth(null);
					int height = src.getHeight(null);
					image = new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					g = image.createGraphics();
					g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
							RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g.drawImage(src, 0, 0, width, height, null);
					g.rotate(Math.toRadians(-35),
							(double) image.getWidth() / 2,
							(double) image.getHeight() / 2);
					g.setColor(Color.red);
					g.setFont(new Font(fontName, fontStyle, fontSize));
					g.setComposite(AlphaComposite.getInstance(
							AlphaComposite.SRC_ATOP, 0.3f));
					g.drawString(
							pressText,
							(width - (getLength(pressText) * fontSize)) / 2 + x,
							(height - fontSize) / 2 + y);
					// g.drawString(pressText, 5, (height - fontSize) / 2 + y);
					// g.drawString(pressText, -20, 120);
					g.dispose();
					ImageIO.write((BufferedImage) image, "jpg", img);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			if (img != null) {
				try {
					img = null;
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}
			if (src != null) {
				try {
					src = null;
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}			
			if (image != null) {
				try {
					image = null;
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}
			if (g != null) {
				try {
					g = null;
				}catch (Exception e){
					throw new Cls_exception("ImageUtils.pressText()" + e.toString());
				}
			}	
		}
	}


	public int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
}

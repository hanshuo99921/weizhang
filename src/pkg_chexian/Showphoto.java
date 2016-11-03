/**
 * 
 */
package pkg_chexian;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.sql.*;

public class Showphoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "image/jpeg";

	public Showphoto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Connection con = null;
		InputStream in = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		GZIPInputStream bos = null;
		OutputStream out = response.getOutputStream();
		String id = request.getParameter("id");
		int dd_id = Integer.parseInt(request.getParameter("dd_id"));
		String org_code = request.getParameter("orgcode");
		int lb = Integer.parseInt(request.getParameter("lb"));
		DES des;
		try {
			des = new DES();
			org_code = des.decrypt(org_code);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			if (org_code != null) {
				Cls_connect cn = new Cls_connect();
				con = cn.connect().getConnection();
				stmt = con
						.prepareStatement("select PHOTO from kcs_fkphoto where org_code=? and fk_serial=? and dd_id=? and mark=?");
				// System.out.println("select PHOTO from kcs_fkphoto where org_code="+org_code+" and fk_serial="+id+" and dd_id="+dd_id);
				stmt.setString(1, org_code);
				stmt.setString(2, id);
				stmt.setInt(3, dd_id);
				stmt.setInt(4, lb);
				rs = stmt.executeQuery();
				while (rs.next()) {
					in = rs.getBinaryStream("PHOTO");
					if (in == null) {
						continue;
					}
					bos = new GZIPInputStream(in);
					int len = 0;
					byte[] img = new byte[1024];
					while ((len = bos.read(img)) != -1) {
						out.write(img, 0, len);
					}
					bos.close();
					in.close();
					out.close();
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					throw new ServletException("photo.insert2()" + e.toString());
				}
				out = null;
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new ServletException("photo.insert2()" + e.toString());
				}
				out = null;
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new ServletException("photo.insert2()" + e.toString());
				}
				out = null;
			}
			if (rs != null)
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (stmt != null)
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
					con = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}

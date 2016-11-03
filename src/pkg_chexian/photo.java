package pkg_chexian;

//import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.zip.GZIPInputStream;
//import java.util.zip.GZIPOutputStream;
//import oracle.sql.BLOB;
//import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 
 */
public class photo {
	private static Logger log = Logger.getLogger(photo.class);

	/*	public void insert(int xh, String id, String org_code, String filepath,
			String ddid, String lb) throws Exception {
		java.sql.Connection con = null;
		PreparedStatement p = null;
		java.sql.ResultSet rs = null;
		GZIPOutputStream out = null;
		FileInputStream in = null;
		try {

			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);// 很关键，否则下面的update会抱错
			p = con.prepareStatement("INSERT INTO kcs_fkphoto (serial_no,fk_serial,ORG_CODE,PHOTO,dd_id,rq,mark)values(?,?,?,?,?,sysdate,?)");
			p.setInt(1, xh);
			p.setString(2, id);
			p.setString(3, org_code);
			p.setBlob(4, BLOB.empty_lob());// 此BLOB乃oracle.sql.BLOB类
			p.setInt(5, Integer.parseInt(ddid));
			p.setInt(6, Integer.parseInt(lb));
			p.executeUpdate();
			p.close();
			p = con.prepareStatement("select PHOTO from kcs_fkphoto where serial_no=? for update");
			// select for update的好处是先锁住此行，防止并发问题
			p.setInt(1, xh);
			rs = p.executeQuery();
			rs.next();
			oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("PHOTO");// jboss
			// weblogic.jdbc.vendor.oracle.OracleThinBlob blob =
			// (weblogic.jdbc.vendor.oracle.OracleThinBlob)rs.getBlob("PHOTO");//weblogic
			// jndi
			in = new FileInputStream(filepath);
			out = new GZIPOutputStream(blob.getBinaryOutputStream());
			byte[] buf = new byte[blob.getBufferSize()];// 缓冲的大小用这种方式获得性能最好
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
			out.close();
			in.close();
			p.close();
			con.commit();
			con.close();
		} catch (SQLException se) {
			se.printStackTrace();
			try {
				if (con != null) {
					con.rollback();// 出现sql异常，事务回滚
					con.setAutoCommit(true);// 设置提交方式为默认方式
				}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					throw new Cls_exception("photo.insert2()" + e.toString());
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					throw new Cls_exception("photo.insert2()" + e.toString());
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("photo.insert2()" + e.toString());
				}
				rs = null;
			}
			if (p != null) {
				try {
					p.close();
				} catch (Exception e) {
					throw new Cls_exception("photo.insert2()" + e.toString());
				}
				p = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("photo.insert2()" + e.toString());
				}
				con = null;
			}
		}
	}
*/
	public boolean insert_lj(int xh, String id, String org_code,
			String filepath, String ddid, String lb) throws Exception {
		boolean tag = false;
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		int a = 0;
		if (!"".equals(lb)&&lb!=null&&!"".equals(ddid)&&ddid!=null) 
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt = con
					.prepareStatement("INSERT INTO kcs_fkphoto (serial_no,fk_serial,ORG_CODE,dd_id,rq,mark,wjm)values(?,?,?,?,sysdate,?,?)");
			stmt.setInt(1, xh);
			stmt.setString(2, id);
			stmt.setString(3, org_code);
			stmt.setInt(4, Integer.parseInt(ddid));
			stmt.setInt(5, Integer.parseInt(lb));
			stmt.setString(6, filepath);
			a = stmt.executeUpdate();
			log.error("INSERT INTO kcs_fkphoto (serial_no,fk_serial,ORG_CODE,dd_id,rq,mark,wjm)values("
					+ xh
					+ ","
					+ id
					+ ","
					+ org_code
					+ ","
					+ ddid
					+ ",sysdate,"
					+ lb + "," + filepath + ")");
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				tag = true;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			log.error("gg.update.sqlexcepiton["+xh+"]"+se.toString());
			try {
				if (con != null) {
					con.rollback();// 出现sql异常，事务回滚
					con.setAutoCommit(true);// 设置提交方式为默认方式
				}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
				// log.error("gg.update.sqlexcepiton["+tsql+"]", se);
			}
		}
		
		log.error("insert_lj:"+tag);
		return tag;
	}

/*	public void insert2(int xh, String id, String org_code, String filepath,
			String ddid, String lb) throws Exception {
		Cls_connect cn = new Cls_connect();
		Connection con = cn.connect().getConnection();

		con.setAutoCommit(false);// 很关键，否则下面的update会抱错
		PreparedStatement p = null;
		p = con.prepareStatement("INSERT INTO kcs_fkphoto (serial_no,fk_serial,ORG_CODE,PHOTO,dd_id,rq,mark)values(?,?,?,?,?,sysdate,?)");
		p.setInt(1, xh);
		p.setString(2, id);
		p.setString(3, org_code);
		p.setBlob(4, BLOB.empty_lob());// 此BLOB乃oracle.sql.BLOB类
		p.setInt(5, Integer.parseInt(ddid));
		p.setInt(6, Integer.parseInt(lb));
		p.executeUpdate();
		p.close();
		p = con.prepareStatement("select PHOTO from kcs_fkphoto where serial_no=? for update");
		// select for update的好处是先锁住此行，防止并发问题
		p.setInt(1, xh);
		ResultSet rs = p.executeQuery();
		rs.next();

		weblogic.jdbc.vendor.oracle.OracleThinBlob blob = (weblogic.jdbc.vendor.oracle.OracleThinBlob) rs
				.getBlob("PHOTO");
		FileInputStream in = new FileInputStream(filepath);
		GZIPOutputStream out = new GZIPOutputStream(
				blob.getBinaryOutputStream());
		byte[] buf = new byte[blob.getBufferSize()];// 缓冲的大小用这种方式获得性能最好
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		out.flush();
		out.close();
		in.close();
		p.close();
		con.commit();
		con.close();

	}
*/
	public boolean reset_byorgcode(String org_code) throws Exception {
		StringBuffer sqlUpdate = new StringBuffer();
		sqlUpdate
				.append("delete from kcs_fkphoto where dd_id=0 and org_code='");
		sqlUpdate.append(org_code + "'");
		Cls_gg gg = new Cls_gg();
		boolean tag = gg.update(sqlUpdate.toString());
		gg = null;
		sqlUpdate = null;
		return tag;
	}

	public boolean keep_byorgcode(int dd_id, String org_code) throws Exception {
		StringBuffer sqlUpdate = new StringBuffer();
		sqlUpdate.append("update kcs_fkphoto set dd_id=" + dd_id);
		sqlUpdate.append("where dd_id=0 and org_code='" + org_code + "'");
		Cls_gg gg = new Cls_gg();
		boolean tag = gg.update(sqlUpdate.toString());
		gg = null;
		sqlUpdate = null;
		return tag;
	}

/*	public byte[] showphoto(String org_code, String id, String dd_id)
			throws Exception {
		Cls_connect cn = new Cls_connect();
		Connection con = cn.connect().getConnection();
		PreparedStatement p = null;
		p = con.prepareStatement("select PHOTO from kcs_fkphoto where org_code=? and fk_serial=? and dd_id=?");
		p.setString(2, id);
		p.setString(1, org_code);
		p.setString(3, dd_id);
		ResultSet rs = p.executeQuery();
		rs.next();

		Object obj = rs.getBlob("PHOTO");
		Class clazz = obj.getClass();
		Method method = clazz
				.getMethod("getBinaryOutputStream", new Class[] {});
		oracle.sql.BLOB blob = (oracle.sql.BLOB) method.invoke(obj,
				new Object[] {});
		// oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("PHOTO");
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		GZIPInputStream bos = new GZIPInputStream(blob.getBinaryStream());
		byte[] buf = new byte[blob.getBufferSize()];
		int len;
		while ((len = bos.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		bos.close();
		out.close();
		p.close();

		con.close();

		return out.toByteArray();

	}
*/
}

package pkg_chexian;

import java.sql.SQLException;

/**
 * 此处插入类型说明。 创建日期：(2004-8-31 13:42:48)
 * 
 * @author：Administrator
 */
public class Cls_gg {
	/**
	 * 得到记录行数。 创建日期：(2004-8-31 13:43:33)
	 * 
	 * @return int
	 * @param tsql
	 *            java.lang.String
	 * @exception pkg_yzh.Cls_exception
	 *                异常说明。
	 */
	public int getrow(String tsql) throws Cls_exception {
		int a = 0;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			tsql = "SELECT COUNT(*) FROM " + tsql;
			pstmt = conn.prepareStatement(tsql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_gg.getrow(string)   " + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.getrow(string) finally"
						+ e.toString());
			}
		}

		return a;
	}

	public int getint(String tsql) throws Cls_exception {
		int a = 0;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn.prepareStatement(tsql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			// throw new Cls_exception("Cls_gg.maxid(string)   "+e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// throw new
				// Cls_exception("Cls_gg.maxid(string) finally"+e.toString());
			}
		}

		return a;
	}

	/**
	 * 执行更新、删除、插入操作。 创建日期：(2004-8-31 13:50:53)
	 * 
	 * @return boolean
	 * @param tsql
	 *            java.lang.String
	 * @exception pkg_dpiao.Cls_exception
	 *                异常说明。
	 */
	public boolean update(String tsql) throws Exception {
		boolean tag = false;
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		int a = 0;
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(tsql);
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				tag = true;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			// log.error("gg.update.sqlexcepiton["+tsql+"]", se);
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
		return tag;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-9-7 16:30:38)
	 * 
	 * @return int
	 * @param tsql
	 *            java.lang.String
	 * @exception pkg_yzh.Cls_exception
	 *                异常说明。
	 */
	public int check(String tsql) throws Cls_exception {
		int a = 0;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			tsql = "SELECT COUNT(*) FROM" + tsql;
			pstmt = conn.prepareStatement(tsql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_gg.check  " + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.check finally" + e.toString());
			}
		}

		return a;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-9-7 14:32:54)
	 * 
	 * @return int
	 * @param tsql
	 *            java.lang.String
	 * @exception pkg_dpiao.Cls_exception
	 *                异常说明。
	 */
	public int maxid(String tsql) throws Cls_exception {
		int a = 0;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			tsql = "SELECT MAX(serial_no) FROM " + tsql;
			pstmt = conn.prepareStatement(tsql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_gg.maxid(string)   " + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.maxid(string) finally"
						+ e.toString());
			}
		}

		return a;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-7-14 15:54:49)
	 * 
	 * @return java.lang.String
	 * @param source
	 *            java.lang.String
	 * @param oldString
	 *            java.lang.String
	 * @param newString
	 *            java.lang.String
	 */
	public String replace(String source, String oldString, String newString) {
		StringBuffer output = new StringBuffer();

		int lengthOfSource = source.length(); // 源字符串长度
		int lengthOfOld = oldString.length(); // 老字符串长度
		int posStart = 0; // 开始搜索位置
		int pos; // 搜索到老字符串的位置

		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));
			output.append(newString);
			posStart = pos + lengthOfOld;
		}
		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-7-14 15:33:41)
	 * 
	 * @return java.lang.String
	 * @param iso
	 *            java.lang.String
	 * @exception pkg_gg.Cls_exception
	 *                异常说明。
	 */
	public String toGB(String iso) throws Cls_exception {
		String gb = null;

		if (iso != null) {
			try {
				gb = new String(iso.getBytes("ISO-8859-1"), "GB2312");
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.toGB" + e.toString());

			}
		}

		return gb;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-7-14 16:03:33)
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public String toHtml(String str) {
		String html = str;

		html = replace(html, "&", "&amp;");
		html = replace(html, "<", "&lt;");
		html = replace(html, ">", "&gt;");
		html = replace(html, "\r\n", "\n");
		html = replace(html, "\n", "<br>\n");
		html = replace(html, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		html = replace(html, "\\", "\\\\");
		html = replace(html, "\'", "\\\'");
		html = replace(html, "\"", "\\\"");
		html = replace(html, "  ", "&nbsp;&nbsp;"); // 两个英文空格

		return html;
	}

	public String formHtml(String str) {
		String html = str;

		html = replace(html, "&amp;", "&");
		html = replace(html, "&lt;", "<");
		html = replace(html, "&gt;", ">");
		html = replace(html, "<br>", "");
		html = replace(html, "\\\\", "\\");
		html = replace(html, "\\\'", "\'");
		html = replace(html, "\\\"", "\"");
		html = replace(html, "&nbsp;&nbsp;", "  "); // 两个英文空格

		return html;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2011-6-2 14:43:53)
	 * 
	 * @return java.lang.Double
	 * @param sql
	 *            java.lang.String
	 * @exception pkg_dpiao.Cls_exception
	 *                异常说明。
	 */
	public String getdouble(String sql) throws Cls_exception {
		double a = 0.0;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			// tsql="select count(*) from "+tsql;
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getDouble(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_gg.getdouble(string)   "
					+ e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.getdouble(string) finally"
						+ e.toString());
			}
		}

		return a + "";
	}

	/**
	 * 此处插入方法说明。 创建日期：(2010-5-27 9:37:29)
	 * 
	 * @return java.lang.String
	 * @param sql
	 *            java.lang.String
	 */
	public String getstring(String sql) throws Cls_exception {
		String a = "";
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			// tsql="select count(*) from "+tsql;
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getString(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_gg.getstring(string)   "
					+ e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.getstring(string) finally"
						+ e.toString());
			}
		}

		return a;
	}

	public int maxdd(String tsql) throws Cls_exception {
		int a = 0;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			tsql = "SELECT MAX(dd_id) FROM " + tsql;
			pstmt = conn.prepareStatement(tsql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_gg.maxid(string)   " + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.maxid(string) finally"
						+ e.toString());
			}
		}

		return a;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-7-14 15:54:49)
	 * 
	 * @return java.lang.String
	 * @param source
	 *            java.lang.String
	 * @param oldString
	 *            java.lang.String
	 * @param newString
	 *            java.lang.String
	 */
	public int minid(String tsql) throws Cls_exception {
		int a = 0;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			tsql = "SELECT MIN(serial_no) FROM " + tsql;
			pstmt = conn.prepareStatement(tsql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_gg.maxid(string)   " + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.maxid(string) finally"
						+ e.toString());
			}
		}

		return a;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-7-14 15:54:49)
	 * 
	 * @return java.lang.String
	 * @param source
	 *            java.lang.String
	 * @param oldString
	 *            java.lang.String
	 * @param newString
	 *            java.lang.String
	 */
	public String toIso(String iso) throws Cls_exception {
		String gb = null;

		if (iso != null) {
			try {
				gb = new String(iso.getBytes("GB2312"), "ISO-8859-1");
			} catch (Exception e) {
				throw new Cls_exception("Cls_gg.toIso" + e.toString());

			}
		}
		return gb;
	}
}
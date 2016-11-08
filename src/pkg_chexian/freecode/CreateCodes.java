package pkg_chexian.freecode;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import pkg_chexian.Cls_connect;
import pkg_chexian.Cls_exception;
import pkg_chexian.Cls_gg;

public class CreateCodes {
	private static Logger log = Logger.getLogger(CreateCodes.class);

	private  boolean isNewCode(String cell) {
		boolean tag = true;
		Cls_gg gg = new Cls_gg();
		try {
			int num = gg.getrow(" kcs_fk_wxcode  where wx_code='" + cell + "'");
			if (num != 0)
				tag = false;
		} catch (Cls_exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return tag;
	}

	private static String getsql(String wx_code, String jgh, String czy)
			throws Cls_exception {
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("insert into kcs_fk_wxcode(id,wx_code,in_time,oper_orgcode,oper_opercode)");
		sqlInsert.append(" values (ZBWZ_ID.nextval,'");
		sqlInsert.append(wx_code);
		sqlInsert.append("',sysdate,'");
		sqlInsert.append(jgh);
		sqlInsert.append("','");
		sqlInsert.append(czy);
		sqlInsert.append("')");
		return sqlInsert.toString();
	}

	public int createCode(int num, String jgh, String czy) {
		int isucc = 0;
		List<String> results = new ArrayList<String>();
		CodeList wx = new CodeList();
		results = wx.genCodes(num);
		String sql = "", errinfo = "";
		java.sql.Connection con = null;
		Statement stmt = null;
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement();
			for (String strCode : results) {
				if (isNewCode(strCode)) {
					sql = getsql(strCode, jgh, czy);
					stmt.addBatch(sql);
				}
			}
			int[] counts = stmt.executeBatch();
			int counts_num = counts.length;
			for (int i = 0; i < counts_num; i++) {
				if (counts[i] > 0)
					isucc = isucc + 1;

			}
			con.commit();
			stmt.close();
			con.setAutoCommit(true);
		} catch (SQLException se) {
			log.error(sql + se.toString());
			errinfo = errinfo + "导入数据异常;" + se.toString();
			se.printStackTrace();
			try {
				if (con != null) {
					con.rollback();// 出现sql异常，事务回滚
					con.setAutoCommit(true);// 设置提交方式为默认方式
				}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			errinfo = errinfo + e.toString();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException se) {
				log.error(se.toString());
				se.printStackTrace();
			}

		}

		return isucc;
	}
}

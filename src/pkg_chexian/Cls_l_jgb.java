package pkg_chexian;

import java.util.ArrayList;
import java.util.List;

/**
 * 此处插入类型说明。 创建日期：(2011-5-20 16:34:23)
 * 
 * @author：Administrator
 */
public class Cls_l_jgb {

	public java.util.Vector<Cls_e_jgb> v_tmp = new java.util.Vector<Cls_e_jgb>();

	/**
	 * 此处插入方法说明。 创建日期：(2011-5-20 16:35:09)
	 * 
	 * @return java.util.Vector
	 */
	public java.util.Vector<Cls_e_jgb> getV_tmp() {
		return v_tmp;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2011-5-20 16:37:04)
	 * 
	 * @return boolean
	 * @param tj
	 *            java.lang.String
	 * @exception pkg_dpiao.Cls_exception
	 *                异常说明。
	 */
	public boolean select_bytj(String tj) throws Cls_exception {
		boolean tag = false;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn.prepareStatement(tj);

			rs = pstmt.executeQuery();

			Cls_e_jgb tmp_jg;
			getV_tmp().removeAllElements();
			while (rs.next()) {
				tmp_jg = new Cls_e_jgb();

				tmp_jg.setSerial_no(rs.getInt("serial_no"));
				tmp_jg.setCity_code(rs.getString("city_code"));
				tmp_jg.setCoun_code(rs.getString("coun_code"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_orgname(rs.getString("oper_orgname"));
				tmp_jg.setOrg_tel(rs.getString("org_tel"));
				tmp_jg.setOrg_addr(rs.getString("org_addr"));
				tmp_jg.setQq_number(rs.getString("qq_number"));
				tmp_jg.setWork_time(rs.getString("work_time"));

				v_tmp.addElement(tmp_jg);
			}
			if (v_tmp.size() > 0) {
				tag = true;
			}

		} catch (Exception e) {
			throw new Cls_exception("Cls_l_jgb.select_bytj() " + e.toString());
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
				throw new Cls_exception("Cls_l_jgb.select_bytj()"
						+ e.toString());
			}

		}

		return tag;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2011-5-20 16:35:09)
	 * 
	 * @param newV_tmp
	 *            java.util.Vector
	 */
	public void setV_tmp(java.util.Vector<Cls_e_jgb> newV_tmp) {
		v_tmp = newV_tmp;
	}

	public List<Cls_e_jgb> select_sjb_bytj(String org_code)
			throws Cls_exception {
		ArrayList<Cls_e_jgb> rtnAL = new ArrayList<Cls_e_jgb>();
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect80 cn = new Cls_connect80();
			conn = cn.connect().getConnection();
			pstmt = conn
					.prepareStatement("select orgname as oper_orgname,org_code as oper_orgcode,org_tel,org_addr,city_org as city_code,coun_org as coun_code from esseapp.KCS_ORG_INFO where org_code=? and org_level<>'3' and org_flag='0' ");
			pstmt.setString(1, org_code);
			rs = pstmt.executeQuery();

			Cls_e_jgb tmp_jg;
			while (rs.next()) {
				tmp_jg = new Cls_e_jgb();
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_orgname(rs.getString("oper_orgname"));
				tmp_jg.setOrg_tel(rs.getString("org_tel"));
				tmp_jg.setOrg_addr(rs.getString("org_addr"));
				tmp_jg.setCity_code(rs.getString("city_code"));
				tmp_jg.setCoun_code(rs.getString("coun_code"));

				rtnAL.add(tmp_jg);
				tmp_jg = null;
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_l_jgb.select_sjb_bytj() "
					+ e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_jgb.select_sjb_bytj()"
							+ e.toString());
				}
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_jgb.select_sjb_bytj()"
							+ e.toString());
				}
				pstmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_jgb.select_sjb_bytj()"
							+ e.toString());
				}
				conn = null;
			}
		}
		return rtnAL;
	}

	public Cls_e_jgb select_byorgcode2(String jgh) throws Cls_exception {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		Cls_e_jgb tmp_jg = new Cls_e_jgb();
		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn
					.prepareStatement("select * from KCS_FKCLJGB where oper_orgcode='"
							+ jgh + "'");
			rs = pstmt.executeQuery();

			while (rs.next()) {

				tmp_jg.setSerial_no(rs.getInt("serial_no"));
				tmp_jg.setCity_code(rs.getString("city_code"));
				tmp_jg.setCoun_code(rs.getString("coun_code"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_orgname(rs.getString("oper_orgname"));
				tmp_jg.setOrg_tel(rs.getString("org_tel"));
				tmp_jg.setOrg_addr(rs.getString("org_addr"));
				tmp_jg.setQq_number(rs.getString("qq_number"));
				tmp_jg.setWork_time(rs.getString("work_time"));

			}

		} catch (Exception e) {
			throw new Cls_exception("Cls_l_jgb.select_byorgcode() "
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
				throw new Cls_exception("Cls_l_jgb.select_byorgcode()"
						+ e.toString());
			}

		}

		return tmp_jg;
	}

	public boolean select_byorgcode(String tj) throws Cls_exception {
		boolean tag = false;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn.prepareStatement(tj);

			rs = pstmt.executeQuery();

			Cls_e_jgb tmp_jg;
			getV_tmp().removeAllElements();
			while (rs.next()) {
				tmp_jg = new Cls_e_jgb();
				tmp_jg.setSerial_no(rs.getInt("serial_no"));
				tmp_jg.setCity_code(rs.getString("city_code"));
				tmp_jg.setCoun_code(rs.getString("coun_code"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_orgname(rs.getString("oper_orgname"));
				tmp_jg.setOrg_tel(rs.getString("org_tel"));
				tmp_jg.setOrg_addr(rs.getString("org_addr"));
				tmp_jg.setQq_number(rs.getString("qq_number"));
				tmp_jg.setWork_time(rs.getString("work_time"));

				v_tmp.addElement(tmp_jg);
			}
			if (v_tmp.size() > 0) {
				tag = true;
			}

		} catch (Exception e) {
			throw new Cls_exception("Cls_l_jgb.select_byorgcode() "
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
				throw new Cls_exception("Cls_l_jgb.select_byorgcode()"
						+ e.toString());
			}

		}

		return tag;
	}
}

/**
 * 
 */
package pkg_chexian;

import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class Cls_l_half {
	private static Logger log = Logger.getLogger(Cls_l_half.class);
	public int insertByObj(Cls_e_fkddmx obj, String type) throws Exception {
		if (obj == null) {
			throw new NullPointerException("Cls_e_fkddmx obj");
		}
		Cls_gg gg = new Cls_gg();
		int id = gg.getint("select ZBWZ_ID.nextval from dual");
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("insert into KCS_FKDDMX(serial_no,org_code,opercode,oper_orgcode,oper_opercode,tb_date,name,fkbs,deli_fee,tele_no,");
		sqlInsert
				.append("order_time,order_mark,proc_orgcode,org_tel,yj_fsh,carnob,jshzh,jshzh_no,yw_type,order_type,wxcode) values (");
		sqlInsert
				.append("?,?,?,?,?,?,?,?,?,?,SYSDATE,'0',?,?,?,?,?,?,?,?,?)");
		log.error("------insertByObj---ddid:"+id+"----jgh:"+obj.getOrg_code()+"------------");
		//log.error(sqlInsert.toString());
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		int a = 0, b = 0;
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(sqlInsert.toString());
			stmt.setInt(1, id);
			stmt.setString(2, obj.getOrg_code());
			stmt.setString(3, obj.getOpercode());
			stmt.setString(4, obj.getOper_orgcode());
			stmt.setString(5, obj.getOper_opercode());
			stmt.setString(6, obj.getTb_date());
			stmt.setString(7, obj.getName());
			stmt.setInt(8, obj.getFkbs());
			stmt.setDouble(9, obj.getDeli_fee());
			stmt.setString(10, obj.getTele_no());
			stmt.setString(11, obj.getProc_orgcode());
			stmt.setString(12, obj.getOrg_tel());
			stmt.setString(13, obj.getYj_fsh());
			stmt.setString(14, obj.getCarnob());
			stmt.setString(15, obj.getJshzh());
			stmt.setString(16, obj.getJshzh_no());
			stmt.setString(17, obj.getYw_type());
			stmt.setString(18, type);
			stmt.setString(19, obj.getWxcode());
			//log.error(stmt.toString());
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				b = id;
				log.error("------insertByObj---ddid:"+id+"----jgh:"+obj.getOrg_code()+"-----------成功-");
			}
		} catch (SQLException se) {
			log.error("------insertByObj---ddid:"+id+"---"+se.toString());
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
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_half.insertByObj()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_half.insertByObj()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_half.insertByObj()"
							+ e.toString());
				}
				con = null;
			}
		}
		return b;
	}
	public boolean set_fina(int serial_no,Cls_e_fkddmx obj) throws Exception {
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("update kcs_fkddmx set order_type='0',sum_price=?,fk_serial=?,id=?,fk_price=?,Deli_fee=?  where serial_no=?");

		boolean tag = false;
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		int a = 0;
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(sqlInsert.toString());
			stmt.setDouble(1, obj.getSum_price());
			stmt.setString(2, obj.getFk_serial());
			stmt.setString(3, obj.getId());
			stmt.setDouble(4, obj.getFk_price());
			stmt.setDouble(5, obj.getDeli_fee());
			stmt.setInt(6, serial_no);
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				tag = true;
			}
		} catch (SQLException se) {
			log.error(se.toString());
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
			sqlInsert = null;
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_half.set_fina()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_half.set_fina()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_half.set_fina()"
							+ e.toString());
				}
				con = null;
			}
		}
		return tag;
	}
	public Cls_e_fkddmx select_byid(int ddid) throws Cls_exception {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		Cls_e_fkddmx tmp_jg = new Cls_e_fkddmx();
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("select to_char(order_time,'yyyy-mm-dd hh24:mi:ss') as sorder_time,to_char(proce_time,'yyyy-mm-dd hh24:mi:ss') as sproce_time,");
		sqlInsert.append("org_code,opercode,oper_orgcode,oper_opercode,fkbs,fk_serial,tele_no,name,id,order_mark,order_type,order_result,proc_orgcode,proc_opercode,");
		sqlInsert.append("sum_price,fk_price,deli_fee,t_message,org_tel,carnob,jshzh,jshzh_no,zjlsh,yw_type from kcs_fkddmx t where serial_no="+ddid);
		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn.prepareStatement(sqlInsert.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tmp_jg.setOrg_code(rs.getString("org_code"));
				tmp_jg.setOpercode(rs.getString("opercode"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_opercode(rs.getString("oper_opercode"));
				tmp_jg.setFkbs(rs.getInt("fkbs"));
				tmp_jg.setFk_serial(rs.getString("fk_serial"));
				tmp_jg.setTele_no(rs.getString("tele_no"));
				tmp_jg.setName(rs.getString("name"));
				tmp_jg.setId(rs.getString("id"));
				tmp_jg.setOrder_time(rs.getString("sorder_time"));
				tmp_jg.setOrder_mark(rs.getString("order_mark"));
				tmp_jg.setProce_time(rs.getString("sproce_time"));
				tmp_jg.setOrder_type(rs.getString("order_type"));
				tmp_jg.setOrder_result(rs.getString("order_result"));
				tmp_jg.setProc_orgcode(rs.getString("proc_orgcode"));
				tmp_jg.setProc_opercode(rs.getString("proc_opercode"));
				tmp_jg.setSum_price(rs.getDouble("sum_price"));
				tmp_jg.setFk_price(rs.getDouble("fk_price"));
				tmp_jg.setDeli_fee(rs.getDouble("deli_fee"));
				tmp_jg.setT_message(rs.getString("t_message"));
				tmp_jg.setOrg_tel(rs.getString("org_tel"));
				tmp_jg.setCarnob(rs.getString("carnob"));
				tmp_jg.setJshzh(rs.getString("jshzh"));
				tmp_jg.setJshzh_no(rs.getString("jshzh_no"));
				tmp_jg.setZjlsh(rs.getString("zjlsh"));
				tmp_jg.setYw_type(rs.getString("yw_type"));
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_l_half.select_byid() "
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
				throw new Cls_exception("Cls_l_half.select_byid()"
						+ e.toString());
			}

		}

		return tmp_jg;
	}
	public boolean ddlock(int dd_id) throws Exception {
		if (dd_id == 0) {
			throw new NullPointerException("dd_id");
		}
		StringBuffer sqlUpdate = new StringBuffer();
		sqlUpdate
				.append("update kcs_fkddmx set proc_opercode='0000' where serial_no=");
		sqlUpdate.append(dd_id);
		Cls_gg gg = new Cls_gg();
		boolean tag = gg.update(sqlUpdate.toString());
		gg = null;
		sqlUpdate = null;
		return tag;
	}
}

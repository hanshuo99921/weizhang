package pkg_chexian;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class Cls_l_fkddmx {
	private static Logger log = Logger.getLogger(Cls_l_fkddmx.class);

	public boolean xg_dd(int ddid, String delid, Cls_e_fkddmx obj,
			List<Wfjl> list) throws Cls_exception {
		if (obj == null) {
			throw new NullPointerException("Cls_e_fkddmx obj");
		}
		boolean tag = false;
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		int a = 0, num = 0, i = 0;
		String sql = "";
		log.error("订单修改："+ddid);
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			sql = "update kcs_fkddmx set fkbs=?,fk_price=?,sum_price=?,fk_code=? where serial_no=?";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, obj.getFkbs());
			stmt.setDouble(2, obj.getFk_price());
			stmt.setDouble(3, obj.getSum_price());
			stmt.setString(4, obj.getFk_code());
			stmt.setInt(5, ddid);
			a = stmt.executeUpdate();
			stmt.close();
			if (a > 0) {
				tag = true;
				if (!delid.equals("")) {
					String dsno[] = delid.split(";");
					for (i = 0; i < dsno.length; i++) {
						// sql = "delete from kcs_fkxxjl where serial_no="+
						// dsno[i];
						sql = "update  kcs_fkxxjl set mark=0 where serial_no="
								+ dsno[i];
						stmt = con.prepareStatement(sql);
						a = stmt.executeUpdate();
						stmt.close();
						if (a > 0)
							num = num + 1;
					}
					if (dsno.length != num)
						tag = false;
				}
				for (i = 0; i < list.size(); i++) {
					sql = "update kcs_fkxxjl set fkje=" + list.get(i).getFkje()
							+ ",fen=" + list.get(i).getFen()
							+ " where serial_no=" + list.get(i).getId();
					stmt = con.prepareStatement(sql);
					a = stmt.executeUpdate();
					stmt.close();
				}
			}
			con.commit();
			con.setAutoCommit(true);

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
			sql=null;
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_wfjl.insertByObj()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_wfjl.insertByObj()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_wfjl.insertByObj()"
							+ e.toString());
				}
				con = null;
			}
		}
		return tag;

	}

	public List<Cls_e_fkddmx> select_bypage(String tsql, int p, int ev)
			throws Cls_exception {
		ArrayList<Cls_e_fkddmx> rtnAL = new ArrayList<Cls_e_fkddmx>();
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn
					.prepareStatement("select * from (select my_table.*,rownum as my_rownum from ("
							+ tsql
							+ ") my_table where rownum<"
							+ ev
							+ ") where my_rownum>=" + p);
			rs = pstmt.executeQuery();
			Cls_e_fkddmx tmp_jg;
			while (rs.next()) {
				tmp_jg = new Cls_e_fkddmx();
				tmp_jg.setSerial_no(rs.getInt("serial_no"));
				tmp_jg.setOrg_code(rs.getString("org_code"));
				tmp_jg.setOpercode(rs.getString("opercode"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_opercode(rs.getString("oper_opercode"));
				tmp_jg.setTb_date(rs.getString("tb_date"));
				tmp_jg.setFkbs(rs.getInt("fkbs"));
				tmp_jg.setFk_no(rs.getString("fk_no"));
				tmp_jg.setFk_serial(rs.getString("fk_serial"));
				tmp_jg.setFk_code(rs.getString("fk_code"));
				tmp_jg.setTele_no(rs.getString("tele_no"));
				tmp_jg.setAdd_message(rs.getString("add_message"));
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
				tmp_jg.setD_message(rs.getString("d_message"));
				tmp_jg.setKk_mark(rs.getString("kk_mark"));
				tmp_jg.setChf_date(rs.getString("chf_date"));
				tmp_jg.setDshr(rs.getString("dshr"));
				tmp_jg.setCarnob(rs.getString("carnob"));
				tmp_jg.setPost_addr(rs.getString("post_addr"));
				tmp_jg.setJshzh(rs.getString("jshzh"));
				tmp_jg.setJshzh_no(rs.getString("jshzh_no"));
				tmp_jg.setWf_code(rs.getString("wf_code"));
				tmp_jg.setYj_serial(rs.getString("yj_serial"));
				tmp_jg.setYj_fsh(rs.getString("yj_fsh"));
				tmp_jg.setZjlsh(rs.getString("zjlsh"));
				tmp_jg.setYw_type(rs.getString("yw_type"));
				rtnAL.add(tmp_jg);
				tmp_jg = null;
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_l_fkddmx.select_bypage() "
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
				throw new Cls_exception("Cls_l_fkddmx.select_bypage()"
						+ e.toString());
			}

		}

		return rtnAL;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2011-6-2 10:46:51)
	 * 
	 * @return boolean
	 * @param sql
	 *            java.lang.String
	 * @exception pkg_dpiao.Cls_exception
	 *                异常说明。
	 */
	public List<Cls_e_fkddmx> select_bytj(String sql) throws Cls_exception {
		ArrayList<Cls_e_fkddmx> rtnAL = new ArrayList<Cls_e_fkddmx>();
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			Cls_e_fkddmx tmp_jg;
			while (rs.next()) {
				tmp_jg = new Cls_e_fkddmx();
				tmp_jg.setSerial_no(rs.getInt("serial_no"));
				tmp_jg.setOrg_code(rs.getString("org_code"));
				tmp_jg.setOpercode(rs.getString("opercode"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_opercode(rs.getString("oper_opercode"));
				tmp_jg.setTb_date(rs.getString("tb_date"));
				tmp_jg.setFkbs(rs.getInt("fkbs"));
				tmp_jg.setFk_no(rs.getString("fk_no"));
				tmp_jg.setFk_serial(rs.getString("fk_serial"));
				tmp_jg.setFk_code(rs.getString("fk_code"));
				tmp_jg.setTele_no(rs.getString("tele_no"));
				tmp_jg.setAdd_message(rs.getString("add_message"));
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
				tmp_jg.setD_message(rs.getString("d_message"));
				tmp_jg.setOrg_tel(rs.getString("org_tel"));
				tmp_jg.setKk_mark(rs.getString("kk_mark"));
				tmp_jg.setChf_date(rs.getString("chf_date"));
				tmp_jg.setDshr(rs.getString("dshr"));
				tmp_jg.setCarnob(rs.getString("carnob"));
				tmp_jg.setPost_addr(rs.getString("post_addr"));
				tmp_jg.setJshzh(rs.getString("jshzh"));
				tmp_jg.setJshzh_no(rs.getString("jshzh_no"));
				tmp_jg.setWf_code(rs.getString("wf_code"));
				tmp_jg.setYj_serial(rs.getString("yj_serial"));
				tmp_jg.setYj_fsh(rs.getString("yj_fsh"));
				tmp_jg.setWzxx(rs.getString("wzxx"));
				tmp_jg.setZjlsh(rs.getString("zjlsh"));
				tmp_jg.setYw_type(rs.getString("yw_type"));
				rtnAL.add(tmp_jg);
				tmp_jg = null;
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_l_fkddmx.select_bytj() "
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
				throw new Cls_exception("Cls_l_fkddmx.select_bytj()"
						+ e.toString());
			}

		}

		return rtnAL;
	}

	public Cls_e_fkddmx select_byid(int ddid) throws Cls_exception {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		Cls_e_fkddmx tmp_jg = new Cls_e_fkddmx();
		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn
					.prepareStatement("select to_char(order_time,'yyyy-mm-dd hh24:mi:ss') as sorder_time,to_char(proce_time,'yyyy-mm-dd hh24:mi:ss') as sproce_time,t.*　from kcs_fkddmx t where serial_no="
							+ ddid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tmp_jg.setSerial_no(rs.getInt("serial_no"));
				tmp_jg.setOrg_code(rs.getString("org_code"));
				tmp_jg.setOpercode(rs.getString("opercode"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_opercode(rs.getString("oper_opercode"));
				tmp_jg.setTb_date(rs.getString("tb_date"));
				tmp_jg.setFkbs(rs.getInt("fkbs"));
				tmp_jg.setFk_no(rs.getString("fk_no"));
				tmp_jg.setFk_serial(rs.getString("fk_serial"));
				tmp_jg.setFk_code(rs.getString("fk_code"));
				tmp_jg.setTele_no(rs.getString("tele_no"));
				tmp_jg.setAdd_message(rs.getString("add_message"));
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
				tmp_jg.setD_message(rs.getString("d_message"));
				tmp_jg.setOrg_tel(rs.getString("org_tel"));
				tmp_jg.setKk_mark(rs.getString("kk_mark"));
				tmp_jg.setChf_date(rs.getString("chf_date"));
				tmp_jg.setDshr(rs.getString("dshr"));
				tmp_jg.setCarnob(rs.getString("carnob"));
				tmp_jg.setPost_addr(rs.getString("post_addr"));
				tmp_jg.setJshzh(rs.getString("jshzh"));
				tmp_jg.setJshzh_no(rs.getString("jshzh_no"));
				tmp_jg.setWf_code(rs.getString("wf_code"));
				tmp_jg.setYj_serial(rs.getString("yj_serial"));
				tmp_jg.setYj_fsh(rs.getString("yj_fsh"));
				tmp_jg.setWzxx(rs.getString("wzxx"));
				tmp_jg.setZjlsh(rs.getString("zjlsh"));
				tmp_jg.setYw_type(rs.getString("yw_type"));
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_l_fkddmx.select_byid() "
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
				throw new Cls_exception("Cls_l_fkddmx.select_byid()"
						+ e.toString());
			}

		}

		return tmp_jg;
	}

	public Cls_e_fkddmx select_byid(String sql) throws Cls_exception {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		Cls_e_fkddmx tmp_jg = new Cls_e_fkddmx();
		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tmp_jg.setSerial_no(rs.getInt("serial_no"));
				tmp_jg.setOrg_code(rs.getString("org_code"));
				tmp_jg.setOpercode(rs.getString("opercode"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setOper_opercode(rs.getString("oper_opercode"));
				tmp_jg.setTb_date(rs.getString("tb_date"));
				tmp_jg.setFkbs(rs.getInt("fkbs"));
				tmp_jg.setFk_no(rs.getString("fk_no"));
				tmp_jg.setFk_serial(rs.getString("fk_serial"));
				tmp_jg.setFk_code(rs.getString("fk_code"));
				tmp_jg.setTele_no(rs.getString("tele_no"));
				tmp_jg.setAdd_message(rs.getString("add_message"));
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
				tmp_jg.setD_message(rs.getString("d_message"));
				tmp_jg.setOrg_tel(rs.getString("org_tel"));
				tmp_jg.setKk_mark(rs.getString("kk_mark"));
				tmp_jg.setChf_date(rs.getString("chf_date"));
				tmp_jg.setDshr(rs.getString("dshr"));
				tmp_jg.setCarnob(rs.getString("carnob"));
				tmp_jg.setPost_addr(rs.getString("post_addr"));
				tmp_jg.setJshzh(rs.getString("jshzh"));
				tmp_jg.setJshzh_no(rs.getString("jshzh_no"));
				tmp_jg.setWf_code(rs.getString("wf_code"));
				tmp_jg.setYj_serial(rs.getString("yj_serial"));
				tmp_jg.setYj_fsh(rs.getString("yj_fsh"));
				tmp_jg.setWzxx(rs.getString("wzxx"));
				tmp_jg.setZjlsh(rs.getString("zjlsh"));
				tmp_jg.setYw_type(rs.getString("yw_type"));
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_l_fkddmx.select_byid() "
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
				throw new Cls_exception("Cls_l_fkddmx.select_byid()"
						+ e.toString());
			}

		}

		return tmp_jg;
	}

	public int insertByObj(Cls_e_fkddmx obj) throws Exception {
		if (obj == null) {
			throw new NullPointerException("Cls_e_fkddmx obj");
		}
		Cls_gg gg = new Cls_gg();
		int id = gg.getint("select ZBWZ_ID.nextval from dual");
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("insert into KCS_FKDDMX(serial_no,org_code,opercode,oper_orgcode,oper_opercode,tb_date,name,fkbs,fk_no,fk_serial,fk_code,id,deli_fee,sum_price,tele_no,add_message,");
		sqlInsert
				.append("order_time,order_mark,proc_orgcode,d_message,org_tel,yj_fsh,chf_date,dshr,carnob,wf_code,jshzh,jshzh_no,post_addr,fk_price,wzxx,yw_type) values (");
		sqlInsert
				.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'0',?,?,?,?,?,?,");
		sqlInsert.append("?,?,?,?,?,?,?,?)");
		//log.error("----insertByObj---ddid:"+id+"--jgh:"+obj.getOrg_code()+"--");
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
			stmt.setString(9, obj.getFk_no());
			stmt.setString(10, obj.getFk_serial());
			stmt.setString(11, obj.getFk_code());
			stmt.setString(12, obj.getId());
			stmt.setDouble(13, obj.getDeli_fee());
			stmt.setDouble(14, obj.getSum_price());
			stmt.setString(15, obj.getTele_no());
			stmt.setString(16, obj.getAdd_message());
			stmt.setString(17, obj.getProc_orgcode());
			stmt.setString(18, obj.getD_message());
			stmt.setString(19, obj.getOrg_tel());
			stmt.setString(20, obj.getYj_fsh());
			stmt.setString(21, obj.getChf_date());
			stmt.setString(22, obj.getDshr());
			stmt.setString(23, obj.getCarnob());
			stmt.setString(24, obj.getWf_code());
			stmt.setString(25, obj.getJshzh());
			stmt.setString(26, obj.getJshzh_no());
			stmt.setString(27, obj.getPost_addr());
			stmt.setDouble(28, obj.getFk_price());
			stmt.setString(29, obj.getWzxx());
			stmt.setString(30, obj.getYw_type());
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				b = id;
			}
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
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				con = null;
			}
		}
		return b;
	}

	public int insertByObj2(Cls_e_fkddmx obj, String type) throws Exception {
		if (obj == null) {
			throw new NullPointerException("Cls_e_fkddmx obj");
		}
		Cls_gg gg = new Cls_gg();
		int id = gg.getint("select ZBWZ_ID.nextval from dual");
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("insert into KCS_FKDDMX(serial_no,org_code,opercode,oper_orgcode,oper_opercode,tb_date,name,fkbs,fk_no,fk_serial,fk_code,id,deli_fee,sum_price,tele_no,add_message,");
		sqlInsert
				.append("order_time,order_mark,proc_orgcode,d_message,org_tel,yj_fsh,chf_date,dshr,carnob,wf_code,jshzh,jshzh_no,post_addr,fk_price,wzxx,yw_type,order_type) values (");
		sqlInsert
				.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'0',?,?,?,?,?,?,");
		sqlInsert.append("?,?,?,?,?,?,?,?,?)");
		//log.error("----insertByObj2(obj,type)---ddid:"+id+"--jgh:"+obj.getOrg_code()+"--");
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
			stmt.setString(9, obj.getFk_no());
			stmt.setString(10, obj.getFk_serial());
			stmt.setString(11, obj.getFk_code());
			stmt.setString(12, obj.getId());
			stmt.setDouble(13, obj.getDeli_fee());
			stmt.setDouble(14, obj.getSum_price());
			stmt.setString(15, obj.getTele_no());
			stmt.setString(16, obj.getAdd_message());
			stmt.setString(17, obj.getProc_orgcode());
			stmt.setString(18, obj.getD_message());
			stmt.setString(19, obj.getOrg_tel());
			stmt.setString(20, obj.getYj_fsh());
			stmt.setString(21, obj.getChf_date());
			stmt.setString(22, obj.getDshr());
			stmt.setString(23, obj.getCarnob());
			stmt.setString(24, obj.getWf_code());
			stmt.setString(25, obj.getJshzh());
			stmt.setString(26, obj.getJshzh_no());
			stmt.setString(27, obj.getPost_addr());
			stmt.setDouble(28, obj.getFk_price());
			stmt.setString(29, obj.getWzxx());
			stmt.setString(30, obj.getYw_type());
			stmt.setString(31, type);
			//log.error(stmt.toString());
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				b = id;
			}
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
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				con = null;
			}
		}
		return b;
	}

	public int insertByObj2(Cls_e_fkddmx obj) throws Exception {
		if (obj == null) {
			throw new NullPointerException("Cls_e_fkddmx obj");
		}
		Cls_gg gg = new Cls_gg();
		int id = gg.getint("select ZBWZ_ID.nextval from dual");
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("insert into KCS_FKDDMX(serial_no,org_code,opercode,oper_orgcode,oper_opercode,tb_date,name,fkbs,fk_no,fk_serial,fk_code,id,deli_fee,sum_price,tele_no,add_message,");
		sqlInsert
				.append("order_time,order_mark,proc_orgcode,d_message,org_tel,yj_fsh,chf_date,dshr,carnob,wf_code,jshzh,jshzh_no,post_addr,fk_price,wzxx,yw_type,order_type,wxcode) values (");
		sqlInsert
				.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'0',?,?,?,?,?,?,");
		sqlInsert.append("?,?,?,?,?,?,?,?,?,?)");
		log.error("----insertByObj2(obj)---ddid:"+id+"--jgh:"+obj.getOrg_code()+"--");
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
			stmt.setString(9, obj.getFk_no());
			stmt.setString(10, obj.getFk_serial());
			stmt.setString(11, obj.getFk_code());
			stmt.setString(12, obj.getId());
			stmt.setDouble(13, obj.getDeli_fee());
			stmt.setDouble(14, obj.getSum_price());
			stmt.setString(15, obj.getTele_no());
			stmt.setString(16, obj.getAdd_message());
			stmt.setString(17, obj.getProc_orgcode());
			stmt.setString(18, obj.getD_message());
			stmt.setString(19, obj.getOrg_tel());
			stmt.setString(20, obj.getYj_fsh());
			stmt.setString(21, obj.getChf_date());
			stmt.setString(22, obj.getDshr());
			stmt.setString(23, obj.getCarnob());
			stmt.setString(24, obj.getWf_code());
			stmt.setString(25, obj.getJshzh());
			stmt.setString(26, obj.getJshzh_no());
			stmt.setString(27, obj.getPost_addr());
			stmt.setDouble(28, obj.getFk_price());
			stmt.setString(29, obj.getWzxx());
			stmt.setString(30, obj.getYw_type());
			stmt.setString(31, "0");
			stmt.setString(32, obj.getWxcode());
			//log.error(stmt.toString());
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				b = id;
				log.error("----insertByObj2(obj)---ddid:"+id+"--jgh:"+obj.getOrg_code()+"--成功");
			}
		} catch (SQLException se) {
			log.error("----insertByObj2(obj)---"+se.toString());
			se.printStackTrace();
			try {
				if (con != null) {
					con.rollback();// 出现sql异常，事务回滚
					con.setAutoCommit(true);// 设置提交方式为默认方式
				}
			} catch (SQLException se1) {
				log.error("----insertByObj2(obj)---"+se1.toString());
				se1.printStackTrace();
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.insertByObj()"
							+ e.toString());
				}
				con = null;
			}
		}
		return b;
	}

	public boolean set_fina(int serial_no, String orgtel) throws Exception {
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("update kcs_fkddmx set order_type='0',org_tel=? where serial_no=?");

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
			stmt.setString(1, orgtel);
			stmt.setInt(2, serial_no);
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				tag = true;
			}
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
			sqlInsert = null;
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.set_fina()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.set_fina()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.set_fina()"
							+ e.toString());
				}
				con = null;
			}
		}
		return tag;
	}

	public boolean del_byid(int ddid, String org_code) throws Exception {
		StringBuffer sqlUpdate = new StringBuffer();
		sqlUpdate.append("delete from kcs_fkddmx  where serial_no=" + ddid
				+ " and org_code='");
		sqlUpdate.append(org_code + "'");
		log.error("数据同步失败撤销订单："+sqlUpdate.toString());
		Cls_gg gg = new Cls_gg();
		boolean tag = gg.update(sqlUpdate.toString());
		gg = null;
		sqlUpdate = null;
		return tag;
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

	public boolean set_mailno(int ddid, String mailno) throws Exception {
		if (ddid == 0) {
			throw new NullPointerException("dd_id");
		}
		StringBuffer sqlUpdate = new StringBuffer();
		sqlUpdate.append("update kcs_fkddmx set yj_serial='" + mailno
				+ "' where serial_no=");
		sqlUpdate.append(ddid);
		Cls_gg gg = new Cls_gg();
		boolean tag = gg.update(sqlUpdate.toString());
		gg = null;
		sqlUpdate = null;
		return tag;

	}

	public boolean set_hx(int ddid) throws Exception {
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("update kcs_fkddmx set order_type='6'  where serial_no=?");
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
			stmt.setInt(1, ddid);
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a > 0) {
				tag = true;
			}
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
			sqlInsert = null;
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.set_hx()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.set_hx()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.set_hx()"
							+ e.toString());
				}
				con = null;
			}
		}
		return tag;
	}

	public List<Cls_e_fkddmx> x_bypage(String tsql, int p, int ev)
			throws Cls_exception {
		StringBuffer sqlUpdate = new StringBuffer();
		ArrayList<Cls_e_fkddmx> rtnAL = new ArrayList<Cls_e_fkddmx>();
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		sqlUpdate
				.append("select serial_no,org_code,oper_orgcode,yw_type,dshr,name,tb_date,carnob,fk_price,yj_fsh,deli_fee,kk_mark,order_type,tele_no,order_mark,post_addr,yj_serial  from kcs_fkddmx ");
		tsql = sqlUpdate.toString() + tsql;
		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn
					.prepareStatement("select * from (select my_table.*,rownum as my_rownum from ("
							+ tsql
							+ ") my_table where rownum<"
							+ ev
							+ ") where my_rownum>=" + p);
			rs = pstmt.executeQuery();
			Cls_e_fkddmx tmp_jg;
			while (rs.next()) {
				tmp_jg = new Cls_e_fkddmx();
				tmp_jg.setSerial_no(rs.getInt("serial_no"));
				tmp_jg.setOrg_code(rs.getString("org_code"));
				tmp_jg.setOper_orgcode(rs.getString("oper_orgcode"));
				tmp_jg.setYw_type(rs.getString("yw_type"));
				tmp_jg.setDshr(rs.getString("dshr"));
				tmp_jg.setName(rs.getString("name"));
				tmp_jg.setTb_date(rs.getString("tb_date"));
				tmp_jg.setCarnob(rs.getString("carnob"));
				tmp_jg.setFk_price(rs.getDouble("fk_price"));
				tmp_jg.setYj_fsh(rs.getString("yj_fsh"));
				tmp_jg.setDeli_fee(rs.getDouble("deli_fee"));
				tmp_jg.setKk_mark(rs.getString("kk_mark"));
				tmp_jg.setOrder_type(rs.getString("order_type"));
				tmp_jg.setTele_no(rs.getString("tele_no"));
				tmp_jg.setOrder_mark(rs.getString("order_mark"));
				tmp_jg.setPost_addr(rs.getString("post_addr"));
                tmp_jg.setYj_serial(rs.getString("yj_serial"));
				rtnAL.add(tmp_jg);
				tmp_jg = null;
			}
		} catch (Exception e) {
			throw new Cls_exception("Cls_l_fkddmx.x_bypage() " + e.toString());
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
				throw new Cls_exception("Cls_l_fkddmx.x_bypage()"
						+ e.toString());
			}

		}

		return rtnAL;
	}

}

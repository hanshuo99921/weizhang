package pkg_chexian.freecode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pkg_chexian.Cls_connect;
import pkg_chexian.Cls_e_jgb;
import pkg_chexian.Cls_e_tmp;
import pkg_chexian.Cls_exception;
import pkg_chexian.Cls_gg;
import pkg_chexian.Cls_zd;

public class OrgXx {
	private static Logger log = Logger.getLogger(OrgXx.class);
	public String addOrg(String jgh,String operorg,String czy) throws Exception {
		String info = "";
		if (isNewCode(jgh)) {
			Cls_zd zd = new Cls_zd();
			String jgname = zd.GetJgname(jgh);
			boolean tag = insertWdxx(jgh,operorg,czy,jgname);
			if (tag)
				info = "添加成功";
			else 
				info = "添加失败";
		} else {
			info = "该机构已经存在";
		}
		return info;
	}
	private  boolean isNewCode(String jgh) {
		boolean tag = true;
		Cls_gg gg = new Cls_gg();
		try {
			int num = gg.getrow(" kcs_fkwdxx  where orgcode='" + jgh + "'");
			if (num != 0)
				tag = false;
		} catch (Cls_exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return tag;
	}
	private boolean insertWdxx(String jgh,String operOrg,String czy,String jgname) throws Exception {
	    if (jgh == null) {
	        throw new NullPointerException("jgh is null");
	      }	
	    boolean tag = false;
	    java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;	    
	    int a=0;
	    String citycode="",coun_code="";
	    StringBuffer sqlInsert = new StringBuffer();
		try {
			citycode = jgh.substring(0, 2);
			coun_code = jgh.substring(2, 4);

			sqlInsert.append("insert into kcs_fkwdxx values(ZBWZ_ID.nextval,'");
			sqlInsert.append(jgh+"','");
			sqlInsert.append(citycode+"','");
			sqlInsert.append(coun_code+"',sysdate,'");
			sqlInsert.append(operOrg+"','");
			sqlInsert.append(czy+"','"+jgname+"')");
			
			
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(sqlInsert.toString());
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
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("OrgXx.insertWdxx()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("OrgXx.insertWdxx()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.insertWdxx()"+e.toString());
						}
				   con =null;
							}
		}
		return tag;	
	}
	public int getNum(String tsql) throws Cls_exception {
		int a = 0;
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;

		try {
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			tsql = "SELECT COUNT(*) FROM kcs_fkwdxx where 1=1" + tsql;
			pstmt = conn.prepareStatement(tsql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Cls_exception("OrgXx.getNum(string)   " + e.toString());
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
				throw new Cls_exception("OrgXx.getNum(string) finally"
						+ e.toString());
			}
		}

		return a;
	}

	public List<Cls_e_jgb>  getAll(String sql, int p, int ev)
			throws Cls_exception {
		ArrayList<Cls_e_jgb> rtnAL = new ArrayList<Cls_e_jgb>();
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		StringBuffer sqlInsert = new StringBuffer();
		
		try {
			sqlInsert.append("select * from kcs_fkwdxx t");
			sqlInsert.append(" WHERE ROWID IN (");
		 	sqlInsert.append(" SELECT rid FROM (");
		 	sqlInsert.append(" SELECT rid, ROWNUM AS rn FROM (");
		 	sqlInsert.append(" SELECT ROWID rid FROM kcs_fkwdxx");
		 	sqlInsert.append(" where 1=1 "+sql);
		 	//if (!"".equals(coun_code))
				//sqlInsert.append(" and coun_code='"+coun_code+"'");		 	
		 	sqlInsert.append(" ORDER BY id");
		 	sqlInsert.append(" ) t1 WHERE  ROWNUM<"+ev);
		 	sqlInsert.append(" ) t2 WHERE  rn>="+p);
		 	sqlInsert.append(" ) ORDER BY id");			
			
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn
					.prepareStatement(sqlInsert.toString());
			rs = pstmt.executeQuery();

			Cls_e_jgb tmp_jg;
			while (rs.next()) {
				tmp_jg = new Cls_e_jgb();
				tmp_jg.setSerial_no(rs.getInt("id"));
				tmp_jg.setOper_orgcode(rs.getString("orgcode"));
				tmp_jg.setCity_code(rs.getString("city_code"));
				tmp_jg.setCoun_code(rs.getString("coun_code"));
				tmp_jg.setOper_orgname(rs.getString("orgname"));

				rtnAL.add(tmp_jg);
				tmp_jg = null;
			}
		} catch (Exception e) {
			throw new Cls_exception("OrgXx. getAll() "
					+ e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("OrgXx. getAll()"
							+ e.toString());
				}
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					throw new Cls_exception("OrgXx. getAll()"
							+ e.toString());
				}
				pstmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					throw new Cls_exception("OrgXx. getAll()"
							+ e.toString());
				}
				conn = null;
			}
		}
		return rtnAL;
	}
	/*
	 * kcs_fkwd表在66测试数据库
	 */
	public List<Cls_e_tmp>  getTmpAll()
			throws Cls_exception {
		ArrayList<Cls_e_tmp> rtnAL = new ArrayList<Cls_e_tmp>();
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		StringBuffer sqlInsert = new StringBuffer();
		
		try {
			sqlInsert.append("select * from kcs_fkwd t");

			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			pstmt = conn
					.prepareStatement(sqlInsert.toString());
			rs = pstmt.executeQuery();

			Cls_e_tmp tmp_jg;
			while (rs.next()) {
				tmp_jg = new Cls_e_tmp();
                tmp_jg.setS1(rs.getString("orgcode"));

				rtnAL.add(tmp_jg);
				tmp_jg = null;
			}
		} catch (Exception e) {
			throw new Cls_exception("OrgXx. getTmpAll() "
					+ e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					throw new Cls_exception("OrgXx. getTmpAll()"
							+ e.toString());
				}
				rs = null;
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					throw new Cls_exception("OrgXx. getTmpAll()"
							+ e.toString());
				}
				pstmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					throw new Cls_exception("OrgXx. getTmpAll()"
							+ e.toString());
				}
				conn = null;
			}
		}
		return rtnAL;
	}
}

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
public class WxCode {
	private  Logger log = Logger.getLogger(WxCode.class);
	private int id = 0;
	private String wx_code = "";
	private String sin_time = "";
	private int use_mark = 0;
	private String str_use_mark = "";
	private String org_code = "";
	private int fkddmx_id = 0;
	private int fkxxjl_id = 0;
	private String suse_time = "";
	private String cphm = "";
	private String oper_orgcode = "";
	private String oper_opercodeString = "";
	public WxCode(){
		super();
	}
	public WxCode(String code){
		this.wx_code=code;
	}
	
	public String getStr_use_mark() {
		return str_use_mark;
	}
	public void setStr_use_mark(String str_use_mark) {
		this.str_use_mark = str_use_mark;
	}
	public String getOper_orgcode() {
		return oper_orgcode;
	}
	public String getOper_opercodeString() {
		return oper_opercodeString;
	}
	public void setOper_orgcode(String oper_orgcode) {
		this.oper_orgcode = oper_orgcode;
	}
	public void setOper_opercodeString(String oper_opercodeString) {
		this.oper_opercodeString = oper_opercodeString;
	}
	public int getId() {
		return id;
	}
	public String getWx_code() {
		return wx_code;
	}
	public String getSin_time() {
		return sin_time;
	}
	public int getUse_mark() {
		return use_mark;
	}
	public String getOrg_code() {
		return org_code;
	}
	public int getFkddmx_id() {
		return fkddmx_id;
	}
	public int getFkxxjl_id() {
		return fkxxjl_id;
	}
	public String getSuse_time() {
		return suse_time;
	}
	public String getCphm() {
		return cphm;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setWx_code(String wx_code) {
		this.wx_code = wx_code;
	}
	public void setSin_time(String sin_time) {
		this.sin_time = sin_time;
	}
	public void setUse_mark(int use_mark) {
		this.use_mark = use_mark;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public void setFkddmx_id(int fkddmx_id) {
		this.fkddmx_id = fkddmx_id;
	}
	public void setFkxxjl_id(int fkxxjl_id) {
		this.fkxxjl_id = fkxxjl_id;
	}
	public void setSuse_time(String suse_time) {
		this.suse_time = suse_time;
	}
	public void setCphm(String cphm) {
		this.cphm = cphm;
	}
	public int getUse_mark(String wxcode) throws Cls_exception {
		int a = 0;
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			stmt = con.prepareStatement("select use_mark from kcs_fk_wxcode where wx_code='"+wxcode+"'");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				a = rs.getInt(1);
			}
			
		} catch (SQLException se) {
			log.error("WxCode.getUse_mark(s):"+se.toString());
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
				log.error("WxCode.getUse_mark(s):"+se.toString());
				se.printStackTrace();
				
			}
		}
		return a;
	}
    public boolean isPrepare() throws Cls_exception {
    	boolean tag = false;
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		int a = 0;
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			stmt = con.prepareStatement("select count(*) from kcs_fk_wxcode where use_mark=1 and wx_code='"+this.wx_code+"'");
	        rs = stmt.executeQuery();
			
			while (rs.next()) {
				a = rs.getInt(1);
			}
			if (a ==1) {
				tag = true;
			}
		} catch (SQLException se) {
			log.error("WxCode.isPrepare():"+se.toString());
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
				log.error("WxCode.isPrepare():"+se.toString());
				se.printStackTrace();
				
			}
		}
		return tag;
    }
  /*  public boolean isPrepare() throws Cls_exception {
    	boolean tag = false;
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		int a = 0;
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement("update kcs_fk_wxcode set use_mark=3 where use_mark=1 and wx_code='"+this.wx_code+"'");
			a = stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认
			if (a ==1) {
				tag = true;
			}
		} catch (SQLException se) {
			log.error("WxCode.isPrepare():"+se.toString());
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
				log.error("WxCode.isPrepare():"+se.toString());
				se.printStackTrace();
				
			}
		}
		return tag;
    }*/
    
}

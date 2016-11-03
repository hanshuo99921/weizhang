package pkg_chexian;
import java.sql.SQLException;
import org.apache.log4j.Logger;
/**
 * @author Administrator
 *
 */
public class Cls_ddcl {
	private static Logger log = Logger.getLogger(Cls_ddcl.class);
	public boolean set_zjyw(String opercode,int serial_no,String yjhm,String kk_mark) throws Exception{
		log.error("中间业务平台"+serial_no);
	    if (opercode == null) {
	        throw new NullPointerException("Cls_ddcl opercode");
	      }
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("update kcs_fkddmx set proce_time=SYSDATE,order_type='1',order_result='1',");
		sqlInsert.append("proc_opercode=?,yj_serial=?,kk_mark=?  where serial_no=?");
		boolean tag=false;
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		int a=0;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt=con.prepareStatement(sqlInsert.toString());
		    stmt.setString(1, opercode);
		    stmt.setString(2, yjhm);
		    stmt.setString(3, kk_mark);
		    stmt.setInt(4, serial_no);
			a=stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认	
			if (a>0)
			 {
				 tag=true;
			 }
		} catch (SQLException se) {
			log.error(se.toString());
			se.printStackTrace();
			try {
					if (con != null) {
					con.rollback();//出现sql异常，事务回滚
					con.setAutoCommit(true);//设置提交方式为默认方式			
					}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} finally {
			sqlInsert=null;
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_withoutkk()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_withoutkk()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_withoutkk()"+e.toString());
						}
				   con =null;
							}
		}
		return tag;	
	}	
	public boolean set_withkk(String opercode,int serial_no,String zjls,String yjhm,String kk_mark) throws Exception{
		log.error("订单扣款："+serial_no);
	    if (opercode == null) {
	    	log.error("订单扣款："+serial_no+"opercode is null");
	        throw new NullPointerException("Cls_ddcl opercode");
	      } else log.error("["+serial_no+"]opercode:"+opercode);
	    if (zjls == null) {
	        throw new NullPointerException("Cls_ddcl zjls");
	      }   
		StringBuffer sqlInsert = new StringBuffer();
			sqlInsert.append("update kcs_fkddmx set proce_time=SYSDATE,order_type='1',order_mark='1',order_result='1',proc_opercode=");
			sqlInsert.append("?,yj_serial=?,zjlsh=?,kk_mark=? where serial_no=?");

		boolean tag=false;
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		int a=0;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt=con.prepareStatement(sqlInsert.toString());
		    stmt.setString(1, opercode);
		    stmt.setString(2, yjhm);
		    stmt.setString(3, zjls);
		    stmt.setString(4, kk_mark);
		    stmt.setInt(5, serial_no);
			a=stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认	
			if (a>0)
			 {
				 tag=true;
			 }
		} catch (SQLException se) {
			log.error("Cls_l_ddcl.set_withkk()"+se.toString());
			se.printStackTrace();
			try {
					if (con != null) {
					con.rollback();//出现sql异常，事务回滚
					con.setAutoCommit(true);//设置提交方式为默认方式			
					}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} finally {
			sqlInsert=null;
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_withkk()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_withkk()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_withkk()"+e.toString());
						}
				   con =null;
							}
		}
		return tag;	
	}
	public int get_chzhid(int serial_no) throws Exception {
		log.error(serial_no+"生成冲正订单");
		int a=0;
		StringBuffer sqlInsert = new StringBuffer();
		Cls_gg gg = new Cls_gg();
		//int chzhid=gg.maxid(" kcs_fkddmx")+1;
		int chzhid=gg.getint("select ZBWZ_ID.nextval from dual");
		sqlInsert.append("insert into kcs_fkddmx(serial_no,org_code,opercode,oper_orgcode,tb_date,name,fkbs,fk_no,fk_serial,fk_code,");
		sqlInsert.append("fk_price,deli_fee,sum_price,tele_no,add_message,proce_time,order_type) values (");
		sqlInsert.append(chzhid+",'62009999','100001','62009999',to_char(sysdate,'yyyy-mm-dd'),'0',1,'0','0','0',0,0,0,'0','");
		sqlInsert.append(serial_no+"',sysdate,'4')");
		log.error(sqlInsert.toString());
		boolean emp = gg.update(sqlInsert.toString());
		if (emp) a = chzhid;
		sqlInsert=null;
		return a;
	}
	public boolean set_erro(int serial_no) throws Exception {
		log.error(serial_no+"订单异常");
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("update kcs_fkddmx set proce_time=SYSDATE,order_type='7',order_result='2',order_mark='2' where serial_no=?");

		boolean tag=false;
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		int a=0;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt=con.prepareStatement(sqlInsert.toString());
		    stmt.setInt(1, serial_no);
			a=stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认	
			if (a>0)
			 {
				 tag=true;
			 }
		} catch (SQLException se) {
			log.error(se.toString());
			se.printStackTrace();
			try {
					if (con != null) {
					con.rollback();//出现sql异常，事务回滚
					con.setAutoCommit(true);//设置提交方式为默认方式			
					}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} finally {
			sqlInsert=null;
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_erro()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_erro()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_erro()"+e.toString());
						}
				   con =null;
							}
		}
		return tag;	
	
}	
	public boolean set_td(int serial_no,String opercode,String tdyy) throws Exception {
		log.error(serial_no+"订单退单");
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("update kcs_fkddmx set proce_time=SYSDATE,order_type='2',order_result='1',proc_opercode=?,t_message=? where serial_no=?");

		boolean tag=false;
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		int a=0;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt=con.prepareStatement(sqlInsert.toString());
			stmt.setString(1, opercode);
			stmt.setString(2, tdyy);
		    stmt.setInt(3, serial_no);
			a=stmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);// 恢复默认	
			if (a>0)
			 {
				 tag=true;
			 }
		} catch (SQLException se) {
			log.error(se.toString());
			se.printStackTrace();
			try {
					if (con != null) {
					con.rollback();//出现sql异常，事务回滚
					con.setAutoCommit(true);//设置提交方式为默认方式			
					}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} finally {
			sqlInsert=null;
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_td()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_td()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_td()"+e.toString());
						}
				   con =null;
							}
		}
		return tag;	
}	
	public int set_xg(int serial_no,String deli_fee,String sum_price,String opercode) throws Exception {
		log.error("修改订单"+serial_no);
		Cls_gg gg = new Cls_gg();
		int id=0;
		int maxid=gg.getint("select ZBWZ_ID.nextval from dual");
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("insert into  kcs_fkddmx t (select  "+maxid+",a.org_code,a.opercode,a.oper_orgcode,'"+opercode+"',a.tb_date,a.name,a.fkbs,a.fk_no,");
		sqlInsert.append("a.fk_serial,a.fk_code,a.fk_price,"+deli_fee+","+sum_price+",a.tele_no,a.add_message,a.id,SYSDATE,'0',a.proce_time,");
		sqlInsert.append("'0','0',a.proc_orgcode,a.proc_opercode,a.t_message,a.d_message,a.org_tel,a.kk_mark,a.chf_date,a.dshr,a.carnob,");
		sqlInsert.append("a.post_addr,a.jshzh,a.jshzh_no,a.wf_code,a.yj_serial,a.yj_fsh,a.wzxx,a.zjlsh,a.yw_type ");
		sqlInsert.append("  from kcs_fkddmx a where serial_no="+serial_no+")");
	    log.error(sqlInsert.toString());
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		int a=0;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt=con.prepareStatement(sqlInsert.toString());
		   	a=stmt.executeUpdate();
			stmt.close();
			sqlInsert.setLength(0);
			sqlInsert.append("update kcs_fkddmx set proce_time=SYSDATE,order_type='3',order_result='1',proc_opercode='"+opercode+"',t_message='改单为"+maxid+"' where serial_no="+serial_no);
			log.error(sqlInsert.toString());
			stmt=con.prepareStatement(sqlInsert.toString());
			a=stmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);// 恢复默认	
			if (a>0)
			 {
				 id=maxid;
			 }
		} catch (SQLException se) {
			log.error(se.toString());
			se.printStackTrace();
			try {
					if (con != null) {
					con.rollback();//出现sql异常，事务回滚
					con.setAutoCommit(true);//设置提交方式为默认方式			
					}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} finally {
			sqlInsert=null;
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_xg()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_xg()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_ddcl.set_xg()"+e.toString());
						}
				   con =null;
							}
		}
		return id;	
	
}	
    public boolean get_ddtype0(int ddid) throws Exception{
        boolean tag=false;
        int a=1;
	    if (ddid==0)
	    	return false;
	    else {
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
	 try{	
		 Cls_connect cn = new Cls_connect();
		 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement("select order_type+order_mark  from kcs_fkddmx where serial_no=?");
		 pstmt.setInt(1, ddid);
		 rs=pstmt.executeQuery();

		while(rs.next()) {
			a=rs.getInt(1);
					}
		if (a==0) tag=true;
		 }
	  catch(Exception e){
		    log.error(e.toString());
			throw new Cls_exception("Cls_ddcl.get_ddtype0() "+e.toString());
	 }	
	finally{		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_ddcl.get_ddtype0()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_ddcl.get_ddtype0()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_ddcl.get_ddtype0()"+e.toString());
					}
			   conn =null;
						}
		}
	return tag;	
	    }
    }
}

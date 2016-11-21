package pkg_chexian;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class Cls_l_wfjl {
	//private static Logger log = Logger.getLogger(Cls_l_fkddmx.class);
	public boolean  Wfjl_valid(String wfdm) throws Cls_exception{
		boolean tag=false;
		String a="";
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("select blbzh from kcs_fkwzdm t where wfdm='"+wfdm+"'");
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			stmt=con.prepareStatement(sqlInsert.toString());
			rs=stmt.executeQuery();
			while(rs.next()) 
			 {
				 a=rs.getString(1);
			 }
			if (a.equals("1")) tag=true;
		 }
		  catch(Exception e){
				throw new Cls_exception("Cls_l_wfjl.Wfjl_valid() "+e.toString());
		 }	
		finally{
			a=null;
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				   con =null;
							}
			}
		return tag;
	}
	public String Wfjl_fk(String wfdm) throws Cls_exception{
		String a="";
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("select fkje from kcs_fkwzdm t where wfdm='"+wfdm+"'");
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			stmt=con.prepareStatement(sqlInsert.toString());
			rs=stmt.executeQuery();
			while(rs.next()) 
			 {
				 a=rs.getString(1);
			 }
		 }
		  catch(Exception e){
				throw new Cls_exception("Cls_l_wfjl.Wfjl_valid() "+e.toString());
		 }	
		finally{
			a=null;
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				   con =null;
							}
			}
		return a;
	}
	public int Wfjl_fen(String wfdm) throws Cls_exception{
		int a=0;
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("select JFFZH from kcs_fkwzdm t where wfdm='"+wfdm+"'");
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			stmt=con.prepareStatement(sqlInsert.toString());
			rs=stmt.executeQuery();
			while(rs.next()) 
			 {
				 a=rs.getInt(1);
			 }
		 }
		  catch(Exception e){
				throw new Cls_exception("Cls_l_wfjl.Wfjl_valid() "+e.toString());
		 }	
		finally{
			
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				   con =null;
							}
			}
		return a;
	}	
	public WfChfaTl GetTl(String wfdm) throws Cls_exception{
		WfChfaTl tmp_dd= new WfChfaTl();
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("select * from kcs_fkwzdm t where wfdm='"+wfdm+"'");
		java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;
		try {
			Cls_connect cn = new Cls_connect();
        	con = cn.connect().getConnection();
			stmt=con.prepareStatement(sqlInsert.toString());
			rs=stmt.executeQuery();
			while(rs.next()) 
			 {
				tmp_dd.setWfxw(rs.getString("wfxw"));
				tmp_dd.setWfjc(rs.getString("wfjc"));
				tmp_dd.setWftk(rs.getString("wftk"));
				tmp_dd.setCfyj(rs.getString("cfyj"));
				tmp_dd.setJffzh(rs.getInt("jffzh"));
				tmp_dd.setFkje(rs.getString("fkje"));
				tmp_dd.setXzchf(rs.getString("xzchf"));
				tmp_dd.setQtcsh(rs.getString("qtcsh"));
				tmp_dd.setBlbzh(rs.getString("blbzh"));
				tmp_dd.setDx(rs.getString("dx"));
			 }
		 }
		  catch(Exception e){
				throw new Cls_exception("Cls_l_wfjl.Wfjl_valid() "+e.toString());
		 }	
		finally{
			
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.Wfjl_valid()"+e.toString());
						}
				   con =null;
							}
			}
		return tmp_dd;
	}	
    public List<Wfjl> Get_ddid(int ddid) throws Cls_exception {
    	ArrayList<Wfjl> rtnAL = new ArrayList<Wfjl>();
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
		String tmp = "";
		 try{	
				Cls_connect cn = new Cls_connect();
				conn = cn.connect().getConnection();
				pstmt=conn.prepareStatement("select * from kcs_fkxxjl where mark=1 and dd_id="+ddid);
				rs=pstmt.executeQuery();
				Wfjl tmp_jg;
				while(rs.next()) {
				  tmp_jg=new Wfjl();
				  tmp_jg.setId(rs.getInt("serial_no"));
                  tmp_jg.setWfrq(rs.getString("rq"));
                  tmp_jg.setWfaddr(rs.getString("wfaddr"));
                  tmp_jg.setWfdm(rs.getString("wfdm"));
                  tmp_jg.setWfxw(rs.getString("wfxw"));
                  tmp_jg.setFkje(rs.getString("fkje"));
                  tmp_jg.setFen(rs.getInt("fen"));
                  tmp_jg.setWfcl(rs.getString("wfcl"));
                  tmp = rs.getString("cfdh");
                  if (tmp != null)
                        tmp_jg.setCfdh(tmp);
                  tmp = rs.getString("skdh");
                  if (tmp != null)
                	  tmp_jg.setSkdh(tmp);
	              rtnAL.add(tmp_jg);
	              tmp_jg=null;
							}
				 }
			  catch(Exception e){
					throw new Cls_exception("Cls_l_wfjl.get_ddid() "+e.toString());
			 }	
			finally{
					
				try{
						if (rs!=null){
							rs.close();
						}
						if (pstmt!=null) {
							pstmt.close();
						}
						if (conn!=null){
							conn.close();
						}
					   }
					catch(Exception e){
						 throw new Cls_exception("Cls_l_wfjl.get_ddid"+e.toString());
					}
					
				}

			return rtnAL;
    }
	public int insertByObj(List<Wfjl> list,String jgh,String cphm) throws Exception {
	    if (list == null) {
	        throw new NullPointerException("Wfjl is null");
	      }	
	    java.sql.Connection con=null;
		java.sql.PreparedStatement stmt=null;
		java.sql.ResultSet rs=null;	    
	    int a=0,num=0;
		String sql="";
		try {
			Cls_connect cn = new Cls_connect();
	    	con = cn.connect().getConnection();
			con.setAutoCommit(false);	
			
			for (int i=0;i<list.size();i++) {
				sql="insert into kcs_fkxxjl values(ZBWZ_ID.nextval,0,?,?,?,?,?,?,?,?,?,1,?,?)";
				//log.error(sql);
				stmt=con.prepareStatement(sql);
				stmt.setString(1, cphm);
				stmt.setString(2, jgh);
				stmt.setString(3, list.get(i).getWfrq());
				stmt.setString(4, list.get(i).getWfaddr());
				stmt.setString(5, list.get(i).getWfdm());
				stmt.setString(6, list.get(i).getWfxw());
				stmt.setInt(7, Integer.parseInt(list.get(i).getFkje()));
				stmt.setInt(8, list.get(i).getFen());
				stmt.setString(9, list.get(i).getWfcl());
				stmt.setString(10, "");
				stmt.setString(11, "");
				a=stmt.executeUpdate();
 			  	stmt.close();
 			  	if (a>0) num=num+1;
				
			}
			con.commit();
 		    con.setAutoCommit(true);

		} catch (SQLException se) {
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
			sql=null;
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.insertByObj()"+e.toString());
						}
				     rs=null;  
							}
							if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.insertByObj()"+e.toString());
						}
				     stmt=null;
							}
							if (con!=null){
				try{
								con.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_wfjl.insertByObj()"+e.toString());
						}
				   con =null;
							}
		}
		return num;	
	}	
	public boolean reset_byorgcode(String org_code,String cphm) throws Exception {
	    StringBuffer sqlUpdate = new StringBuffer();
	    sqlUpdate.append("delete from kcs_fkxxjl where dd_id=0 and org_code='");
	    sqlUpdate.append(org_code+"' and cphm='"+cphm+"'");
	    //log.error(sqlUpdate.toString());
	    Cls_gg gg = new Cls_gg();
	    boolean tag = gg.update(sqlUpdate.toString());
	    gg = null;
	    sqlUpdate = null;
		return tag;
	}
	public boolean keep_byorgcode(int ddid,String org_code,String cphm) throws Exception {
	    StringBuffer sqlUpdate = new StringBuffer();
	    sqlUpdate.append("update kcs_fkxxjl set dd_id="+ddid+"  where dd_id=0 and org_code='");
	    sqlUpdate.append(org_code+"' and cphm='"+cphm+"'");
	    //log.error(sqlUpdate.toString());
	    Cls_gg gg = new Cls_gg();
	    boolean tag = gg.update(sqlUpdate.toString());
	    gg = null;
	    sqlUpdate = null;
		return tag;
	}
    public boolean set_invalid(int serial_no,int ddid) throws Exception{
	    StringBuffer sqlUpdate = new StringBuffer();
	    sqlUpdate.append("update kcs_fkxxjl set mark=0 where dd_id="+ddid+" and serial_no="+serial_no);
	    //log.error(sqlUpdate.toString());
	    Cls_gg gg = new Cls_gg();
	    boolean tag = gg.update(sqlUpdate.toString());
	    gg = null;
	    sqlUpdate = null;
		return tag;
	}    	
    public boolean set_hx(int ddid,List<Wfjl> list) throws Exception{
		if (ddid == 0) {
			throw new NullPointerException("ddid is null");
		}
		boolean tag = false;
		java.sql.Connection con = null;
		java.sql.PreparedStatement stmt = null;
		java.sql.ResultSet rs = null;
		int i = 0,a=0,j=0;
		String sql = "";
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
    	
            for (i=0;i<list.size();i++) {
            	sql="update kcs_fkxxjl set cfdh='"+list.get(i).getCfdh()+"',skdh='"+list.get(i).getSkdh()+"' where dd_id="+ddid+" and  serial_no="+list.get(i).getId();
            	stmt = con.prepareStatement(sql);
            	a = stmt.executeUpdate();
            	if (a>0) j=j+1;
				stmt.close();
            }
		    if (j==list.size()) tag =true;
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
}

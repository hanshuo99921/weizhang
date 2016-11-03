package pkg_chexian;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
/**
 * @author Administrator
 *
 */
public class Cls_l_ddtj {
	private static Logger log = Logger.getLogger(Cls_l_ddtj.class);
	public List<Cls_e_tmp> zj_tjb(String sql,String qxcode) throws Cls_exception {  
		ArrayList<Cls_e_tmp> rtnAL = new ArrayList<Cls_e_tmp>();
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("select count(*) as ddnum,sum(deli_fee) as d3,yw_type,order_type,org_code from kcs_fkddmx t where ");
		sqlInsert.append(" order_type<>'4' and order_type<>'5' and order_type<>'3' and substr(org_code,0,4)='"+qxcode);
		sqlInsert.append("' ");
		sqlInsert.append(sql);
		sqlInsert.append("   group by yw_type,order_type,org_code  order by org_code");
		log.error("------ zj_tjb---------");
		log.error(sqlInsert.toString());
	 try{	
		 Cls_connect cn = new Cls_connect();
     	 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement(sqlInsert.toString());
		 rs=pstmt.executeQuery();

		while(rs.next()) {
		 Cls_e_tmp tmp_jg=new Cls_e_tmp();
		    tmp_jg.setS2(rs.getString("yw_type"));
		    tmp_jg.setS1(rs.getString("org_code"));
            tmp_jg.setNum1(rs.getInt("ddnum"));
            tmp_jg.setNum2(rs.getInt("order_type"));
            tmp_jg.setD3(rs.getDouble("d3"));

		  rtnAL.add(tmp_jg);
	      tmp_jg=null;
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_l_bxtj.zj_tjb() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtj.zj_tjb()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtjx.zj_tjb()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtj.zj_tjb()"+e.toString());
					}
			   conn =null;
						}
		}
	return rtnAL;
		}
	public List<Cls_e_tmp> qx_tjb(String sql) throws Cls_exception {  
		ArrayList<Cls_e_tmp> rtnAL = new ArrayList<Cls_e_tmp>();
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("select substr(org_code,3,2) as city_code,count(*) as ddnum, sum(fk_price) as d2,sum(deli_fee) as d3  from kcs_fkddmx t  where");
		sqlInsert.append("  order_type<>'4' and order_type<>'5' and order_type<>'3' "+sql);
		sqlInsert.append("   group by substr(org_code,3,2) order by substr(org_code,3,2)");
	 try{	
		 Cls_connect cn = new Cls_connect();
     	 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement(sqlInsert.toString());
		 rs=pstmt.executeQuery();

		while(rs.next()) {
		 Cls_e_tmp tmp_jg=new Cls_e_tmp();
            tmp_jg.setS1(rs.getString("city_code"));
            tmp_jg.setNum1(rs.getInt("ddnum"));
            tmp_jg.setD2(rs.getDouble("d2"));
            tmp_jg.setD3(rs.getDouble("d3"));
		  rtnAL.add(tmp_jg);
	      tmp_jg=null;
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_l_bxtj.qx_tjb() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtj.qx_tjb()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtj.qx_tjb()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtj.qx_tjb()"+e.toString());
					}
			   conn =null;
						}
		}
	return rtnAL;
		}	
	public List<Cls_e_tmp> qx_tjb(String sql,String citycode) throws Cls_exception {  
		ArrayList<Cls_e_tmp> rtnAL = new ArrayList<Cls_e_tmp>();
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("select count(*) as ddnum,sum(fk_price) as d2,sum(deli_fee) as d3,yw_type,order_type from kcs_fkddmx t  where ");
		sqlInsert.append(" order_type<>'4' and order_type<>'5' and order_type<>'3'  and substr(org_code,0,4)='"+citycode);
		sqlInsert.append("' ");
		sqlInsert.append(sql);
		sqlInsert.append("   group by yw_type,order_type  order by yw_type,order_type");
	 try{	
		 Cls_connect cn = new Cls_connect();
     	 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement(sqlInsert.toString());
		 rs=pstmt.executeQuery();

		while(rs.next()) {
		 Cls_e_tmp tmp_jg=new Cls_e_tmp();
            tmp_jg.setNum2(rs.getInt("order_type"));
            tmp_jg.setNum1(rs.getInt("ddnum"));
            tmp_jg.setS1(rs.getString("yw_type"));
            tmp_jg.setD2(rs.getDouble("d2"));
            tmp_jg.setD3(rs.getDouble("d3"));

		  rtnAL.add(tmp_jg);
	      tmp_jg=null;
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_l_bxtj.qs_tjb() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtj.qs_tjb()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtjx.qs_tjb()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_l_bxtj.qs_tjb()"+e.toString());
					}
			   conn =null;
						}
		}
	return rtnAL;
		}
	public List<Cls_e_tmp> tj_jgrb(String rq,String jgh) throws Cls_exception {
		ArrayList<Cls_e_tmp> rtnAL = new ArrayList<Cls_e_tmp>();
			java.sql.Connection conn=null;
			java.sql.PreparedStatement pstmt=null;
			java.sql.ResultSet rs=null;
			StringBuffer sqlInsert = new StringBuffer();
		 try{	
			 Cls_connect cn = new Cls_connect();
	     	 conn = cn.connect().getConnection();
	     	 sqlInsert.append("select count(*) as n1,sum(sum_price) as d1, sum(fk_price) as d2,sum(deli_fee) as d3,yw_type,order_type from kcs_fkddmx t where  ");
			 sqlInsert.append(" order_type<>'4' and order_type<>'5' and order_type<>'3' and org_code='"+jgh+"' and TO_char(order_time,'yyyy-mm-dd')='"+rq+"'");
			 sqlInsert.append(" group by yw_type,order_type");
	     	 pstmt=conn.prepareStatement(sqlInsert.toString());
			 rs=pstmt.executeQuery();

			while(rs.next()) {
			 Cls_e_tmp tmp_jg=new Cls_e_tmp();
			  tmp_jg.setNum1(rs.getInt("n1"));
			  tmp_jg.setS1(rs.getString("yw_type"));
			  tmp_jg.setS2(rs.getString("order_type"));
              tmp_jg.setD1(rs.getDouble("d1"));
              tmp_jg.setD2(rs.getDouble("d2"));
              tmp_jg.setD3(rs.getDouble("d3"));
			  rtnAL.add(tmp_jg);
		      tmp_jg=null;
						}
			 }
		  catch(Exception e){
				throw new Cls_exception("Cls_l_ddtj.tj_jgrb() "+e.toString());
		 }	
		finally{
			
			if (rs!=null){
				try{
					rs.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddtj.tj_jgrb()"+e.toString());
						}
				     rs=null;  
							}
							if (pstmt!=null) {
				try{
								pstmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_l_ddtj.tj_jgrb()"+e.toString());
						}
				     pstmt=null;
							}
							if (conn!=null){
				try{
								conn.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_l_ddtj.tj_jgrb()"+e.toString());
						}
				   conn =null;
							}
			}
		return rtnAL;
			}
}

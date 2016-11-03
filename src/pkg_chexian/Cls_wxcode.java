/**
 * 
 */
package pkg_chexian;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class Cls_wxcode {
	 private  Logger log = Logger.getLogger(Cls_wxcode.class);
	public  List<WxCode>  getlist(String tsql, int p, int ev) throws Cls_exception {
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
		ArrayList<WxCode> rtnAL = new ArrayList<WxCode>();
		StringBuffer sqlInsert = new StringBuffer();
		String temp = "";
	 try{	
		 Cls_connect cn = new Cls_connect();
	 	conn = cn.connect().getConnection();
	 	sqlInsert.append("SELECT id,to_char(in_time,'yyyy-mm-dd hh24:mi:ss') as sin_time,wx_code,org_code,fkddmx_id,");
	 	sqlInsert.append("use_mark,cphm,to_char(use_time,'yyyy-mm-dd hh24:mi:ss') as suse_time");
	 	sqlInsert.append("   FROM kcs_fk_wxcode t");
	 	sqlInsert.append(" WHERE ROWID IN (");
	 	sqlInsert.append(" SELECT rid FROM (");
	 	sqlInsert.append(" SELECT rid, ROWNUM AS rn FROM (");
	 	sqlInsert.append(" SELECT ROWID rid FROM kcs_fk_wxcode");
	 	sqlInsert.append(" where 1=1 "+tsql);
	 	sqlInsert.append(" ORDER BY id");
	 	sqlInsert.append(" ) t1 WHERE  ROWNUM<"+ev);
	 	sqlInsert.append(" ) t2 WHERE  rn>="+p);
	 	sqlInsert.append(" ) ORDER BY id");
	 	pstmt=conn.prepareStatement(sqlInsert.toString());
	 	//log.error(sqlInsert.toString());
		rs=pstmt.executeQuery();
		WxCode tmp_jg;
		while(rs.next()) {
		  tmp_jg=new WxCode();
		  tmp_jg.setId(rs.getInt("id"));
		  temp = rs.getString("org_code");
		  if (temp!=null)
			  tmp_jg.setOrg_code(temp);
		  
		  tmp_jg.setSin_time(rs.getString("sin_time"));
		  tmp_jg.setWx_code(toScrecString(rs.getString("wx_code")));
		  tmp_jg.setFkddmx_id(rs.getInt("fkddmx_id"));
		  if (rs.getInt("use_mark")== 1)
			  tmp_jg.setStr_use_mark("未使用");
		  else  if (rs.getInt("use_mark") == 2)
			  tmp_jg.setStr_use_mark("已核销");
		  else 
              tmp_jg.setStr_use_mark("锁定");
		  tmp_jg.setUse_mark(rs.getInt("use_mark"));
		  temp = rs.getString("cphm");
		  if (temp!=null)
			    tmp_jg.setCphm(temp);
		  temp = rs.getString("suse_time");
		  if (temp != null) {
			tmp_jg.setSuse_time(temp);
		}
		  
	      rtnAL.add(tmp_jg);
	      tmp_jg=null;
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_wxcode.getlist() "+e.toString());
	 }	
	finally{
			
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_wxcode.getlist()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_wxcode.getlist()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_wxcode.getlist()"+e.toString());
					}
			   conn =null;
						}
			
		}
	return rtnAL;

		}
	private String toScrecString(String str) {
		int len = str.length();
		
		if (len>5) {
			int beginLocation = (int)Math.floor(len/2);
			StringBuffer stringBuffer = new StringBuffer(str);
			stringBuffer.replace(beginLocation, beginLocation+3, "***");
			str = stringBuffer.toString();
		}
		return str;
		
	}
/*	public List<Boolean> writeOff(List<String> wx_codes,int ddid,String orgcode,String cphm) throws Cls_exception {
		ArrayList<Boolean> rtnAL = new ArrayList<Boolean>();
		rtnAL.add(false);
		return rtnAL;
	}*/
    public List<Boolean> writeOff(List<String> wx_codes,int ddid,String orgcode,String cphm) throws Cls_exception {
    	ArrayList<Boolean> rtnAL = new ArrayList<Boolean>();
    	int i = 0;
    	java.sql.Connection conn = null;
		Statement stmt = null;
		StringBuffer sqlInsert = new StringBuffer();
		try{	
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for ( i=0;i<wx_codes.size();i++) {
				if (!"".equals(wx_codes.get(i))) {
					sqlInsert.delete(0, sqlInsert.length());
					sqlInsert.append("update kcs_fk_wxcode set use_mark=2,use_time=sysdate, fkddmx_id=");
					sqlInsert.append(ddid);
					sqlInsert.append(",org_code='");
					sqlInsert.append(orgcode);
					sqlInsert.append("',cphm='");
					sqlInsert.append(cphm);
					sqlInsert.append("' where use_mark=1 and wx_code='");
					sqlInsert.append(wx_codes.get(i));
					sqlInsert.append("'");
					stmt.addBatch(sqlInsert.toString());
				}
			}
			int[] counts = stmt.executeBatch();
			int num = counts.length;
			//log.error("num = "+num);
			conn.commit();
			stmt.close();
			for (i = 0; i < num; i++) {
				log.error(counts[i]);
				if (counts[i] > 0)
					rtnAL.add(true);
				else rtnAL.add(false);

			}
			conn.setAutoCommit(true);
			 }
		  catch(Exception e){
				throw new Cls_exception("Cls_wxcode.writeOff() "+e.toString());
		 }	
		finally{
			if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_wxcode.writeOff()"+e.toString());
						}
				     stmt=null;
							}
			if (conn!=null){
				try{
								conn.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_wxcode.writeOff()"+e.toString());
						}
				   conn =null;
							}
				
			}
		return rtnAL;
    }
    /*
     * 当一次多个码没有全部成功writeOff的时候，订单状态写异常后，已写成核销的微信码也标记为未使用
     * 以便再次下单时使用。
     * mark = 2(已核销)--> mark = 1(未使用)
     * 
     * */
    public List<Boolean> writeNew(List<String> wx_codes) throws Cls_exception {
    	ArrayList<Boolean> rtnAL = new ArrayList<Boolean>();
    	int i = 0;
    	java.sql.Connection conn = null;
		Statement stmt = null;
		StringBuffer sqlInsert = new StringBuffer();
		try{	
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for ( i=0;i<wx_codes.size();i++) {
				if (!"".equals(wx_codes.get(i))) {
					sqlInsert.delete(0, sqlInsert.length());
					sqlInsert.append("update kcs_fk_wxcode set use_mark=1,use_time=sysdate, ");
					sqlInsert.append("org_code='',cphm=''");
					sqlInsert.append(" where use_mark=2 and wx_code='");
					sqlInsert.append(wx_codes.get(i));
					sqlInsert.append("'");
					stmt.addBatch(sqlInsert.toString());
				}
			}
			int[] counts = stmt.executeBatch();
			int num = counts.length;
			//log.error("num = "+num);
			conn.commit();
			stmt.close();
			for (i = 0; i < num; i++) {
				log.error(counts[i]);
				if (counts[i] > 0)
					rtnAL.add(true);
				else rtnAL.add(false);

			}
			conn.setAutoCommit(true);
			 }
		  catch(Exception e){
				throw new Cls_exception("Cls_wxcode.writeNew() "+e.toString());
		 }	
		finally{
			if (stmt!=null) {
				try{
								stmt.close();}
				catch(Exception e){
							 throw new Cls_exception("Cls_wxcode.writeNew()"+e.toString());
						}
				     stmt=null;
							}
			if (conn!=null){
				try{
								conn.close();
				}catch(Exception e){
							 throw new Cls_exception("Cls_wxcode.writeNew()"+e.toString());
						}
				   conn =null;
							}
				
			}
		return rtnAL;
    }
}

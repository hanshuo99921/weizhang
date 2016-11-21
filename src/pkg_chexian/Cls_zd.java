package pkg_chexian;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import org.apache.log4j.Logger;
public class Cls_zd {
	//private static Logger log = Logger.getLogger(Cls_zd.class);
    public static void main(String[] args)
    {

    }
    public  String maxstr(String s1,String s2) {
    	if (s1.compareTo(s2)>0) return s1;
    	else return s2;
    }
	public int yjf(String cphm){
		int a = 0,num=0;
		//boolean ishy = false;
		String hyrq="",bxrq="";
		Cls_gg gg = new Cls_gg();
		String sql="";
		try {	
		    sql="select * from ( select to_char(proce_time,'yyyy-mm-dd') from kcs_specddmx t where add_months(proce_time,12)>sysdate and order_type='1' and order_mark='1' and cphm like '%"+cphm+"%' order by serial_no desc ) where rownum=1";
			hyrq=gg.getstring(sql);
			//log.error("hyrq="+hyrq);
		    sql="select * from ( select to_char(proce_time,'yyyy-mm-dd') from kcs_specbaoxian t where add_months(proce_time,12)>sysdate and order_type='6'  and cphm like '%"+cphm+"%' order by serial_no desc ) where rownum=1";
		    bxrq=gg.getstring(sql);
		    //log.error("bxrq="+bxrq);
			if ("".equals(hyrq)&&"".equals(bxrq)) {
				a = 12;
				//log.error("a=12");
			} /*else{
				hyrq=maxstr(hyrq,bxrq);
				//log.error("maxrq="+hyrq);
				sql=" kcs_fkmfjl where cphm like '%"+cphm+"%' and rq<add_months(to_date('"+hyrq+"','yyyy-mm-dd'),12) and rq>to_date('"+hyrq+"','yyyy-mm-dd')";
				//log.error(sql);
				num=gg.getrow(sql);
			    if (num>=2) a = 12;
			}*/

		} catch (Cls_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			hyrq=null;bxrq=null;sql=null;
		}
		return a;
	}
	public String hpzhl(String zl) throws Cls_exception{
		String a="";
		 if (zl.equals("01")) a="大型汽车";
		 if (zl.equals("02")) a="小型汽车";
		 if (zl.equals("15")) a="挂车";
		 if (zl.equals("14")) a="拖拉机";
		return a;  
	}
	public String GetOrdertype(int ord) throws Cls_exception{
		String a="";
	     if (ord==0) a="未处理";
	     if (ord==1) a="已扣款";
	     if (ord==2) a="退单";
	     if (ord==3) a="改单";
	     if (ord==4) a="冲正";
	     if (ord==5) a="不完整";
	     if (ord==6) a="已核销";	    
	     if (ord==7) a="处理异常";
	     if (ord==8) a="站点撤销";
	     if (ord==9) a="已反馈";
		return a;		
	}
	public int Getywtype(String ord) throws Cls_exception{
		int a=0;
	     if ("便民站代办违章缴费".equals(ord)) a=1;//大厅       
	     if ("便民站代办车辆审验".equals(ord)) a=2;
	     if ("便民站违章缴费".equals(ord)) a=3; //便民站[半]
	     if ("便民站代办违章".equals(ord)) a=5; //[全]
         if ("网点代办违章".equals(ord)) a=6; //网点免费 全
         if ("网点代办驾驶证".equals(ord)) a=7;
         if ("网点代办行驶证".equals(ord)) a=8;
         if ("网点代办车辆审验".equals(ord)) a=9;
         if ("便民站代办号牌换领".equals(ord)) a=10;
         if ("便民站代办号牌补领".equals(ord)) a=11;
	     if ("便民站代办行驶证换领".equals(ord)) a=12;
	     if ("便民站代办行驶证补领".equals(ord)) a=13;	
	     if ("便民站代办驾驶证补领".equals(ord)) a=20;
	     if ("便民站代办驾驶证期满换证".equals(ord)) a=21;
	     if ("便民站代办驾驶证转入换证".equals(ord)) a=22;
	     if ("便民站代办驾驶证自愿降级换证".equals(ord)) a=23;
	     if ("便民站代办驾驶证注销".equals(ord)) a=24;
	     
	     
	     return a;		
	}
	public String Getywtype(int ord) throws Cls_exception{
		String a="";
		 
	     if (ord==1) a="便民站代办违章缴费";
	     if (ord==2) a="便民站代办车辆审验";
	     if (ord==3) a="便民站违章缴费";
	     if (ord==5) a="便民站代办违章";
	     if (ord==6) a="网点代办违章";
	     if (ord==7) a="网点代办驾驶证";
	     if (ord==8) a="网点代办行驶证";
	     if (ord==9) a="网点代办车辆审验";
	     if (ord==10) a="便民站代办号牌换领";
	     if (ord==11) a="便民站代办号牌补领";
	     if (ord==12) a="便民站代办行驶证换领";
	     if (ord==13) a="便民站代办行驶证补领";	  
	     if (ord==20) a="便民站代办驾驶证补领";
	     if (ord==21) a="便民站代办驾驶证期满换证";
	     if (ord==22) a="便民站代办驾驶证转入换证";
	     if (ord==23) a="便民站代办驾驶证自愿降级换证";
	     if (ord==24) a="便民站代办驾驶证注销";
	     
		return a;		
	}	

	public String getSysDate() {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String mytime = sdf.format(new Date());
		     return mytime;
		  }
	public String getSysDatenyr() {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		    String mytime = sdf.format(new Date());
		     return mytime;
		  }
	public String getSysDatetime() {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
		    String mytime = sdf.format(new Date());
		     return mytime;
		  }
	public boolean Qmhz(String rq) {
		boolean tag = false;
		SimpleDateFormat qf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Calendar hrq = Calendar.getInstance();
		Calendar qrq = Calendar.getInstance();
		hrq.add(Calendar.DAY_OF_YEAR, 90);//90天后
		qrq.add(Calendar.YEAR, -1);//1年前日起
		try {
			cal.setTime(qf.parse(rq));
			if (cal.after(qrq)&&cal.before(hrq)) tag = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tag;
	}

	/**
	 * 查询机构名称
	 */
	public String GetJgname(String jgh) throws Cls_exception{
	    String a="";
	    if (jgh == null||"".equals(jgh))
	    	return a;
	    else {
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
	 try{	
		 Cls_connect80 cn = new Cls_connect80();
		conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement("select orgname  from esseapp.KCS_ORG_INFO where org_code=?");
		 pstmt.setString(1, jgh);
		 rs=pstmt.executeQuery();

		while(rs.next()) {
			a=rs.getString(1);
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_zd.GetJgname() "+e.toString());
	 }	
	finally{		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetJgname()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetJgname()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetJgname()"+e.toString());
					}
			   conn =null;
						}
		}
	return a;	
	    }
	}
	/**
	 * 查询操作员姓名
	 * @param czy
	 * @return
	 * @throws Cls_exception
	 */
	public String GetCzyname(String czy,String jgh) throws Cls_exception{
	    String a="";
	    if (czy == null||"".equals(czy)||jgh==null||"".equals(jgh))
	    	return a;
	    else {
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
	 try{	
		 Cls_connect80 cn = new Cls_connect80();
		conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement("select opername  from esseapp.kcs_teller_info where oper_code=? and org_code=?");
		 pstmt.setString(1, czy);
		 pstmt.setString(2, jgh);
		 rs=pstmt.executeQuery();

		while(rs.next()) {
			a=rs.getString(1);
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_zd.GetCzyname() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetCzyname()"+e.toString());
					}
			     rs=null;  
						}
		if (pstmt!=null) {
			try{
				pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetCzyname()"+e.toString());
					}
			     pstmt=null;
					}
		if (conn!=null){
			try{
				conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetCzyname()"+e.toString());
					}
			   conn =null;
				}
		}
		 return a; 
	    }
	  
	} 
	public String GetJgtel(String jgh) throws Cls_exception{
	    String a="";
	    if (jgh == null||"".equals(jgh))
	    	return a;
	    else {
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
	 try{	
		 Cls_connect cn = new Cls_connect();
		 conn = cn.connect().getConnection();
		 //pstmt=conn.prepareStatement("select org_tel  from kcs_fkddmx where org_code=? order by serial_no desc");
		 pstmt=conn.prepareStatement("select org_tel from (select org_tel from kcs_fkddmx where  org_code=?  order by serial_no desc) where rownum=1");
		 pstmt.setString(1, jgh);
		 rs=pstmt.executeQuery();

		while(rs.next()) {
			a=rs.getString(1);
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_zd.GetJgtel() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetJgtel()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetJgtel()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetJgtel()"+e.toString());
					}
			   conn =null;
						}
		}
	return a;	
	    }
	}
	public String GetQxmch(String cityno,String countno) throws Cls_exception{
	    String a="";
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
	 try{	
		 Cls_connect80 cn = new Cls_connect80();
		 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement("select para_value from esseapp.kcs_para t where city_no=? and coun_no=?");
		 pstmt.setString(1, cityno);
		 pstmt.setString(2, countno);
		 rs=pstmt.executeQuery();

		while(rs.next()) {
			a=rs.getString(1);
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_zd.GetQxmch() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetQxmch()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetQxmch()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetQxmch()"+e.toString());
					}
			   conn =null;
						}
		}
	return a;
		}
	/**
	 * 查询某地市所有区县名称代码
	 * @return
	 */
	public List<Cls_e_tmp> GetQxdm(String cityno) throws Cls_exception{
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
		ArrayList<Cls_e_tmp> rtnAL = new ArrayList<Cls_e_tmp>();
	 try{	
		 Cls_connect80 cn = new Cls_connect80();
		 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement("select coun_no,para_value from esseapp.kcs_para t where city_no=? order by coun_no");
		 pstmt.setString(1, cityno);
		 rs=pstmt.executeQuery();
	     Cls_e_tmp v_tmp;
		while(rs.next()) {
			v_tmp=new Cls_e_tmp();
			v_tmp.setS1(rs.getString("coun_no"));
			v_tmp.setS2(rs.getString("para_value"));
			rtnAL.add(v_tmp);
			v_tmp=null;
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_zd.GetQxdm() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetQxdm()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetQxdm()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetQxdm()"+e.toString());
					}
			   conn =null;
						}
		}
	return rtnAL;
		}	
	public List<Cls_e_tmp> GetDsdm() throws Cls_exception{
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
		ArrayList<Cls_e_tmp> rtnAL = new ArrayList<Cls_e_tmp>();
	 try{	
		 Cls_connect80 cn = new Cls_connect80();
		 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement("select city_no,para_value from esseapp.kcs_para t where coun_no='00'  order by city_no");
		 rs=pstmt.executeQuery();
	     Cls_e_tmp v_tmp;
		while(rs.next()) {
			v_tmp=new Cls_e_tmp();
			v_tmp.setS1(rs.getString("city_no"));
			v_tmp.setS2(rs.getString("para_value"));
			rtnAL.add(v_tmp);
			v_tmp=null;
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_zd.GetDsdm() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetDsdm()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetDsdm()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetDsdm()"+e.toString());
					}
			   conn =null;
						}
		}
	return rtnAL;
		}	
	/**
	 * 取图片路径
	 * @param cityno
	 * @param countno
	 * @return
	 * @throws Cls_exception
	 */
	public String GetTp(String jgh,String fk_serial,int ddid,int mark) throws Cls_exception{
	    String a="";
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
	 try{	
		 Cls_connect cn = new Cls_connect();
		 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement("select wjm from kcs_fkphoto where org_code=? and fk_serial=? and dd_id=? and mark=?");
		 pstmt.setString(1, jgh);
		 pstmt.setString(2, fk_serial);
		 pstmt.setInt(3, ddid);
		 pstmt.setInt(4, mark);
		 rs=pstmt.executeQuery();

		while(rs.next()) {
			a=rs.getString(1);
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_zd.GetTp() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetTp()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetTp()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_zd.GetTp()"+e.toString());
					}
			   conn =null;
						}
		}
	return a;
		}	
	public int free_jl(int ddid,String cphm) throws Exception {
		Cls_gg gg = new Cls_gg();
		int id = gg.getint("select ZBWZ_ID.nextval from dual");
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert.append("insert into KCS_fkmfjl(id,cphm,fkdd_id,rq) values (");
		sqlInsert.append("?,?,?,SYSDATE)");
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
			stmt.setString(2, cphm);
			stmt.setInt(3, ddid);
			
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
					throw new Cls_exception("Cls_l_fkddmx.free_jl()"
							+ e.toString());
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.free_jl()"
							+ e.toString());
				}
				stmt = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new Cls_exception("Cls_l_fkddmx.free_jl()"
							+ e.toString());
				}
				con = null;
			}
		}
		return b;
	}	
	public Boolean isWd(String jgh) throws Cls_exception{
	    int a = 0;
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;
		Boolean tag = false;
	 try{	
		 Cls_connect cn = new Cls_connect();
		 conn = cn.connect().getConnection();
		 pstmt=conn.prepareStatement("select count(*) from kcs_fkwdxx where orgcode = ?");
		 pstmt.setString(1, jgh);

		 rs=pstmt.executeQuery();

		while(rs.next()) {
			a=rs.getInt(1);
			if (a == 1)
				tag = true;
					}
		 }
	  catch(Exception e){
			throw new Cls_exception("Cls_zd.isWd() "+e.toString());
	 }	
	finally{
		
		if (rs!=null){
			try{
				rs.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.isWd()"+e.toString());
					}
			     rs=null;  
						}
						if (pstmt!=null) {
			try{
							pstmt.close();}
			catch(Exception e){
						 throw new Cls_exception("Cls_zd.isWd()"+e.toString());
					}
			     pstmt=null;
						}
						if (conn!=null){
			try{
							conn.close();
			}catch(Exception e){
						 throw new Cls_exception("Cls_zd.isWd()"+e.toString());
					}
			   conn =null;
						}
		}
	return tag;
		}
}

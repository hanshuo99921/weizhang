/**
 * 
 */
package pkg_chexian;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;


/**
 * @author Administrator
 *
 */
public class Outimg {
  private static Logger log = Logger.getLogger(Outimg.class);
  public void Creat_img(int xh,String courseFile) throws Cls_exception{
	   Connection   con =null;
       InputStream in = null;
       ResultSet rs = null;
       PreparedStatement stmt = null;
       GZIPInputStream bos = null;
       FileOutputStream fileOutStream = null;
	   try {

		fileOutStream = new FileOutputStream(courseFile);
	    Cls_connect cn = new Cls_connect();
		con = cn.connect().getConnection();
    	stmt = con.prepareStatement("select PHOTO from kcs_fkphoto where serial_no=?");
		stmt.setInt(1, xh);
		rs = stmt.executeQuery();
    	while(rs.next()) {
    		in = rs.getBinaryStream("PHOTO");
    		if(in==null){
    			continue;
    		}
    		bos = new GZIPInputStream(in);
    		int count;  
            byte data[] = new byte[1024];  
            while ((count = bos.read(data, 0, 1024)) != -1) {  
            	 fileOutStream.write(data, 0, count);      
            }  
            bos.close();  	
    		in.close();
    		fileOutStream.close();
    		log.info("序列号："+xh+"，写入文件");
    	}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Cls_exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				throw new Cls_exception("Outimg.Creat_img()"
						+ e.toString());
			}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				throw new Cls_exception("Outimg.Creat_img()"
						+ e.toString());
			}
			stmt = null;
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				throw new Cls_exception("Outimg.Creat_img()"
						+ e.toString());
			}
			con = null;
		}
	}
   }
  public String getstring(int xh) throws Cls_exception {
		String a="",b="",str="";
		int mark=0;
		java.sql.Connection conn=null;
		java.sql.PreparedStatement pstmt=null;
		java.sql.ResultSet rs=null;

		try{	
			Cls_connect cn = new Cls_connect();
			conn = cn.connect().getConnection();
 		    pstmt=conn.prepareStatement("select dd_id,fk_serial,mark from kcs_fkphoto where serial_no="+xh);
		
		rs=pstmt.executeQuery();
		
		while(rs.next()) {
			a=rs.getString("dd_id");
			b=rs.getString("fk_serial");
			mark=rs.getInt("mark");
		}
	 }
		catch(Exception e){
			throw new Cls_exception("Cls_gg.getstring(string)   "+e.toString());
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
				 throw new Cls_exception("Cls_gg.getstring(string) finally"+e.toString());
			}
		}
       str=a+"_"+b+"_"+mark+".jpg";
	   log.info("序列号："+xh+" 文件名："+str);
		return str;
	}  
   public static void main(String[] args) throws IOException {
	   
	   File directory = new File("");// 参数为空
	   String courseFile = directory.getCanonicalPath();
	   System.out.println(courseFile);


   }
}

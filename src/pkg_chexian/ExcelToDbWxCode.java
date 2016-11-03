/**
 * 
 */
package pkg_chexian;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 
 */
public class ExcelToDbWxCode {
	private static Logger log = Logger.getLogger(ExcelToDbWxCode.class);

	private static boolean isvalid(Cell cells) {
		boolean tag = false;
		String str = cells.getContents();
		str = str.replaceAll("\\s*", "");
		if (str != null && !str.equals(""))
			tag = true;
		return tag;
	}

	private static String strim(Cell cells) {
		String str = cells.getContents();
		str = str.replaceAll("\\s*", "");
		return str;
	}

	private static boolean isNewCode(String cell) {
		boolean tag = true;
		Cls_gg gg = new Cls_gg();
		try {
			int num = gg.getrow(" kcs_fk_wxcode  where wx_code='" + cell + "'");
			if (num != 0)
				tag = false;
		} catch (Cls_exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return tag;
	}

	public Map<String, Object> chex_in(String wjm, String jgh, String czy)
			throws Cls_exception {
		log.error("΢��������ļ����룺" + wjm);
		int empt = 0, a = 0, num = 0, i = 0;
		String errinfo = "", sql = "", tempCell = "";
		File file = new File(wjm.replace(".xls", ".txt"));
		Map<String, Object> map = new HashMap<String, Object>();
		java.sql.Connection con = null;
		Statement stmt = null;
		try {
			Cls_connect cn = new Cls_connect();
			con = cn.connect().getConnection();
			con.setAutoCommit(false);
			stmt = con.createStatement();
			InputStream is = new FileInputStream(wjm);
			Workbook book = Workbook.getWorkbook(is);// ���һ�����������
			Sheet sheet = book.getSheet(0);// ȡ�õ�һ��������Ҳ����sheet���ֻ�á�
			Cell[] cells = null;
			int len = sheet.getRows();// ȡ������
			for (i = 1; i < len; i++) {
				cells = null;
				cells = sheet.getRow(i);
				if (cells.length == 0) { // �ж������ǲ��ǿ��У��ǿ��еĻ�������
					continue;
				}

				if (isvalid(cells[0])) {
					tempCell = strim(cells[0]);
					if (isNewCode(tempCell)) {
						sql = getsql(tempCell, jgh, czy);
						//log.error(sql);
						stmt.addBatch(sql);

					} else {
						errinfo = errinfo + (i+1) + "�����[" + tempCell
								+ "]�ظ���\r\n";
						log.error(errinfo);
						empt = empt + 1;

					}
				} else {
					errinfo = errinfo + (i+1) + "�����[" + "]Ϊ�ա�\r\n";
					empt = empt + 1;

				}
			}
			int[] counts = stmt.executeBatch();
			num = counts.length;
			con.commit();
			stmt.close();
			for (i = 0; i < num; i++) {
				if (counts[i] > 0)
					a = a + 1;

			}
			con.setAutoCommit(true);
			is.close();

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(errinfo.toCharArray());
			bw.newLine();
			bw.flush();
			file = null;
		} catch (SQLException se) {
			log.error(sql + se.toString());
			errinfo = errinfo + "���������쳣;" + se.toString();
			se.printStackTrace();
			try {
				if (con != null) {
					con.rollback();// ����sql�쳣������ع�
					con.setAutoCommit(true);// �����ύ��ʽΪĬ�Ϸ�ʽ
				}
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			log.error(sql + e1.toString());
			e1.printStackTrace();
			errinfo = errinfo + e1.toString();
		} catch (BiffException e) {
			log.error(sql + e.toString());
			e.printStackTrace();
			errinfo = errinfo + e.toString();
		} catch (IOException e) {
			log.error(sql + e.toString());
			e.printStackTrace();
			errinfo = errinfo + e.toString();
		} catch (Exception e) {
			log.error(sql + e.toString());
			e.printStackTrace();
			errinfo = errinfo + e.toString();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} catch (SQLException se) {
				se.printStackTrace();
				// log.error("gg.update.sqlexcepiton["+tsql+"]", se);
			}

		}
		map.put("empt", empt);
		map.put("info", "ִ�е��룺"+ num+"�������гɹ���"+ a+"����δִ�У�"+ empt);
		log.error("�����ִ�е��룺"+ num+"���ɹ���"+ a+"��δִ�У�"+ empt);
		return map;

	}

	private static String getsql(String wx_code, String jgh, String czy)
			throws Cls_exception {
		StringBuffer sqlInsert = new StringBuffer();
		sqlInsert
				.append("insert into kcs_fk_wxcode(id,wx_code,in_time,oper_orgcode,oper_opercode)");
		sqlInsert.append(" values (ZBWZ_ID.nextval,'");
		sqlInsert.append(wx_code);
		sqlInsert.append("',sysdate,'");
		sqlInsert.append(jgh);
		sqlInsert.append("','");
		sqlInsert.append(czy);
		sqlInsert.append("')");
		return sqlInsert.toString();
	}
}

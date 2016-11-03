package pkg_chexian;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Cls_connect80 {
	public javax.sql.DataSource connect() throws Cls_exception {

		try {
			InitialContext ctx = new InitialContext();
			//DataSource ds = (DataSource) ctx.lookup("ZbBmzh80");// 83生产80
			// DataSource ds = (DataSource)ctx.lookup("ZbpostJndi");//9生产80
			// DataSource ds = (DataSource)ctx.lookup("Zb66jndi");//9测试66
			// DataSource ds =(DataSource)ctx.lookup("java:/ZbBmzh83");//localhost jboss
			 DataSource ds =(DataSource)ctx.lookup("java:/ZbBmzh80");//localhost jboss
			return ds;
		} catch (Exception e) {
			Cls_exception myexception = new Cls_exception(
					"Cls_connect().connect()" + e.toString());
			throw myexception;

		}

	}

}

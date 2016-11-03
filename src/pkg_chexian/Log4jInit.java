/**
 * 
 */
package pkg_chexian;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Log4jInit extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Log4jInit() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Log4j-fk init");
        String prefix = config.getServletContext().getRealPath("/");
        String file = config.getInitParameter("log4j");
        String filePath = prefix + file;
        Properties props = new Properties();
        try {
            FileInputStream istream = new FileInputStream(filePath);
            props.load(istream);
            istream.close();
            //toPrint(props.getProperty("log4j.appender.file.File"));
           //String logFile = prefix + props.getProperty("log4j.appender.chex.File");//设置路径
            // String logFile = "/oracle/weblogic/user_projects/domains/base_domain" + props.getProperty("log4j.appender.fk.File");//设置路径
           String logFile = "/opt/weblogic/user_projects/domains/base_domain/servers/bmzww" + props.getProperty("log4j.appender.fk.File");//设置路径

            toPrint(logFile);
            props.setProperty("log4j.appender.fk.File",logFile);
            PropertyConfigurator.configure(props);//装入log4j配置信息
        } catch (IOException e) {
            toPrint("Could not read configuration file [" + filePath + "].");
            toPrint("Ignoring configuration file [" + filePath + "].");
            return;
        }



    }



    public static void toPrint(String content) {
        System.out.println(content);
    }

}

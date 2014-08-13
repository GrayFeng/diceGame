package net.netne.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.netne.common.SpringConstant;
import net.netne.mina.Launcher;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application Lifecycle Listener implementation class GameServer
 *
 */
public class GameServer implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public GameServer() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent context) {
    	try {
    		Launcher lanucher = new Launcher();
    		WebApplicationContext wac = WebApplicationContextUtils
        			.getRequiredWebApplicationContext(context.getServletContext());
        	SpringConstant.setWebApplicationContext(wac);
			lanucher.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}

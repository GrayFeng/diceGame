package net.netne.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.netne.mina.Launcher;

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
    public void contextInitialized(ServletContextEvent arg0) {
    	Launcher lanucher = new Launcher();
    	try {
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

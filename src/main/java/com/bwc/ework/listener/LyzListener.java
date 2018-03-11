package com.bwc.ework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bwc.ework.common.TimerManager;

/**
 * Application Lifecycle Listener implementation class LyzListener
 *
 */
public class LyzListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public LyzListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	new TimerManager();
    }
	
}

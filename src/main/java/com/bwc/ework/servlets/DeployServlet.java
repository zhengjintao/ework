package com.bwc.ework.servlets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeployServlet
 */
public class DeployServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeployServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String edit = request.getParameter("edit");
		String address = request.getParameter("address");
		String port = request.getParameter("port");
		String dbname = request.getParameter("dbname");
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		
		if(edit!=null){
			Properties prop = new Properties();
			FileInputStream fileInput = null;
			String path = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties").getPath(); 
			try {
				
				fileInput = new FileInputStream(path);
				prop.load(fileInput);
				
					
				 FileOutputStream oFile = new FileOutputStream(path);//true表示追加打开
				 prop.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
				 prop.setProperty("jdbc.host", address);
					prop.setProperty("jdbc.port", port);
					prop.setProperty("jdbc.dbname", dbname);
					prop.setProperty("jdbc.username", userid);
					prop.setProperty("jdbc.password", password);
				 prop.store(oFile, "The New properties file");
				 fileInput.close();
				 oFile.close();
			} catch (IOException io) {
				System.out.println("Read file[jdbc.properties] failed.");
			}
			
			RequestDispatcher re = request.getRequestDispatcher("index.jsp");
			re.forward(request, response);
		}else{
			
			Properties prop = new Properties();
			FileInputStream fileInput = null;
			String path = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties").getPath(); 
			try {
				
				fileInput = new FileInputStream(path);
				prop.load(fileInput);
			} catch (IOException io) {
				System.out.println("Read file[jdbc.properties] failed.");
			}
			address = prop.getProperty("jdbc.host");
			request.setAttribute("address", address);
			
		    port = prop.getProperty("jdbc.port");
			request.setAttribute("port", port);
			
			
			dbname = prop.getProperty("jdbc.dbname");
			request.setAttribute("dbname", dbname);
			
			userid = prop.getProperty("jdbc.username");
			request.setAttribute("userid", userid);
			
		    password = prop.getProperty("jdbc.password");;
			request.setAttribute("password", password);
			
			RequestDispatcher re = request.getRequestDispatcher("deploy.jsp");
			re.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

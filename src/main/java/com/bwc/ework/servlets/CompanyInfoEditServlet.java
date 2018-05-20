package com.bwc.ework.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CompanyInfoEditServlet
 */
public class CompanyInfoEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyInfoEditServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		if("submit".equals(mode)){
			this.submit(request, response);
		}else if("list".equals(mode)){
			
		}
		else{
			this.init(request, response);
		}
	}
    private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
	private void submit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
	
    private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.getRequestDispatcher("companyinfoedit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package com.bwc.ework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		if(uri.contains(".jsp")
			|| uri.contains(".html")
			||uri.contains(".do")){
			if(!uri.contains("login") && !uri.contains("deploy")){
				HttpSession session = req.getSession();
				Object userId = session.getAttribute("userinfo");
				
				if(userId == null){
					rep.sendRedirect("login.do");
					return;
				}
			}
		}
	    
		// min.css / min.js 以外不缓存
		if(!uri.contains("min.css") && !uri.contains("min.js")){
			System.out.println(uri);
			rep.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.  
			rep.setHeader("Pragma", "no-cache"); // HTTP 1.0.  
			rep.setDateHeader("Expires", 0); // Proxies. 
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

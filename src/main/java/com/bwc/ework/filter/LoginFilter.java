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
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest)request;
		HttpServletResponse httpresponse = (HttpServletResponse)response;
		httprequest.setCharacterEncoding("UTF-8");
		httpresponse.setHeader("Content-Type","text/html;charset=UTF-8");
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		if(uri.contains(".jsp")
			|| uri.contains(".html")
			||uri.contains(".do")){
			if(!uri.contains("login") && !uri.contains("deploy") && !uri.contains("callback")){
				HttpSession session = req.getSession();
				Object userId = session.getAttribute("userinfo");
				
				if(userId == null){
					rep.sendRedirect("login.do");
					return;
				}
			}
		}
	    
		// .min.css / .min.js / .woff2 以外不缓存
		if(!uri.contains(".min.css") && !uri.contains(".min.js") && !uri.contains(".woff2")){
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

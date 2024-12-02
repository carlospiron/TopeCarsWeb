package com.pinguela.topecars.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet Filter implementation class AuthenticatorFilter
 */
//@WebFilter("/*")
public class Logfilter extends HttpFilter implements Filter {
	private static Logger logger = LogManager.getLogger(Logfilter.class);

	public Logfilter() {
        super();
        
    }
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		StringBuffer url = new StringBuffer();
		url.append(httpRequest.getScheme());

		url.append(httpRequest.getRequestURI());
		
		logger.info("--> Request" + url + " from " + httpRequest.getRemoteHost());
		
		if (httpRequest.getCookies()!=null) {
			for (Cookie c: httpRequest.getCookies()) {
				logger.info("Cookie ["+c.getPath()+"] "+c.getName()+"="+c.getValue()+" (ttl:"+c.getMaxAge()+")");
			}	
		}
//		Map<String, String[]> parametres = httpRequest.getParameterMap();
		//TODO 
		
		chain.doFilter(request, response);
		logger.info("Request" + url + " from " + httpRequest.getRemoteAddr());
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}
}

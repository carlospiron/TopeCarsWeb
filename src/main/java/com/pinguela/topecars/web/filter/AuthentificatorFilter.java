package com.pinguela.topecars.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.topecars.web.util.Attributes;
import com.pinguela.topecars.web.util.RouterUtils;
import com.pinguela.topecars.web.util.SessionManager;
import com.pinguela.topecars.web.util.Views;

/**
 * Servlet Filter implementation class AuthenticatorFilter
 */
@WebFilter("/private/*")
public class AuthentificatorFilter extends HttpFilter implements Filter {
       

    public AuthentificatorFilter() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		if(SessionManager.getAttribute(httpRequest, Attributes.CLIENTE)==null) {
			RouterUtils.route(httpRequest, (HttpServletResponse) response, false, Views.LOGIN);
		}else {
			chain.doFilter(request, response);
		}
		
	}
	
	public void destroy() {
		
	}


}

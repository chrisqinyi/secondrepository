package org.financial.foa.base;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

public class DWRCSRFFilter extends GenericFilterBean {
	//private SessionRegistry sessionRegistry;
	String dwrMethodNames;

	public void setDwrMethodNames(String dwrMethodNames) {
		this.dwrMethodNames = dwrMethodNames;
	}
/*
	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
*/
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		String requestURI = ((HttpServletRequest) arg0).getRequestURI();
//		if (dwrMethodNames.contains(requestURI.substring(requestURI
//				.lastIndexOf('/') + 1))
//				&& "false".equals(RequestContextHolder.getRequestAttributes()
//						.getAttribute("windowActivated",
//								RequestAttributes.SCOPE_SESSION))) {
//			HttpSession session = ((HttpServletRequest) arg0).getSession(false);
//			sessionRegistry.removeSessionInformation(session.getId());
//			session.invalidate();
//			logger.warn("CSRF detected! request is not sent from a valid page!");
//			return;
//			
//		}
		arg2.doFilter(arg0, arg1);

	}

}

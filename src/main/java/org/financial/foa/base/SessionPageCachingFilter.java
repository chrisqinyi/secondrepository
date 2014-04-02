package org.financial.foa.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class SessionPageCachingFilter extends SimplePageCachingFilter {

    public SessionPageCachingFilter(String cacheName) {
        super(cacheName);
    }

    @Override
    protected String calculateKey(HttpServletRequest httpRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        String queryString = httpRequest.getQueryString();
        queryString = (null == queryString) ? null : queryString.replaceAll("sessionGroupId=[^&]*", "");
        String sessionID = null;
        HttpSession session = httpRequest.getSession(false);
        if(null!=session){
            sessionID=session.getId();
        }
        stringBuffer.append(httpRequest.getMethod()).append(httpRequest.getRequestURI()).append(queryString).append(sessionID);
        String key = stringBuffer.toString();
        return key;
    }

}

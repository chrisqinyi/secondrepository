package org.financial.foa.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class CachingHeaderFilter implements Filter{

    private int cacheSeconds = -1;
    
    private boolean isStatic;
    
    
    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }


    public void init(FilterConfig filterConfig) throws ServletException {
    }

    
    public void setCacheSeconds(int cacheSeconds) {
        this.cacheSeconds = cacheSeconds;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp=(HttpServletResponse) response;
        if(isStatic){
            resp.setHeader("Cache-Control", "max-age="+cacheSeconds+",public");
        }else{
            resp.setHeader("Pragma", "");                   // to avoid error opening in IE6 and IE7 
            resp.setHeader("Cache-Control", "private");     // otherwise unable to "open" file in IE6
        }
        chain.doFilter(request, response);
        
    }

    public void destroy() {
    }

}

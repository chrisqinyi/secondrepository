package org.financial.foa.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.LastModified;

@Controller
@RequestMapping("/view{version}")
public class UrlFilenameViewController extends org.springframework.web.servlet.mvc.UrlFilenameViewController implements LastModified {

    private long lastModified = System.currentTimeMillis();

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.mvc.AbstractController#handleRequest(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */

    @RequestMapping(value = "{viewPath}", method = RequestMethod.GET)
    public String handleRequest(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest, @PathVariable String version, @PathVariable String viewPath)
            throws Exception {
    	/*
        if (!version.equals(BaseIBankController.version)) {
            response.sendRedirect(request.getContextPath()+"/nfs/home/init.do");
        }*/
//        if (!"true".equalsIgnoreCase(System.getProperty("com.scb.ibnk.jersey.debug")) && webRequest.checkNotModified(lastModified)) {
//            checkAndPrepare(request, response, true);
//            return null;
//        }
//        response.setHeader("Age", "576");
//        response.setHeader("Connection", "Keep-Alive");
//        response.setHeader("Keep-Alive", "timeout=10, max=82");
        //response.setHeader("Warning", "1103 /10.20.165.38:1024 \"Response is stale\"");
        
        //ModelAndView handleRequest = super.handleRequest(request, response);
        
        //checkAndPrepare(request, response, true);
        response.setHeader("Cache-Control", "min-fresh="+this.getCacheSeconds()+",public");
        //Qin Yi:fake date header to prevent incorrect caching behavior.
        response.setDateHeader("Last-Modified", System.currentTimeMillis());
//        response.setDateHeader("Date", 0);
        return viewPath;
    }

    public long getLastModified(HttpServletRequest request) {
        return lastModified;  
    }

}

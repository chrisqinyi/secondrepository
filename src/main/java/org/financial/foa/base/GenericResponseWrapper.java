package org.financial.foa.base;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;


public class GenericResponseWrapper extends net.sf.ehcache.constructs.web.GenericResponseWrapper {

    /**
     * 
     */
    private static final long serialVersionUID = 6763580074996882725L;

    public GenericResponseWrapper(HttpServletResponse response, OutputStream outstr) {
        super(response, outstr);
    }

    @Override
    public void sendRedirect(String string) throws IOException {
        super.sendRedirect(string);
        this.getHeaders().add(new String[]{"Location", string});
    }

}

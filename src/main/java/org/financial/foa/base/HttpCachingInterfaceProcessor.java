package org.financial.foa.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.servlet.UrlProcessor;
/**
 * Extends UrlProcessor to enable DWR cache as the generated JSs
 * by DWR is always the same,no need to generate and transfer every
 * time. As a result network load and bandwidth can be optimized.
 * The caching will exclude dynamic DWR call which contains business
 * data.
 */
public class HttpCachingInterfaceProcessor extends UrlProcessor {
    /**
     *  default period for seconds is one year.
     */
    private static final Integer SECONDS_OF_A_YEAR = 31556926;

    /**
     * the cache store period in seconds.
     */
    private Integer seconds = SECONDS_OF_A_YEAR;
    /**
     * Can be set from spring context if necessary.
     * @param seconds the value to be set.
     */
    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }
    /**
     * Store the application startup time. This will be the time we will set
     * as the Last-Modified time for all the interface scripts.
     */
    private final long lastUpdatedTime = System.currentTimeMillis();
    /**
     * If-Modified-Since header.
     */
    private static final String HEADER_IF_MODIFIED = "If-Modified-Since";
    /**
     * Last-Modified header.
     */
    private static final String HEADER_LAST_MODIFIED = "Last-Modified";
    /**
     * Expires header.
     */
    private static final String HEADER_EXPIRES = "Expires";
    /**
     * Cache-Control header.
     */
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    /**
     * @see org.directwebremoting.servlet.UrlProcessor#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     * @param req The HTTP request data
     * @param resp Where we write the HTTP response data
     * @throws IOException If the write process fails
     */
    @Override
    public void handle(HttpServletRequest  req, HttpServletResponse resp) throws IOException {
//        resp.setHeader("Cache-Control", "no-store");
//        resp.setHeader("Pragma", "no-cache");
//        resp.setDateHeader("Expires", -1);
        //If it is a method call, invoke & return directly.
        if (req.getRequestURL().toString().endsWith(".dwr")) {
            super.handle(req, resp);
            return;
        }
        

        //These two headers are always needed.
        //Set expires date to one year later 
        resp.setDateHeader(HEADER_EXPIRES, System.currentTimeMillis() + seconds * 1000L);
        resp.setHeader(HEADER_CACHE_CONTROL, "max-age=" + seconds + ", must-revalidate");
        long ifModifiedSince = req.getDateHeader(HEADER_IF_MODIFIED);
//        if (!"true".equalsIgnoreCase(System.getProperty("com.scb.ibnk.jersey.debug")) && ifModifiedSince >= lastUpdatedTime ) {
//             If the browser has current version of the file, dont send the script. Just say it has not changed
//            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
//        } else {
         // If the browser does not have the script in the cache or the cached copy is stale
            // set the Last-Modified date header and send the new script file
            // Note: If the browser does not have the script in its cache ifModifiedSince will be -1
            resp.setDateHeader(HEADER_LAST_MODIFIED, lastUpdatedTime);
            super.handle(req, resp);
//        }
    }
}

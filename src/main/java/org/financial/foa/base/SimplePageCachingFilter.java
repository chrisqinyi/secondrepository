package org.financial.foa.base;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.PageInfo;
import net.sf.ehcache.constructs.web.ResponseHeadersNotModifiableException;
import net.sf.ehcache.constructs.web.ResponseUtil;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimplePageCachingFilter extends net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter {

    private static final Log LOG = LogFactory.getLog(SimplePageCachingFilter.class.getName());
    private static final String PAGE_FROM_CACHE = "pageFromCache";
    private String cacheName=NAME;
    private boolean ignoreMethodCallParameter = false;
    private boolean enabled=true;
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isIgnoreMethodCallParameter() {
        return ignoreMethodCallParameter;
    }
    
    public void setIgnoreMethodCallParameter(boolean ignoreMethodCallParameter) {
        this.ignoreMethodCallParameter = ignoreMethodCallParameter;
    }
    @Override
    protected String getCacheName() {
        return cacheName;
    }
    public SimplePageCachingFilter() {
        doInit(null);
    }
    public SimplePageCachingFilter(String cacheName) {
        this.cacheName=cacheName;
        doInit(null);
    }
    
    /**
     * Writes the response content.
     * This will be gzipped or non gzipped depending on whether the User Agent accepts
     * GZIP encoding.
     * <p/>
     * If the body is written gzipped a gzip header is added.
     *
     * @param response
     * @param pageInfo
     * @throws IOException
     */
    protected void writeContent(final HttpServletRequest request,
                                final HttpServletResponse response, final PageInfo pageInfo)
            throws IOException, ResponseHeadersNotModifiableException {
        Boolean isPageFromCache=(Boolean) request.getAttribute(PAGE_FROM_CACHE)&&request.getRequestURI().endsWith(".dwr");
        String batchId=null;
        if (isPageFromCache) {
            String payLoad=null;
            ServletInputStream is = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int letti;
            try {
                is = request.getInputStream();
                while ((letti = is.read(buf)) > 0) {
                    baos.write(buf, 0, letti);
                }
                byte[] byteArray = baos.toByteArray();
                payLoad=new String(byteArray);
            } catch (IOException e) {
            } finally {
                try {
                    is.close();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Matcher matcher=Pattern.compile("\\S\\sbatchId=(\\d+?)\\s\\S").matcher(payLoad);
            if(matcher.find()){
                batchId = matcher.group(1);
            }
        }
        byte[] body;
        if (acceptsGzipEncoding(request)) {
            ResponseUtil.addGzipHeader(response);
            body = pageInfo.getGzippedBody();
            if (ResponseUtil.shouldGzippedBodyBeZero(body, request)) {
                body = new byte[0];
            }
        } else {
            body = pageInfo.getUngzippedBody();
            if (isPageFromCache && null!=batchId) {
                String content=new String(body);
                content=content.replaceAll("dwr.engine.remote.handleNewScriptSession(.*?);", "")
                        .replaceFirst("\".*?\"", "\""+batchId+"\"");
                body = content.getBytes();
            }
        }

        boolean shouldBodyBeZero = ResponseUtil.shouldBodyBeZero(request, pageInfo.getStatusCode());
        if (shouldBodyBeZero) {
            body = new byte[0];
        }

        response.setContentLength(body.length);
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(body);
        out.flush();
    }
    /**
     * Performs the filtering for a request. This method caches based responses keyed by {@link #calculateKey(javax.servlet.http.HttpServletRequest)}
     * <p/>
     * By default this method will queue requests requesting the page response for a given key until the first thread in the queue has completed. The
     * request which occurs when the page expires incurs the cost of waiting for the downstream processing to return the respone.
     * <p/>
     * The maximum time to wait can be configured by setting <code>setTimeoutMillis</code> on the underlying <code>BlockingCache</code>.
     * 
     * @param request
     * @param response
     * @param chain
     * @throws AlreadyGzippedException
     *             if a double gzip is attempted
     * @throws AlreadyCommittedException
     *             if the response was committed on the way in or the on the way back
     * @throws FilterNonReentrantException
     *             if an attempt is made to reenter this filter in the same request.
     * @throws LockTimeoutException
     *             if this request is waiting on another that is populating the cache entry and timeouts while waiting. Only occurs if the
     *             BlockingCache has a timeout set.
     * @throws Exception
     *             for all other exceptions. They will be caught and logged in
     *             {@link Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)}
     */
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws AlreadyGzippedException, AlreadyCommittedException, FilterNonReentrantException, LockTimeoutException, Exception {
        if (response.isCommitted()) {
            throw new AlreadyCommittedException("Response already committed before doing buildPage.");
        }
        logRequestHeaders(request);
        PageInfo pageInfo = buildPageInfo(request, response, chain);

        // return on error or redirect code
        int statusCode = pageInfo.getStatusCode();
        if (statusCode != HttpServletResponse.SC_OK && 
           !(statusCode == HttpServletResponse.SC_MOVED_TEMPORARILY &&request.getRequestURI().endsWith(".do"))) {
            return;
        }
        if (!response.isCommitted()) {
            writeResponse(request, response, pageInfo);
        }

    }

    protected boolean acceptsGzipEncoding(HttpServletRequest request) {
//        final boolean ie6 = headerContains(request, "User-Agent", "MSIE 6.0");
//        final boolean ie7 = headerContains(request, "User-Agent", "MSIE 7.0");
//        return super.acceptsGzipEncoding(request);
        return false;
    }

    /**
     * Checks if request contains the header value.
     */
    private boolean headerContains(final HttpServletRequest request, final String header, final String value) {

        logRequestHeaders(request);

        final Enumeration accepted = request.getHeaders(header);
        while (accepted.hasMoreElements()) {
            final String headerValue = (String) accepted.nextElement();
            if (headerValue.indexOf(value) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Builds the PageInfo object by passing the request along the filter chain
     * 
     * @param request
     * @param response
     * @param chain
     * @return a Serializable value object for the page or page fragment
     * @throws AlreadyGzippedException
     *             if an attempt is made to double gzip the body
     * @throws Exception
     */
    protected PageInfo buildPage(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws AlreadyGzippedException, Exception {

        // Invoke the next entity in the chain
        final ByteArrayOutputStream outstr = new ByteArrayOutputStream();
        final GenericResponseWrapper wrapper = new GenericResponseWrapper(response, outstr);
        chain.doFilter(request, wrapper);
        wrapper.flush();

        // Return the page info
        return new PageInfo(wrapper.getStatus(), wrapper.getContentType(), wrapper.getHeaders(), wrapper.getCookies(), outstr.toByteArray(), false);
    }

    /**
     * Build page info either using the cache or building the page directly.
     * <p/>
     * Some requests are for page fragments which should never be gzipped, or for other pages which are not gzipped.
     */
    protected PageInfo buildPageInfo(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws Exception {
        if(!enabled){
        	request.setAttribute(PAGE_FROM_CACHE, false);
            return buildPage(request, response, chain);
        }
        // Look up the cached page
        final String key = calculateKey(request);
        PageInfo pageInfo = null;
        String originalThreadName = Thread.currentThread().getName();
        try {
            checkNoReentry(request);
            Element element = blockingCache.get(key);
            if (element == null || element.getObjectValue() == null) {
                try {
                    // Page is not cached - build the response, cache it, and send to client
                    request.setAttribute(PAGE_FROM_CACHE, false);
                    pageInfo = buildPage(request, response, chain);
                    if (null!=pageInfo &&(ignoreMethodCallParameter||request.getRequestURL().toString().contains("Sybase365AckServlet") ||request.getRequestURL().toString().endsWith(".dwr") && (null!=request.getAttribute(Remoter.METHOD_CALL_HAS_PARAMETER)&&!(Boolean) request.getAttribute(Remoter.METHOD_CALL_HAS_PARAMETER))) && (pageInfo.isOk() || HttpServletResponse.SC_MOVED_TEMPORARILY == pageInfo.getStatusCode()
                            && request.getRequestURL().toString().endsWith(".do"))) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("PageInfo ok. Adding to cache " + blockingCache.getName() + " with key " + key);
                        }
                        blockingCache.put(new Element(key, pageInfo));    
                    } else {
                        if (LOG.isWarnEnabled()) {
                            LOG.warn("PageInfo was not ok(200). Putting null into cache " + blockingCache.getName() + " with key " + key);
                        }
                        blockingCache.put(new Element(key, null));
                    }
                } catch (final Throwable throwable) {
                    // Must unlock the cache if the above fails. Will be logged at Filter
                    blockingCache.put(new Element(key, null));
                    throw new Exception(throwable);
                }
            } else {
                request.setAttribute(PAGE_FROM_CACHE, true);
                pageInfo = (PageInfo) element.getObjectValue();
            }
        } catch (LockTimeoutException e) {
            // do not release the lock, because you never acquired it
            throw e;
        } finally {
            Thread.currentThread().setName(originalThreadName);
        }
        return pageInfo;
    }

    /**
     * Pages are cached based on their key. The key for this cache is the URI followed by the query string. An example is
     * <code>/admin/SomePage.jsp?id=1234&name=Beagle</code>.
     * <p/>
     * This key technique is suitable for a wide range of uses. It is independent of hostname and port number, so will work well in situations where
     * there are multiple domains which get the same content, or where users access based on different port numbers.
     * <p/>
     * A problem can occur with tracking software, where unique ids are inserted into request query strings. Because each request generates a unique
     * key, there will never be a cache hit. For these situations it is better to parse the request parameters and override
     * {@link #calculateKey(javax.servlet.http.HttpServletRequest)} with an implementation that takes account of only the significant ones.
     * <p/>
     * The key should be unique. Implementers should differentiate between GET and HEAD requests otherwise blank pages can result.
     * 
     * @param httpRequest
     * @return the key, generally the URI plus request parameters
     */
    protected String calculateKey(HttpServletRequest httpRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        String queryString = httpRequest.getQueryString();
        queryString = (null == queryString) ? null : queryString.replaceAll("sessionGroupId=[^&]*", "");
        stringBuffer.append(httpRequest.getMethod()).append(httpRequest.getRequestURI()).append(queryString);
        String key = stringBuffer.toString();
        return key;
    }
    /**
     * Writes the response from a PageInfo object.
     *
     * @param request
     * @param response
     * @param pageInfo
     * @throws IOException
     * @throws DataFormatException
     * @throws ResponseHeadersNotModifiableException
     *
     */
    protected void writeResponse(final HttpServletRequest request, final HttpServletResponse response, final PageInfo pageInfo)
            throws IOException, DataFormatException, ResponseHeadersNotModifiableException {
        boolean requestAcceptsGzipEncoding = acceptsGzipEncoding(request);
        setStatus(response, pageInfo);
        setHeaders(pageInfo, requestAcceptsGzipEncoding, response);
        if(pageInfo.getStatusCode()==HttpServletResponse.SC_MOVED_TEMPORARILY){
            return;
        }
        setCookies(pageInfo, response);
        setContentType(response, pageInfo);
        writeContent(request, response, pageInfo);
    }

}

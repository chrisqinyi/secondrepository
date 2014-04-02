package org.financial.foa.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DwrVersionFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(DwrVersionFilter.class);
	private String lowVersion;
	private String highVersion;
	private String dwrVersion;
	private Map<String, Boolean> versionMap = new HashMap<String, Boolean>();
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{
		HttpServletRequest req = (HttpServletRequest)request;
		String pathInfo = req.getRequestURL().toString();
		Matcher m = Pattern.compile("/dwr-[\\d\\.]*/").matcher(pathInfo);
		logger.info("[path info:{}]",pathInfo);
		String currentVersion=null;
		if(m.find()) {
			try {
				String result = m.group(0);
				currentVersion = result.substring(5, result.length()-1);
				logger.info("[currentVersion:{}]",currentVersion);
				String path =  req.getServletPath();
				if(req.getPathInfo()!=null)
					path =  path.concat(req.getPathInfo());
				logger.info("[ServletPath:{},PathInfo:{}]",new String[]{req.getServletPath(),req.getPathInfo()});
				Boolean flag = versionMap.get(currentVersion);
				if(flag!=null) {
					logger.info("flag:"+flag);
					if(flag) {
						logger.info("[flag:{},redirectPath:{}]", new Object[]{flag,path.replace(currentVersion, dwrVersion)});
						if(!dwrVersion.equalsIgnoreCase(currentVersion)){
							request.getRequestDispatcher(path.replace(currentVersion, dwrVersion)).forward(request, response);
						} else {
							chain.doFilter(request, response);
						}
					} else {
						logger.info("[DwrVersionFilter: currentVersion]={}",currentVersion);
						response.getWriter().print("INVALID_DWR_VERSION");
					}
				} else {
					int compareLowVersion = compareVersionNumber(currentVersion,lowVersion);
					int compareHighVersion = compareVersionNumber(currentVersion,highVersion);
					logger.info("[compareLowVersion:{},compareHighVersion:{}]", new String[]{String.valueOf(
						compareLowVersion),String.valueOf(compareHighVersion)});
					if(compareLowVersion>=0&&compareHighVersion<=0)
					{
						versionMap.put(currentVersion, Boolean.TRUE);
						if(!dwrVersion.equalsIgnoreCase(currentVersion)) {
							request.getRequestDispatcher(path.replace(currentVersion, dwrVersion)).forward(request, response);
						} else {
							chain.doFilter(request, response);
						}
					} else {
						versionMap.put(currentVersion, Boolean.FALSE);
						response.getWriter().print("INVALID_DWR_VERSION");
					}
				}
			} catch (Exception e) {
				logger.info("[DwrVersionFilter: currentVersion]={}",currentVersion);
				response.getWriter().print("INVALID_DWR_VERSION");
			}
		} else {
			response.getWriter().print("INVALID_DWR_VERSION");
		}
	}

	public void init(FilterConfig config) throws ServletException {
		
	}
	
	private int compareVersionNumber(String currentVersion, String comparedVersion) throws Exception{
		if(comparedVersion.equalsIgnoreCase(currentVersion)){
			return 0;
		}
		String[] currentArray = currentVersion.split("\\.");
		String[] comparedArray = comparedVersion.split("\\.");
		if(currentArray.length==3) {
			if(compareInteger(currentArray[0],comparedArray[0])!=0) {
				return compareInteger(currentArray[0],comparedArray[0]);
			} else { 
				if(compareInteger(currentArray[1],comparedArray[1])!=0){
					return compareInteger(currentArray[1],comparedArray[1]);
				} else {
					return compareInteger(currentArray[2],comparedArray[2]);
				}
			}
		} else {
			throw new Exception("INVALID_DWR_VERSION");
		}
	}
	
	private int compareInteger(String first, String second) throws NumberFormatException {
		int a = Integer.parseInt(first);
		int b = Integer.parseInt(second);
		return a-b;
	}

	public void setLowVersion(String lowVersion) {
		this.lowVersion = lowVersion;
	}

	public void setHighVersion(String highVersion) {
		this.highVersion = highVersion;
	}

	public void setDwrVersion(String dwrVersion) {
		this.dwrVersion = dwrVersion;
	}
	
}

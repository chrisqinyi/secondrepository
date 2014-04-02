package org.financial.foa.base;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomePageInitController extends BaseIBankController {

    private static final Logger logger = LoggerFactory.getLogger(HomePageInitController.class);
    private String dwrLowVersion;
    private String dwrHighVersion;
    private String dwrCurrentVersion;
    private String androidUpgradeUrl;
    private String iosUpgradeUrl;
    private MessageSource messageSource;
    
    public void setDwrLowVersion(String dwrLowVersion) {
		this.dwrLowVersion = dwrLowVersion;
	}

	public void setDwrHighVersion(String dwrHighVersion) {
		this.dwrHighVersion = dwrHighVersion;
	}

	public void setDwrCurrentVersion(String dwrCurrentVersion) {
		this.dwrCurrentVersion = dwrCurrentVersion;
	}

	public void setAndroidUpgradeUrl(String androidUpgradeUrl) {
		this.androidUpgradeUrl = androidUpgradeUrl;
	}

	public void setIosUpgradeUrl(String iosUpgradeUrl) {
		this.iosUpgradeUrl = iosUpgradeUrl;
	}
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping("init.do")
    public String init(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("[HomePageInitController] -- init.do");
        boolean hiddenFlag = true;
        // Qin Yi 2012.10.12 already populated in init filer.
        // BSOIContext.setCountry("JE");
        try {
            logger.info("[HomePageInitController] getHiddenLoginStatus");
            logger.info("[HomePageInitController] getHiddenLoginStatus -- hiddenFlag : " + hiddenFlag);
        } catch (Exception e) {
            logger.error("[HomePageInitController] ErrorCode: " + e.getMessage());
            logger.error("[HomePageInitController] Exception: " + e);
            return "redirect:error.do";
        }
        if (hiddenFlag) {
            logger.info("[HomePageInitController] -- Fail");
            return "redirect:error.do";
        } else {
            logger.info("[HomePageInitController] -- Success");
            String url = generateRedirectURL(request, "security/login");
            StringBuffer sb = new StringBuffer();
            sb.append("?");
            @SuppressWarnings("unchecked")
            Enumeration<String> it = request.getParameterNames();
            while (it.hasMoreElements()) {
                String paramName = it.nextElement();
                String paramValue = request.getParameter(paramName);
                sb.append(paramName + "=" + paramValue + "&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return url + sb.toString();
        }
    }

    @RequestMapping("error.do")
    public String error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("[HomePageInitController] -- error.do");
        return generateRedirectURL(request, "maintenance/maintenance");
    }

    @RequestMapping("hideURLLogin.do")
    public String hideURLLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("[HomePageInitController] -- hideURLLogin.do");
        return generateRedirectURL(request, "security/login");
    }

}

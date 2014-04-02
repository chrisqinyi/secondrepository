package org.financial.foa.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import ua_parser.Client;
import ua_parser.Parser;

public class BaseIBankController implements BeanFactoryAware {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static final String MOBILE = "mobile";
    static String version;

    private List<String> tabletDeviceList;


    public void setTabletDevices(String tabletDevices) {
        tabletDeviceList = new ArrayList<String>();
        String[] tabletDevicesArray = tabletDevices.split(",");
        if (tabletDevicesArray != null && tabletDevicesArray.length > 0) {
            for (int i = 0; i < tabletDevicesArray.length; i++) {
                tabletDeviceList.add(tabletDevicesArray[i].trim());
            }
        }
    }

    public void setVersion(String version) {
        BaseIBankController.version = version;
        REDIRECT_IBANK = "redirect:/nfs/view" + version + "/ibank/";
        REDIRECT_MOBILE = "redirect:/nfs/view" + version + "/mobile/";
    }

    protected static String REDIRECT_IBANK = "redirect:/nfs/view" + version + "/ibank/";
    protected static String REDIRECT_MOBILE = "redirect:/nfs/view" + version + "/mobile/";
    protected static final String REDIRECT_HTML = ".html";
    protected BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Prefix Redirect URL (Mobile / IBank)
     * 
     * @param request
     * @param url
     * @return URL
     * @throws IOException
     */
    protected String generateRedirectURL(HttpServletRequest request, String url) throws IOException {
        String clientDevice = request.getHeader("User-Agent");
        Parser uaParser = new Parser();
        Client c = uaParser.parse(clientDevice);
        logger.warn("User-Agent: " + clientDevice);
        logger.warn("uaParser - UserAgent - Family: " + c.userAgent.family);
        logger.warn("uaParser - Device - Family: " + c.device.family);
//        if (clientDevice.toLowerCase().contains(MOBILE) && !tabletDeviceList.contains(c.device.family)) {
//            return REDIRECT_MOBILE + url + REDIRECT_HTML;
//        }
        return REDIRECT_IBANK + url + REDIRECT_HTML;
    }

    protected void addAuditLog(String functionCode, String event){

    }

}

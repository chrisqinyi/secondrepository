package org.financial.foa.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BypassSecurityNonceCheckFilter implements BeanFactoryAware, Filter {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private String mode;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestParam = request.getParameter("s");
        if (mode.equals("READ_ONLY") && null != requestParam) {
            //UserInfoBean userInfoBean = (UserInfoBean) beanFactory.getBean("userInfoBean");
            //userInfoBean.setSecurityNonce(requestParam);
        }
        chain.doFilter(request, response);

    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

}

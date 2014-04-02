package org.financial.foa.validation;

import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


public class SecurityNonceConverter extends BeanConverter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityNonceConverter.class);
    @Override
    public Object convertInbound(Class<?> paramType, InboundVariable data) throws MarshallException {
        if(System.getProperties().containsKey("aop.generator.host.url")){
            return new SecurityNonce();
        }
        String requestNonce = data.getValue();
        String sessionNonce = null;
        //String sessionNonce = SecurityUtils.getSessionNonce();
        
        if (StringUtils.isEmpty(requestNonce)) {
            logger.error("[validateNonce: invalid nonce request, session]={},{}", requestNonce, sessionNonce);
            throw new RuntimeException("request nouce is null");
        }
        
        if (StringUtils.isEmpty(sessionNonce)) {
            logger.error("[validateNonce: invalid nonce request, session]={},{}", requestNonce, sessionNonce);
            throw new RuntimeException("session nouce is null");
        }
        
        if (!StringUtils.pathEquals(requestNonce, sessionNonce)) {
            logger.error("[validateNonce: invalid nonce request, session]={},{}", requestNonce, sessionNonce);
            throw new RuntimeException("ERR_SEC_INVALID_NONCE");
        }
        
        return new SecurityNonce();
    }

}

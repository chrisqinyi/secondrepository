package org.financial.foa.validation;

import java.util.Map;

public class ValidationRuleStrategy {

    static Map<String, String> strategyMap;

    public void setStrategyMap(Map<String, String> strategyMap) {
        ValidationRuleStrategy.strategyMap = strategyMap;
    }
    
    static String defaultValidationRegex;

    public void setDefaultValidationRegex(String defaultValidationRegex) {
        ValidationRuleStrategy.defaultValidationRegex = defaultValidationRegex;
    }
    
}

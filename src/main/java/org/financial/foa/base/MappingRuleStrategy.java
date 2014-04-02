package org.financial.foa.base;

import java.util.Map;


public class MappingRuleStrategy {    
static Map<String,Map<String,String>> strategyMap;
public void setStrategyMap(Map<String, Map<String, String>> strategyMap) {
    MappingRuleStrategy.strategyMap = strategyMap;
}
}

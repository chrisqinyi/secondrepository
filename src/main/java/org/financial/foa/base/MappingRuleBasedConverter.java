package org.financial.foa.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.convert.MapConverter;
import org.directwebremoting.extend.Converter;
import org.directwebremoting.extend.ConverterManager;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;

public class MappingRuleBasedConverter extends BeanConverter implements Converter {
    private MapConverter mapConverter=new MapConverter();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!MappingRuleStrategy.strategyMap.containsKey(data.getClass().getName())) {
            return super.convertOutbound(data, outctx);
        }
        Map<String, String> mappingRule = MappingRuleStrategy.strategyMap.get(data.getClass().getName());
        for (String key : mappingRule.keySet()) {
            String value = mappingRule.get(key);
            try {
                Object property = PropertyUtils.getProperty(data, key);
                if (null != property) {
                    if (map.containsKey(value)) {
                        if (property instanceof Collection) {
                            Object object = map.get(value);
                            if (object instanceof Collection) {
                                Collection c = ((Collection) object.getClass().newInstance());
                                c.addAll((Collection) object);
                                c.addAll((Collection) property);
                                map.put(value, c);
                            } else {
                                throw new MarshallException(data.getClass(), object.getClass() + " in map is not instance of collection");
                            }
                        } else {
                            throw new MarshallException(data.getClass(), property.getClass() + " to be merged is not instance of collection");
                        }
                    } else {
                        map.put(value, property);
                    }
                }
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            } catch (org.apache.commons.beanutils.NestedNullException e) {
            } catch (InstantiationException e) {
            }
        }
        return mapConverter.convertOutbound(map, outctx);
    }
    public void setConverterManager(ConverterManager converterManager) {
        mapConverter.setConverterManager(converterManager);
        super.setConverterManager(converterManager);   
    }
}

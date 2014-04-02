package org.financial.foa.base;

import java.util.Map;

import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.PlainProperty;
import org.directwebremoting.extend.Property;

public class ExceptionConverter extends org.directwebremoting.convert.ExceptionConverter {

    @Override
    public Map<String, Property> getPropertyMapFromClass(Class<?> type, boolean readRequired, boolean writeRequired) throws MarshallException {
        Map<String, Property> result = super.getPropertyMapFromClass(type, readRequired, writeRequired);
        result.put("javaClassName", new PlainProperty("javaClassName", type.getSimpleName()));
        return result;
    }
}

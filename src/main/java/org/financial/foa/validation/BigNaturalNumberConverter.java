package org.financial.foa.validation;

import org.directwebremoting.ConversionException;
import org.directwebremoting.convert.BigNumberConverter;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;

public class BigNaturalNumberConverter extends BigNumberConverter {

	@Override
	public Object convertInbound(Class<?> paramType, InboundVariable data)
			throws ConversionException {
		Object convertInbound = super.convertInbound(paramType, data);
		if (convertInbound instanceof Number && 0 > ((Number)convertInbound).doubleValue()) {
			 throw new MarshallException(paramType, "BigNumberConverter.FormatError");
		}
		return convertInbound;
	}

}

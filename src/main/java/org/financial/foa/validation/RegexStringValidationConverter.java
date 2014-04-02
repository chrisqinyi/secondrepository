package org.financial.foa.validation;

import java.net.URLDecoder;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;
import org.directwebremoting.convert.StringConverter;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.springframework.util.StringUtils;

public class RegexStringValidationConverter extends StringConverter {

    @SuppressWarnings("deprecation")
    @Override
    public Object convertInbound(Class<?> paramType, InboundVariable data) throws MarshallException {
        boolean valid = true;
        String value = data.getValue();
        if (StringUtils.isEmpty(value)) {
            return super.convertInbound(paramType, data);
        }
        String decode = URLDecoder.decode(value);
        String string =null;
        //String string = inctx.getCurrentTypeHintContext().toString();
        if (!antiSqlInjection(decode) || !antiScriptInjection(decode)) {
            throw new RuntimeException("You have entered a blank or an invalid value. Please check and try again.");
        }
        string = string.replaceAll("<.*?>", "");
        String validationRegex = ValidationRuleStrategy.strategyMap.get(string);

        if ("AllValidator".equals(validationRegex)) {
            valid = true;
        } else if ("EmailValidator".equals(validationRegex) && !GenericValidator.isEmail(decode)) {
            valid = false;
        } else if ("NumberValidator".equals(validationRegex) && !decode.matches("^[0-9]*$")) {
            valid = false;
        } else if ("IndexValidator".equals(validationRegex) && !decode.matches("^[A-Z]*-[0-9]*$")) {
            valid = false;
        } else if ("CreditCardValidator".equals(validationRegex) && !GenericValidator.isCreditCard(decode)) {
            valid = false;
        } else if ("UrlValidator".equals(validationRegex) && !GenericValidator.isUrl(decode)) {
            valid = false;
        } else if (valid && StringUtils.isEmpty(validationRegex)) {
            validationRegex = ValidationRuleStrategy.defaultValidationRegex;
        }
        if (valid && !validationRegex.endsWith("Validator") && !decode.matches(validationRegex)) {
            valid = false;
        }
        if (!valid) {
            // throw new MarshallException(paramType, "invalid string value:" + decode);
            throw new RuntimeException("You have entered a blank or an invalid value. Please check and try again.");
        }
        // String replace = decode.replace("_", "").replace("-", "").replace("@", "").replace(".", "").replace(" ", "").replace("\n", "");
        // if (!StringUtils.isAlphanumericSpace(replace)) {
        // throw new RuntimeException("invalid string value:" + decode);
        // }
        return super.convertInbound(paramType, data);
    }

    private boolean antiSqlInjection(String str) {
        String testStr = str.replaceAll("\\\\r|\\\\n", " ");
        // System.out.println(testStr);
        String sqlHeadKeyWord = "(select|update|delete|insert (into)?)\\b";
        String sqlHeadKeyWord2 = "(alter|drop|create|use|exec)\\b";
        String sqlBodyKeyWord = "(from|where)\\b";

        // filter ; select * from || update set
        if (Pattern.compile("'(((\\s*);)*)(\\s*)" + sqlHeadKeyWord + "(.+)" + sqlBodyKeyWord + "(.+)", Pattern.CASE_INSENSITIVE).matcher(testStr)
                .find()) {
            return false;
        }
        // filter or 1=1 || or 2 > 1 || and
        else if (Pattern.compile("'(\\s*)(or|and)", Pattern.CASE_INSENSITIVE).matcher(testStr).find()) {
            return false;
        }
        // filter drop DATABASE || alter
        else if (Pattern.compile("'(((\\s*);)*)(\\s*)" + sqlHeadKeyWord2, Pattern.CASE_INSENSITIVE).matcher(testStr).find()) {
            return false;
        }
        // filter -- || ;--
        else if (Pattern.compile("'(((\\s*);)*)(\\s*)--").matcher(testStr).find()) {
            return false;
        }
        // filter ASCII
        else if (Pattern.compile("chr\\((\\d)+\\)", Pattern.CASE_INSENSITIVE).matcher(str).find()) {
            return false;
        } else
            return true;
    }

    private boolean antiScriptInjection(String str) {

        // filter <script >||</script >||<object >||</object >||<applet >||</applet >
        String scriptreg = "<(/?)script| <(/?)object|<(/?)applet";
        Pattern scriptPattern = Pattern.compile(scriptreg, Pattern.CASE_INSENSITIVE);
        if (scriptPattern.matcher(str).find()) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        String decode = "io()ah190874";
        // boolean valid = true;
        String swiftRegex = "~`!@#$%^*_=(){}[]|\\;,\"<>&?/";
        // char[] chars = swiftRegex.toCharArray();
        // for (char c : chars) {
        // if (decode.contains(CharUtils.toString(c))) {
        // valid = false;
        // break;
        // }
        // }
        // if (!valid) {
        // System.out.println("Catch!!!");
        // } else {
        // System.err.println("Not catch...");
        // }

        swiftRegex = "^[a-zA-Z0-9 ,.]*$";
        decode = "afbhaAF2 45,0ghp.?";
        if (!decode.matches(swiftRegex)) {
            System.out.println("Catch!!!");
        } else {
            System.err.println("Not catch...");
        }
    }
}

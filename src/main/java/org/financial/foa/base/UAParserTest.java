package org.financial.foa.base;

import java.io.IOException;

import ua_parser.Client;
import ua_parser.Parser;

public class UAParserTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            String clientDevice = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0_1 like Mac OS X; en-us) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/23.0.1271.100 Mobile/10A525 Safari/8536.25";
            Parser uaParser = new Parser();
            Client c = uaParser.parse(clientDevice);
            System.out.println("User-Agent: " + c.device.family);
            System.out.println("User Device Family: " + c.device.family);
            System.out.println("UA - userAgent: " + c.userAgent.family.toLowerCase().contains("mobile"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.uy.record.base64;

import sun.misc.BASE64Encoder;

public class Base64Encode {

    private final static String token = "uy1991";

    public static String encode(String text){
        text += token;
        BASE64Encoder encoder = new BASE64Encoder();
        String encodeStr = encoder.encode(text.getBytes());
        return  encodeStr;
    }
}

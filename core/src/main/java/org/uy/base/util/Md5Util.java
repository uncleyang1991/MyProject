package org.uy.base.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.plugin2.message.Message;

import java.security.MessageDigest;

public class Md5Util {

    //MD5加盐
    private static String token = "org.uy";

    public static String md5(String content){
        String str = null;
        content += token;
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64 = new BASE64Encoder();
            str = base64.encode(md5.digest(content.getBytes("utf-8")));
        }catch(Exception e){
            System.err.println("MD5转换异常");
            e.printStackTrace();
        }
        return str;
    }
}

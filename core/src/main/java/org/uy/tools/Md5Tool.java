package org.uy.tools;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5Tool {

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            System.err.println("MD5加密出现错误");
        }
        return null;
    }
}

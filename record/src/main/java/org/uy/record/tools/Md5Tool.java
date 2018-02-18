package org.uy.record.tools;

import org.apache.log4j.Logger;
import org.uy.record.system.SystemCount;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5Tool {

    private final static Logger log = Logger.getLogger(Md5Tool.class);

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            log.error("MD5加密异常 "+e.toString());
            SystemCount.errorCount++;
        }
        return null;
    }
}

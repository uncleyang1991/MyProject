package org.uy.base.util;

import java.util.UUID;

public class IdUtil {

    //获取一个UUID
    public static String getUUID(){
        String[] uids = UUID.randomUUID().toString().split("//-");
        StringBuffer sb = new StringBuffer();
        for(String uid:uids)
            sb.append(uid);
        return sb.toString();
    }
}

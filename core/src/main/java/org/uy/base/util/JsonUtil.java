package org.uy.base.util;

import com.google.gson.Gson;

public class JsonUtil {

    private static Gson gson;

    static{
        gson = new Gson();
    }

    public static String o2json(Object obj){
        return gson.toJson(obj);
    }
}

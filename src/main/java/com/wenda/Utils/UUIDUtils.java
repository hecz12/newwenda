package com.wenda.Utils;

import java.util.UUID;

/**
 * Created by 49540 on 2017/6/27.
 */
public class UUIDUtils {
    public static  String getUUIDName()
    {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

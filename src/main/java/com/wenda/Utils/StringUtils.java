package com.wenda.Utils;

import sun.font.TrueTypeFont;

/**
 * Created by 49540 on 2017/6/27.
 */
public class StringUtils {

    /**
     * 判断字符串是否不为空
     * @param string
     * @return
     */
    public static boolean isBlank(String string)
    {
        return !isNotBlank(string);
    }


    /**
     * 判断字符串是否为空，如果为空，返回false，否则返回true
     * @param string
     * @return
     */
    public static boolean isNotBlank(String string)
    {
        if(string==null)
        {
            return false;
        }
        else if(string.trim()=="")
        {
            return false;
        }
        return true;
    }

    /**
     * 用户名超出边界
     * @param string
     * @param bound
     * @return
     */
    public static boolean isBeyond(String string,int bound)
    {
        return !isNotBeyond(string,bound);
    }

    /**
     * 判断字符串是否超出边界，如果超出，则报错
     * @param string
     * @param bound
     * @return
     */
    public static boolean isNotBeyond(String string,int bound)
    {
        if(string!=null&&string.length()<=bound)
        {
            return  true;
        }
        return false;
    }

    /**
     * 字符串不遵守正则规范
     * @param name
     * @param regex
     * @return
     */
    public static boolean isNotObserveUsername(String name,String regex)
    {
        return !isObserveUsername(name, regex);
    }
    /**
     * 判断字符串是否符合正则规范
     * @param name
     * @param regex
     * @return
     */
    public static boolean isObserveUsername(String name,String regex)
    {
       if(name!=null&&name.matches(regex))
       {
           return true;
       }
       return false;
    }

    /**
     * 字符串不相等
     * @param string
     * @param anotherStr
     * @return
     */
    public static boolean isNotEqual(String string,String anotherStr)
    {
        return isEqual(string, anotherStr);
    }
    /**
     * 判断字符串是否相等，相等返回true，不等返回false
     * @param string
     * @param anotherStr
     * @return
     */
    public static boolean isEqual(String string,String anotherStr)
    {
        if(string!=null&&string.equals(anotherStr))
        {
            return true;
        }
        else{
            return false;
        }
    }
}

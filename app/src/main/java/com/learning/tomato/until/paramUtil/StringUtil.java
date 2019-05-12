package com.learning.tomato.until.paramUtil;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 字符串校验工具类
 * @date 2019/5/11 14:36
 */

public class StringUtil {
    public static boolean isEmpty(String string) {
        if (string == null || string.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }
}

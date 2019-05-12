package com.learning.tomato.until.paramUtil;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 对象校验工具类
 * @date 2019/5/11 14:36
 */

public class ObjectUtil {

    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }
}

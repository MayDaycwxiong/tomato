package com.learning.tomato.until.paramUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: 日期格式转换工具类
 * @date 2019/5/11 21:15
 */
public class DateTranslate {
    /**
     * 日期转换为String
     * @param date
     * @return
     */
    public static String translateToString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    public static Date translateToDate(String date) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }
}

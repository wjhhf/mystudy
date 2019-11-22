package com.hhf.study.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Description: </p>
 *
 * @author hehuifeng
 * @Date 2019/11/18 14:06
 */
public class DateUtil {

    public static final String YYYY_MM="yyyy-MM";

    /*
     * @Author hehuifeng
     * @Description
     * @Date 2019/11/18 14:10
     * @Param type 类型 0Y,1M,2W,3D,4H,5M,6S
     * @return
     **/
    public static Date AddTime(Date startDate,int type, int addnos) {
        Calendar cc = Calendar.getInstance();
        if (startDate != null) {
            cc.setTime(startDate);
            switch (type){
                case 0:
                    cc.add(Calendar.YEAR, addnos);
                    break;
                case 1:
                    cc.add(Calendar.MONTH, addnos);
                    break;
                case 2:
                    cc.add(Calendar.WEEK_OF_MONTH, addnos);
                    break;
                case 3:
                    cc.add(Calendar.DATE, addnos);
                    break;
                case 4:
                    cc.add(Calendar.HOUR, addnos);
                    break;
                case 5:
                    cc.add(Calendar.MINUTE, addnos);
                    break;
                case 6:
                    cc.add(Calendar.SECOND, addnos);
                    break;
            }
            return cc.getTime();
        } else {
            return null;
        }
    }
    /*
     * @Author hehuifeng
     * @Description 根据日期获取字符串
     * @Date 2019/11/19 13:33
     * @Param
     * @return
     **/
    public static String dateToString(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}

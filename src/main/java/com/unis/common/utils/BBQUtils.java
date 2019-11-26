package com.unis.common.utils;

import java.util.Calendar;
/**
 * @ClassName: BBQUtils
 * @Description: 报表期工具类
 * @author liyb
 * @date 2019-01-10 16:26:17
 * @version v1.0
 */
public class BBQUtils {
    /**
     * 获取上个季度报表期
     * @return String
     */
    public static String getLastQuarter(){
        Calendar date = Calendar.getInstance();
        String nowYear = String.valueOf(date.get(Calendar.YEAR));
        String lastYear = String.valueOf(date.get(Calendar.YEAR)-1);
        int nowMonth = date.get(Calendar.MONTH);
        if(nowMonth==0 || nowMonth==1 || nowMonth==2){
           return lastYear+"04--";
        }else if(nowMonth==3 || nowMonth==4 || nowMonth==5){
            return nowYear+"01--";
        }else if(nowMonth==6 || nowMonth==7 || nowMonth==8){
            return nowYear+"02--";
        }else if(nowMonth==9 || nowMonth==10 || nowMonth==11){
            return nowYear+"03--";
        }else{
            return null;
        }
    }
}

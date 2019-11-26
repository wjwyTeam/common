package com.unis.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @ClassName: StringUtil
 * @Description: 字符串工具类
 * @author liyb
 * @date 2019-01-29 16:51:03
 * @version v1.0
 */
public class StringUtil {
    /**
     * 字符串校验
     * @param param
     * @return String
     */
    public static  String  checkParam(String param){
        if(StringUtils.isNotEmpty(param) && !"undefined".equals(param) && !"null".equals(param)){
            return param;
        }
        return  null;
    }

    public static String getStringFromMap(Map<String, Object> map, String key) {
        if(null == map || map.size() <= 0) {
            return null;
        } else if( null == key || "".equals(key.trim())) {
            return null;
        }
        return String.valueOf(map.get(key));
    }

    public static String formatList2String(List<String> list, String splitStr){
        String result = "";
        StringBuffer sbf = new StringBuffer();
        if(null != list && list.size()>0){
            for(String str : list){
                sbf.append(str).append(splitStr);
            }
            result = sbf.toString();
            if(result.endsWith(splitStr)){
                result = removeLastStr(result, splitStr);
            }
        }
        return result;
    }

    public static String removeLastStr(String obj, String lastStr) {
        if(StringUtils.isNotEmpty(obj)){
            if(obj.endsWith(lastStr)){
                obj = obj.substring(0, obj.length()-lastStr.length());
            }
        }
        return obj;
    }

    public static String getStringForDW(String s){
        if(StringUtils.isEmpty(s)){
            return "0.00";
        }else {
            return  s;
        }
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期比较
     * @param startDate
     * @param endDate
     * @return String
     */
    public static String compareToDate(String startDate,String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
        int resBegin = nowDate.compareTo(startDate);
        int resEnd = nowDate.compareTo(endDate);
        if (resEnd > 0) {
            return "结束填报";
        } else if (resBegin< 0){
            return "未开始填报";
        }else{
            return "正常填报";
        }
    }

    /**
     * 字符串拼接为'',''形式
     * @param id
     * @return String
     */
    public static String appendIdForData(String id) {
        String[] str = id.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            sb.append("'").append(str[i]).append("'").append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
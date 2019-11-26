package com.unis.common.utils;

import java.math.BigDecimal;

/**
 * @Auther:张新亮
 * @Date: 2019/5/6 0006 16:40
 * @Description:
 */
public class DoubleUtil {
    /**
     * double 相加
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 转 string 去掉后面锝0
     * @param i
     * @return
     */
    public static String getString(double i) {
        String s = String.valueOf(i);
        if (s.indexOf(".") > 0) {
            //正则表达
            s = s.replaceAll("0+?$", "");
            //去掉后面无用的零
            s = s.replaceAll("[.]$", "");
            //如小数点后面全是零则去掉小数点
        }
        return s;
    }


}
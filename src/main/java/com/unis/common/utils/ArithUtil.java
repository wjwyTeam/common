package com.unis.common.utils;

/**
* @ClassName: ArithUtils.java
* @Description: 提供精确的浮点数运算(包括加、减、乘、除、四舍五入)工具类
* @author ZHANGQI
* @date 2019年10月17日 上午11:25:39
* @version 1.0
*/
import java.math.BigDecimal;
public class ArithUtil {
    // 除法运算默认精度
    private static final int DEF_DIV_SCALE = 5;
    
    private ArithUtil() {
 
    }
    
    
	/**
	 * @Title: add
	 * @Description: TODO方法描述:( 精确加法 3个数据相加)
	 * @param @param c6
	 * @param @param c7
	 * @param @param c8
	 * @param @return    设定文件
	 * @return BigDecimal    返回类型
	 * @throws
	*/
	public static BigDecimal add(BigDecimal value1, BigDecimal value2, BigDecimal value3) {
	        return  value1.add(value2).add(value3);
	}
	
	/**
	 * 
	 * @Title: add
	 * @Description: TODO方法描述:( 精确加法 2个数据相加)
	 * @param @param value1
	 * @param @param value2
	 * @param @return    设定文件
	 * @return BigDecimal    返回类型
	 * @throws
	 */
	public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        return  value1.add(value2);
	}
	
    /**
     * 精确加法 2个数据相加
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.add(b2).doubleValue();
    }
    
    /**
     * 精确加法  3个数据相加
     */
    public static double add(double value1, double value2, double value3) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        BigDecimal b3 = BigDecimal.valueOf(value3);
        double doubleValue12 = b1.add(b2).doubleValue();
        BigDecimal b12 = BigDecimal.valueOf(doubleValue12);
        return b12.add(b3).doubleValue();
    }
    /**
     * 精确加法  5个数据相加
     */
    public static double add(double value1, double value2, double value3, double value4,double value5) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        BigDecimal b3 = BigDecimal.valueOf(value3);
        BigDecimal b4 = BigDecimal.valueOf(value4);
        BigDecimal b5 = BigDecimal.valueOf(value5);
        double doubleValue12 = b1.add(b2).doubleValue();
        BigDecimal b12 = BigDecimal.valueOf(doubleValue12);
        double doubleValue34 = b3.add(b4).doubleValue();
        BigDecimal b34 = BigDecimal.valueOf(doubleValue34);
        double doubleValue1234 = b12.add(b34).doubleValue();
        BigDecimal b1234 = BigDecimal.valueOf(doubleValue1234);
        return b1234.add(b5).doubleValue();
    }
    /**
     * 精确减法
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.subtract(b2).doubleValue();
    }
    
    /**
     * 精确减法
     */
    public static String isPlusOrMinus(double valueNew, double valueOld) {
        BigDecimal b1 = BigDecimal.valueOf(valueNew);
        BigDecimal b2 = BigDecimal.valueOf(valueOld);
        double doubleValue = b1.subtract(b2).doubleValue();
        if(doubleValue>0){
        	 return "+";
        }else{
        	 return "-";
        }
    }
    
    /**
     * 精确乘法
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.multiply(b2).doubleValue();
    }
    /**
     * double乘 int 精确乘法
     */
    public static double mul(double value1, int value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 精确除法 使用默认精度
     */
    public static double div(double value1, double value2) throws IllegalAccessException {
        return div(value1, value2, DEF_DIV_SCALE);
    }
    /**
     * 精确除法
     * @param scale 精度
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        if(scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /**
     * 四舍五入
     * @param scale 小数点后保留几位
     */
    public static double round(double v, int scale) throws IllegalAccessException {
        return div(v, 1, scale);
    }
    /**
     * 比较大小
     */
    public static boolean equalTo(BigDecimal b1, BigDecimal b2) {
        if(b1 == null || b2 == null) {
            return false;
        }
        return 0 == b1.compareTo(b2);
    }
}
package com.unis.common.utils;

import java.lang.reflect.Field;

/**
 * @Auther:张新亮
 * @Date: 2018/10/26 0026 14:12
 * @Description:
 */
public class ObjectUtils {


    /**
     *  java 反射 判断实例类是否为空
     * @param obj
     * @return
     */
    public static boolean isAllFieldNull(Object obj){
        @SuppressWarnings("rawtypes")
		Class stuCla = (Class) obj.getClass();// 得到类对象
        Field[] fs = stuCla.getDeclaredFields();//得到属性集合
        boolean flag = true;
        for (Field f : fs) {//遍历属性
            f.setAccessible(true); // 设置属性是可以访问的(私有的也可以)
            Object val = null;// 得到此属性的值
            try {
                val = f.get(obj);
//                if(val!=null && val!="") {//只要有1个属性不为空,那么就不是所有的属性值都为空
                if(f.getType()==String.class){
                    if(val!=null && val!=""){
                        flag = false;
                        break;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


}
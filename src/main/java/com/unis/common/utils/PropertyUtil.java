package com.unis.common.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertyUtil {
    @SuppressWarnings("unchecked")
	public static <T> T fillProperties(T t) {
        try {
            Class<?> clazz = t.getClass();
            T obj = (T) clazz.newInstance();
            //传递过来的属性
            BeanUtils.copyProperties(t, obj);
            String areaCode = CurrentUtil.getAreaCode();//区域code
            String areaName = CurrentUtil.getAreaName();//区域name
            String deptId = CurrentUtil.getDeptId();//部门id
            String deptName = CurrentUtil.getDeptName();//部门name
            String orgId = CurrentUtil.getOrgId();//组织id
            String orgName = CurrentUtil.getOrgName();//组织name
            String userName = CurrentUtil.getUserName();//用户姓名

            //私有方法
            Method setOperator =  getDeclaredMethod(t, "setOperator", String.class);
            setOperator.setAccessible(true);
            setOperator.invoke(obj, userName);
            Method setOrgId = getDeclaredMethod(t, "setOrgId",String.class );
            setOrgId.setAccessible(true);
            setOrgId.invoke(obj, orgId);
            Method setOrgName = getDeclaredMethod(t,"setOrgName",String.class );
            setOrgName.setAccessible(true);
            setOrgName.invoke(obj, orgName);
            Method setDeptId = getDeclaredMethod(t,"setDeptId",String.class );
            setDeptId.setAccessible(true);
            setDeptId.invoke(obj, deptId);
            Method setDeptName = getDeclaredMethod(t,"setDeptName",String.class );
            setDeptName.setAccessible(true);
            setDeptName.invoke(obj, deptName);
            Method setAreaCode = getDeclaredMethod(t,"setAreaCode",String.class );
            setAreaCode.setAccessible(true);
            setAreaCode.invoke(obj, areaCode);
            Method setAreaName = getDeclaredMethod(t,"setAreaName",String.class );
            setAreaName.setAccessible(true);
            setAreaName.invoke(obj, areaName);
            return obj;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 获取对象的 DeclaredMethod
     * @param object : 子类对象
     * @param methodName : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes){
        Method method = null ;

        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) {

            Field[] fs = clazz.getDeclaredFields();// 获取PrivateClass所有属性
            for (int i = 0; i < fs.length; i++) {
                fs[i].setAccessible(true);// 将目标属性设置为可以访问
            }
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes) ;
                return method ;
            } catch (Exception e) {

            }
        }
        if (method == null) {
            throw new RuntimeException("未能反射获取到类的方法。");
        }
        return method;
    }

}

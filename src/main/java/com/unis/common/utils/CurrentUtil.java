package com.unis.common.utils;

import com.unis.common.constant.UserInfoKeyConstant;
import io.jsonwebtoken.Claims;

/**
 * @ClassName CurrentUtil
 * @Description 获取当前登陆用户的用户信息
 * @Author tiechanglong
 * @Date 2019/8/10 15:46
 * @Version 0.0.4
 **/
public class CurrentUtil {
    /**
     *
     * @Description: 获取当前用户用户ID
     *
     * @date 2018年12月1日,下午3:16:27
     * @author tiechanglong
     * @version 1.2.0
     *
     * @return
     */
    public static String getUserId() {
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String userId = parseJWT.get(UserInfoKeyConstant.USER_ID, String.class);
        return userId;
    }
    /**
     *
     * @Description: 获取当前用户登陆账户
     *
     * @date 2018年12月1日,下午3:16:31
     * @author tiechanglong
     * @version 1.2.0
     *
     * @return
     */
    public static String getUserAccount() {
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String userAccount = parseJWT.get(UserInfoKeyConstant.ACCOUNT, String.class);
        return userAccount;
    }

    public static String getUserAccount(String token) {
        Claims parseJWT = JwtUtil.parseJWT(token);
        String userAccount = parseJWT.get(UserInfoKeyConstant.ACCOUNT, String.class);
        return userAccount;
    }

    /**
     * 获取当前用户用户姓名
     * @return
     */
    public static String getUserName(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String userName = parseJWT.get(UserInfoKeyConstant.USER_NAME, String.class);
        return userName;
    }

    /**
     * 获取当前用户所属用户性别
     * @return
     */
    public static String getUserSex(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String userName = parseJWT.get(UserInfoKeyConstant.USER_SEX, String.class);
        return userName;
    }

    /**
     * 获取当前用户所属用户类型
     * @return
     */
    public static String getUserType(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String userType = parseJWT.get(UserInfoKeyConstant.USER_TYPE, String.class);
        return userType;
    }
    /**
     * 获取当前用户所属用户类型
     * @return
     */
    public static String getUserType(String token){
        Claims parseJWT = JwtUtil.parseJWT(token);
        String userType = parseJWT.get(UserInfoKeyConstant.USER_TYPE, String.class);
        return userType;
    }
    /**
     * 获取当前用户所属组织机构id
     * @return
     */
    public static String getOrgId(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String orgId = parseJWT.get(UserInfoKeyConstant.ORG_ID, String.class);
        return orgId;
    }

    /**
     * 获取当前用户所属组织机
     * @return
     */
    public static String getOrgName(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String orgName = parseJWT.get(UserInfoKeyConstant.ORG_NAME, String.class);
        return orgName;
    }
    /**
     * 获取当前用户所属部门ID
     * @return
     */
    public static String getDeptId(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String deptId = parseJWT.get(UserInfoKeyConstant.DEPT_ID, String.class);
        return deptId;
    }

    /**
     * 获取当前用户所属部门名称
     * @return
     */
    public static String getDeptName(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String deptName = parseJWT.get(UserInfoKeyConstant.DEPT_NAME, String.class);
        return deptName;
    }

    /**
     * 获取当前用户所属行政区划编码
     * @return
     */
    public static String getAreaCode(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String areaCode = parseJWT.get(UserInfoKeyConstant.AREA_CODE, String.class);
        return areaCode;
    }

    /**
     * 获取当前用户所属行政区划名称
     * @return
     */
    public static String getAreaName(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String areaName = parseJWT.get(UserInfoKeyConstant.AREA_NAME, String.class);
        return areaName;
    }

    /**
     * 获取当前用户统一社会信用代码
     * @return
     */
    public static String getCreditCode(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String creditCode = parseJWT.get(UserInfoKeyConstant.CREDIT_CODE, String.class);
        return creditCode;
    }

    /**
     * 获取当前用户统一社会信用代码
     * @return
     */
    public static String getCreditCode(String token){
        Claims parseJWT = JwtUtil.parseJWT(token);
        String creditCode = parseJWT.get(UserInfoKeyConstant.CREDIT_CODE, String.class);
        return creditCode;
    }

    /**
     * 获取当前用户所属企业名称
     * @return
     */
    public static String getCompanyName(){
        Claims parseJWT = JwtUtil.parseJWT(RequestUtils.getHeaderToken());
        String comanyName = parseJWT.get(UserInfoKeyConstant.COMPANY_NAME, String.class);
        return comanyName;
    }

}

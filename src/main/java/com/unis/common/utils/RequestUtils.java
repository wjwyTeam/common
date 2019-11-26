package com.unis.common.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: request工具类
 *
 * @Auther: 蒋青茂
 * @Date: 2018/6/13 17:23
 */
public class RequestUtils {

    /**
     * @描述 获取 sessionID
     * @author：蒋青茂
     * @date 2018/6/14
     */
    public static String getSessionID() {
        HttpSession session = getSession();
        String sessionID = session.getId();
        return sessionID;
    }

    /**
     * @描述 清除 session
     * @author：蒋青茂
     * @date 2018/6/14
     */
    public static void deletesSession() {
        getSession().invalidate();
    }

    /**
     * @描述 获取session
     * @author：蒋青茂
     * @date 2018/6/14
     */
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        return session;
    }

    /**
     * @描述 获取 request
     * @author：蒋青茂
     * @date 2018/6/14
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request;
    }

    /**
     * @描述 获取 response
     * @author：蒋青茂
     * @date 2018/6/14
     */
    public  static HttpServletResponse getResponse(){
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        return  response;
    }
    public void setHeader(String name,String value){

    }
    /**
     *
     * @Description: 获取Header中信息
     *
     * @date 2018年11月22日,上午10:02:35
     * @author tiechanglong
     * @version 1.2.0
     *
     * @return
     */
    public static Map<String, String> getHeadersInfo() {
        HttpServletRequest request = getRequest();
        Map<String, String> map = new HashMap<String, String>();
        @SuppressWarnings("rawtypes")
		Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取header中指定key的value
     * @param name
     * @return
     */
    public static String getHeaderKey(String name){
        String value = null;
        Map<String, String> headersInfo = getHeadersInfo();
        for (String key : headersInfo.keySet()) {
            if (ObjectUtils.nullSafeEquals(key, "token")) {
                value = headersInfo.get(key);
            }
        }
        return value;
    }

    /**
     * 获取header中token
     * @return
     */
    public static String getHeaderToken(){
        return getHeaderKey("token");
    }
}

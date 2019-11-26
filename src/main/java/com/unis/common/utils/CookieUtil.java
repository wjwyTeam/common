package com.unis.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 蒋青茂
 * Cookie工具类
 *
 */
public class CookieUtil {

    /**
     * 添加cookie
     *
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(String name, String value, int maxAge) {
        HttpServletResponse response = RequestUtils.getResponse();
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param name
     */
    public static void removeCookie( String name) {
        HttpServletResponse response = RequestUtils.getResponse();
        Cookie uid = new Cookie(name, null);
        uid.setPath("/");
        uid.setMaxAge(0);
        response.addCookie(uid);
    }

    /**
     * 获取cookie值
     *
     * @return
     */
    public static String getCookie(String cookieName) {
        HttpServletRequest request = RequestUtils.getRequest();
        Cookie cookies[] = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
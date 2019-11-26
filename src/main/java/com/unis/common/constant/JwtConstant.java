package com.unis.common.constant;

import java.util.UUID;

/**
 * 
 * @ClassName: JwtConstant 
 * @Description: jwt常量类 
 * @author tiechanglong
 * @date 2018年11月22日 下午1:44:21 
 * 
 * @version 1.2.0
 */
public class JwtConstant {
	/**
	 * jwtId
	 */
	public static final String JWT_ID = UUID.randomUUID().toString();
	/**
	 * 密钥
	 */
	public static final String JWT_SECRET = "mySecret";

	/**
	 * 颁发人
	 */
	public static final String ISS_USER = "auth";
	/**
	 * 默认APP_ID
	 */
	public String APP_ID = "business-management";
	/**
	 * jwt键名
	 */
	public static String TOKEN = "token";
	
}

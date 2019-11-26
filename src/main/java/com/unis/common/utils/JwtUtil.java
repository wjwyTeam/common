package com.unis.common.utils;

import com.unis.common.constant.JwtConstant;
import com.unis.common.constant.UserInfoKeyConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tiechanglong
 * @version 1.2.0
 * @ClassName: JwtUtil
 * @Description: jwt工具类
 * @date 2018年11月22日 上午10:34:04
 */
public class JwtUtil {
    /**
     * 创建时间
     */
    public static Long CREAT_TIME;

    /**
     * @return
     * @Description: 生成加密串
     * @date 2018年11月22日, 下午2:07:23
     * @author tiechanglong
     * @version 1.2.0
     */
    public static SecretKey generalKey() {
        String stringKey = JwtConstant.JWT_SECRET;
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * @param id
     * @param claims    创建payload的私有声明
     * @param effectiveTimeLong
     * @return
     */
    public static String createJWT(String id, Map<String, Object> claims, long effectiveTimeLong, long effectiveRefresh) {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        CREAT_TIME = nowMillis;
        Date now = new Date(CREAT_TIME);
        long effectiveTime = nowMillis + effectiveTimeLong;
        long effectiveRefreshTime = effectiveTime - effectiveRefresh;
        claims.put("effectiveRefreshTime",effectiveRefreshTime);
        // 生成签名的时候使用的秘钥secret，一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();
        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(now)
                //.setSubject(subject)
                .signWith(signatureAlgorithm, key);
        // 设置过期时间
        if (effectiveTimeLong >= 0) {
            Date exp = new Date(effectiveTime);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 获取生成jwt的用户相关参数
     * @param userId
     * @param account
     * @param userName
     * @param userSex
     * @param userType
     * @param orgId
     * @param orgName
     * @param deptId
     * @param deptName
     * @param areaCode
     * @param areaName
     * @return
     */
    public static Map<String, Object> getClaims(String userId, String account, String userName,
                                                String userSex, String userType,
                                                String orgId, String orgName,
                                                String deptId, String deptName,
                                                String areaCode, String areaName,String creditCode, String compnayName) {
        Map<String, Object> claims = new HashMap<>(11);
        claims.put(UserInfoKeyConstant.USER_ID, userId);
        claims.put(UserInfoKeyConstant.ACCOUNT, account);
        claims.put(UserInfoKeyConstant.USER_NAME, userName);
        claims.put(UserInfoKeyConstant.USER_SEX, userSex);
        claims.put(UserInfoKeyConstant.USER_TYPE, userType);
        claims.put(UserInfoKeyConstant.ORG_ID, orgId);
        claims.put(UserInfoKeyConstant.ORG_NAME, orgName);
        claims.put(UserInfoKeyConstant.DEPT_ID, deptId);
        claims.put(UserInfoKeyConstant.DEPT_NAME, deptName);
        claims.put(UserInfoKeyConstant.AREA_CODE, areaCode);
        claims.put(UserInfoKeyConstant.AREA_NAME, areaName);
        claims.put(UserInfoKeyConstant.CREDIT_CODE, creditCode);
        claims.put(UserInfoKeyConstant.COMPANY_NAME, compnayName);
        return claims;
    }

    /**
     * @param jwt
     * @return
     * @throws Exception
     * @Description: 解析JWT
     * @date 2018年11月22日, 下午2:14:37
     * @author tiechanglong
     * @version 1.2.0
     */
    public static Claims parseJWT(String jwt) {
        SecretKey key = generalKey();
        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody(); // 设置需要解析的jwt
        return claims;
    }

    /**
     * @param jwt
     * @return
     * @Description: 获取jwtID
     * @date 2018年12月1日, 下午3:05:11
     * @author tiechanglong
     * @version 1.2.0
     */
    public static String getJwtId(String jwt) {
        String jwtId;
        Claims parseJWT = parseJWT(jwt);
        jwtId = parseJWT.getId();
        return jwtId;
    }

    /**
     * @param jwt
     * @return
     * @Description: 获取JWT创建时间
     * @date 2018年12月1日, 下午3:16:34
     * @author tiechanglong
     * @version 1.2.0
     */
    public static Long getJwtGreatDate(String jwt) {
        Claims parseJWT = parseJWT(jwt);
        Date issuedAt = parseJWT.getIssuedAt();
        Long issuedAtLong = issuedAt.getTime();
        return issuedAtLong;
    }

    /**
     * 获取jwt刷新时间
     * @param jwt
     * @return
     */
    public static Long getEffectiveRefreshTime(String jwt) {
        Claims parseJWT = parseJWT(jwt);
        Long effectiveRefreshTime = (Long) parseJWT.get("effectiveRefreshTime");
        return effectiveRefreshTime;
    }

}

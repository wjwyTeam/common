package com.unis.common.utils;

/**
 * @Description: 密码工具类
 * @author: 杨祖榕
 * @Date: 2019-01-15 13:27
 * @Version v1.0
 **/
public class EncryptUtil {

    /**
     * 默认的盐值，长度必须为16位，请勿修改
     */
    private final static String DF_SALT = "bjszlssaltpasswd";

//    /**
//     * 使用默认的盐值，生成含有加盐的密码
//     */
//    public static String generate(String password) {
//        return generate(password, null);
//    }

    /**
     * 使用盐值，生成含有加盐的密码
     */
    public static String generate(String password, String dbSalt) {
        String salt = getSalt(dbSalt);
        password = MD5Util.md5ToHex(password + salt);
        char[] cs1 = password.toCharArray();
        char[] cs2 = salt.toCharArray();
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = cs1[i / 3 * 2];
            cs[i + 1] = cs2[i / 3];
            cs[i + 2] = cs1[i / 3 * 2 + 1];
        }
        return new String(cs).toUpperCase();
    }

    /**
     * 对密码进行MD5加密，不做其它任何处理
     */
    public static String generate(String password) {
        password = MD5Util.md5ToHex(password);
        return password;
    }

    private static String getSalt(String salt){
        if(salt == null || salt.length() == 0){
            return EncryptUtil.DF_SALT;
        }
        if(salt.length() <= 16 ){
            return getStringLen8(salt) + EncryptUtil.DF_SALT.substring(7, 15);
        } else {
            return salt.substring(4, 12) + EncryptUtil.DF_SALT.substring(2, 10);
        }
    }

    public static String getStringLen8(String salt){
        if(salt.length() == 8){
            return salt;
        }
        if(salt.length() > 8){
            return salt.substring(1, 9);
        } else {
            return getStringLen8(salt + salt);
        }
    }

    /**
     * 校验密码是否正确
     * @param password
     * @param dbSalt
     * @param encrypedPwd
     * @return
     */
    public static boolean verify(String password, String dbSalt, String encrypedPwd) {
//        char[] cs = md5.toLowerCase().toCharArray();
//        char[] cs1 = new char[32];
//        char[] cs2 = new char[16];
//        for (int i = 0; i < 48; i += 3) {
//            cs1[i / 3 * 2] = cs[i];
//            cs1[i / 3 * 2 + 1] = cs[i + 2];
//            cs2[i / 3] = cs[i + 1];
//        }
//        String salt = new String(cs2);
//        return MD5Util.md5ToHex(password + salt).equals(new String(cs1));
        String gnePwd = generate(password, dbSalt);
        return gnePwd.equals(encrypedPwd);
    }


    /**
     * 校验密码是否正确
     * @param password
     * @param encrypedPwd
     * @return
     */
    public static boolean verify(String password, String encrypedPwd) {
        String gnePwd = generate(password);
        return gnePwd.equals(encrypedPwd);
    }

    public static void main(String[] args ){
        String prePwd = "123456";
        //String pwd = "927EAE5B82E12DB5F8882ED4BSA5Z15LD1SF2S4FAA0LA5TB";
//        String salt = "7FDD2ABEDF8D0384E053B77BA8C01B0B";

//        String resultPwd = generate(prePwd, salt);
        String resultPwd = generate(prePwd);
        System.out.println(resultPwd);

        boolean flag = verify(prePwd, resultPwd);
        System.out.println(flag);

    }


}

package com.unis.common.utils;
import org.apache.commons.lang.StringUtils;
public class OrgUtil {

    /**
     * 获取父组织code,无则为空
     * @param code
     * @return
     */
    public static String getParentOrg(String code){
        if(StringUtils.isNotEmpty(code)){
            code=code.trim();
            int len=code.length();
            if(len>5){
                return  code.substring(0,len-3);
            }
            return null;
        }
        return null;
    }

    public static void main(String[] args) {

    }
}

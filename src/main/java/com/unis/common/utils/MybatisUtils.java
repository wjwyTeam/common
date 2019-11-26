/**  
 * @Title:MybatisUtils.java
 * @Package: com.unis.common.utils
 * @Description: TODO功能描述:(用一句话描述该文件做什么)
 * @Author ZHANGQI
 * @date 2019年11月22日 下午2:14:07
 * @version V1.0  
*/
package com.unis.common.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
/**
 * @ClassName: MybatisUtils
 * @Description: TODO功能描述:生成mybatis的session对象
 * @author ZHANGQI
 * @date 2019年11月22日 下午2:14:07
 * @remark 备注:
*/

public class MybatisUtils {
    private static String resource = "/persnel-service/src/main/resources/mapper/mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory = null;
    private static SqlSession session = null;

    private static void init() {
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
//            创建工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//            创建session对象
            session = sqlSessionFactory.openSession(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSession() {
        if (session == null) {
            init();
        }
        return session;
    }

    public static void close() {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public static <T> T getMapper(Class<T> tClass) {
        if (session == null) {
            init();
        }
        return session.getMapper(tClass);
    }

    public static void commit() {
        if (session != null) {
            session.commit();
        }
    }


}

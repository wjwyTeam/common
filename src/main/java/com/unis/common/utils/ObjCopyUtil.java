package com.unis.common.utils;

import java.io.*;
import java.util.List;

/**
 * @Description: 对象clone
 * @author: 杨祖榕
 * @Date: 2018-11-21 16:09
 * @Version v1.0
 **/
public class ObjCopyUtil {

    @SuppressWarnings("unchecked")
	public static <T> List<T> deepCopy(List<T> src) {

        List<T> dest = null;
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
            try {
                in.close();
            } catch (IOException e) {
            }
        }

        return dest;
    }

}

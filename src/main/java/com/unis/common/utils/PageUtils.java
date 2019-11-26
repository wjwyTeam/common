package com.unis.common.utils;

/**
 * @Auther: 杨祖榕
 * @Date: 2019/1/11 13:39
 */
public class  PageUtils {

    /**
     *
     * @param page 第几页，从 1 开始
     * @param size 每页几条记录
     * @return
     */
    public static Integer getStart(Integer page, Integer size) {
        int start = (page - 1) * size + 1;
        return start < 0 ? 0 : start;
    }

    /**
     *
     * @param page 第几页，从 1 开始
     * @param size 每页几条记录
     * @return
     */
    public static Integer getEnd(Integer page, Integer size) {
        return getStart(page, size) + size - 1;
    }
}

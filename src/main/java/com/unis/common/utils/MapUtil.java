package com.unis.common.utils;

import java.util.*;

/**
 * @Description: Map工具类
 * @author: 杨祖榕
 * @Date: 2019-01-18 14:48
 * @Version v1.0
 **/
public class MapUtil {

    /**
     * 将List中每个Map的Key转换为小写
     * @param list 返回新对象
     * @return
     */
    public static List<Map<String, Object>> convertKeyList2LowerCase(List<Map<String, Object>> list){
        if(null==list) {
            return null;
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        //
        Iterator<Map<String, Object>> iteratorL = list.iterator();
        while (iteratorL.hasNext()) {
            Map<String, Object> map = iteratorL.next();
            //
            Map<String, Object> result = convertKey2LowerCase(map);
            if(null != result){
                resultList.add(result);
            }
        }
        //
        return resultList;
    }

    /**
     * 转换单个map,将key转换为小写.
     * @param map 返回新对象
     * @return
     */
    public static Map<String, Object> convertKey2LowerCase(Map<String, Object> map){
        if(null == map) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        //
        Set<String> keys  = map.keySet();
        //
        Iterator<String> iteratorK = keys.iterator();
        while(iteratorK.hasNext()) {
            String key = iteratorK.next();
            Object value = map.get(key);
            if(null == key){
                continue;
            }
            //
            String keyL = key.toLowerCase();
            result.put(keyL, value);
        }
        return result;
    }


    /**
     * 将List中每个Map的Key转换为小写
     * @param list 返回新对象
     * @return
     */
    public static List<Map<String, String>> convertKeyList2LowerCase2(List<Map<String, String>> list){
        if(null==list) {
            return null;
        }
        List<Map<String, String>> resultList = new ArrayList<>();
        //
        Iterator<Map<String, String>> iteratorL = list.iterator();
        while (iteratorL.hasNext()) {
            Map<String, String> map = iteratorL.next();
            //
            Map<String, String> result = convertKey2LowerCase2(map);
            if(null != result){
                resultList.add(result);
            }
        }
        //
        return resultList;
    }


    /**
     * 转换单个map,将key转换为小写.
     * @param map 返回新对象
     * @return
     */
    public static Map<String, String> convertKey2LowerCase2(Map<String, String> map){
        if(null == map) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        //
        Set<String> keys  = map.keySet();
        //
        Iterator<String> iteratorK = keys.iterator();
        while(iteratorK.hasNext()) {
            String key = iteratorK.next();
            String value = map.get(key);
            if(null == key){
                continue;
            }
            //
            String keyL = key.toLowerCase();
            result.put(keyL, value);
        }
        return result;
    }

    /**
     * 分页返回Map
     * @param resultList
     * @param page
     * @param size
     * @return Map<String,Object>
     */
    @SuppressWarnings("rawtypes")
	public static Map<String,Object> getMapForPage(List resultList,Integer page,Integer size){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", resultList.size());
        // 从第几条数据开始
        int firstIndex = (page - 1) * size;
        // 到第几条数据结束
        int lastIndex = page * size;
        if (resultList.size() >= lastIndex) {
            resultList = resultList.subList(firstIndex, lastIndex);
        } else {
            resultList = resultList.subList(firstIndex, resultList.size());
        }
        resultMap.put("list",resultList);
        resultMap.put("page", page);
        resultMap.put("size", size);
        return resultMap;
    }

}

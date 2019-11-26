package com.unis.common.utils;

import java.util.*;

/**
 * @Description: 集合工具类
 * @author: 杨祖榕
 * @Date: 2019-01-13 12:58
 * @Version v1.0
 **/
public class CollectionUtil {

    public static void main(String args[]) {
        getList();
    }

    // 获取两个ArrayList的差集、交集、去重并集(数据量大小不限制)
    private static void getList() {
        List<String> firstArrayList = new ArrayList<String>();
        List<String> secondArrayList = new ArrayList<String>();
        List<String> defectList = new ArrayList<String>();//差集List
        List<String> collectionList = new ArrayList<String>();//交集List
        List<String> unionList = new ArrayList<String>();//去重并集List
        try {
            firstArrayList.add("aaa");
            firstArrayList.add("bbb");
            firstArrayList.add("ccc");
            firstArrayList.add("ddd");

            secondArrayList.add("bbb");
            secondArrayList.add("ccc");
            secondArrayList.add("eee");
            // 获取差集
            defectList = differenceList(firstArrayList, secondArrayList);
            Iterator<String> defectIterator = defectList.iterator();
            System.out.println("===================差集===================");
            while(defectIterator.hasNext()) {
                System.out.println(defectIterator.next());
            }
            // 获取交集
            collectionList = intersectionList(firstArrayList, secondArrayList);
            Iterator<String> collectionIterator = collectionList.iterator();
            System.out.println("===================交集===================");
            while(collectionIterator.hasNext()) {
                System.out.println(collectionIterator.next());
            }
            // 获取去重并集
            unionList = unionDistinctList(firstArrayList, secondArrayList);
            Iterator<String> unionIterator = unionList.iterator();
            System.out.println("===================去重并集===================");
            while(unionIterator.hasNext()) {
                System.out.println(unionIterator.next());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取两个List的差集
     * @param firstList
     * @param secondList
     * @return
     */
    public static List<String> differenceList(List<String> firstList, List<String> secondList) {
        if(null == firstList || firstList.isEmpty()) {
            return null;
        }
        //
        if(null == secondList || secondList.isEmpty()) {
            return firstList;
        }
        //
        LinkedList<String> list = new LinkedList<>(firstList);// 大集合用linkedlist
        HashSet<String> set = new HashSet<>(secondList);// 小集合用hashset
        //
        Iterator<String> iterator = list.iterator();//采用Iterator迭代器进行数据的操作
        while(iterator.hasNext()){
            if(set.contains(iterator.next())){
                iterator.remove();
            }
        }
        return new ArrayList<>(list);
    }


    /**
     * 获取两个List的交集
     * @param firstList
     * @param secondList
     * @return
     */
    public static List<String> intersectionList(List<String> firstList, List<String> secondList) {
        if(null == firstList || firstList.isEmpty()) {
            return null;
        }
        if(null == secondList || secondList.isEmpty()) {
            return null;
        }
        //
        LinkedList<String> list = new LinkedList<>(firstList);// 大集合用linkedlist
        HashSet<String> set = new HashSet<>(secondList);// 小集合用hashset
        //
        Iterator<String> iterator = list.iterator();// 采用Iterator迭代器进行数据的操作
        while(iterator.hasNext()) {
            if(! set.contains(iterator.next())) {
                iterator.remove();
            }
        }
        return new ArrayList<>(list);
    }

    /**
     * 获取两个ArrayList的去重并集
     * @param firstList
     * @param secondList
     * @return
     */
    public static List<String> unionDistinctList(List<String> firstList, List<String> secondList) {
        if(null == firstList || firstList.isEmpty()) {
            return secondList;
        }
        //
        if(null == secondList || secondList.isEmpty()) {
            return firstList;
        }
        //
        Set<String> firstSet = new TreeSet<>(firstList);
        for(String str : secondList) {
            firstSet.add(str);
        }
        return new ArrayList<>(firstSet);
    }

}

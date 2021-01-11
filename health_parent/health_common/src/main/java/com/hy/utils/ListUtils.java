package com.hy.utils;

import java.util.*;

/**
 * @author HY
 * @ClassName ListUtils
 * @Description TODE
 * @DateTime 2020/12/19 19:49
 * Version 1.0
 */
public class ListUtils {
    /**
     * 比较两个list集合的不同
     * @param first
     * @param second
     * @param <T>
     * @return
     */
    public static <T> List<T> getListDiff(List<T> first,List<T> second) {
        //声明一个集合用于存放不同的数据
        List<T> diff=new ArrayList<>();
        //map集合用于存放两个集合的数据,长度最长的情况就是两个集合的size相加
        Map<T,Integer> map=new HashMap(first.size()+second.size());
        //先将第一个集合放进map
        for (T t : first) {
            map.put(t,1);
        }
        //遍历第二个集合
        for (T t : second) {
            Integer integer = map.get(t);
            //如果integer不为null说明有重复数据,将对应的value+1
            if (integer!=null){
                map.put(t,++integer);
            }else {
                //没有数据,放入并赋初始值
                map.put(t,1);
            }
        }
        //遍历值为1的数据
        Set<Map.Entry<T, Integer>> entries = map.entrySet();
        for (Map.Entry<T, Integer> entry : entries) {
            if (entry.getValue()==1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    /**
     * 测试
     * @param args
     */
   /* public static void main(String[] args) {
        List<Integer> integers=new ArrayList<>();
        List<Integer> integers1=new ArrayList<>();
        Collections.addAll(integers,1,2,3,4,5,6);
        Collections.addAll(integers1,0,4,5,6,7,8,9);
        List<Integer> listDiff = getListDiff(integers, integers1);
        List<String> stringList=new ArrayList<>();
        List<String> stringList2=new ArrayList<>();
        Collections.addAll(stringList,"a","b","c","d","2");
        Collections.addAll(stringList2,"z","q","c","d","s");
        List<String> listDiff1 = getListDiff(stringList, stringList2);
        System.out.println(listDiff1);
    }*/
}

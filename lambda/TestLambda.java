package com.ruoyi.web.controller.common;

import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.IntStream;

public class TestLambda {
    public static void main(String[] args) throws Exception{
        // threadDiff();
        // comparatorDiff();
        lamndaFor();
    }

    /**
     * 线程区别
     * @throws Exception
     */
    public static void threadDiff() throws Exception{
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread-name:"+Thread.currentThread().getName());
            }
        });
        thread1.setName("thread1");
        //lambda
        Thread thread2 =new Thread(()->{
            System.out.println("thread-name:"+Thread.currentThread().getName());
        });
        thread2.setName("thread2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("main-thread-name:"+Thread.currentThread().getName());
    }

    /**
     * Comparator区别
     */
    public static void comparatorDiff(){
        List<Integer> list = Arrays.asList(3,1,6);
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        list.forEach((i)->{
            System.out.println(i);
        });
        List<Integer> list2 = Arrays.asList(3,1,6);
        //lambda
        Collections.sort(list2,(Integer o1,Integer o2)->o1-o2);
        list.forEach((i)->{
            System.out.println(i);
        });
    }

    /**
     * 集合遍历
     */
    public static void lamndaFor() {
        List<String> strings = Arrays.asList("1", "2", "3");
        //传统foreach
        for (String s : strings) {
            System.out.println(s);
        }
        //Lambda foreach
        strings.forEach((s) -> System.out.println(s));
        //or
        strings.forEach(System.out::println);
        //map
        Map<Integer, String> map = Maps.newHashMap();
        IntStream.range(1,10).forEach((i)->{map.put(i,i+"-s");});
        map.forEach((k,v)->System.out.println(v));
    }

}

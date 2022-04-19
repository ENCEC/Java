package com.ruoyi.web.controller.common.parallel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 执行0-100的累加算法
 */
public class ParallelTest {

    public static void main(String[] args) throws InterruptedException{
        //lambda表达式写法
        Integer num1 = IntStream.rangeClosed(1,100).parallel().boxed().reduce((i1, i2)->i1+i2).get();
        System.out.println("lambda表达式运算结果："+num1);

        //传统写法
        int num2 = 0;
        for (int i = 0; i <= 100;i++){
            num2 = num2 + i;
        }
        System.out.println("传统加法，运算结果："+num2);

        //创建线程池，并分5个线程执行从1-100的累加操作
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new LinkedBlockingQueue<>(5));
        int N = 100;
        int numThread = 5;
        parallel(N,numThread,threadPoolExecutor);
    }
    private static void parallel(int N, int numThread, ThreadPoolExecutor threadPoolExecutor) throws InterruptedException{
        long[] result = new long[numThread];
        CountDownLatch countDownLatch = new CountDownLatch(numThread);
        for(int i =0; i < numThread; i++){
            TestTask testTask = new TestTask(i*N/numThread,(i+1)*N/numThread,i,result,countDownLatch);
            threadPoolExecutor.execute(testTask);
        }
        countDownLatch.await();
        long sum = 0;
        for (long r : result){
            sum += r;
        }
        System.out.println("并行计算的结果为："+sum);
        if(threadPoolExecutor.getActiveCount() == 0){
            threadPoolExecutor.shutdown();
        }
    }
}



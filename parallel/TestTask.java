package com.ruoyi.web.controller.common.parallel;

import java.util.concurrent.CountDownLatch;

public class TestTask implements Runnable{
    private long start;
    private long end;
    private int num;
    private long[] result;
    //这个是为了判断所有线程是否全部执行完，因为继承thread类的线程没有返回值
    private CountDownLatch cdl;

    public TestTask(long start, long end, int num,long[] result, CountDownLatch cdl){
        this.start = start;
        this.end = end;
        this.num = num;
        this.result = result;
        this.cdl = cdl;
    }
    @Override
    public void run() {
        long sum = 0;
        for (long i = start+1; i < end+1; i++){
            sum += i;
        }
        result[num] = sum;
        cdl.countDown();
    }
}

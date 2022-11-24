package com.jaa.thread03;

import com.jaa.utils.Print;

import java.util.ArrayList;
import java.util.List;

import static com.jaa.utils.ThreadUtil.sleepMilliSeconds;
import static com.jaa.utils.ThreadUtil.sleepSeconds;

/**
 * @Author: Jaa
 * @Date: 2022/11/23 23:12
 * @Description:
 */
public class StatusDemo {

    // 每次线程执行的轮次
    public static final int MAX_TURN = 5;

    // 线程编号
    static int threadSeqNumber = 0;

    // 全局的静态线程列表
    static List<Thread> threadList = new ArrayList<>();

    // 输出静态线程列表中每个线程的状态
    private static void printThreadStatus() {
        for (Thread thread : threadList) {
            Print.tco(thread.getName() + " 状态为" + thread.getState());
        }
    }

    // 向全局的静态线程列表加入线程
    private static void addStatusThread(Thread thread) {
        threadList.add(thread);
    }

    static class StatusDemoThread extends Thread {
        public StatusDemoThread() {
            super("statusDemoThread" + (++threadSeqNumber));
            // 将自己加入全局的静态线程列表
            addStatusThread(this);
        }

        @Override
        public void run() {
            Print.cfo(getName() + ", 状态为" + getState());
            for (int i = 0; i < MAX_TURN; i++) {
                // 线程睡眠
                sleepMilliSeconds(500);
                // 输出所有线程的状态
                printThreadStatus();
            }
            Print.tco(getName() + "- 运行结束.");
        }
    }

    public static void main(String[] args) {
        // 将main线程加入全局列表
        addStatusThread(Thread.currentThread());
        // 新建三个线程，这些线程在构造器中会将自己加入全局列表
        Thread sThread1 = new StatusDemoThread();
        Print.cfo(sThread1.getName() + "- 状态为" + sThread1.getState());
        Thread sThread2 = new StatusDemoThread();
        Print.cfo(sThread2.getName() + "- 状态为" + sThread2.getState());
        Thread sThread3 = new StatusDemoThread();
        Print.cfo(sThread3.getName() + "- 状态为" + sThread3.getState());

        sThread1.start();              //启动第一个线程

        sleepMilliSeconds(500);        //等待500毫秒启动第二个线程
        sThread2.start();

        sleepMilliSeconds(500);        //等待1000毫秒启动第三个线程
        sThread3.start();

        sleepSeconds(100);             //睡眠100秒
    }
}

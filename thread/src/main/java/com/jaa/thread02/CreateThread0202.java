package com.jaa.thread02;

import com.jaa.utils.ThreadUtil;

/**
 * @Author Jaa
 * @Date 2022/11/19 9:53
 * @Description 使用Lambda表达式优雅地创建Runnable线程目标类
 * 标记Runnable接口是一个“函数式接口”。
 * 在Java中，“函数式接口”是有且仅有一个抽象方法的接口。
 *
 * @FunctionalInterface
 * public interface Runnable {
 *      public abstract void run();
 * }
 */
public class CreateThread0202 {

    public static final int MAX_TURN = 5;

    static int threadNo = 1;

    public static void main(String[] args) {
        Thread thread = null;
        for (int i = 0; i < 2; i++) {
            // 使用Lambda表达式创建和启动线程
            thread = new Thread(() -> {
                for (int j = 1; j < MAX_TURN; j++) {
                    System.out.println(ThreadUtil.getCurThreadName() + ", 轮次：" + j);
                }
                System.out.println(ThreadUtil.getCurThreadName() + "运行结束.");
            }, "RunnableThread" + threadNo++);
            thread.start();
        }
        System.out.println(ThreadUtil.getCurThreadName() + "运行结束.");
    }
}

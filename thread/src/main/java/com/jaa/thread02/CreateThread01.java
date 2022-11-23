package com.jaa.thread02;

/**
 * @Author Jaa
 * @Date 2022/11/19 08:23
 * @Description 线程创建方法一：继承Thread类创建线程类
 *
 * （1）需要继承Thread类，创建一个新的线程类。
 * （2）同时重写run()方法，将需要并发执行的业务代码编写在run()方法中。
 */
public class CreateThread01 {

    private static final int MAX_TURN = 5;

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    // 线程编号
    static int threadNo = 1;

    // 为什么要将DemoThread设计成静态内部类呢？
    // 主要是为了方便访问外部类的成员属性和方法，和线程的使用没有任何关系。
    static class DemoThread extends Thread { // 1、继承Thread类
        public DemoThread() {
            super("DemoThread-" + threadNo++); // 2、设置线程名称
        }

        @Override
        public void run() { // 3、重写run方法
            for (int i = 1; i < MAX_TURN; i++) {
                System.out.println(getName() + ", 轮次：" + i);
            }
            System.out.println(getName() + " 运行结束.");
        }
    }

    public static void main(String[] args) {
        Thread thread = null;
        for (int i = 0; i < 2; i++) {
            thread = new DemoThread();
            thread.start();
        }
        System.out.println(getCurThreadName() + "运行结束.");
    }
}

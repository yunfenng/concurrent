package com.jaa.thread02;

import com.jaa.utils.ThreadUtil;

/**
 * @Author Jaa
 * @Date 2022/11/19 9:34
 * @Description 通过匿名类优雅地创建Runnable线程目标类
 */
public class CreateThread0201 {

    public static final int MAX_TURN = 5;

    static int threadNo = 1;

    public static void main(String[] args) {

        Thread thread = null;

        for (int i = 0; i < 2; i++) {
            // 使用Runable匿名内部类创建和启动线程
            thread = new Thread(new Runnable() { // 匿名实例
                @Override
                public void run() { // 异步执行的逻辑
                    for (int j = 1; j < MAX_TURN; j++) {
                        System.out.println(ThreadUtil.getCurThreadName() + ", 轮次：" + j);
                    }
                    System.out.println(ThreadUtil.getCurThreadName() + "运行结束.");
                }
            }, "RunnableThread" + threadNo++);
            thread.start();
        }
        System.out.println(ThreadUtil.getCurThreadName() + "运行结束.");
    }
}

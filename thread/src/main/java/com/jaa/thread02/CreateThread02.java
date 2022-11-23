package com.jaa.thread02;

import com.utils.ThreadUtil;

/**
 * @Author Jaa
 * @Date 2022/11/19 8:58
 * @Description 线程创建方法二：实现Runnable接口创建线程目标类
 * <p>
 * 将需要异步执行的业务逻辑代码放在Runnable实现类的run()方法中，将Runnable实例作为target执行目标传入Thread实例
 * <p>
 * 1、定义一个新类实现Runnable接口
 * 2、实现Runnable接口中的run()抽象方法，将线程代码逻辑存放在该run()实现版本中
 * 3、通过Thread类创建线程对象，将Runnable实例作为实际参数传递给Thread类的构造器，由Thread构造器将该Runnable实例赋值给自己的target执行目标属性
 * 4、调用Thread实例的start()方法启动线程
 * 5、线程启动之后，线程的run()方法将被JVM执行，该run()方法将调用target属性的run()方法，从而完成Runnable实现类中业务代码逻辑的并发执行
 */
public class CreateThread02 {

    private static final int MAX_TURN = 5;

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    // 线程编号
    static int threadNo = 1;

    static class RunTarget implements Runnable { // 1、实现Runnable接口
        @Override
        public void run() { // 2、重写run方法编写业务逻辑
            for (int j = 1; j < MAX_TURN; j++) {
                // RunTarget内部类和Thread类不再是继承关系，无法直接调用Thread类的任何实例方法
                System.out.println(ThreadUtil.getCurThreadName() + ", 轮次：" + j);
            }
            System.out.println(getCurThreadName() + "运行结束.");
        }
    }

    public static void main(String[] args) {
        Thread thread = null;
        for (int i = 0; i < 2; i++) {
            RunTarget target = new RunTarget();
            // 通过Thread类创建线程对象，将Runnable实例target作为实际参数传入
            thread = new Thread(target, "RunnableThread" + threadNo++);
            thread.start();
        }
    }
}

package com.jaa.thread01;

/**
 * @Author Jaa
 * @Date 2022/11/19 08:10
 * @Description 创建一个空线程
 */
public class EmptyThreadDemo {

    public static void main(String[] args) {
        // 使用Thread创建和启动线程
        Thread thread = new Thread();
        System.out.println("线程名称：" + thread.getName());
        System.out.println("线程ID：" + thread.getId());
        System.out.println("线程状态：" + thread.getState());
        System.out.println("线程优先级：" + thread.getPriority());
        System.out.println(thread.getName() + " 运行结束。");
        thread.start();
    }
}

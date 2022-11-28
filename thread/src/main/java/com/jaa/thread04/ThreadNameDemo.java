package com.jaa.thread04;

import com.jaa.utils.Print;

import static com.jaa.utils.ThreadUtil.sleepMilliSeconds;
import static com.jaa.utils.ThreadUtil.sleepSeconds;

/**
 * @author : Jaa
 * @date : 2022-11-28 13:18
 * @description: 线程名称的设置和获取
 *
 * (1) 可以通过构造器Thread(…)初始化设置线程名称
 * (2) 也可以通过setName(…)实例方法设置线程名称
 * (3) 取得线程名称可以通过getName()
 *
 * 关于线程名称有以下几个要点：
 * （1）线程名称一般在启动线程前设置，但也允许为运行的线程设置名称。
 * （2）允许两个Thread对象有相同的名称，但是应该避免。
 * （3）如果程序没有为线程指定名称，系统会自动为线程设置名称。
 */
public class ThreadNameDemo {

    private static final int MAX_TURN = 3;

    // 异步执行目标类
    static class RunTarget implements Runnable {
        // 实现Runnable接口，重写run方法
        @Override
        public void run() {
            for (int i = 0; i < MAX_TURN; i++) {
                sleepMilliSeconds(500); // 线程睡眠
                Print.tco("线程执行轮次：" + i);
            }
        }
    }

    public static void main(String[] args) {
        // 实例化Runnable异步执行目标类
        RunTarget target = new RunTarget();
        new Thread(target).start();
        new Thread(target).start();
        new Thread(target).start();
        new Thread(target, "手动命名线程-A").start();
        new Thread(target, "手动命名线程-B").start();
        sleepSeconds(Integer.MAX_VALUE);
    }

}

package com.jaa.thread03;


import com.jaa.utils.Print;
import lombok.extern.slf4j.Slf4j;

import static com.jaa.utils.ThreadUtil.sleepSeconds;

/**
 * @Author: Jaa
 * @Date: 2022/11/22 22:58
 * @Description:
 */
@Slf4j
public class InterruptDemo {
    public static final int SLEEP_GAP = 5000;//睡眠时长
    public static final int MAX_TURN = 50;//睡眠次数

    static class SleepThread extends Thread {
        static int threadSeqNumber = 1;

        public SleepThread() {
            super("sleepThread-" + threadSeqNumber);
            threadSeqNumber++;
        }

        @Override
        public void run() {
            try {
                Print.tco(getName() + " 进入睡眠.");
                // 线程睡眠一会
                Thread.sleep(SLEEP_GAP);
            } catch (InterruptedException e) {
                log.info("Interrupted", e);
                Thread.currentThread().interrupt();
                e.printStackTrace();
                Print.tco(getName() + " 发生被异常打断.");
                return;
            }
            Print.tco(getName() + " 运行结束.");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new SleepThread();
        thread1.start();
        Thread thread2 = new SleepThread();
        thread2.start();

        sleepSeconds(2);        //主线程等待2秒
        thread1.interrupt();    //打断线程1

        sleepSeconds(5);       //主线程等待5秒
        thread2.interrupt();   //打断线程2，此时线程2已经终止

        sleepSeconds(1);       //主线程等待1秒
        Print.tco("程序运行结束.");
    }
}

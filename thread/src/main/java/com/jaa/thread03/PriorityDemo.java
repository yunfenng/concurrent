package com.jaa.thread03;

import com.jaa.utils.Print;

/**
 * @Author: Jaa
 * @Date: 2022/11/21 22:24
 * @Description:
 */
public class PriorityDemo {
    public static final int SLEEP_GAP = 1000;

    static class PrioritySetThread extends Thread {
        static int threadNo = 1;

        public PrioritySetThread() {
            super("thread-" + threadNo);
            threadNo++;
        }

        public long opportunities = 0;

        @Override
        public void run() {
            for (int i = 0; ; i++) {
                opportunities++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrioritySetThread[] threads = new PrioritySetThread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new PrioritySetThread();
            //优先级的设置，从1-10
            threads[i].setPriority(i + 1);
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        Thread.sleep(SLEEP_GAP);

        for (int i = 0; i < threads.length; i++) {
            threads[i].stop();
        }

        for (int i = 0; i < threads.length; i++) {
            Print.cfo(threads[i].getName() +
                    "; 优先级为-" + threads[i].getPriority() +
                    "; 机会值为-" + threads[i].opportunities
            );
        }

    }
}

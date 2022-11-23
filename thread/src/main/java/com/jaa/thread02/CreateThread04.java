package com.jaa.thread02;

import com.utils.ThreadUtil;

import java.util.concurrent.*;

/**
 * @Author: Jaa
 * @Date: 2022/11/19 23:36
 * @Description: 线程创建方法四：通过线程池创建线程
 *
 * ExecutorService线程池的execute(...)与submit(...)方法的区别如下:
 *
 * （1）接收的参数不一样submit()可以接收两种入参：无返回值的Runnable类型的target执行目标实例和有返回值的Callable类型的target执行目标实例。
 *  而execute()仅仅接收无返回值的target执行目标实例，或者无返回值的Thread实例。
 * （2）submit()有返回值，而execute()没有submit()方法在提交异步target执行目标之后会返回Future异步任务实例，
 *  以便对target的异步执行过程进行控制，比如取消执行、获取结果等。execute()没有任何返回，
 *  target执行目标实例在执行之后没有办法对其异步执行过程进行控制，只能任其执行，直到其执行结束。
 *
 *  实际生产环境禁止使用Executors创建线程池！！！
 */
public class CreateThread04 {

    public static final int MAX_TURN = 5;

    public static final int COMPUTE_TIMES = 100000000;

    // 创建一个包含3个线程的线程池
    private static ExecutorService pool = Executors.newFixedThreadPool(3);

    static class DemoThread implements Runnable {
        @Override
        public void run() {
            for (int j = 1; j < MAX_TURN; j++) {
                System.out.println(this.getClass().getSimpleName() + ": " + ThreadUtil.getCurThreadName() + ", 轮次" + j);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ReturnableTask implements Callable {
        @Override
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.println(ThreadUtil.getCurThreadName() + "线程执行start...");
            for (int j = 1; j < MAX_TURN; j++) {
                System.out.println(this.getClass().getSimpleName() + ": " + ThreadUtil.getCurThreadName() + ", 轮次" + j);
                Thread.sleep(10);
            }
            long used = System.currentTimeMillis() - startTime;
            System.out.println(ThreadUtil.getCurThreadName() + "线程执行end...");
            return used;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        pool.execute(new DemoThread()); // 执行线程实例, 无返回
        /*pool.execute(new Runnable() {
            @Override
            public void run() {
                for (int j = 1; j < MAX_TURN; j++) {
                    System.out.println(ThreadUtil.getCurThreadName() + ", 轮次" + j);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
        pool.execute(() -> {
            for (int j = 1; j < MAX_TURN; j++) {
                System.out.println(ThreadUtil.getCurThreadName() + ", 轮次" + j);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // 提交Callable执行目标实例, 有返回
        Future future = pool.submit(new ReturnableTask());
        Long result = (Long) future.get();
        System.out.println("异步任务执行的结果: " + result);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

package com.jaa.threadpool;

import com.jaa.utils.Print;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaa.utils.ThreadUtil.sleepMilliSeconds;

/**
 * @Author: Jaa
 * @Date: 2022/12/6 21:17
 * @Description: newSingleThreadExecutor创建“单线程化线程池”
 */
public class CreateThreadPoolDemo01 {

    private static final int SLEEP_GAP = 500;

    static class TargetTask implements Runnable {

        static AtomicInteger taskNo = new AtomicInteger(0);

        private String taskName;

        public TargetTask() {
            taskName = "task-" + taskNo.get();
            taskNo.incrementAndGet();
        }

        @Override
        public void run() {
            Print.tco("任务：" + taskName + " doing");
            sleepMilliSeconds(SLEEP_GAP);
            Print.tco(taskName + " 执行结束，");
        }
    }

    // 测试用例：只有一个线程的线程池
    @Test
    public void testSingleThreadExecutor() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            pool.execute(new TargetTask());
            pool.submit(new TargetTask());
        }
        sleepMilliSeconds(1000);
        pool.shutdown();
    }
}

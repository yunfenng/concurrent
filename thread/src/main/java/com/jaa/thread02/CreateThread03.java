package com.jaa.thread02;

import com.jaa.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: Jaa
 * @Date: 2022/11/19 20:58
 * @Description: 线程创建方法三：使用Callable和FutureTask创建线程
 * <p>
 * （1）创建一个Callable接口的实现类，并实现其call()方法，编写好异步执行的具体逻辑，可以有返回值。
 * （2）使用Callable实现类的实例构造一个FutureTask实例。
 * （3）使用FutureTask实例作为Thread构造器的target入参，构造新的Thread线程实例。
 * （4）调用Thread实例的start()方法启动新线程，启动新线程的run()方法并发执行。
 * 其内部的执行过程为：启动Thread实例的run()方法并发执行后，会执行FutureTask实例的run()方法，最终会并发执行Callable实现类的call()方法。
 * （5）调用FutureTask对象的get()方法阻塞性地获得并发线程的执行结果。
 */
public class CreateThread03 {

    public static final int COMPUTE_TIMES = 100000000;

    // 1、创建一个Callable接口的实现类
    static class ReturnableTask implements Callable<Long> {
        // 2、编写好异步执行的逻辑，可以有返回值
        @Override
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.println(ThreadUtil.getCurThreadName() + "线程运行start...");
            Thread.sleep(1000);
            for (int i = 0; i < COMPUTE_TIMES; i++) {
                int j = i * 10000;
            }
            long used = System.currentTimeMillis() - startTime;
            System.out.println(ThreadUtil.getCurThreadName() + "线程运行end...");
            return used;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 两个线程:
         *    一个是执行main()方法的主线程，叫作main；
         *    另一个是main线程通过thread.start()方法启动的业务线程，叫作returnableThread。该线程是一个包含FutureTask任务作为target的Thread线程。
         *
         * main线程通过thread.start()启动returnableThread线程之后，会继续自己的事情，returnableThread线程开始并发执行。
         *
         * main线程通过thread.start()启动returnableThread线程之后，会继续自己的事情，returnableThread线程开始并发执行。
         * 接着在这个futureTask.run()方法中会执行futureTask的callable成员的call()方法，
         * 这里的callable成员（ReturnableTask实例）是通过FutureTask构造器在初始化时传递进来的、自定义的Callable实现类的实例。
         *
         * FutureTask的Callable成员的call()方法执行完成后，会将结果保存在FutureTask内部的outcome实例属性中。
         * 以上演示实例的Callable实现类中，这里call()方法中业务逻辑的返回结果是call()方法从进入到出来的执行时长：
         * 执行时长返回之后，将被作为结果保存在FutureTask内部的outcome实例属性中。
         * 至此，异步的returnableThread线程执行完毕。在main线程处理完自己的事情后（以上实例中是一个消磨时间的循环），
         *
         * 通过futureTask的get实例方法获取异步执行的结果。这里有两种情况：
         * （1）futureTask的结果outcome不为空，callable.call()执行完成。
         *  在这种情况下，futureTast.get会直接取回outcome结果，返回给main线程（结果获取线程）。
         * （2）futureTask的结果outcome为空，callable.call()还没有执行完。
         * 在这种情况下，main线程作为结果获取线程会被阻塞住，一直阻塞到callable.call()执行完成。
         * 当执行完后，最终结果会保存到outcome中，futureTask会唤醒main线程，去提取callable.call()执行结果。
         */
        ReturnableTask returnableTask = new ReturnableTask(); // 3、
        FutureTask<Long> futureTask = new FutureTask<>(returnableTask); // 4、
        Thread thread = new Thread(futureTask, "returnableThread"); // 5、
        thread.start(); // 6、
        Thread.sleep(500);
        System.out.println(ThreadUtil.getCurThreadName() + "让子弹再飞会儿...");
        System.out.println(ThreadUtil.getCurThreadName() + "做一点自己的事情...");
        for (int i = 0; i < COMPUTE_TIMES; i++) {
            int j = i * 10000;
        }
        System.out.println(ThreadUtil.getCurThreadName() + "获取并发任务执行的结果...");
        try {
            System.out.println(thread.getName() + "线程占用时间: " + futureTask.get()); // 7、
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(ThreadUtil.getCurThreadName() + "运行结束...");
    }
}

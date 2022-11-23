package com.jaa.thread02;

import com.jaa.utils.ThreadUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Jaa
 * @Date 2022/11/19 12:18
 * @Description: 逻辑和数据更好分离。通过实现Runnable接口的方法创建多线程更加适合同一个资源被多段业务逻辑并行处理的场景。
 * 在同一个资源被多个线程逻辑异步、并行处理的场景中，通过实现Runnable接口的方式设计多个target执行目标类可以更加方便、
 * 清晰地将执行逻辑和数据存储分离，更好地体现了面向对象的设计思
 */
public class SalesDemo {

    public static final int MAX_AMOUNT = 5; // 商品数量

    // 商店商品类（销售线程类），一个商品一个销售线程，每个线程异步销售4次
    static class StoreGoods extends Thread {
        StoreGoods(String name) {
            super(name);
        }

        private int goodsAmount = MAX_AMOUNT;

        @Override
        public void run() {
            for (int i = 0; i <= MAX_AMOUNT; i++) {
                if (this.goodsAmount > 0) {
                    try {
                        System.out.println(ThreadUtil.getCurThreadName() + "卖出一件，还剩" + (--goodsAmount));
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(ThreadUtil.getCurThreadName() + "运行结束.");
        }
    }

    // 商场商品类（target销售线程的目标类），一个商品最多销售4次，可以多人销售
    static class MallGoods implements Runnable {

        // 多人销售可能导致数据出错，使用原子数据类型保障数据安全
        private AtomicInteger goodAmount = new AtomicInteger(MAX_AMOUNT);

        @Override
        public void run() {
            for (int i = 0; i <= MAX_AMOUNT; i++) {
                synchronized (goodAmount) {
                    if (this.goodAmount.get() > 0) {
                        System.out.println(ThreadUtil.getCurThreadName() + "卖出一件，还剩" + (goodAmount.decrementAndGet()));
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println(ThreadUtil.getCurThreadName() + "运行结束.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("***商品版本的销售***");
        for (int i = 1; i <= 2; i++) {
            Thread thread = null;
            thread = new StoreGoods("店员-" + i);
            thread.start();
        }
        Thread.sleep(200);

        System.out.println("***商场版本的销售***");
        MallGoods mallGoods = new MallGoods();
        for (int i = 1; i <= 2; i++) {
            Thread thread = null;
            thread = new Thread(mallGoods, "商场销售员-" + i);
            thread.start();
        }
        System.out.println(ThreadUtil.getCurThreadName() + "运行结束");
    }
}

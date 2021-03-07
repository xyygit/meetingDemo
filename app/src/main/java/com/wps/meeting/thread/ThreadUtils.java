package com.wps.meeting.thread;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程管理
 * Created by yayun.xia on 2021/1/24.
 */
public final class ThreadUtils {
    private ExecutorService executorService;
    //参数初始化
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量大小
    private static final int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    //线程池最大容纳线程数
    private static final int maximumPoolSize = CPU_COUNT * 2 + 1;
    //        private static final int maximumPoolSize = 2000;
    //线程空闲后的存活时长
    private static final int keepAliveTime = 30;

    //任务过多后，存储任务的一个阻塞队列
    private final static LinkedBlockingQueue<Runnable> workQueue2 = new LinkedBlockingQueue<>(100);
    private static boolean isFirst = true;
    public static ThreadPoolExecutor get() {
        if (isFirst) {
            mExecute.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            isFirst = false;
        }
        return mExecute;
    }

    //线程的创建工厂
    private final static ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "CTP #" + mCount.getAndIncrement());
        }
    };

    //线程池任务满载后采取的任务拒绝策略
    private final static RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.DiscardOldestPolicy();

    //线程池对象，创建线程
    private final static ThreadPoolExecutor mExecute = new ThreadPoolExecutor(
            corePoolSize,
            maximumPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            workQueue2,
            threadFactory,
            rejectHandler
    );

    private ThreadUtils() {
        init();
    }

    private static class ThreadUtilsHolder {
        private static final ThreadUtils mInstance = new ThreadUtils();
    }

    public static ThreadUtils getInstance() {
        return ThreadUtilsHolder.mInstance;
    }

    private void init() {
        //初始化线程池
        executorService = Executors.newFixedThreadPool(3, new AppThreadFactory());
    }

    /**
     * 执行异步任务
     *
     * @param runnable
     */
    public void execute(Runnable runnable) {
        try {
            executorService.execute(runnable);
            mExecute.execute(new Runnable() {
                @Override
                public void run() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行主线程任务
     *
     * @param runnable
     */
    public void runOnUi(Runnable runnable) {
        try {
            MainThread.run(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程切换
     *
     * @param task         子线程执行
     * @param taskCallback 回调到主线程
     */
    public <T> void execute(Task<T> task, UiTaskCallback<T> taskCallback) {
        executorService.execute(() -> {
            try {
                //子线程执行任务
                T t = task.execute();
                //切换到主线程执行回调
                MainThread.run(() -> taskCallback.callback(t));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 线程工厂
     */
    public static class AppThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("app_thread_" + thread.getId());
            return thread;
        }
    }

    public interface Task<T> {
        T execute();
    }

    public interface UiTaskCallback<T> {
        void callback(T t);
    }
}

package com.wps.meeting;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 线程管理
 * Created by yayun.xia on 2021/1/24.
 */
public final class ThreadUtils {
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    private ExecutorService executorService;

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
            mHandler.post(runnable);
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
                mHandler.post(() -> taskCallback.callback(t));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 移除任务
     *
     * @param runnable
     */
    public void removeCallback(@NonNull Runnable runnable) {
        mHandler.removeCallbacks(runnable);
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

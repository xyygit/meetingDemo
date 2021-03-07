package com.wps.meeting.thread;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

/**
 * 主线程
 * Created by yayun.xia on 2021/1/31.
 */
public final class MainThread {
    private MainThread() {
    }

    //创建主线程handler
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 在主线程执行任务
     *
     * @param runnable
     */
    public static void run(@NonNull Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            HANDLER.post(runnable);
        }
    }

    /**
     * 当前线程是否主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}

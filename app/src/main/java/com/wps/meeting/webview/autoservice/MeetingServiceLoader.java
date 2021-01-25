package com.wps.meeting.webview.autoservice;

import java.util.ServiceLoader;

/**
 * Created by yayun.xia on 2021/1/24.
 */
public final class MeetingServiceLoader {
    private MeetingServiceLoader() {
    }

    public static <S> S load(Class<S> service) {
        try {
            return ServiceLoader.load(service).iterator().next();
        } catch (Exception e) {
            return null;
        }
    }
}

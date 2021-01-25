package com.wps.meeting.webview;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;

/**
 * Created by yayun.xia on 2021/1/24.
 */
public interface WebViewCallBack {
    void pageStarted(String url);

    void pageFinished(String url);

    void onError(WebResourceRequest request, WebResourceError error);

    void updateTitle(String title);
}

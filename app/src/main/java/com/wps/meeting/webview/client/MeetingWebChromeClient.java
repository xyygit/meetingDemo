package com.wps.meeting.webview.client;

import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.wps.meeting.webview.WebViewCallBack;

/**
 * Created by yayun.xia on 2021/1/24.
 */
public class MeetingWebChromeClient extends WebChromeClient {
    private static final String TAG = MeetingWebChromeClient.class.getSimpleName();
    private final WebViewCallBack mWebViewCallBack;

    public MeetingWebChromeClient(WebViewCallBack mWebCallBack) {
        this.mWebViewCallBack = mWebCallBack;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (mWebViewCallBack != null) {
            mWebViewCallBack.updateTitle(title);
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }

    //获取加载进度
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        String progress = newProgress + "%";
        if (newProgress < 100) {
            progress = newProgress + "%";
        } else if (newProgress == 100) {
            progress = newProgress + "%";
        }
        Log.e("WebView", "onProgressChanged:" + progress);
    }
}

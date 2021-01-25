package com.wps.meeting.webview.client;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wps.meeting.webview.WebViewCallBack;

/**
 * Created by yayun.xia on 2021/1/24.
 */
public class MeetingWebViewClient extends WebViewClient {
    private static final String TAG = MeetingWebChromeClient.class.getSimpleName();
    private WebViewCallBack mWebViewCallBack;

    public MeetingWebViewClient(WebViewCallBack mWebViewCallBack) {
        this.mWebViewCallBack = mWebViewCallBack;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mWebViewCallBack != null) {
            mWebViewCallBack.pageStarted(url);
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mWebViewCallBack != null) {
            mWebViewCallBack.pageFinished(url);
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //设置不用系统浏览器打开,直接显示在当前Webview
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (mWebViewCallBack != null) {
            mWebViewCallBack.onError(request, error);
        } else {
            Log.e(TAG, "WebViewCallBack is null.");
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //解决x509报错
        //忽略证书检验
        handler.proceed();
    }
}

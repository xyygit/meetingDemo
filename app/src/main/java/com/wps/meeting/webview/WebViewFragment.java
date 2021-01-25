package com.wps.meeting.webview;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wps.meeting.R;
import com.wps.meeting.webview.client.MeetingWebChromeClient;
import com.wps.meeting.webview.client.MeetingWebViewClient;
import com.wps.meeting.webview.utils.Constants;

/**
 * Created by yayun.xia on 2021/1/24.
 */
public class WebViewFragment extends Fragment implements WebViewCallBack {
    private static final String TAG = WebViewFragment.class.getSimpleName();
    private RelativeLayout rlTitle;
    private TextView mTvTitle;
    private WebView mWebView;
    private String mUrl;
    private String mTitle;
    private boolean mIsError;
    private boolean mIsShowTitle;

    public static WebViewFragment newInstance(String url, boolean mIsShowTitle, String title) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url);
        bundle.putBoolean(Constants.IS_SHOW_ACTION_BAR, mIsShowTitle);
        bundle.putString(Constants.TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(Constants.URL);
            mIsShowTitle = bundle.getBoolean(Constants.IS_SHOW_ACTION_BAR);
            mTitle = bundle.getString(Constants.TITLE);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        rlTitle = view.findViewById(R.id.rl_webview_title);
        mTvTitle = view.findViewById(R.id.tv_webview_title);
        if (mIsShowTitle) {
            rlTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(mTitle);
        } else {
            rlTitle.setVisibility(View.GONE);
        }
        mWebView = view.findViewById(R.id.web_meeting);

        //websetting
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new MeetingWebViewClient(this));
        mWebView.setWebChromeClient(new MeetingWebChromeClient(this));
        mWebView.loadUrl(mUrl);
        return view;
    }

    @Override
    public void pageStarted(String url) {
        Log.d(TAG, "pageStarted:" + url);
        //显示loading
    }

    @Override
    public void pageFinished(String url) {
        Log.d(TAG, "pageFinished" + url);
        //此处隐藏loading

        if (mIsError) {
            //加载错误
        } else {
            //加载成功
        }
        //标记置位
        mIsError = false;
    }

    @Override
    public void onError(WebResourceRequest request, WebResourceError error) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e(TAG, "onError " + error.getDescription().toString());
        }
        mIsError = true;
    }

    @Override
    public void updateTitle(String title) {
        //更新标题
        Log.d(TAG, "updateTitle" + title);
        if (mIsShowTitle) {
            mTvTitle.setText(title);
        }
    }

    public void onClickBack(View view) {
        //返回上一页

    }
}

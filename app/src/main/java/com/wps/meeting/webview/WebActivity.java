package com.wps.meeting.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.wps.meeting.R;
import com.wps.meeting.webview.autoservice.IWebViewService;
import com.wps.meeting.webview.autoservice.MeetingServiceLoader;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        IWebViewService webviewService = MeetingServiceLoader.load(IWebViewService.class);
        Fragment fragment;
        if (webviewService != null) {
            fragment = webviewService.getWebViewFragment("https://www.baidu.com", true, "百度");
        } else {
            fragment = WebViewFragment.newInstance("https://www.baidu.com", true, "百度");
        }
        transaction.replace(R.id.web_view_fragment, fragment).commit();
    }
}
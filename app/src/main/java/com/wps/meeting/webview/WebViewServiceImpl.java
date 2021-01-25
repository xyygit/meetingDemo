package com.wps.meeting.webview;

import androidx.fragment.app.Fragment;

import com.google.auto.service.AutoService;
import com.wps.meeting.webview.autoservice.IWebViewService;

/**
 * Created by yayun.xia on 2021/1/24.
 */
@AutoService({IWebViewService.class})
public class WebViewServiceImpl implements IWebViewService {

    @Override
    public Fragment getWebViewFragment(String url, boolean isShowActionBar, String title) {
        return WebViewFragment.newInstance(url, isShowActionBar, title);
    }
}

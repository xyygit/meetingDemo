package com.wps.meeting.webview.autoservice;

import androidx.fragment.app.Fragment;

/**
 * Created by yayun.xia on 2021/1/24.
 */
public interface IWebViewService {
    Fragment getWebViewFragment(String url, boolean isShowActionBar, String title);
}

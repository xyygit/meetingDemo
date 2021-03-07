package com.wps.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wps.meeting.recylerview.RecyclerTestActivity;
import com.wps.meeting.recylerview.TestRecyclerAdapter;
import com.wps.meeting.webview.WebActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testWeb(View view) {
        startClass(WebActivity.class);
    }

    public void testRecyclerView(View view) {
        startClass(RecyclerTestActivity.class);
    }

    private void startClass(Class cls) {
        startActivity(new Intent(this, cls));
    }
}
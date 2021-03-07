package com.wps.meeting.recylerview;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wps.meeting.R;


/**
 * Created by yayun.xia on 2021/3/7.
 */
public class RecyclerTestActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvTest;
    private TestRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test);
        findViewById(R.id.btn_linear_layout_manager).setOnClickListener(this);
        findViewById(R.id.btn_grid_layout_manager).setOnClickListener(this);
        rvTest = findViewById(R.id.rv_test);
        adapter = new TestRecyclerAdapter(this, 10);
        rvTest.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_linear_layout_manager:
                rvTest.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.btn_grid_layout_manager:
                rvTest.setLayoutManager(new GridLayoutManager(this, 2));
                rvTest.addItemDecoration(new GridDividerItemDecoration(this,
                        R.drawable.shape_item_grid_divider));
                break;
            default:
                break;
        }
    }

}

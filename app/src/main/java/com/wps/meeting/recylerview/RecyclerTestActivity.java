package com.wps.meeting.recylerview;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
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
    private DividerItemDecoration linearDivider;
    private GridDividerItemDecoration gridDivider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test);
        findViewById(R.id.btn_linear_layout_manager).setOnClickListener(this);
        findViewById(R.id.btn_grid_layout_manager).setOnClickListener(this);
        rvTest = findViewById(R.id.rv_test);
        adapter = new TestRecyclerAdapter(this, 10);
        rvTest.setAdapter(adapter);

        linearDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        linearDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_item_grid_divider));

        gridDivider = new GridDividerItemDecoration(this, R.drawable.shape_item_grid_divider);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_linear_layout_manager:
                rvTest.setLayoutManager(new LinearLayoutManager(this));
                rvTest.removeItemDecoration(gridDivider);
                rvTest.removeItemDecoration(linearDivider);
                rvTest.addItemDecoration(linearDivider);
                break;
            case R.id.btn_grid_layout_manager:
                rvTest.setLayoutManager(new GridLayoutManager(this, 2));
                rvTest.removeItemDecoration(linearDivider);
                rvTest.removeItemDecoration(gridDivider);
                rvTest.addItemDecoration(gridDivider);
                break;
            default:
                break;
        }
    }

}

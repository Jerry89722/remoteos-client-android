package com.example.zhang.remoteos;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

//import com.example.zhang.remoteos.apps.media.PlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView mGridView;
    List<AppBean> mdatas;
    private IconAdapter mAdapter;
    private String[] itemames = new String[]{"播放器"};  //, "播放器"};
    private Class[] classes = new Class[]{MediaActivity.class}; // , PlayerActivity.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdatas = new ArrayList<>();
        initView();
        mAdapter = new IconAdapter(this, mdatas);

        datasLoad();
        mGridView.setAdapter(mAdapter);
        setEventListener();
    }

    private void setEventListener() {
        mAdapter.setOnItemClickListener(new IconAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AppBean ab = mdatas.get(position);
                Log.d("click", ab.name);
                Intent intent = new Intent(MainActivity.this, ab.cls);
                startActivity(intent);
            }
        });
    }

    private void appAccess() {
    }

    private void datasLoad() {

        int i = 0;
        for (String itemame : itemames) {
            AppBean ib = new AppBean(R.mipmap.ic_launcher, itemame, classes[i]);
            ++i;
            mdatas.add(ib);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mGridView = findViewById(R.id.icon_gv);
    }

}

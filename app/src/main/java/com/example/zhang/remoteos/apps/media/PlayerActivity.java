//package com.example.zhang.remoteos.apps.media;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.example.zhang.remoteos.utils.ExplorerList;
//import com.example.zhang.remoteos.utils.ResourceListBean;
//import com.example.zhang.remoteos.utils.GetData;
//import com.example.zhang.remoteos.R;
//import com.example.zhang.remoteos.utils.FileAdapter;
//import com.example.zhang.remoteos.utils.ResourceBean;
//import com.example.zhang.remoteos.utils.PlayStatusBean;
//import com.example.zhang.remoteos.utils.ROSUtils;
//import com.example.zhang.remoteos.utils.TaskBean;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class PlayerActivity extends Activity implements View.OnClickListener{
//    TextView name_tv, cur_time_tv, total_time_tv;
//    ImageView next_iv, last_iv, play_iv, touch_line_iv;
//    SeekBar seekBar;
//    RecyclerView list_rv;
//    List<ResourceBean> mFiles;
//    String curPath = "/";
//
//    ResourceBean playingFileBean;
//
//    private String detail = "";
//
//    private FileAdapter adapter;
//
//    Timer timer;
//    TimerTask task;
//
//    @Override
//    protected void onCreate(Bundle saveInstanceState) {
//
//        super.onCreate(saveInstanceState);
//        setContentView(R.layout.activity_player);
//        mFiles = new ArrayList<>();
//        initView();
//
//        adapter = new FileAdapter(this, mFiles);
//        list_rv.setAdapter(adapter);
//        list_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//        listLoad();
//
//        setEventListener();
//    }
//
//    private void setEventListener() {
//        /* 设置每一项的点击事件*/
//        adapter.setOnItemClickListener(new FileAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int i) {
//
//                ResourceBean fileBean = mFiles.get(i);
//                Log.e("list clicked, position", "" + i);
//                itemClicked(fileBean);
//            }
//        });
//    }
//
//    private void listLoad() {
//
//        new Thread() {
//            public void run() {
//                TaskBean<ExplorerList> task = new TaskBean<>("list", new ExplorerList("video/music", curPath));
//                String url = task.toString();
//
//                Log.e("list load html request", url);
//                try {
//                    url = new String(url.getBytes("ISO-8859-1"),"UTF-8");
//                    detail = GetData.getHtml("/explorer" + url);
//                    Log.d("detail html", detail);
//                } catch (Exception e) {
//                    Log.e("detail html", "get failed");
//                    e.printStackTrace();
//                }
//
//                Handler handler = new Handler(Looper.getMainLooper()){
//                    @Override
//                    public void handleMessage(Message msg){ // 会在主线程被通知(sendEmptyMessage)后执行
//                        mFiles.clear();
//                        JSONObject jsonObject = JSON.parseObject(detail);
//                        ResourceListBean fileListBean = JSON.toJavaObject(jsonObject, ResourceListBean.class);
////                        String uuid = fileListBean.getUuid();
//                        List<ResourceBean> fileList = fileListBean.getList();
//                        for(int i = 0; i < fileList.size(); ++i){
//                            ResourceBean fileBean = fileList.get(i);
//                            fileBean.setId(i + 1);
//                            mFiles.add(fileBean);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                };
//                handler.sendEmptyMessage(0);
//            }
//        }.start();
//    }
//
//    private void itemClicked(final ResourceBean fileBean){
//        if(fileBean.getType().equals("dir")){
//            curPath = curPath + fileBean.getName() + "/";
//            listLoad();
//            return;
//        }
//        playingFileBean = fileBean;
//        mediaPlayerCtrl(playingFileBean, PlayActionEnum.playNew, -1);
//    }
//
//    private void mediaPlayerCtrl(final ResourceBean fileBean, final PlayActionEnum action, final int progress){
//        new Thread() {
//            public void run() {
//                try {
//
//                    TaskBean<MediaPlay> task;
//                    if (action == PlayActionEnum.playNew) {
//                        task = new TaskBean<>("play", new MediaPlay(curPath + playingFileBean.getName()));
//                    }else if(action == PlayActionEnum.playCur) {
//                        task = new TaskBean<>("play", progress);
//                    }else if(action == PlayActionEnum.statusCheck) {
//                        task = new TaskBean<>("status", progress);
//                    }else{
//                        task = new TaskBean<>("seek", progress);
//                    }
//                    String url = task.toString();
//
//                    url = new String(url.getBytes("ISO-8859-1"),"UTF-8");
//                    detail = GetData.getHtml("/media" + url);
//                    Log.e("media response", detail);
//                } catch (Exception e) {
//                    Log.e("play html", "get failed");
//                    e.printStackTrace();
//                }
//
//                Handler handler = new Handler(Looper.getMainLooper()) {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        JSONObject jsonObject = JSON.parseObject(detail);
//                        PlayStatusBean playStatusBean = JSON.toJavaObject(jsonObject, PlayStatusBean.class);
//
//                        playerBoarderUpdate(playStatusBean);
//
//                        String name = fileBean.getName();
//                        name_tv.setText(name);
//                        switch (playStatusBean.getStatus()) {
//                            case "playing":
//                                play_iv.setImageResource(R.mipmap.icon_pause);
//                                break;
//                            case "pause":
//                                play_iv.setImageResource(R.mipmap.icon_play);
//                                break;
//                        }
//                    }
//                };
//                handler.sendEmptyMessage(0);
//            }
//
//        }.start();
//    }
//
//    private void playerBoarderUpdate(PlayStatusBean playStatusBean) {
//
//        Log.e("cur_time", playStatusBean.getCur_time());
//        Log.e("total_time", playStatusBean.getTotal_time());
//        cur_time_tv.setText(playStatusBean.getCur_time());
//        total_time_tv.setText(playStatusBean.getTotal_time());
//
//        seekBar.setMax((int) playStatusBean.getTimeAsSec(true));
//        seekBar.setProgress((int)playStatusBean.getTimeAsSec(false));
//    }
//
//    private void initView() {
//         name_tv = findViewById(R.id.tv_name);
//         cur_time_tv = findViewById(R.id.tv_cur_time);
//         total_time_tv = findViewById(R.id.tv_total_time);
//         next_iv = findViewById(R.id.iv_next);
//         last_iv = findViewById(R.id.iv_last);
//         play_iv = findViewById(R.id.iv_play);
//         touch_line_iv = findViewById(R.id.iv_touch_line);
//         seekBar = findViewById(R.id.seek_bar);
//         list_rv = findViewById(R.id.rv);
//
//         next_iv.setOnClickListener(this);
//         last_iv.setOnClickListener(this);
//         play_iv.setOnClickListener(this);
//         touch_line_iv.setOnClickListener(this);
//         seekBar.setEnabled(false);
//
//         timer = new Timer();
//
//         seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//             int progress;
//             @Override
//             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                 cur_time_tv.setText(ROSUtils.secToTime(i));
//                 progress = i;
//             }
//
//             @Override
//             public void onStartTrackingTouch(SeekBar seekBar) {
//                 if(task != null){
//                     task.cancel();
//                 }
//             }
//
//             @Override
//             public void onStopTrackingTouch(SeekBar seekBar) {
//                 mediaPlayerCtrl(playingFileBean, PlayActionEnum.playSeek, progress);
//                 timerTaskStart();
//             }
//         });
//    }
//
//    void timerTaskStart(){
//        if(task != null){
//            task.cancel();
//        }
//        task = new TimerTask() {
//            @Override
//            public void run() {
//                Handler handler = new Handler(Looper.getMainLooper()) {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        seekBar.setEnabled(false);
//                    }
//                };
//                handler.sendEmptyMessage(0);
//            }
//        };
//        timer.schedule(task, 2000);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.iv_next:
//                Log.e("click", "next ......");
//                break;
//            case R.id.iv_last:
//                Log.e("click", "last ......");
//                break;
//            case R.id.iv_play:
//                mediaPlayerCtrl(playingFileBean, PlayActionEnum.playCur, -1);
//                break;
//            case R.id.iv_touch_line:
//                Log.e("click", "enable seek_bar ......");
//                mediaPlayerCtrl(playingFileBean, PlayActionEnum.statusCheck, -1);
//                seekBar.setEnabled(true);
//                timerTaskStart();
//                break;
//        }
//    }
//}

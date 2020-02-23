package com.example.zhang.remoteos;


import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.zhang.remoteos.apps.media.PlayActionEnum;
import com.example.zhang.remoteos.adapters.FileAdapter;
import com.example.zhang.remoteos.beans.MediaStatusBean;
import com.example.zhang.remoteos.beans.ResourceBean;
import com.example.zhang.remoteos.beans.ResourceListBean;
import com.example.zhang.remoteos.utils.ROSUtils;
import com.example.zhang.remoteos.beans.TaskBean;
import com.example.zhang.remoteos.utils.okhttputils.CallBackUtil;
import com.example.zhang.remoteos.utils.okhttputils.OkhttpUtil;
import com.hjq.bar.TitleBar;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class MediaActivity extends Activity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
//    private String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        Intent intent = getIntent();
//        session = intent.getStringExtra("session");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                Log.e("selected page", String.valueOf(mViewPager.getCurrentItem()));
                if(i == 0){
//                    TV

                }else{
//                    local

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.e("final page", String.valueOf(mViewPager.getCurrentItem()));
            }
        });
    }

    @Override
    public void onBackPressed(){
        Log.e("clicked", "back pressed "+ mViewPager.getCurrentItem());
        PlaceholderFragment frgmt = mSectionsPagerAdapter.getFrgmt(mViewPager.getCurrentItem()); // getFragmentManager().findFragmentById(mViewPager.getCurrentItem());
        if(frgmt == null){
            Log.e("fragment is null", "is null");
        }
        if(frgmt.lastPage()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_media, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /****************************************************************
     * A placeholder fragment containing a simple view.
     ****************************************************************/
    public static class PlaceholderFragment extends Fragment{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        TextView name_tv, cur_time_tv, total_time_tv;
        ImageView next_iv, last_iv, play_iv, stop_iv, mute_iv, low_iv, high_iv, touch_line_iv;
        SeekBar seekBar;
        RecyclerView list_rv;
        List<ResourceBean> mResources;
        String curPath = "/";

        ResourceBean playingResourceBean;

        private FileAdapter adapter;

        Timer timer;
        TimerTask task;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_media, container, false);
            TitleBar titleBar = rootView.findViewById(R.id.section_label);

            mResources = new ArrayList<>();

            initView(rootView);
            adapter = new FileAdapter(this.getActivity(), mResources);
            list_rv.setAdapter(adapter);
            list_rv.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));

            String title;
            ResourceBean resourceBean;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                title = "电视";
                resourceBean = new ResourceBean("tv", "internet");

            }else{
                title = "本地媒体";
                resourceBean = new ResourceBean("video/music", curPath);
            }

            listLoad(resourceBean);

            titleBar.setTitle(title);

            setEventListener();
            return rootView;
        }

        public boolean lastPage(){
            Log.e("current path", curPath);

            if(curPath.equals("/")) {
                return false;
            }else {

                if(curPath.charAt(curPath.length() - 1) == '/') {
                    curPath = curPath.substring(0, curPath.lastIndexOf('/', curPath.length()-2)+1);
                }else{
                    curPath = curPath.substring(0, curPath.lastIndexOf('/') + 1);
                }
                Log.e("current path2", curPath);

                ResourceBean resourceBean = new ResourceBean("video/music", curPath);
                listLoad(resourceBean);
                return true;
            }
        }

        private void setEventListener() {
            /* 设置每一项的点击事件*/
            adapter.setOnItemClickListener(new FileAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {

                    ResourceBean resourceBean = mResources.get(i);
                    Log.e("list clicked, position", "" + i);
                    itemClicked(resourceBean);
                }
            });
        }

        private void listLoad(final ResourceBean resourceBean) {

            TaskBean task = new TaskBean(PlayActionEnum.list, resourceBean);
            String url = task.toString();
            try {
                url = new String(url.getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String base_url = getResources().getString(R.string.base_url);
            url = String.format(base_url, "toshiba", "explorer" + url);

            // public static void okHttpGet(String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack)
            OkhttpUtil.okHttpGet(url, null, null, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {}

                @Override
                public void onResponse(String response) {
                    //                                Toast.makeText(MediaActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.d("list load response", response);
                    mResources.clear();
                    JSONObject jsonObject = JSON.parseObject(response);
                    ResourceListBean resourceListBean = JSON.toJavaObject(jsonObject, ResourceListBean.class);
//                        String uuid = fileListBean.getUuid();
                    List<ResourceBean> resourceList = resourceListBean.getList();
                    for(int i = 0; i < resourceList.size(); ++i){
                        ResourceBean resourceBean = resourceList.get(i);
                        if(!resourceBean.getType().equals("tv")){
                            resourceBean.setId(i + 1);
                        }
                        mResources.add(resourceBean);
                    }
                    adapter.notifyDataSetChanged();
                }
            });

        }

        private void itemClicked(final ResourceBean resourceBean){
            if(resourceBean.getType().equals("dir")){
                curPath = curPath + resourceBean.getName() + "/";
                Log.e("next current path", curPath);
                resourceBean.setPath(curPath);
                listLoad(resourceBean);
            }else{
                resourceBean.setPath(curPath + resourceBean.getName());
                playingResourceBean = resourceBean;
                if(getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER) == 1){
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.playTv, 0);
                }else{
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.playNew, 0);
                }
            }
        }

        private void mediaPlayerCtrl(final ResourceBean resourceBean, final PlayActionEnum action, final int arg){
            TaskBean task = new TaskBean(action, resourceBean, arg);
            String url = task.toString();
            try {
                url = new String(url.getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String base_url = getResources().getString(R.string.base_url);
            url = String.format(base_url, "toshiba", "media" + url);

            Log.e("media player url", url);

//          public static void okHttpGet(String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack)
            OkhttpUtil.okHttpGet(url, null, null, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {}

                @Override
                public void onResponse(String response) {
//                  Toast.makeText(MediaActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.d("media player response", response);
                    JSONObject jsonObject = JSON.parseObject(response);
                    MediaStatusBean playStatusBean = JSON.toJavaObject(jsonObject, MediaStatusBean.class);

                    playerBoarderUpdate(playStatusBean);
                    if(resourceBean != null) {
                        String name = resourceBean.getName();
                        name_tv.setText(name);
                        switch (playStatusBean.getStatus()) {
                            case "playing":
                                play_iv.setImageResource(R.mipmap.icon_pause);
                                break;
                            case "pause":
                            case "stop":
                                play_iv.setImageResource(R.mipmap.icon_play);
                                break;
                        }
                    }
                }
            });
        }

        private void playerBoarderUpdate(MediaStatusBean playStatusBean) {

            Log.e("cur_time", playStatusBean.getCur_time());
            Log.e("total_time", playStatusBean.getTotal_time());
            cur_time_tv.setText(playStatusBean.getCur_time());
            total_time_tv.setText(playStatusBean.getTotal_time());

            seekBar.setMax((int) playStatusBean.getTimeAsSec(true));
            seekBar.setProgress((int)playStatusBean.getTimeAsSec(false));
        }

        private void initView(View view) {
            name_tv = view.findViewById(R.id.tv_name);
            cur_time_tv = view.findViewById(R.id.tv_cur_time);
            total_time_tv = view.findViewById(R.id.tv_total_time);
            next_iv = view.findViewById(R.id.iv_next);
            last_iv = view.findViewById(R.id.iv_last);
            play_iv = view.findViewById(R.id.iv_play);
            touch_line_iv = view.findViewById(R.id.iv_touch_line);
            seekBar = view.findViewById(R.id.seek_bar);
            list_rv = view.findViewById(R.id.rv);
            stop_iv = view.findViewById(R.id.iv_stop);
            mute_iv = view.findViewById(R.id.iv_mute);
            low_iv = view.findViewById(R.id.vol_low);
            high_iv = view.findViewById(R.id.vol_high);

            seekBar.setEnabled(false);

//            next_iv.setOnClickListener((View.OnClickListener) view);
//            last_iv.setOnClickListener(this);

            stop_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.playStop, 0);
                }
            });

            mute_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.volMute, 0);
                }
            });

            low_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.volLow, -8);
                }
            });

            high_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.volHigh, 8);
                }
            });

            play_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("click", "enable seek_bar ......");
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.playCur, 0);
                }
            });
            touch_line_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("click", "enable seek_bar ......");
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.statusCheck, 0);
                    seekBar.setEnabled(true);
                    timerTaskStart();
                }
            });

            timer = new Timer();
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress;
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    cur_time_tv.setText(ROSUtils.secToTime(i));
                    progress = i;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if(task != null){
                        task.cancel();
                    }
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayerCtrl(playingResourceBean, PlayActionEnum.playSeek, progress);
                    timerTaskStart();
                }
            });
        }

        void timerTaskStart(){
            if(task != null){
                task.cancel();
            }
            task = new TimerTask() {
                @Override
                public void run() {
                    Handler handler = new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            seekBar.setEnabled(false);
                        }
                    };
                    handler.sendEmptyMessage(0);
                }
            };
            timer.schedule(task, 2000);
        }

//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.iv_next:
//                    Log.e("click", "next ......");
//                    break;
//                case R.id.iv_last:
//                    Log.e("click", "last ......");
//                    break;
//                case R.id.iv_play:
//                    mediaPlayerCtrl(playingFileBean, PlayActionEnum.playCur, -1);
//                    break;
//                case R.id.iv_touch_line:
//                    Log.e("click", "enable seek_bar ......");
//                    mediaPlayerCtrl(playingFileBean, PlayActionEnum.statusCheck, -1);
//                    seekBar.setEnabled(true);
//                    timerTaskStart();
//                    break;
//            }
//        }
    }

    /***************************************************************************
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     ***************************************************************************/
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<PlaceholderFragment> fragments;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.e("getItem", String.valueOf(position));
            Fragment fragment = PlaceholderFragment.newInstance(position + 1);
            fragments.add((PlaceholderFragment) fragment);
            return fragment;
        }

        public PlaceholderFragment getFrgmt(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }
}

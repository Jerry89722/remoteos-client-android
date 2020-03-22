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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zhang.remoteos.apps.media.BaseMedia;
import com.example.zhang.remoteos.apps.media.LocalMedia;
import com.example.zhang.remoteos.apps.media.OnlineVideo;
import com.example.zhang.remoteos.apps.media.Television;
import com.example.zhang.remoteos.beans.requestbean.PlayerRequestBean;
import com.example.zhang.remoteos.beans.requestbean.RequestBaseBean;
import com.example.zhang.remoteos.beans.responsebean.MediaStatusResponseBean;
import com.example.zhang.remoteos.beans.requestbean.SearchRequestBean;
import com.example.zhang.remoteos.utils.PlayActionEnum;
import com.example.zhang.remoteos.utils.ROSUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

        BaseMedia media;

        TextView titleBar;

        EditText et_keyword;
        Button btn_search;

        MediaBoarder mediaBoarder;

        RecyclerView list_rv;

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
            initView(rootView);

            String title;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                title = "电视";
                media = new Television(this.getActivity());
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                title = "网络视频";
                media = new OnlineVideo(this.getActivity());
            } else {
                title = "本地媒体";
                media = new LocalMedia(this.getActivity());
            }

            list_rv.setAdapter(media.getAdapter());
            list_rv.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
            media.listRequest(null);

            media.playerStatusRequest();

            media.setMediaBoarderNotifier(new BaseMedia.MediaBoarderNotifier() {
                @Override
                public void notifyMediaStatusChanged(MediaStatusResponseBean mediaStatusBean) {
                    mediaBoarder.update(mediaStatusBean);
                }
            });

            titleBar.setText(title);

            return rootView;
        }

        private void initView(View view) {
            et_keyword = view.findViewById(R.id.et_kw);
            btn_search = view.findViewById(R.id.search_btn);
            mediaBoarder = new MediaBoarder(view);
            titleBar = view.findViewById(R.id.section_label);
            list_rv = view.findViewById(R.id.rv);
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String keyword = et_keyword.getText().toString();
                    Log.e("search kw", keyword);
                    media.searchRequest(new SearchRequestBean(keyword));
                }
            });
        }

        public boolean lastPage(){
            Log.e("last page", "... ...");
            return media.goBack();
        }

        class MediaBoarder implements View.OnClickListener{
            TextView name_tv, cur_time_tv, total_time_tv;
            ImageView next_iv, last_iv, play_iv, stop_iv, mute_iv, low_iv, high_iv, touch_line_iv;
            SeekBar seekBar;

            Timer timer;
            TimerTask task;

            MediaBoarder(View view) {
                name_tv = view.findViewById(R.id.tv_name);
                cur_time_tv = view.findViewById(R.id.tv_cur_time);
                total_time_tv = view.findViewById(R.id.tv_total_time);
                next_iv = view.findViewById(R.id.iv_next);
                last_iv = view.findViewById(R.id.iv_last);
                play_iv = view.findViewById(R.id.iv_play);
                touch_line_iv = view.findViewById(R.id.iv_touch_line);
                seekBar = view.findViewById(R.id.seek_bar);
                stop_iv = view.findViewById(R.id.iv_stop);
                mute_iv = view.findViewById(R.id.iv_mute);
                low_iv = view.findViewById(R.id.vol_low);
                high_iv = view.findViewById(R.id.vol_high);
                seekBar.setEnabled(false);
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
                        media.mediaCtrlRequest(new PlayerRequestBean(PlayActionEnum.playSeek, progress));
                        timerTaskStart();
                    }
                });

                next_iv.setOnClickListener(this);
                last_iv.setOnClickListener(this);
                play_iv.setOnClickListener(this);
                stop_iv.setOnClickListener(this);
                mute_iv.setOnClickListener(this);
                low_iv.setOnClickListener(this);
                high_iv.setOnClickListener(this);
                touch_line_iv.setOnClickListener(this);
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

            public void update(MediaStatusResponseBean mediaStatus){
                Log.d("cur_time", "|" + mediaStatus.getCur_time());
                Log.d("total_time", "|" + mediaStatus.getTotal_time());
                cur_time_tv.setText(mediaStatus.getCur_time());
                total_time_tv.setText(mediaStatus.getTotal_time());

                seekBar.setMax((int) mediaStatus.getTimeAsSec(true));
                seekBar.setProgress((int) mediaStatus.getTimeAsSec(false));

                if (mediaStatus.getName().length() > 0) {
                    String name = mediaStatus.getName();
                    name_tv.setText(name);
                }
                if(mediaStatus.getStatus().equals("playing")){
                    play_iv.setImageResource(R.mipmap.icon_play);
                }else {
                    play_iv.setImageResource(R.mipmap.icon_pause);
                }
            }

            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.iv_play:
                        Log.e("play button",  "clicked ... ");
                        media.mediaCtrlRequest(new RequestBaseBean(PlayActionEnum.playCur));
                        break;
                    case R.id.iv_stop:
                        Log.e("stop button",  "clicked ... ");
                        media.mediaCtrlRequest(new RequestBaseBean(PlayActionEnum.playStop));
                        break;
                    case R.id.iv_next:
                        Log.e("next button",  "clicked ... ");
//                        media.mediaCtrlRequest(new RequestBaseBean(PlayActionEnum.));
                        break;
                    case R.id.iv_last:
                        Log.e("last button",  "clicked ... ");
                        break;
                    case R.id.iv_mute:
                        Log.e("mute button",  "clicked ... ");
                        media.mediaCtrlRequest(new PlayerRequestBean(PlayActionEnum.volMute, 0));
                        break;
                    case R.id.vol_low:
                        Log.e("volume low button",  "clicked ... ");
                        media.mediaCtrlRequest(new PlayerRequestBean(PlayActionEnum.volLow, -5));
                        break;
                    case R.id.vol_high:
                        Log.e("volume high button",  "clicked ... ");
                        media.mediaCtrlRequest(new PlayerRequestBean(PlayActionEnum.volHigh, 5));
                        break;
                    case R.id.iv_touch_line:
                        Log.e("touch line button",  "clicked ... ");
                        media.mediaCtrlRequest(new RequestBaseBean(PlayActionEnum.statusCheck));
                        seekBar.setEnabled(true);
                        timerTaskStart();
                        break;
                }
            }
        }
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
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}

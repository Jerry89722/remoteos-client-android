package com.example.zhang.remoteos.apps.media;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.zhang.remoteos.R;
import com.example.zhang.remoteos.adapters.ResourceAdapter;
import com.example.zhang.remoteos.beans.BaseTask;
import com.example.zhang.remoteos.beans.responsebean.MediaStatusResponseBean;
import com.example.zhang.remoteos.beans.ResourceBaseBean;
import com.example.zhang.remoteos.beans.requestbean.RequestBaseBean;
import com.example.zhang.remoteos.beans.responsebean.ResponseBaseBean;
import com.example.zhang.remoteos.utils.PlayActionEnum;
import com.example.zhang.remoteos.utils.okhttputils.CallBackUtil;
import com.example.zhang.remoteos.utils.okhttputils.OkhttpUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public abstract class BaseMedia {
    Activity activity;
    private ResourceAdapter adapter;
    List<ResourceBaseBean> mResources;
    private MediaBoarderNotifier mediaBoarderNotifier;
    RequestBaseBean requestBean;

    BaseMedia(Activity activity) {
        this.activity = activity;
        mResources = new ArrayList<>();
        adapter = new ResourceAdapter(activity, mResources);
        setEventListener();
    }

    BaseMedia(Activity activity, RequestBaseBean requestBaseBean) {
        this.activity = activity;
        mResources = new ArrayList<>();
        adapter = new ResourceAdapter(activity, mResources);
        this.requestBean = requestBaseBean;
        setEventListener();
    }

    /**
    *   用于列表中项的点击事件处理
    * */
    public abstract void itemClicked(ResourceBaseBean resourceBean);

    /**
     *   用于将获取到的数据处理为列表中的资源
     * */
    public abstract void resourceListHandle(ResponseBaseBean responseBean);

    /**
     *   用于将获取到的数据处理为媒体播放状态数据
     * */
    private MediaStatusResponseBean mediaStatusHandle(ResponseBaseBean responseBean){
        return (MediaStatusResponseBean) responseBean;
    }


    /**
     *   用于将获取到的数据处理为媒体播放状态数据
     * */
    public abstract void searchListHandle(ResponseBaseBean responseBean);

    /**
     *
     * @return flase退出, true返回上一页
     */
    public boolean goBack(){
        return false;
    }

    public void setMediaBoarderNotifier(MediaBoarderNotifier mediaBoarderNotifier){
        this.mediaBoarderNotifier = mediaBoarderNotifier;
    }
    /**
     *   用于通知媒体播放器面板状态
     * */
    public interface MediaBoarderNotifier{
        void notifyMediaStatusChanged(MediaStatusResponseBean mediaStatusBean);
    }

    /**
     *   设置列表中项的点击事件
     * */
    private void setEventListener() {
        adapter.setOnItemClickListener(new ResourceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {

                ResourceBaseBean resourceBean = mResources.get(i);
                Log.e("item clicked, position", "" + i);
                itemClicked(resourceBean);
            }
        });
    }

    /**
     *   用于请求应答信息的解析
     *   @param clazz 必须是ResponseBaseBean的子类
     * */
    private <T> ResponseBaseBean responseParse(String response, Class<T> clazz){
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(response);
        }catch (JSONException ex){
            return null;
        }

        return (ResponseBaseBean) JSON.toJavaObject(jsonObject, clazz);
    }

    /**
     *  用于搜索请求
     * @param requestBean 无
     */
    public abstract void searchRequest(RequestBaseBean requestBean);

    /**
     *  用于列表获取请求
     * @param requestBean 无
     */
    public void listRequest(RequestBaseBean requestBean) {
        if(requestBean == null && this.requestBean == null){
            Log.e("list request", "request bean is null");
            return;
        }

        if(requestBean != null) {
            this.requestBean = requestBean;
        }
        mediaRequest(this.requestBean, "explorer", "video");
    }

    /**
     *  用于播放器操作请求
     * @param requestBean 无
     */
    public void mediaCtrlRequest(RequestBaseBean requestBean){
        mediaRequest(requestBean, "media");
    }

    /**
     *  用于播放状态获取
     */
    public void playerStatusRequest(){
        RequestBaseBean requestBean = new RequestBaseBean(PlayActionEnum.statusCheck);
        mediaRequest(requestBean, "media");
    }

    /**
     *  用于将点击列表中获取到的资源转换成request请求的数据
     * @param resourceBaseBean 要转换的数据
     * @return  转换后的数据
     */
    RequestBaseBean resourceToRequest(ResourceBaseBean resourceBaseBean) {
        return new RequestBaseBean(resourceBaseBean.getType(), resourceBaseBean.getFingerprint());
    }

    public ResourceAdapter getAdapter() {
        return adapter;
    }

    private void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }

    private String httpPathGet(String... args) {
        String base_url = activity.getString(R.string.base_url);
        StringBuilder paramStr = new StringBuilder();
        for (int i = 0; i < args.length; ++i){
            paramStr.append(args[i]);
            if(i < args.length - 1){
                paramStr.append("/");
            }
        }
        return String.format(base_url, "toshiba", paramStr);
    }

    void mediaRequest(final RequestBaseBean requestBean, String... args){

        final TaskBean task = new TaskBean(requestBean.getAction(), requestBean);
        String url = task.toString();
        try {
            url = new String(url.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String httpPath = httpPathGet(args);

        url = httpPath + url;
        Log.e("request url", url);

        OkhttpUtil.okHttpGet(url, null, null, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Log.e("failed", "http request failed");
            }

            @Override
            public void onResponse(String response) {
                task.responseHandle(response);
            }
        });
    }

    private class TaskBean extends BaseTask {
        TaskBean(PlayActionEnum actionEnum, RequestBaseBean requestBean) {
            super(actionEnum, requestBean);
        }

        @Override
        public <T> void listResponseHandle(String response, Class<T> clazz) {
            Log.d("list load response", response);
            ResponseBaseBean responseBean = responseParse(response, clazz);
            resourceListHandle(responseBean);
            notifyDataSetChanged();
        }

        @Override
        public void mediaStatusResponseHandle(String response){
            Log.d("media response", response);
            ResponseBaseBean responseBean = responseParse(response, MediaStatusResponseBean.class);
            MediaStatusResponseBean mediaStatusBean = mediaStatusHandle(responseBean);
            mediaBoarderNotifier.notifyMediaStatusChanged(mediaStatusBean);
        }

        @Override
        public <T> void searchResponseHandle(String response, Class<T> clazz) {
            Log.d("search response", response);
            ResponseBaseBean responseBean = responseParse(response, clazz);
            searchListHandle(responseBean);
            notifyDataSetChanged();
        }
    }
}

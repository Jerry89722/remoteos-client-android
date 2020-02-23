package com.example.zhang.remoteos.utils.okhttputils;

import com.example.zhang.remoteos.utils.ContextUtil;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;

/**
 * @author dmrfcoder
 * @date 2018/8/28
 */

public class OkManager {
    public OkHttpClient getClient() {
        return client;
    }

    private OkHttpClient client;
    private volatile static OkManager okManager;
    private final String TAG = OkManager.class.getSimpleName();

    OkHttpClient.Builder httpBuilder;

    public OkManager() {
        //需要设置请求超时调用下面两行
        httpBuilder = new OkHttpClient.Builder();

        client = httpBuilder.readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS) //设置超时
                .cookieJar(new CookieJarImpl(new SPCookieStore(ContextUtil.getInstance())))
                .build();
        //将上面的CashApplication换成你自己的Application或者传入同样性质的参数
    }

    /**
     * 获取全局的cookie实例
     */
    public CookieJarImpl getCookieJar() {
        return (CookieJarImpl) client.cookieJar();
    }

    /**
     * 采用单例获取对象
     *
     * @return
     */
    public static OkManager getInstance() {
        if (okManager == null) {
            synchronized (OkManager.class) {
                if (okManager == null) {
                    okManager = new OkManager();
                }
            }
        }
        return okManager;
    }
}

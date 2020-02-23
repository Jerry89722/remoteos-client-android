package com.example.zhang.remoteos.utils.okhttputils;/*
实现cookieJar接口，包含一个管理cookie的公共接口CookieStore
*/

import java.net.CookieStore;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
* @author dmrfcoder
* @date 2018/8/28
*/

public class CookieJarImpl implements CookieJar {


  public interface CookieStore {

      /**
       * 保存url对应所有cookie
       */
      void saveCookie(HttpUrl url, List<Cookie> cookie);

      /**
       * 保存url对应所有cookie
       */
      void saveCookie(HttpUrl url, Cookie cookie);

      /**
       * 加载url所有的cookie
       */
      List<Cookie> loadCookie(HttpUrl url);

      /**
       * 获取当前所有保存的cookie
       */
      List<Cookie> getAllCookie();

      /**
       * 获取当前url对应的所有的cookie
       */
      List<Cookie> getCookie(HttpUrl url);

      /**
       * 根据url和cookie移除对应的cookie
       */
      boolean removeCookie(HttpUrl url, Cookie cookie);

      /**
       * 根据url移除所有的cookie
       */
      boolean removeCookie(HttpUrl url);

      /**
       * 移除所有的cookie
       */
      boolean removeAllCookie();
  }


  private CookieStore cookieStore;

  public CookieJarImpl(CookieStore cookieStore) {
      if (cookieStore == null) {
          throw new IllegalArgumentException("cookieStore can not be null!");
      }
      this.cookieStore = cookieStore;
  }

  @Override
  public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
      cookieStore.saveCookie(url, cookies);
  }

  @Override
  public synchronized List<Cookie> loadForRequest(HttpUrl url) {
      return cookieStore.loadCookie(url);
  }

  public CookieStore getCookieStore() {
      return cookieStore;
  }
}


package cn.chestnut.mvvm.teamworker.http;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.chestnut.mvvm.teamworker.utils.PreferenceUtil;

/**
 * Copyright (c) 2017, Chestnut All rights reserved
 * Author: Chestnut
 * CreateTime：at 2017/12/10 20:49:36
 * Description：数据请求管理类
 * Email: xiaoting233zhang@126.com
 */

public class RequestManager {
    static RequestManager requestManager;
    private Context mContext;
    private Gson gson = new Gson();

    public static RequestManager getInstance(Context mContext) {
        if (requestManager == null) {
            requestManager = new RequestManager();
        }
        requestManager.mContext = mContext;
        return requestManager;
    }

    public static void clearActivity() {
        if (requestManager != null && requestManager.mContext != null) {
            requestManager.mContext = null;
        }
    }

    /**
     * @param api
     * @param params
     * @param <T>
     * @return
     */
    public <T> void executeRequest(final String api, Map<String, Object> params, final AppCallBack<ApiResponse<T>> callBack) {
        Map<String, String> headers = new HashMap<>();
        headers.put("uid", PreferenceUtil.getInstances(mContext).getPreferenceString("userId"));
        headers.put("token", PreferenceUtil.getInstances(mContext).getPreferenceString("token"));
        RQ.post(HttpUrls.getUrls(api), headers, gson.toJson(params), callBack);
//        RQ.post(HttpUrls.getUrls(api), gson.toJson(params), callBack);
    }

    /**
     * @param api
     * @param params
     * @param <T>
     * @return
     */
    public <T> void executeRequest(final String api, Object params, final AppCallBack<ApiResponse<T>> callBack) {
        Map<String, String> headers = new HashMap<>();
        headers.put("uid", PreferenceUtil.getInstances(mContext).getPreferenceString("userId"));
        headers.put("token", PreferenceUtil.getInstances(mContext).getPreferenceString("token"));
        RQ.post(HttpUrls.getUrls(api), headers, gson.toJson(params), callBack);
//        RQ.post(HttpUrls.getUrls(api), gson.toJson(params), callBack);
    }

}

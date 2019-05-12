package com.learning.tomato.until.netUtil;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.OutputKeys;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: OKHttp封装
 * @date 2019/5/8 16:50
 */

public class OkManager {
    private OkHttpClient client;
    private volatile static OkManager manager;
    private Handler handler;
    private static final String TAG=OkManager.class.getSimpleName();

    private OkManager(){
        client=new OkHttpClient();
        handler=new Handler(Looper.getMainLooper());
    }

    /**
     * 单例模式的到OkManager对象
     * @return
     */
    public static OkManager getInstance(){
        if(manager==null){
            synchronized (OkManager.class){
                if(manager==null){
                    manager=new OkManager();
                }
            }
        }
        return manager;
    }
//    public void syncJsonObjectByRequest(String url,RequestBody requestBody,final Func1 callBack){
//        Request request=new Request.Builder().url(url).post(requestBody).build();
//        Response response=null;
//        try {
//            response=client.newCall(request).execute();
//            if(response.isSuccessful()){
//                onSuccessJsonObjectMethod(response.body().string(),callBack);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * 异步请求
     * @param url
     * @param map
     * @param callBack
     */
    public void asynJsonObjectByRequest(String url, final Map<String, String> map, final Func1 callBack){
        FormBody.Builder responseBodyBuilder=new FormBody.Builder();
        if(map!=null&&!map.isEmpty()){
            for(Map.Entry<String,String> entry:map.entrySet()){
                responseBodyBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody requestBody=responseBodyBuilder.build();
        final Request request=new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"okhttp 网络异常"+e.getMessage());
                String userid=map.get("userid");
                map.clear();
                map.put("userid",userid);
                Log.e(TAG,"当前只有userid="+userid);
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response!=null&&response.isSuccessful()){
                    onSuccessJsonObjectMethod(response.body().string(),callBack);
                }
            }
        });
    }
    /***
     * 返回结果是一个json对象
     * @param jsonValue
     * @param callBack
     */
    private void onSuccessJsonObjectMethod(final String jsonValue,final Func1 callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(callBack!=null){
                    callBack.onResponse(jsonValue);
                }
            }
        });
    }
    public interface Func1{
        void onResponse(String result);
    }
}

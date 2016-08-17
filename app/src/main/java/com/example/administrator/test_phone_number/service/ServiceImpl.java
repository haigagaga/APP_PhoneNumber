package com.example.administrator.test_phone_number.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.test_phone_number.MainActivity;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/13.
 */

public class ServiceImpl implements ServiceInter{
    private Context mContext;




    @Override
    public void phoneNumber(String number) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        //http://apis.juhe.cn/mobile/get?phone=13429667914&key=7db320dc0b22daddaea37b445f5103d4
        String url = "http://apis.juhe.cn/mobile/get?phone=" + number + "&key=7db320dc0b22daddaea37b445f5103d4";
        System.out.println(url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.context, "查询失败", Toast.LENGTH_SHORT).show();
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(jsonObjectRequest);
    }
}

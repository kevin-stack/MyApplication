package com.tw.homework;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tw.beans.UserBean;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView)findViewById(R.id.iv);

        RequestQueue mQueue = Volley.newRequestQueue(this);
        ImageRequest request = new ImageRequest("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRDy7HZaHxn15wWj6pXE4uMKAqHTC_uBgBlIzeeQSj2QaGgUzUmHg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        iv.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("volley",error.toString());
                    }
        });

        StringRequest strRequest = new StringRequest("http://thoughtworks-ios.herokuapp.com/user/jsmith", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("volley",response);
                transJsonToObject(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley",error.toString());
            }
        });
        mQueue.add(request);
        mQueue.add(strRequest);
    }

    public UserBean transJsonToObject(String json){
        Gson gson = new Gson();

        UserBean user = gson.fromJson(json,UserBean.class);

        return user;
    }
}

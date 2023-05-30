package com.example.project.api;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.project.R;
import com.example.project.adapter.BannerAdapter;
import com.example.project.event.InitHomeContent;
import com.example.project.event.OnClickListener;
import com.example.project.event.SetURLStream;
import com.example.project.model.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SongAPI{
    private static OnClickListener o;
    private static ArrayList<Subject> rs = new ArrayList<>();


    private static final String API_URL = "  https://6e2b-2402-800-63a6-f45c-6c5e-ea77-c06e-bc22.ngrok-free.app/api";

    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();


    public static void setO(OnClickListener o) {
        SongAPI.o = o;
    }

    public static ArrayList<Subject> searchSongByName(String name) throws IOException, JSONException {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(API_URL+"/search").newBuilder();
        httpBuilder.addQueryParameter("name",name);
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                System.out.println("createOrder: " + result);
                JSONArray songs = null;
                ArrayList<Subject> subjects = new ArrayList<>();
                try {
                    songs = new JSONArray(result);
                    for (int i = 0; i < songs.length(); i++)
                    {
                        String id = songs.getJSONObject(i).getString("id");
                        String title = songs.getJSONObject(i).getString("title");
                        String artistsNames = songs.getJSONObject(i).getString("artistsNames");
                        String img = songs.getJSONObject(i).getString("thumbnailM");
                        String url = songs.getJSONObject(i).getString("linkStream");
                        Subject subject = new Subject(id, title, artistsNames, img, url);
                        subjects.add(subject);
                    }

                o.setData(subjects);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return rs;
    }
    public static void initBanner(ArrayList<String> images, View view){
        ViewPager viewPager;
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        BannerAdapter viewPagerAdapter = new BannerAdapter(view.getContext(), images);
        viewPager.setAdapter(viewPagerAdapter);
    }
    public static void getHome(InitHomeContent callback){
        HttpUrl.Builder httpBuilder = HttpUrl.parse(API_URL+"/").newBuilder();
        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject res = new JSONObject(result);
                    JSONObject data = res.getJSONObject("data");
                    ArrayList<String> banners = new ArrayList<>();
                    JSONArray items = data.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject tmp = items.getJSONObject(i);

                        if(tmp.getString("sectionType").contains("banner")){
                            JSONArray arrBanner = tmp.getJSONArray("items");

                            for (int j = 0; j <arrBanner.length(); j++) {
                                banners.add(arrBanner.getJSONObject(j).getString("banner"));

                            }
                            System.out.println(arrBanner.length() + "THanh phan");
                        }else{
                            if(tmp.getString("sectionType").contains("new-release")){
                                JSONObject arr = tmp.getJSONObject("items");
                                JSONArray allSong = arr.getJSONArray("all");

                                for (int j = 0; j <allSong.length(); j++) {
                                    banners.add(allSong.getJSONObject(j).getString("banner"));

                                }
                            }
                        }
                    }
                    callback.init(banners);

                    System.out.println(items.length());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
    public static void main(String[] args) throws IOException {
    }

}

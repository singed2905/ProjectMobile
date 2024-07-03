package com.example.project.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.project.R;
import com.example.project.cache.SongCache;
import com.example.project.model.Playlist;
import com.example.project.model.Subject;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class  ProcessBar extends Service {
    MediaPlayer mediaPlayer;
    public static final String ACTION_CHANGE_PROGRESS="ACTION_CHANGE_PROGRESS";
    private static final String SHARED_PREFS_KEY = "MyPreferences";
    private static final String CHECK_VALUE_KEY = "checkValue";
    private static Handler handler;
    public static String url = "";
    public static boolean check=false;

    public static void setURL(Context context, String linkStream,String id) throws JSONException {
        SongCache.addMusic(context, id);
        url = linkStream;
    }
    public ProcessBar() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(url));

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.createHandle();
        this.registerBroadcastReceiver();

    }

    public float getCurrentPositionPercent() {
        if (mediaPlayer != null) {
            return ((float) mediaPlayer.getCurrentPosition()) / mediaPlayer.getDuration();
        }
        return 0.0f;
    }

    private void createHandle() {
        handler = new Handler();
        handler.postDelayed(createRunnable(), 1000);
    }
    public Runnable createRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(ACTION_CHANGE_PROGRESS);
                broadcastIntent.putExtra("CURRENT_POSITION_PERCENT", getCurrentPositionPercent());
                broadcastIntent.putExtra("CURRENT_POSITION_MS", (float) mediaPlayer.getCurrentPosition());
                broadcastIntent.putExtra("CURRENT_POSITION_ENDTIME", (float) mediaPlayer.getDuration());
                sendBroadcast(broadcastIntent);
                handler.postDelayed(this, 1000);
            }
        };
    }

    private BroadcastReceiver seekBarReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("SeekBarValueChanged")) {
                int seekBarValue = intent.getIntExtra("seekBarValue", 0);
                setMediaWhenSBChange(seekBarValue);
            }
        }
    };
    private void setMediaWhenSBChange(int percent) {
        mediaPlayer.seekTo((int) (((percent) / (double) 100.0)*mediaPlayer.getDuration()));
    }
    public void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter("SeekBarValueChanged");
        registerReceiver(seekBarReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer.isPlaying()) {
            handler.removeCallbacksAndMessages(null);
            mediaPlayer.pause();
        } else {
            handler.post(createRunnable());
            mediaPlayer.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        mediaPlayer.stop();
        mediaPlayer.release();
        unregisterReceiver(seekBarReceiver);
        super.onDestroy();
    }
}

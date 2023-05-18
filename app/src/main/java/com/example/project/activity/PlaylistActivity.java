package com.example.project.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.project.R;
import com.example.project.service.ProcessBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;

public class PlaylistActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private static final int NOTIFICATION_ID = 0;
    private static final String ACTION_PLAY = "ACTION_PLAY";
    private static final String ACTION_PAUSE = "ACTION_PAUSE";
    private FloatingActionButton btn_back;
    private FloatingActionButton btnPlay;
    private FloatingActionButton btnPause;
    private FloatingActionButton btnReplay;
    private Intent serviceIntent;
    private SeekBar processBar;
    private TextView endTime;
    private TextView runTime;
    boolean check = false;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ProcessBar.ACTION_CHANGE_PROGRESS)) {
                float currentPositionPercent = intent.getFloatExtra("CURRENT_POSITION_PERCENT", 0.0f);
                int currentPositionMs = (int) intent.getFloatExtra("CURRENT_POSITION_MS", 0);
                int currentPositionEndTime = (int) intent.getFloatExtra("CURRENT_POSITION_ENDTIME", 0);
                if (currentPositionMs == currentPositionEndTime) {
                    stopService(serviceIntent);
                } else {
                    processBar.setProgress((int) (currentPositionPercent * processBar.getMax()));
                    runTime.setText(setTime(currentPositionMs));
                    endTime.setText(setTime(currentPositionEndTime));
                }
            } else {
                if (intent.getAction().equals(ACTION_PLAY)) {
                    Intent serviceIntent = new Intent(context, ProcessBar.class);
                    context.startService(serviceIntent);
                } else {
                    if (intent.getAction().equals(ACTION_PAUSE)) {
                        Intent stopServiceIntent = new Intent(context, ProcessBar.class);
                        context.stopService(stopServiceIntent);
                    }
                }

            }
        }
    };
    private void createNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Intent playIntent = new Intent(ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(ACTION_PAUSE);
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.icon_seek_bar);
        builder.setContentTitle("My Music");
        builder.setContentText("Playing music...");
        builder.addAction(R.drawable.baseline_play_arrow_24, "Play", playPendingIntent);
        builder.addAction(R.drawable.baseline_pause_24, "Pause", pausePendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        this.init();
        this.setImgProcessBar();
        this.addEventBtnBack();
        this.addEventPlay();
        this.addEventReplay();
        this.addEventSeekBar();
        this.registerBroadcastReceiver();
    }

    public void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ProcessBar.ACTION_CHANGE_PROGRESS);
        filter.addAction(ACTION_PLAY);
        filter.addAction(ACTION_PAUSE);
        registerReceiver(broadcastReceiver, filter);
    }
    private void addEventSeekBar() {
        processBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekBarValue = seekBar.getProgress();
                Intent intent = new Intent("SeekBarValueChanged");
                intent.putExtra("seekBarValue", seekBarValue);
                sendBroadcast(intent);
            }
        });
    }

    public void addEventBtnBack() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addEventPlay() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check) {
                    btnPlay.setImageResource(R.drawable.baseline_pause_24);
                    check = true;
                } else {
                    btnPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                    check = false;
                }
                if (notificationManager == null) {
                    createNotification();
                }
                serviceIntent = new Intent(getApplicationContext(), ProcessBar.class);
                startService(serviceIntent);
            }
        });
    }


    public void addEventReplay() {
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceIntent != null && broadcastReceiver != null) {
                    getApplicationContext().stopService(serviceIntent);
                    serviceIntent = new Intent(getApplicationContext(), ProcessBar.class);
                    startService(serviceIntent);
                }
            }
        });

    }

    public String setTime(int currentPositionMs) {
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(currentPositionMs),
                TimeUnit.MILLISECONDS.toSeconds(currentPositionMs) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPositionMs))
        );
        return time;
    }

    public void init() {
        btn_back = this.findViewById(R.id.fab);
        btnPlay = findViewById(R.id.play);
        btnPause = findViewById(R.id.previous);
        processBar = findViewById(R.id.seek_bar);
        runTime = findViewById(R.id.run_time);
        endTime = findViewById(R.id.time_end);
        btnReplay = findViewById(R.id.replay);

    }

    public void setImgProcessBar() {
        Glide.with(this)
                .as(GifDrawable.class)
                .load(R.drawable.icon_seek_bar)
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super GifDrawable> transition) {
                        processBar.setThumb(resource);
                    }
                });
    }
}
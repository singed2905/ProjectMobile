package com.example.project.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Matrix;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.project.R;
import com.example.project.cache.SongCache;
import com.example.project.model.Subject;
import com.example.project.service.ProcessBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Random;
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
    private FloatingActionButton btnPrevious;
    private FloatingActionButton btnReplay;
    private FloatingActionButton btnRandom;
    private FloatingActionButton btnNext;
    public static Intent serviceIntent;
    private SeekBar processBar;
    private TextView endTime;
    private TextView nameAstist;
    private TextView titleSong;
    private TextView titleTopSong;
    private ImageView imgView;
    private TextView runTime;
    private String idSong;
    private ArrayList<Subject> subjectList;
    private int position = 0;
    private Subject subject;

    private boolean isRandom = false;
    private int range = 0;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ProcessBar.ACTION_CHANGE_PROGRESS)) {
                float currentPositionPercent = intent.getFloatExtra("CURRENT_POSITION_PERCENT", 0.0f);
                int currentPositionMs = (int) intent.getFloatExtra("CURRENT_POSITION_MS", 0);
                int currentPositionEndTime = (int) intent.getFloatExtra("CURRENT_POSITION_ENDTIME", 0);
                if (currentPositionMs == currentPositionEndTime) {
                    if (!isRandom) {
                        try {
                            changeSong(1);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            changeSong(2);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }


                } else {
                    processBar.setProgress((int) (currentPositionPercent * processBar.getMax()));
                    runTime.setText(setTime(currentPositionMs));
                    endTime.setText(setTime(currentPositionEndTime));
                }
            } else {
                if (intent.getAction().equals(ACTION_PLAY)) {
                    serviceIntent = new Intent(context, ProcessBar.class);
                    context.startService(serviceIntent);
                } else {
                    if (intent.getAction().equals(ACTION_PAUSE)) {
                        serviceIntent = new Intent(context, ProcessBar.class);
                        context.stopService(serviceIntent);
                    }
                }

            }
            rotateImg();
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
        serviceIntent = new Intent(this, ProcessBar.class);
        stopService(serviceIntent);
        try {
            this.init();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        this.setImgProcessBar();
        this.addEventBtnBack();
        this.addEventPlay();
        this.addEventReplay();
        this.addEventSeekBar();
        this.registerBroadcastReceiver();
        this.addEventNextSong();
        this.addEventPrevious();
        this.addEventRandomSong();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!ProcessBar.check) {
            ProcessBar.check = true;
            btnPlay.setImageResource(R.drawable.baseline_pause_24);
        } else {
            ProcessBar.check = false;
            btnPlay.setImageResource(R.drawable.baseline_play_arrow_24);
        }
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

    public void rotateImg() {
        this.range += 1;
        imgView.setRotation(this.range);
    }

    public void addEventBtnBack() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaylistActivity.this, MainActivity.class);
                intent.putExtra("artist", subject.getArtist());
                intent.putExtra("name", subject.getName());
                startActivity(intent);
            }
        });
    }


    public void addEventPlay() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ProcessBar.check) {
                    ProcessBar.check = true;
                    btnPlay.setImageResource(R.drawable.baseline_pause_24);
                } else {
                    ProcessBar.check = false;
                    btnPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                }
                if (notificationManager == null) {
                    createNotification();
                }
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

    public void init() throws JSONException {
        imgView = findViewById(R.id.imageView);
        btn_back = this.findViewById(R.id.fab);
        btnPlay = findViewById(R.id.play);
        btnPrevious = findViewById(R.id.previous);
        processBar = findViewById(R.id.seek_bar);
        runTime = findViewById(R.id.run_time);
        endTime = findViewById(R.id.time_end);
        btnReplay = findViewById(R.id.replay);
        nameAstist = findViewById(R.id.nameAstist);
        titleSong = findViewById(R.id.name);
        titleTopSong = findViewById(R.id.textView2);
        btnNext = findViewById(R.id.next);
        btnRandom = findViewById(R.id.random);
        Bundle extras = getIntent().getExtras();
        subjectList = extras.getParcelableArrayList("subjectList");
        if (extras != null) {
            subject = new Subject(extras.getString("id"), extras.getString("title"), extras.getString("nameAstist"), extras.getString("img"), extras.getString("url"));
            setDisplay(subject, extras.getInt("position"), false);
        }
    }

    private void changeSong(int type) throws JSONException {
        stopService(serviceIntent);
        serviceIntent = new Intent(getApplicationContext(), ProcessBar.class);
        if (type == 1) {
            if (position + 1 < subjectList.size()) {
                subject = subjectList.get(position + 1);
                position = position + 1;
                setDisplay(subject, position,true);
            }
        } else {
            if (type == 0) {

                if (position - 1 >= 0) {
                    subject = subjectList.get(position - 1);
                    position = position - 1;
                    setDisplay(subject, position,true);
                }
            } else {
                Random random = new Random();
                int randomNumber = random.nextInt(subjectList.size() - 1 - 2 + 1) + 2;
                subject = subjectList.get(randomNumber);
                position = randomNumber;
                setDisplay(subject, position,true);
            }

        }
        startService(serviceIntent);
    }

    public void addEventNextSong() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    changeSong(1);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void addEventPrevious() {
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    changeSong(0);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void addEventRandomSong() {
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRandom = !isRandom;
                Drawable drawable = btnRandom.getDrawable();
                if (isRandom) {
                    btnRandom.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                    drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                } else {
                    btnRandom.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

                }
            }
        });

    }

    public void setDisplay(Subject subject, int positio1n, boolean isSetUrl) throws JSONException {
        idSong = subject.getId();
        nameAstist.setText(subject.getArtist());
        titleTopSong.setText(subject.getName());
        titleSong.setText(subject.getName());
        position = positio1n;
        Picasso.get().load(subject.getSrc()).into(imgView);
        if (isSetUrl) {
            ProcessBar.setURL(getApplicationContext(), subject.getUrl(), subject.getId());

        }
        this.range=0;
        imgView.setRotation(0);
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
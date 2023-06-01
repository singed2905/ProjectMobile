package com.example.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.fragment.HomeFragment;
import com.example.project.fragment.HistoryFragment;
import com.example.project.fragment.Login;
import com.example.project.service.ProcessBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton btnPlay;
    private Intent serviceIntent;
    LinearLayout miniControl;
    TextView textArtist;
    TextView textName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
        this.addEventTab();
        this.addEventPlaySong();
        this.showFragHome();
        this.checkShowMiniControl();
        this.addEventKillThis();
        this.setTextFMiniControl();
        if(PlaylistActivity.serviceIntent!=null){
            if (ProcessBar.check) {
                btnPlay.setImageResource(R.drawable.baseline_pause_24);
            } else {
                btnPlay.setImageResource(R.drawable.baseline_play_arrow_24);
            }
        }
    }

    public void showFragHome() {
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).addToBackStack(null).commit();
    }

    public void addEventTab() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).addToBackStack(null).commit();
                        System.out.println("Home");
                        return true;

                    case R.id.lib:
                        HistoryFragment libFragment = new HistoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, libFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.profile:
                        Login loginFragment = new Login();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).addToBackStack(null).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public void checkShowMiniControl() {
        if (!ProcessBar.url.equals("")) {
            miniControl.setVisibility(View.VISIBLE);
        }


    }
    public void setTextFMiniControl(){
        Intent intent = getIntent();
        if(intent!=null){
            String artist = intent.getStringExtra("artist");
            String name = intent.getStringExtra("name");
            textArtist.setText(artist);
            textName.setText(name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.checkShowMiniControl();
    }

    public void addEventKillThis() {
        miniControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void addEventPlaySong() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceIntent = PlaylistActivity.serviceIntent;
                if (!ProcessBar.check) {
                    ProcessBar.check=true;
                    btnPlay.setImageResource(R.drawable.baseline_pause_24);
                } else {
                    ProcessBar.check=false;
                    btnPlay.setImageResource(R.drawable.baseline_play_arrow_24);
                }
                startService(serviceIntent);

            }
        });

    }

    public void init() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btnPlay = findViewById(R.id.play);;
        miniControl = findViewById(R.id.mini_control);
        textArtist=findViewById(R.id.artist);
        textName=findViewById(R.id.name);

    }

}
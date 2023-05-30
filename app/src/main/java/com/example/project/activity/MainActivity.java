package com.example.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.project.R;
import com.example.project.adapter.AdapterFavourites;
import com.example.project.adapter.AdapterRecently;
import com.example.project.fragment.HomeFragment;
import com.example.project.fragment.Lib;
import com.example.project.model.Subject;
import com.example.project.service.ProcessBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
        this.addEventTab();
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).addToBackStack(null).commit();

    }
    public void addEventTab(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).addToBackStack(null).commit();
                        System.out.println("Home");
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.lib:
                        Lib libFragment = new Lib();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, libFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });
    }

    public void init(){
        bottomNavigationView=findViewById(R.id.bottom_navigation);

    };


}
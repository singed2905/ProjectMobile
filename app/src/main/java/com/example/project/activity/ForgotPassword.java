package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.api.LoginAPI;
import com.example.project.cache.UserCache;
import com.example.project.fragment.Login;

public class ForgotPassword extends AppCompatActivity {
    LinearLayout linearLayout;
    EditText input;
    Button button;
    TextView back_login_in_forgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        this.init();
        this.addAction();
    }

    public void init() {
        linearLayout = findViewById(R.id.container_forgot);
        input = findViewById(R.id.text_forgot);
        button = findViewById(R.id.sendMail);
        back_login_in_forgot = findViewById(R.id.back_login_in_forgot);
    }
    public void addAction() {
        back_login_in_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = input.getText().toString();
                LoginAPI.forgotPassword(username, getApplicationContext());
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String notify = UserCache.getNotifyForgot(getApplicationContext());
                Log.e("notify", notify);
                if (notify.equals("Vui lòng kiểm tra thông tin đăng nhập được gửi về email")) {
                    finish();
                    Login.check=true;
                } else{
                    Toast.makeText(getApplicationContext(), notify, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
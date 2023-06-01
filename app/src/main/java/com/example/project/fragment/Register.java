package com.example.project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.api.LoginAPI;
import com.example.project.cache.UserCache;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    LinearLayout linearLayout1;
    LinearLayout linearLayout2;

    TextView go_to_sign_in;
    Button signUp;
    TextView textUser;
    TextView textPass;
    TextView textEmail;
    View view;

    public Register() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        this.init();
        this.addAction();
        return view;
    }
    private void init() {
        linearLayout1 = view.findViewById(R.id.container_login);
        linearLayout2 = view.findViewById(R.id.container_goToSignup);
        go_to_sign_in = view.findViewById(R.id.go_to_sign_in);
        signUp = view.findViewById(R.id.Sign_Up);
        textUser = view.findViewById(R.id.editTextText_register);
        textPass = view.findViewById(R.id.editTextTextPassword_register);
        textEmail = view.findViewById(R.id.editTextTextEmailAddress_register);
    }
    public void addAction() {
        go_to_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login loginFragment = new Login();
                getFragmentManager().beginTransaction().replace(R.id.container, loginFragment).addToBackStack(null).commit();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUser.getText().toString();
                String password = textPass.getText().toString();
                String email = textEmail.getText().toString();
                LoginAPI.register(username,password,email,getContext());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String userData = UserCache.checkRegister(getContext());
                Log.e("result", userData);
                if (username.length() < 1 || password.length() < 1 || email.length() < 1) {
                    Toast.makeText(getActivity(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (!userData.equals("")) {
                        Login login = new Login();
                        getFragmentManager().beginTransaction().replace(R.id.container, login).addToBackStack(null).commit();
                        Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

}
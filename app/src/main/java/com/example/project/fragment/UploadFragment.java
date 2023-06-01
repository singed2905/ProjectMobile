package com.example.project.fragment;


import static android.app.Activity.RESULT_OK;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.api.LoginAPI;
import com.example.project.cache.UserCache;
import com.example.project.event.CallbackAPI;
import com.example.project.model.FileUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int FILE_REQUEST_CODE = 1;

    private Button btnChooseFile;
    private Button btnChooseImg;

    private Button btnUpload;
    private View view;

    private Uri selectedFilePath;
    private Uri selectedFilePathImg;
    private File fileAudio;
    private File fileImage;
    public UploadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadFragment newInstance(String param1, String param2) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload, container, false);

        btnChooseFile = view.findViewById(R.id.btn_select);
        btnChooseImg = view.findViewById(R.id.btn_select_img);
        btnUpload = view.findViewById(R.id.btn_upload);
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser("audio/*", 1);
            }
        });
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser("image/*", 0);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFilePath != null && selectedFilePathImg != null) {
                    uploadFile();
                } else {
                    Toast.makeText(getActivity(), "Vui lòng chọn một file audio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    private void openFileChooser(String type, int isAudio) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        startActivityForResult(intent, isAudio);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedFilePath = data.getData();
            String audioFilePath = FileUtils.getPath(this.getContext(), selectedFilePath);

            fileAudio = new File(audioFilePath);
            System.out.println(selectedFilePath);

        }
        if (requestCode == 0 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedFilePathImg = data.getData();
            String audioFilePath = FileUtils.getPath(this.getContext(), selectedFilePathImg);
            fileImage = new File(audioFilePath);
            System.out.println(selectedFilePathImg);

        }
    }

    private void uploadFile() {
        String auth = UserCache.getToken(getContext());
        if(auth != null && !auth.equals("")){
            TextView tTitle = (TextView)view.findViewById(R.id.editText_Title);
           String title =  tTitle.getText().toString();
            TextView tAstist = (TextView)view.findViewById(R.id.editText_Astris);
            String atist =  tAstist.getText().toString();
            System.out.println("----  :" + title + "_"+ atist + "_" + selectedFilePath + "_" + selectedFilePathImg);
            LoginAPI.uploadFile(fileAudio,fileImage , title, atist, view.getContext(), new CallbackAPI() {
                @Override
                public <T> void callback(T data) {
                    TextView tTitle = (TextView)view.findViewById(R.id.editText_Title);
                    tTitle.setText("");
                    TextView tAstist = (TextView)view.findViewById(R.id.editText_Astris);
                    tAstist.setText("");
                    fileAudio = null;
                    fileImage = null;
                    selectedFilePath = null;
                    selectedFilePathImg = null;
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }else{
           Toast.makeText(getActivity(), "Vui lòng đăng nhập để thực hiện chức năng", Toast.LENGTH_SHORT).show();

        }


    }


}
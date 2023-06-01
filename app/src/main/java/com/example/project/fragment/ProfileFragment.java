package com.example.project.fragment;

import static android.app.Activity.RESULT_OK;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.cache.UserCache;

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
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final int FILE_REQUEST_CODE = 1;

    private Button btnChooseFile;
    private Button btnUpload;
    private TextView tvUser;

    private String selectedFilePath;
    private OkHttpClient client;
    private SharedPreferences sharedPreferences;
    private String accessToken;
    private UserCache ucache;
    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        tvUser = view.findViewById(R.id.tv_username);
        client = new OkHttpClient();
        ucache  =new UserCache(getContext());


        fetchUsername();

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFilePath != null) {
                    uploadFile(selectedFilePath);
                } else {
                    Toast.makeText(getActivity(), "Vui lòng chọn một file audio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }



    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, FILE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedFileUri = data.getData();
            selectedFilePath = getPathFromUri(selectedFileUri);
        }
    }

    private String getPathFromUri(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

    private void uploadFile(String filePath) {
        File file = new File(filePath);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("audio/*"), file))
                .build();

        Request request = new Request.Builder()
                .url("YOUR_API_URL")
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                // Xử lý dữ liệu phản hồi từ API (nếu cần)
            } else {
                // Xử lý lỗi (nếu cần)
                Toast.makeText(getActivity(), "Vui lòng chọn một file audio", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void fetchUsername() {
        // Tạo request để gửi đến API với access token
        accessToken = ucache.getToken(getContext());
        Request request = new Request.Builder()
                .url("https://backend-clone-zing-mp3.vercel.app/auth/favourite")
                .addHeader("Authorization", "Bearer "+ accessToken)
                .build();

        // Thực hiện request bất đồng bộ
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Xử lý khi có lỗi xảy ra
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Xử lý kết quả trả về từ API
                if (response.isSuccessful()) {
                    // Lấy dữ liệu username từ response body
                    final String username = response.body().string();

                    // Cập nhật UI trong luồng giao diện (main thread)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Gắn dữ liệu username vào TextView
                            tvUser.setText(username);
                        }
                    });
                }
            }
        });
    }

}
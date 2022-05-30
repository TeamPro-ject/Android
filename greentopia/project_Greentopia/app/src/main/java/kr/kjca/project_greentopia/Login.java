package kr.kjca.project_greentopia;


import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login extends Fragment {

    //login
    EditText et_id;
    String str_id;
    EditText et_pw;
    String str_pw;
    Button bt_sign_in;
    Button bt_sign_up;
    TextView outPut;

    String str; //서버로 전송할 데이터
    String addr; //호스트 IP

    String response; //서버 응답

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.login, container, false);


        et_id = rootView.findViewById(R.id.et_id);
        et_pw = rootView.findViewById(R.id.et_pw);
        outPut = rootView.findViewById(R.id.output);

        bt_sign_in = rootView.findViewById(R.id.bt_sign_in);
        bt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인
                addr = "";
                str = "";

                if (et_id.getText().toString() != null && et_pw.getText().toString() != null) {

                } else {
                }

      //db와 연결 후 id, password 확인
            }
        });

        bt_sign_up = rootView.findViewById(R.id.bt_sign_up);
        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입
            }


        });


        return rootView;
    }

}


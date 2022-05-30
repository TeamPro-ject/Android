package kr.kjca.project_greentopia;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Nav_monitoring extends Fragment {

    TextView tv_plant_name;
    TextView tv_time;
    TextView tv_temp;
    TextView tv_hum;
    TextView tv_light;
    TextView tv_lock;
    TextView tv_waterTank1;
    TextView tv_waterTank2;
    TextView tv_waterTank3;
    ProgressBar pb_temp;
    ProgressBar pb_hum;
    ProgressBar pb_light;
    ProgressBar pb_waterTank1;
    ProgressBar pb_waterTank2;
    ProgressBar pb_waterTank3;
    ImageView iv_lock;


    FrameLayout flyt_light;
    String str_current_time;

    int temp;
    int hum;
    int light;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.nav_monitoring, container, false);


        tv_plant_name = rootView.findViewById(R.id.tv_plant_name);
        tv_time = rootView.findViewById(R.id.tv_time);
        tv_temp = rootView.findViewById(R.id.tv_temp);
        tv_hum = rootView.findViewById(R.id.tv_hum);
        tv_light = rootView.findViewById(R.id.tv_light);
        tv_lock = rootView.findViewById(R.id.tv_lock);
        tv_waterTank1 = rootView.findViewById(R.id.tv_waterTank1);
        tv_waterTank2 = rootView.findViewById(R.id.tv_waterTank2);
        tv_waterTank3 = rootView.findViewById(R.id.tv_waterTank3);
        pb_temp = rootView.findViewById(R.id.pb_temp);
        pb_hum = rootView.findViewById(R.id.pb_hum);
        pb_light = rootView.findViewById(R.id.pb_light);
        pb_waterTank1 = rootView.findViewById(R.id.pb_waterTank1);
        pb_waterTank2 = rootView.findViewById(R.id.pb_waterTank2);
        pb_waterTank3 = rootView.findViewById(R.id.pb_waterTank3);
        iv_lock = rootView.findViewById(R.id.iv_lock_on);
        flyt_light = rootView.findViewById(R.id.flyt_light);

        // 현재 날짜/시간 구하기
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  hh:mm", Locale.getDefault());
        str_current_time = format.format(currentTime);
        tv_time.setText(str_current_time);

        //Retrofit 객체 생성
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Get
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        retrofitAPI.getData("1").enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> data = response.body();
                    Log.d("test", "성공");
                    Log.d("test", data.get(0).getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }
        });

        //Post
        HashMap<String, Object> input = new HashMap<>();
        input.put("userId", 1);
        input.put("title", "title!!");
        input.put("body", "body!!");
        retrofitAPI.postData(input).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post data = response.body();
                    Log.d("test", "post 성공");
                    Log.d("test", data.getBody());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("test", "실패");
            }
        });


        //db에서 가져온 정보로 셋팅
        temp = 25;
        hum = 25;
        light = 75;
        pb_temp.setProgress(temp); //괄호안에 temp, hum, light로 바꾸기
        tv_temp.setText(temp + " C°");
        pb_hum.setProgress(hum);
        tv_hum.setText(hum + " %");
        pb_light.setProgress(light);
        tv_light.setText(light + " lux");

        //잠김 상태인지 아닌지 정보 받아오고 잠김 상태이면 변경 x 잠김 아니면 변경
        iv_lock.setImageResource(R.drawable.unlock);
        tv_lock.setText("열림");


        //클릭하면 설정화면으로 연결되게
        flyt_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menu_light화면
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Menu_light menu_light = new Menu_light();
                transaction.replace(R.id.container, menu_light);
                transaction.commit();
            }
        });


        return rootView;


    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
//            outPut.setText(s);
        }
    }


}

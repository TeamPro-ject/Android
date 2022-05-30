package kr.kjca.project_greentopia;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class Menu_light extends Fragment {

    private WebSocketClient webSocketClient;

    //menu_light
    TextView tv_current_time;
    TextView tv_light_st_time;
    TextView tv_light_ed_time;

    TimePicker tp_light_st;
    TimePicker tp_light_ed;

    Button bt_ledOn;
    Button bt_ledOff;

    int it_light_st_hour = 0;
    int it_light_st_min = 0;

    int it_light_ed_hour = 0;
    int it_light_ed_min = 0;

    Button bt_save_light;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.menu_light, container, false);
        createWebSocketClient();


        //db에서 저장된 시간 값 가져오기. 없으면 변경 x
        tv_current_time = rootView.findViewById(R.id.tv_current_time);
        tv_light_st_time = rootView.findViewById(R.id.tv_light_st_time);
        tv_light_ed_time = rootView.findViewById(R.id.tv_light_ed_time);
        tp_light_st = rootView.findViewById(R.id.tp_light_st);
        tp_light_ed = rootView.findViewById(R.id.tp_light_ed);
        bt_save_light = rootView.findViewById(R.id.bt_save_light);
        bt_ledOn = rootView.findViewById(R.id.bt_ledOn);
        bt_ledOff = rootView.findViewById(R.id.bt_ledOff);

        //led 켜기 끄기
        bt_ledOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(bt_ledOn);
            }
        });
        bt_ledOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(bt_ledOff);
            }
        });

        CurrentTime currentTime = new CurrentTime();
        tv_current_time.setText(currentTime.getStr_current_time());

        //저장 버튼
        bt_save_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it_light_st_hour = tp_light_st.getHour();
                    it_light_st_min = tp_light_st.getMinute();
                    it_light_ed_hour = tp_light_ed.getHour();
                    it_light_ed_min = tp_light_ed.getMinute();
                } else {
                    it_light_st_hour = tp_light_st.getCurrentHour();
                    it_light_st_min = tp_light_st.getCurrentMinute();
                    it_light_ed_hour = tp_light_ed.getCurrentHour();
                    it_light_ed_min = tp_light_ed.getCurrentMinute();
                }

                sendMessage(bt_save_light);

                Snackbar.make(view, "저장 완료. 시작 시간 : " + it_light_st_hour + " 시 " +
                        it_light_st_min + " 분, 종료 시간 : " + it_light_ed_hour + " 시 " +
                        it_light_ed_min + " 분", Snackbar.LENGTH_LONG).show();

                SettingController.fragmentChange(getActivity());

            }
        });

        return rootView;
    }

    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI(Uri.getUri());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.d("WebSocket", "Session is starting");
//                webSocketClient.send("light");
            }

            @Override
            public void onTextReceived(String s) {
                Log.d("WebSocket", "Message received");
                final String message = s;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Log.d("WebSocket", "Message received : "+message);
//                            TextView textView = findViewById(R.id.animalSound);
//                            textView.setText(message);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {
            }

            @Override
            public void onPongReceived(byte[] data) {
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
                Log.d("WebSocket", "오류 :"+ e.getMessage());
            }


            @Override
            public void onCloseReceived() {
                Log.d("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }
    public void sendMessage(View view) {
        Log.d("WebSocket", "Button was clicked");

        // Send button id string to WebSocket Server
        switch(view.getId()){
            case(R.id.bt_ledOn):
                webSocketClient.send("ledOn");
                Log.d("WebSocket", "bt_ledOn");
                break;

            case(R.id.bt_ledOff):
                webSocketClient.send("ledOff");
                Log.d("WebSocket", "bt_ledOff");
                break;

            case(R.id.bt_save_light):
                webSocketClient.send("changeLedSetting1/"+it_light_st_hour+":"+it_light_st_min+"/"+it_light_ed_hour+":"+it_light_ed_min);
                Log.d("WebSocket", "bt_save_light");
                break;

        }
    }
}


package kr.kjca.project_greentopia;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
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

public class Menu_cooling extends Fragment {
    private WebSocketClient webSocketClient;

    TextView tv_current_time;
    TextView tv_cooling_st_time;
    TextView tv_cooling_ed_time;
    TimePicker tp_cooling_st;
    TimePicker tp_cooling_ed;
    int it_cooling_st_hour;
    int it_cooling_st_min;
    int it_cooling_ed_hour;
    int it_cooling_ed_min;
    Button bt_save_cooling;
    Button bt_cooling_on;
    Button bt_cooling_off;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.menu_cooling, container, false);
        createWebSocketClient();

        tv_current_time = rootView.findViewById(R.id.tv_current_time);
        mainActivity = (MainActivity) getActivity();
        tv_cooling_st_time = rootView.findViewById(R.id.tv_cooling_st_time);
        tv_cooling_ed_time = rootView.findViewById(R.id.tv_cooling_ed_time);
        tp_cooling_st = rootView.findViewById(R.id.tp_cooling_st);
        tp_cooling_ed = rootView.findViewById(R.id.tp_cooling_ed);
        bt_save_cooling = rootView.findViewById(R.id.bt_save_cooling);
        bt_cooling_on = rootView.findViewById(R.id.bt_cooling_on);
        bt_cooling_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(bt_cooling_on);
            }
        });
        bt_cooling_off = rootView.findViewById(R.id.bt_cooling_off);
        bt_cooling_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(bt_cooling_off);
            }
        });
        CurrentTime currentTime = new CurrentTime();
        tv_current_time.setText(currentTime.getStr_current_time());


        //db에서 저장된 시간 값 가져오기. 없으면 변경 x
        //저장된 값으로 변경

        bt_save_cooling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db에 저장
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it_cooling_st_hour = tp_cooling_st.getHour();
                    it_cooling_st_min = tp_cooling_st.getMinute();
                    it_cooling_ed_hour = tp_cooling_ed.getHour();
                    it_cooling_ed_min = tp_cooling_ed.getMinute();
                } else {
                    it_cooling_st_hour = tp_cooling_st.getCurrentHour();
                    it_cooling_st_min = tp_cooling_st.getCurrentMinute();
                    it_cooling_ed_hour = tp_cooling_ed.getCurrentHour();
                    it_cooling_ed_min = tp_cooling_ed.getCurrentMinute();
                }

                Snackbar.make(view, "저장 완료. 시작 시간 : " + it_cooling_st_hour + " 시 " +
                        it_cooling_st_min + " 분, 종료 시간 : " + it_cooling_ed_hour + " 시 " +
                        it_cooling_ed_min + " 분", Snackbar.LENGTH_LONG).show();

                //설정으로 화면 전환
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
        } catch (URISyntaxException e) {
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
                        try {
                            Log.d("WebSocket", "Message received : " + message);
//                            TextView textView = findViewById(R.id.animalSound);
//                            textView.setText(message);
                        } catch (Exception e) {
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
                Log.d("WebSocket", e.getMessage());
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
        switch (view.getId()) {

            //수정 필요////////////////////////////////////////////////////////////////////
            case (R.id.bt_cooling_on):
                webSocketClient.send("fanOn");
                Log.d("WebSocket", "bt_cooling_on");
                break;

            case (R.id.bt_cooling_off):
                webSocketClient.send("fanOff");
                Log.d("WebSocket", "bt_cooling_off");
                break;

            case (R.id.bt_save_cooling):
                webSocketClient.send("changeSetting2/" + it_cooling_st_hour + ":" + it_cooling_st_min + "/" + it_cooling_ed_hour + ":" + it_cooling_ed_min);
                Log.d("WebSocket", "bt_save_cooling");
                break;

        }
    }


}

package kr.kjca.project_greentopia;

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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import tech.gusavila92.websocketclient.WebSocketClient;

public class Menu_water extends Fragment {

    private WebSocketClient webSocketClient;

    TextView tv_current_time;
    TextView tv_water_amount;
    EditText et_water_amount;
    SeekBar sb_water_amount;
    Button bt_water_amount_down;
    Button bt_water_amount_up;
    TextView tv_water_cycle;
    EditText et_water_cycle_hour;
    SeekBar sb_water_cycle;
    Button bt_water_cycle_down;
    Button bt_water_cycle_up;
    Button bt_water;
    Button bt_save_water;
    String str_water_amount;
    String str_water_cycle_hour;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.menu_water, container, false);
        createWebSocketClient();

        tv_current_time = rootView.findViewById(R.id.tv_current_time);
        tv_water_cycle = rootView.findViewById(R.id.tv_water_cycle);
        et_water_cycle_hour = rootView.findViewById(R.id.et_water_cycle_hour);
        sb_water_cycle = rootView.findViewById(R.id.sb_water_cycle);
        bt_water_cycle_down = rootView.findViewById(R.id.bt_water_cycle_down);
        bt_water_cycle_up = rootView.findViewById(R.id.bt_water_cycle_up);
        tv_water_amount = rootView.findViewById(R.id.tv_water_amount);
        et_water_amount = rootView.findViewById(R.id.et_water_amount);
        sb_water_amount = rootView.findViewById(R.id.sb_water_amount);
        bt_water_amount_down = rootView.findViewById(R.id.bt_water_amount_down);
        bt_water_amount_up = rootView.findViewById(R.id.bt_water_amount_up);
        bt_save_water = rootView.findViewById(R.id.bt_save_water);
        bt_water = rootView.findViewById(R.id.bt_water);

        CurrentTime currentTime = new CurrentTime();
        tv_current_time.setText(currentTime.getStr_current_time());

        //db에서 저장된 시간 값 가져오기. 없으면 변경 x
        //저장된 값으로 text 변경, seekbar 변경

        et_water_cycle_hour.setText("0");
        et_water_amount.setText("0");

        //새롭게 급수량 설정
        et_water_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                str_water_amount = et_water_amount.getText().toString();
                sb_water_amount.setProgress(Integer.parseInt(str_water_amount));
            }
        });

        sb_water_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                et_water_amount.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //물 양 줄이기
        bt_water_amount_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.down(et_water_amount, str_water_amount, sb_water_amount, 10);
            }
        });
//        bt_water_amount_down.setOnTouchListener(new RepeatListener(700, 50, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.down(et_water_amount, str_water_amount, sb_water_amount,10);
//            }
//        }));

        //물 양 늘리기
        bt_water_amount_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.up(et_water_amount,str_water_amount,sb_water_amount,10,100);
            }
        });
//        bt_water_amount_up.setOnTouchListener(new RepeatListener(700, 50, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.up(et_water_amount, str_water_amount, sb_water_amount,10, 100);
//            }
//        }));


        //새롭게 시간 설정
        et_water_cycle_hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                str_water_cycle_hour = et_water_cycle_hour.getText().toString();
                sb_water_cycle.setProgress(Integer.parseInt(str_water_cycle_hour));
            }
        });

        //시간 간격 seekbar
        sb_water_cycle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                et_water_cycle_hour.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //시간 간격 줄이는 버튼
        bt_water_cycle_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.down(et_water_cycle_hour, str_water_cycle_hour, sb_water_cycle,1);
            }
        });
//        bt_water_cycle_down.setOnTouchListener(new RepeatListener(700, 50, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.down(et_water_cycle_hour, str_water_cycle_hour, sb_water_cycle,10);
//            }
//        }));

        //시간 간격 늘리는 버튼
        bt_water_cycle_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.up(et_water_cycle_hour, str_water_cycle_hour, sb_water_cycle, 1, 1440);
            }
        });
//
//        bt_water_cycle_up.setOnTouchListener(new RepeatListener(700, 50, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.up(et_water_cycle_hour, str_water_cycle_hour, sb_water_cycle,10, 1440);
//            }
//        }));

        //물 주기
        bt_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(bt_water);
            }
        });

        //저장
        bt_save_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //db에 저장..추가
                SettingController.save(str_water_amount, et_water_cycle_hour);
                sendMessage(bt_save_water);
                Snackbar.make(view, "저장 완료. 급수 간격 : " + str_water_cycle_hour + " 시간, " +
                        "급수량 : " + str_water_amount + " ml", Snackbar.LENGTH_LONG).show();

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
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.d("WebSocket", "Session is starting");
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

    //to WebSocket Server
    public void sendMessage(View view) {
        Log.d("WebSocket", "Button was clicked");

        switch (view.getId()) {
            case (R.id.bt_water):
                webSocketClient.send("pump1On");
                Log.d("WebSocket", "pumpSetting1");
                break;

            case (R.id.bt_save_water):
                webSocketClient.send("pumpSetting1/"+et_water_cycle_hour.getText());
                Log.d("WebSocket", "bt_save_water");
                break;
        }
    }


}
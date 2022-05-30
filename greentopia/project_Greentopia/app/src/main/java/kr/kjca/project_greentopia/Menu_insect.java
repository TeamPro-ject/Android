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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class Menu_insect extends Fragment {
    private WebSocketClient webSocketClient;
    TextView tv_current_time;
    TextView tv_insect_amount;
    EditText et_insect_amount;
    SeekBar sb_insect_amount;
    Button bt_insect_amount_down;
    Button bt_insect_amount_up;
    TextView tv_insect_cycle;
    EditText et_insect_cycle_hour;
    SeekBar sb_insect_cycle;
    Button bt_insect_cycle_down;
    Button bt_insect_cycle_up;
    Button bt_save_insect;
    Button bt_insect;
    String str_insect_amount;
    String str_insect_cycle_hour;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.menu_insect, container, false);
        createWebSocketClient();

        //db에서 저장된 시간 값 가져오기. 없으면 변경 x

        tv_current_time = rootView.findViewById(R.id.tv_current_time);
        tv_insect_amount = rootView.findViewById(R.id.tv_insect_amount);
        et_insect_amount = rootView.findViewById(R.id.et_insect_amount);
        sb_insect_amount = rootView.findViewById(R.id.sb_insect_amount);
        bt_insect_amount_down = rootView.findViewById(R.id.bt_insect_amount_down);
        bt_insect_amount_up = rootView.findViewById(R.id.bt_insect_amount_up);
        tv_insect_cycle = rootView.findViewById(R.id.tv_insect_cycle);
        et_insect_cycle_hour = rootView.findViewById(R.id.et_insect_cycle_hour);
        sb_insect_cycle = rootView.findViewById(R.id.sb_insect_cycle);
        bt_insect_cycle_down = rootView.findViewById(R.id.bt_insect_cycle_down);
        bt_insect_cycle_up = rootView.findViewById(R.id.bt_insect_cycle_up);
        bt_save_insect = rootView.findViewById(R.id.bt_save_insect);
        bt_insect = rootView.findViewById(R.id.bt_insect);
        bt_insect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(bt_insect);
            }
        });

        CurrentTime currentTime = new CurrentTime();
        tv_current_time.setText(currentTime.getStr_current_time());

        //db에서 저장된 시간 값 가져오기. 없으면 변경 x
        //저장된 값으로 text 변경, seekbar 변경

        et_insect_cycle_hour.setText("0");
        et_insect_amount.setText("0");

        //새롭게 급수량 설정
        et_insect_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                str_insect_amount = et_insect_amount.getText().toString();
                //수정하기 에디트텍스트 변경해도 안바뀜
                sb_insect_amount.setProgress(Integer.parseInt(str_insect_amount));
            }
        });

        sb_insect_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                et_insect_amount.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //진딧물 약 양 줄이기
        bt_insect_amount_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.down(et_insect_amount, str_insect_amount, sb_insect_amount, 10);
            }
        });
//        bt_insect_amount_down.setOnTouchListener(new RepeatListener(700, 300, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.down(et_insect_amount, str_insect_amount, sb_insect_amount,10);
//            }
//        }));

        //진딧물 약 양 늘리기
        bt_insect_amount_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.up(et_insect_amount, str_insect_amount, sb_insect_amount, 10, 100);
            }
        });
//        bt_insect_amount_up.setOnTouchListener(new RepeatListener(700, 300, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.up(et_insect_amount, str_insect_amount, sb_insect_amount,10, 100);
//            }
//        }));


        //새롭게 시간 설정
        et_insect_cycle_hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                str_insect_cycle_hour = et_insect_cycle_hour.getText().toString();
                sb_insect_cycle.setProgress(Integer.parseInt(str_insect_cycle_hour));
            }
        });

        sb_insect_cycle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                et_insect_cycle_hour.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //진딧물약 주기 줄이기
        bt_insect_cycle_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.down(et_insect_cycle_hour, str_insect_cycle_hour, sb_insect_cycle,1);
            }
        });
//        bt_insect_cycle_down.setOnTouchListener(new RepeatListener(700, 300, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.down(et_insect_cycle_hour, str_insect_cycle_hour, sb_insect_cycle,10);
//            }
//        }));

        //진딧물약 주기 늘리기
        bt_insect_cycle_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.up(et_insect_cycle_hour, str_insect_cycle_hour, sb_insect_cycle,1, 1440);
            }
        });
//        bt_insect_cycle_up.setOnTouchListener(new RepeatListener(700, 300,new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.up(et_insect_cycle_hour, str_insect_cycle_hour, sb_insect_cycle,10, 1440);
//            }
//        }));

        //저장
        bt_save_insect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db에 저장 .. 추가하기
                SettingController.save(str_insect_amount, et_insect_amount);

                Snackbar.make(view, "저장 완료. 급수 간격 : "+str_insect_cycle_hour+" 시간, "+
                        "급수량 : "+ str_insect_amount+" ml", Snackbar.LENGTH_LONG).show();
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
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.d("WebSocket", "Session is starting");
//                webSocketClient.send("water");
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
        switch(view.getId()){
            case(R.id.bt_insect):
                webSocketClient.send("pump3On");
                Log.d("WebSocket", "pump3On");
                break;

            case(R.id.bt_save_insect):
                webSocketClient.send("pumpSetting3/"+et_insect_cycle_hour.getText());
                Log.d("WebSocket", "bt_save_insect");
                break;

        }
    }

}


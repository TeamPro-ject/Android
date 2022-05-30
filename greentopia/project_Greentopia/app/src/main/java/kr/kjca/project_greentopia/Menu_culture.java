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

public class Menu_culture extends Fragment {

    private WebSocketClient webSocketClient;
    TextView tv_current_time;
    TextView tv_culture_amount;
    EditText et_culture_amount;
    SeekBar sb_culture_amount;
    Button bt_culture_amount_down;
    Button bt_culture_amount_up;
    TextView tv_culture_cycle;
    EditText et_culture_cycle_hour;
    SeekBar sb_culture_cycle;
    Button bt_culture_cycle_down;
    Button bt_culture_cycle_up;
    Button bt_save_culture;
    Button bt_culture;
    String str_culture_amount;
    String str_culture_cycle_hour = "5";
    int count = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.menu_culture, container, false);
        createWebSocketClient();

        //db에서 저장된 시간 값 가져오기. 없으면 변경 x
//        str_culture_amount = "";
//        str_culture_cycle = "";
//        sb_culture_amount.setProgress();
        tv_current_time = rootView.findViewById(R.id.tv_current_time);
        tv_culture_amount = rootView.findViewById(R.id.tv_culture_amount);
        et_culture_amount = rootView.findViewById(R.id.et_culture_amount);
        sb_culture_amount = rootView.findViewById(R.id.sb_culture_amount);
        bt_culture_amount_down = rootView.findViewById(R.id.bt_culture_amount_down);
        bt_culture_amount_up = rootView.findViewById(R.id.bt_culture_amount_up);
        tv_culture_cycle = rootView.findViewById(R.id.tv_culture_cycle);
        et_culture_cycle_hour = rootView.findViewById(R.id.et_culture_cycle_hour);
        sb_culture_cycle = rootView.findViewById(R.id.sb_culture_cycle);
        bt_culture_cycle_down = rootView.findViewById(R.id.bt_culture_cycle_down);
        bt_culture_cycle_up = rootView.findViewById(R.id.bt_culture_cycle_up);
        bt_save_culture = rootView.findViewById(R.id.bt_save_culture);
        bt_culture = rootView.findViewById(R.id.bt_culture);
        bt_culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(bt_culture);
            }
        });

        CurrentTime currentTime = new CurrentTime();
        tv_current_time.setText(currentTime.getStr_current_time());

        //db에서 저장된 시간 값 가져오기. 없으면 변경 x
        //저장된 값으로 text 변경, seekbar 변경
        et_culture_cycle_hour.setText("0");

        et_culture_amount.setText("0");
        //새롭게 급수량 설정
        et_culture_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                str_culture_amount = et_culture_amount.getText().toString();
                //수정하기 에디트텍스트 변경해도 안바뀜
                sb_culture_amount.setProgress(Integer.parseInt(str_culture_amount));
            }
        });
        sb_culture_amount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                et_culture_amount.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        count = 0;
        //배양액 양 줄이기
        bt_culture_amount_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                SettingController.down(et_culture_amount, str_culture_amount, sb_culture_amount, 10);
            }
        });
//        bt_culture_amount_down.setOnTouchListener(new RepeatListener(700, 300, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                count +=1;
//                if (count >= 1){
//                SettingController.down(et_culture_amount, str_culture_amount, sb_culture_amount, 10);}
//            }
//        }));

        //배양액 양 늘리기
        bt_culture_amount_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.up(et_culture_amount, str_culture_amount, sb_culture_amount, 10, 100);
            }
        });
//        bt_culture_amount_up.setOnTouchListener(new RepeatListener(700, 300, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.up(et_culture_amount, str_culture_amount, sb_culture_amount, 10, 100);
//            }
//        }));

        //새롭게 시간 설정
        et_culture_cycle_hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                str_culture_cycle_hour = et_culture_cycle_hour.getText().toString();
                sb_culture_cycle.setProgress(Integer.parseInt(str_culture_cycle_hour));
            }
        });

        sb_culture_cycle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                et_culture_cycle_hour.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //배양액 주기 줄이기
        bt_culture_cycle_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.down(et_culture_cycle_hour, str_culture_cycle_hour, sb_culture_cycle,1);
            }
        });
//        bt_culture_cycle_down.setOnTouchListener(new RepeatListener(700, 300, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.down(et_culture_cycle_hour, str_culture_cycle_hour, sb_culture_cycle, 10);
//            }
//        }));

        //배양액 주기 늘리기
        bt_culture_cycle_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingController.up(et_culture_cycle_hour, str_culture_cycle_hour, sb_culture_cycle,1, 1440);
            }
        });
//        bt_culture_cycle_up.setOnTouchListener(new RepeatListener(700, 300,new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingController.up(et_culture_cycle_hour, str_culture_cycle_hour, sb_culture_cycle,10, 1440);
//            }
//        }));

        //저장
        bt_save_culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db에 저장 .. 추가하기
                SettingController.save(str_culture_amount, et_culture_amount);

                Snackbar.make(view, "저장 완료. 배양 간격 : " + str_culture_cycle_hour + " 시간, " +
                        "배지량 : " + str_culture_amount + " ml", Snackbar.LENGTH_LONG).show();
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
//                webSocketClient.send("water");

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
            case (R.id.bt_culture):
                webSocketClient.send("pump2On");
                Log.d("WebSocket", "pump2On");
                break;

            case (R.id.bt_save_culture):
                webSocketClient.send("pumpSetting2/"+str_culture_cycle_hour);
                Log.d("WebSocket", "bt_save_culture");
                break;

        }
    }

}
package kr.kjca.project_greentopia;

import android.app.Activity;
import android.service.voice.VoiceInteractionSession;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class SettingController {

    public static void down(EditText et, String str, SeekBar sb, int interval){
        if (et.getText().toString().trim().length() != 0) {
            str = (Integer.parseInt(et.getText().toString())) - interval + "";
            if (Integer.parseInt(str) < 0) {
                str = 0 + "";
            }
            et.setText(str);
            sb.setProgress(Integer.parseInt(str));
        }
    }

    public static void up(EditText et, String str, SeekBar sb, int interval, int max){
        if (et.getText().toString().trim().length() != 0) {
            str = (Integer.parseInt(et.getText().toString())) + interval + "";
            if (Integer.parseInt(str) > max) {
                str = max + "";
            }
            et.setText(str);
            sb.setProgress(Integer.parseInt(str));
        }
    }

    public static void save(String str, EditText et){
        if (str != null) {
            str = et.getText().toString() + "";
        }
        if (str != null) {
            str = et.getText().toString() + "";
        }
    }

    public static void fragmentChange(FragmentActivity activity){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Nav_settings Nav_settings = new Nav_settings();
        transaction.replace(R.id.container, Nav_settings);
        transaction.commit();

    }

    public static void fragmentTransaction(FragmentActivity activity, String className){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        switch (className){
            case "Menu_light" :
                Menu_light menu_light = new Menu_light();
                transaction.replace(R.id.container, menu_light);
                transaction.commit();
                break;

            case "Menu_water" :
                Menu_water menu_water = new Menu_water();
                transaction.replace(R.id.container, menu_water);
                transaction.commit();
                break;

            case "Menu_culture" :
                Menu_culture menu_culture = new Menu_culture();
                transaction.replace(R.id.container, menu_culture);
                transaction.commit();
                break;

            case "Menu_insect" :
                Menu_insect menu_insect = new Menu_insect();
                transaction.replace(R.id.container, menu_insect);
                transaction.commit();
                break;

            case "Menu_cooling" :
                Menu_cooling menu_cooling = new Menu_cooling();
                transaction.replace(R.id.container, menu_cooling);
                transaction.commit();
                break;

        }

    }

}

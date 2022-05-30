package kr.kjca.project_greentopia;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CurrentTime {

    String str_current_time;

    public CurrentTime() {
        // 현재 날짜/시간 구하기
        Date currentTime = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        int it_hour =  calendar.get(Calendar.HOUR);
        int it_min = calendar.get(Calendar.MINUTE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  hh:mm" , Locale.getDefault());
        str_current_time = format.format(currentTime);
    }

    public String getStr_current_time() {
        return str_current_time;
    }
}

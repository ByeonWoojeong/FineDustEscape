package mise.woojeong.com.mise;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.Calendar;

public class AlertActivity extends AppCompatActivity {

    private Switch mAlarmSwitch;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_alert);

        mAlarmSwitch = (Switch)findViewById(R.id.alarmSwitch);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_alert);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김



        mAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                registerAlarm();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Noticifation
    public static void alertNotification(Context context, String title, String text) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context.getApplicationContext(),MainActivity.class); //인텐트 생성.



        Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를 없앤다.

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( context,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent는 일회용 인텐트 같은 개념

        //FLAG_UPDATE_CURRENT : 만일 이미 생성된 PendingIntent가 존재 한다면, 해당 Intent의 내용을 변경
        //FLAG_CANCEL_CURRENT : 이전에 생성한 PendingIntent를 취소하고 새롭게 하나 만듬
        //FLAG_NO_CREATE : 현재 생성된 PendingIntent를 반환
        //FLAG_ONE_SHOT : 이 플래그를 사용해 생성된 PendingIntent는 단 한번밖에 사용할 수 없음

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification_large_icon);

        builder
                .setSmallIcon(R.drawable.notification_simple_white)
                .setLargeIcon(largeIcon)
                .setTicker("먼지 탈출!")
                .setWhen(System.currentTimeMillis())
                .setNumber(1)
                .setContentTitle(title)
                .setContentText(text)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true)
                .setOngoing(true);

        /*
            setSmallIcon : 작은 아이콘 이미지
            setTicker : 알람이 출력될 때 상단에 나오는 문구
            setWhen : 알림 출력 시간
            setContentTitle : 알림 제목
            setConentText : 푸시 내용
        */

        notificationManager.notify(1, builder.build()); // Notification send
    }

    private void registerAlarm(){
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlertActivity.this, BroadCast.class);

        PendingIntent sender = PendingIntent.getBroadcast(AlertActivity.this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 8, 00, 0);
        // long time = calendar.getTimeInMillis();
        long time = System.currentTimeMillis() + 5000;

        //알람 예약
        am.set(AlarmManager.RTC_WAKEUP, time, sender);


        // 오레오
    }
}

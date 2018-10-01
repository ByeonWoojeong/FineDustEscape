package mise.woojeong.com.mise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gotoMain();
                    }
                });
            }
        }, 1500);
    }

    private void gotoMain(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        this.finish();

    }
}

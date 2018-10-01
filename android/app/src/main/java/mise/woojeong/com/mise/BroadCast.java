package mise.woojeong.com.mise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        AlertActivity.alertNotification(context, "먼지 탈출!", "미세먼지 상태를 확인해 주세요.");

    }
}

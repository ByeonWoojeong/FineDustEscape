package mise.woojeong.com.mise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        sendEmail();

    }

    public void sendEmail(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("plain/text");
        // email setting 배열로 해놔서 복수 발송 가능
        String[] address = {"qusdnwjd1009@gmail.com"};
        email.putExtra(Intent.EXTRA_EMAIL, address);
        email.putExtra(Intent.EXTRA_SUBJECT,"불편 사항 건의합니다!");
        email.putExtra(Intent.EXTRA_TEXT,"건의 내용을 작성하여 주세요.\n");
        startActivity(email);

        EmailActivity.this.finish();
    }
}

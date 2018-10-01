package mise.woojeong.com.mise;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

    private EditText mEditLocation;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_location);

        mEditLocation = (EditText)findViewById(R.id.editLocation);

        mToolbar = (Toolbar)findViewById(R.id.toolbar_location);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


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

    public void mOnClickBtnSearch(View v){

        String location="";
        location = mEditLocation.getEditableText().toString();

        if(location.equalsIgnoreCase("")){
            Toast.makeText(this, "텍스트를 입력해 주세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(this, "먼지 탈출", "검색중 입니다!", true);


        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("location", location);
        startActivity(intent);

        progressDialog.dismiss();

        this.finish();
    }
}

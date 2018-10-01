package mise.woojeong.com.mise;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LocationHelper locationHelper;

    private ImageView mImgCurrentState1;
    private ImageView mImgCurrentState2;
    private ImageView mImgCurrentState3;
    private ImageView mImgCurrentState4;
    private ImageView mImgCurrentState5;
    private ImageView mImgCurrentState6;
    private ImageView mImgCurrentState7;
    private ImageView mImgCurrentState8;

    private TextView mTextCurrentState;

    private TextView mTextLocation;

    private TextView mTextNow;

    private TextView mTextPm10State;
    private TextView mTextPm10Value;
    private TextView mTextPm25State;
    private TextView mTextPm25Value;
    private TextView mTextNo2State;
    private TextView mTextNo2Value;
    private TextView mTextO3State;
    private TextView mTextO3Value;
    private TextView mTextCoState;
    private TextView mTextCoValue;
    private TextView mTextSo2State;
    private TextView mTextSo2Value;

    private LinearLayout mLayoutCurrentInfo;
    private LinearLayout mLayoutDetailInfo1;
    private LinearLayout mLayoutDetailInfo2;

    private long mNow;
    private Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년 MM월 dd일  hh시 mm분");

    private String mLastLocation = "";

    private Timer mTimer;

    private int mSearchCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mLastLocation = "";

        mImgCurrentState1 = (ImageView)findViewById(R.id.imgCurrentState1);
        mImgCurrentState2 = (ImageView)findViewById(R.id.imgCurrentState2);
        mImgCurrentState3 = (ImageView)findViewById(R.id.imgCurrentState3);
        mImgCurrentState4 = (ImageView)findViewById(R.id.imgCurrentState4);
        mImgCurrentState5 = (ImageView)findViewById(R.id.imgCurrentState5);
        mImgCurrentState6 = (ImageView)findViewById(R.id.imgCurrentState6);
        mImgCurrentState7 = (ImageView)findViewById(R.id.imgCurrentState7);
        mImgCurrentState8 = (ImageView)findViewById(R.id.imgCurrentState8);

        mTextCurrentState = (TextView)findViewById(R.id.textCurrentState);

        //GPS 권한 물어보기
        this.grantPermission();

        locationHelper = new LocationHelper(this);
        locationHelper.register();

        mTextLocation = (TextView)findViewById(R.id.textLocation);

        mTextNow = (TextView)findViewById(R.id.textNow);    //현재 시간

        mTextPm10State = (TextView)findViewById(R.id.textPm10State);    //미세먼지 단계
        mTextPm10Value = (TextView)findViewById(R.id.textPm10Value);    //미세먼지 수치값
        mTextPm25State = (TextView)findViewById(R.id.textPm25State);    //초미세먼지 단계
        mTextPm25Value = (TextView)findViewById(R.id.textPm25Value);    //초미세먼지 수치값
        mTextNo2State = (TextView)findViewById(R.id.textNo2State);      //이산화질소 단계
        mTextNo2Value = (TextView)findViewById(R.id.textNo2Value);      //이산화질소 수치값
        mTextO3State = (TextView)findViewById(R.id.textO3State);        //오존 단계
        mTextO3Value = (TextView)findViewById(R.id.textO3Value);        //오존 수치값
        mTextCoState = (TextView)findViewById(R.id.textCoState);        //일산화탄소 단계
        mTextCoValue = (TextView)findViewById(R.id.textCoValue);        //일산화탄소 수치값
        mTextSo2State = (TextView)findViewById(R.id.textSo2State);      //아황산가스 단계
        mTextSo2Value = (TextView)findViewById(R.id.textSo2Value);      //아황산가스 수치값

        //레이아웃
        mLayoutCurrentInfo = (LinearLayout)findViewById(R.id.layoutCurrentInfo);
        mLayoutDetailInfo1 = (LinearLayout)findViewById(R.id.layoutDetailInfo1);
        mLayoutDetailInfo2 = (LinearLayout)findViewById(R.id.layoutDetailInfo2);

        mLayoutCurrentInfo.setVisibility(View.INVISIBLE);
        mLayoutDetailInfo1.setVisibility(View.INVISIBLE);
        mLayoutDetailInfo2.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onStart(){
        //부모의 함수 호출
        super.onStart();

        // 시간
        mTextNow.setText(getNow());

        // 만약 mLastLocatgion == "" 이면 현재위치 검색
        // 아니면 getMiseInfoByLocation(mLastLocation, mLastLocation);
        if(mLastLocation.equalsIgnoreCase("")){

            final ProgressDialog progressDialog = ProgressDialog.show(this, "먼지 탈출", "위치를 찾고 있습니다.", true);

            mSearchCounter = 0;

            mTimer = new Timer();

            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    mSearchCounter += 1;

                    if(mSearchCounter == 20){
                        mTimer.cancel();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                                        alertDialog.setTitle("먼지 탈출")
                                                .setMessage("GPS 설정을 확인해주세요.")
                                                .setPositiveButton("확인", null)
                                                .show();
                            }
                        });
                    }
                    else{
                        MyLocation myLocation = locationHelper.getLocation();
                        if(myLocation.getLongitude() == 0.0){
                            // 통과~
                        }
                        else{
                            mTimer.cancel();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    searchCurrentLocation();
                                }
                            });
                        }
                    }

                }
            }, 500, 500); //0.5초후에, 0.5초마다

        }
        else{
            // 위치
            getMiseInfoByLocation(mLastLocation, mLastLocation);
        }

    }

    @Override
    protected void onResume(){

        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        mTextNow.setText(getNow());

        String location = getIntent().getStringExtra("location");

        if(location != null){
            mLastLocation = location;
        }

        getMiseInfoByLocation(mLastLocation, mLastLocation);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_location_search) {

            Intent intent = new Intent(this, LocationActivity.class);
            startActivity(intent);

            return true;
        }
        else if(id == R.id.action_location_now){
            boolean success = searchCurrentLocation();
            if(success == false){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("먼지 탈출")
                        .setMessage("GPS 설정을 확인해주세요.")
                        .setPositiveButton("확인", null)
                        .show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_alert) {

            Intent intent = new Intent(this, AlertActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_email) {

            Intent intent = new Intent(this, EmailActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //퍼미션 승인
    private boolean grantPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v("Mise","Permission is granted");
                return true;
            }else{
                Log.v("Mise","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                return false;
            }
        }else{
            Log.d("Mise", "External Storage Permission is Grant ");
            return true;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Log.v("Mise","Permission: "+permissions[0]+ "was "+grantResults[0]);
                //resume tasks needing this permission
            }
        }
    }


    public boolean searchCurrentLocation(){
        MyLocation myLocation = locationHelper.getLocation();
//            Toast.makeText(MainActivity.this, " "+myLocation.getLatitude()+" "+myLocation.getLongitude(), Toast.LENGTH_SHORT).show();

        if(myLocation.getLongitude() == 0.0){
            return false;
        }

        getMiseInfoGeo(myLocation.getLatitude(), myLocation.getLongitude());

        String address = getAddress(MainActivity.this, myLocation.getLatitude(), myLocation.getLongitude());

//        Toast.makeText(MainActivity.this, ""+address, Toast.LENGTH_SHORT).show();

        return true;
    }

    // 서비스키 메소드
    public String getServiceKey(){

        String serviceKey = "";
        try {
            serviceKey = URLDecoder.decode("zckvmg1OH8svbljDs5lezq2jnqX2i0IDDhKSbDjualbVgwMHx8hcwIjB5rI50FB0dQ01W9pLivT%2BYm%2Fy25SZQg%3D%3D","UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return serviceKey;
    }

    // 위도/경도 기준으로 가장 가까운 관측소를 얻어온다
    public void getMiseInfoGeo(double latitude, double longitude){  // 위도, 경도

        String url = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList";

        String serviceKey = getServiceKey();

        // http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?tmX=244148.546388&tmY=412423.75772&pageNo=1&numOfRows=10&ServiceKey=zckvmg1OH8svbljDs5lezq2jnqX2i0IDDhKSbDjualbVgwMHx8hcwIjB5rI50FB0dQ01W9pLivT%2BYm%2Fy25SZQg%3D%3D

        GeoTransPoint src = new GeoTransPoint();
        src.x = longitude;
        src.y = latitude;

        final String address = getAddress(MainActivity.this, latitude, longitude);

        //GeoTransPoint dst = new GeoTransPoint();
        //GeoTrans.geo2tm(GeoTrans.TM, src, dst);

        GeoTransPoint dst = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, src);

        RequestParams params = new RequestParams();
        params.put("tmX", dst.getX());
        params.put("tmY", dst.getY());
        params.put("numOfRows", 5);
        params.put("pageNo", 1);
        params.put("ServiceKey", serviceKey);
        params.put("_returnType", "json");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String body = new String(responseBody);
                NearbyMsrstnListVO nearbyMsrstnListVO = new Gson().fromJson(body, NearbyMsrstnListVO.class);


                if (nearbyMsrstnListVO.getList().size() == 0) {
                    Toast.makeText(MainActivity.this, "결과가 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                getMiseInfoByLocation(nearbyMsrstnListVO.getList().get(0).getStationName(), address);

                Log.d("Mise", body);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String body = new String(responseBody);
                Log.e("Mise", body);
                Toast.makeText(MainActivity.this, "실패", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // 관측소 기준으로 미세먼지 정보를 요청한다
    public void getMiseInfoByLocation(final String location, final String address){

        //프로그래스 바
        final ProgressDialog progressDialog = ProgressDialog.show(this, "먼지 탈출", "잠시만 기다려 주세요!", true);

        mLayoutCurrentInfo.setVisibility(View.INVISIBLE);
        mLayoutDetailInfo1.setVisibility(View.INVISIBLE);
        mLayoutDetailInfo2.setVisibility(View.INVISIBLE);

        String url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty";

        String serviceKey = getServiceKey();

        RequestParams params = new RequestParams();
        params.put("numOfRows", 5);
        params.put("pageNo", 1);
        params.put("stationName", location);
        params.put("dataTerm", "DAILY");
        params.put("ver", 1.3);
        params.put("ServiceKey", serviceKey);
        params.put("_returnType", "json");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    Thread.sleep(2000);
                }
                catch (Exception e) {

                }


                //프로그래스 다이얼로그 닫기
                progressDialog.dismiss();

                String body = new String(responseBody);

                MsrstnAcctoRltmMesureDnstyVO MsrstnAcctoRltmMesureDnstyVO = new Gson().fromJson(body, MsrstnAcctoRltmMesureDnstyVO.class);

                if (MsrstnAcctoRltmMesureDnstyVO.getList().size() == 0) {
                    Toast.makeText(MainActivity.this, "결과가 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                MiseInfoVO miseInfoVO = MsrstnAcctoRltmMesureDnstyVO.getList().get(0);

                final float pm10 = IntegerUtil.parseFloatDefault(miseInfoVO.getPm10Value(), 0);
                final float pm25 = IntegerUtil.parseFloatDefault(miseInfoVO.getPm25Value(), 0);

                final float no2 = IntegerUtil.parseFloatDefault(miseInfoVO.getNo2Value(), 0);
                final float o3 = IntegerUtil.parseFloatDefault(miseInfoVO.getO3Value(), 0);
                final float co = IntegerUtil.parseFloatDefault(miseInfoVO.getCoValue(), 0);
                final float so2 = IntegerUtil.parseFloatDefault(miseInfoVO.getSo2Value(), 0);

                final int miseGrade = MiseUtil.getMiseGrade(pm10, pm25);

                final int pm10Grade = MiseUtil.getPm10Grade(pm10);
                final int pm25Grade = MiseUtil.getPm25Grade(pm25);
                final int no2Grade = MiseUtil.getNo2Grade(no2);
                final int o3Grade = MiseUtil.getO3Grade(o3);
                final int coGrade = MiseUtil.getCoGrade(co);
                final int so2Grade = MiseUtil.getSo2Grade(so2);

                Log.d("Mise", "미세먼지 정보 수신완료");

                Toast.makeText(MainActivity.this, "미세먼지 정보 수신완료", Toast.LENGTH_SHORT).show();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextLocation.setText(address);
                        setMiseGrade(miseGrade);

                        mTextPm10Value.setText(String.valueOf(pm10) + "㎍/㎥");
                        mTextPm25Value.setText(String.valueOf(pm25) + "㎍/㎥");
                        mTextNo2Value.setText(String.valueOf(no2) + "ppm"); // 이산화질소
                        mTextO3Value.setText(String.valueOf(o3) + "ppm");   // 오존
                        mTextCoValue.setText(String.valueOf(co) + "ppm");    //일산화탄소
                        mTextSo2Value.setText(String.valueOf(so2) + "ppm");  //아황산가스

                        setMiseTextByGrade(mTextPm10State, pm10Grade);
                        setMiseTextByGrade(mTextPm25State, pm25Grade);
                        setMiseTextByGrade(mTextNo2State, no2Grade);
                        setMiseTextByGrade(mTextO3State, o3Grade);
                        setMiseTextByGrade(mTextCoState, coGrade);
                        setMiseTextByGrade(mTextSo2State, so2Grade);

                        mLayoutCurrentInfo.setVisibility(View.VISIBLE);
                        mLayoutDetailInfo1.setVisibility(View.VISIBLE);
                        mLayoutDetailInfo2.setVisibility(View.VISIBLE);
                    }
                });

                Log.d("mise", body);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String str = new String(responseBody);
                Log.d("Mise", str);
                Toast.makeText(MainActivity.this, "미세먼지 정보 수신 실패!\n"+str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getAddress(Context mContext, double lat, double lng) {
        String nowAddress = "현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress = currentLocationAddress;

                }
            }

        } catch (IOException e) {
            Toast.makeText(mContext, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }

        // 첫번째 마지막 없애기
        String[] arr = nowAddress.split(" ");
        ArrayList<String> arrList = new ArrayList<String>(Arrays.asList(arr));

        arrList.remove(0);
        arrList.remove(arrList.size()-1);

        //String.join(" ", arrList);

        String result= "";

        for(int i=0; i<arrList.size(); i++){
            result = result + " " + arrList.get(i);
        }

        String resultAddress = result.substring(1);

        return resultAddress;
    }

    private void setMiseTextByGrade(TextView textView, int miseGrade) {

        if(miseGrade== MiseConst.MISE_GRADE_1){
            textView.setText("최고");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_2){
            textView.setText("좋음");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_3){
            textView.setText("양호");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_4){
            textView.setText("보통");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_5){
            textView.setText("나쁨");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_6){
            textView.setText("상당히 나쁨");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_7){
            textView.setText("매우 나쁨");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_8){
            textView.setText("최악");
        }

    }

    //미세먼지 단계 표시
    private void setMiseGrade(int miseGrade) {

        mImgCurrentState1.setVisibility(View.GONE);
        mImgCurrentState2.setVisibility(View.GONE);
        mImgCurrentState3.setVisibility(View.GONE);
        mImgCurrentState4.setVisibility(View.GONE);
        mImgCurrentState5.setVisibility(View.GONE);
        mImgCurrentState6.setVisibility(View.GONE);
        mImgCurrentState7.setVisibility(View.GONE);
        mImgCurrentState8.setVisibility(View.GONE);

        if(miseGrade== MiseConst.MISE_GRADE_1){
            mImgCurrentState1.setVisibility(View.VISIBLE);
            mTextCurrentState.setText("최고");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_2){
            mImgCurrentState2.setVisibility(View.VISIBLE);
            mTextCurrentState.setText("좋음");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_3){
            mImgCurrentState3.setVisibility(View.VISIBLE);
            mTextCurrentState.setText("양호");
        }
        else if(miseGrade == MiseConst.MISE_GRADE_4){
            mImgCurrentState4.setVisibility(View.VISIBLE);
            mTextCurrentState.setText("보통");

        }
        else if(miseGrade == MiseConst.MISE_GRADE_5){
            mImgCurrentState5.setVisibility(View.VISIBLE);
            mTextCurrentState.setText("나쁨");

        }
        else if(miseGrade == MiseConst.MISE_GRADE_6){
            mImgCurrentState6.setVisibility(View.VISIBLE);
            mTextCurrentState.setText("상당히 나쁨");

        }
        else if(miseGrade == MiseConst.MISE_GRADE_7){
            mImgCurrentState7.setVisibility(View.VISIBLE);
            mTextCurrentState.setText("매우 나쁨");

        }
        else if(miseGrade == MiseConst.MISE_GRADE_8){
            mImgCurrentState8.setVisibility(View.VISIBLE);
            mTextCurrentState.setText("최악");

        }



    }

    //현재 날짜와 시간 구하기
    public String getNow(){

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);

        return mFormat.format(mDate);
    }


}

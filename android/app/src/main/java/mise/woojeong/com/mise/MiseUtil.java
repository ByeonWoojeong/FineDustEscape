package mise.woojeong.com.mise;

import android.widget.TextView;

/**
 * Created by apple on 2018. 7. 21..
 */

public class MiseUtil {

    //메인 단계
    public static int getMiseGrade(float pm10, float pm25){
        int result = 0;
        int pm10Grade = 0, pm25Grade =0;

        if (0 <= pm10 && pm10 <= 15) {
            pm10Grade = MiseConst.MISE_GRADE_1;

        }
        else if (16 <= pm10 && pm10 <= 30) {
            pm10Grade = MiseConst.MISE_GRADE_2;
        }
        else if (31 <= pm10 && pm10 <= 40){
            pm10Grade = MiseConst.MISE_GRADE_3;
        }
        else if (41 <= pm10 && pm10 <= 50){
            pm10Grade = MiseConst.MISE_GRADE_4;
        }
        else if (51 <= pm10 && pm10 <= 75){
            pm10Grade = MiseConst.MISE_GRADE_5;
        }
        else if (76 <= pm10 && pm10 <= 100){
            pm10Grade = MiseConst.MISE_GRADE_6;
        }
        else if (101 <= pm10 && pm10 <= 150){
            pm10Grade = MiseConst.MISE_GRADE_7;
        }
        else if (151 <= pm10){
            pm10Grade = MiseConst.MISE_GRADE_8;
        }

        if (0 <= pm25 && pm25 <= 8) {
            pm25Grade = MiseConst.MISE_GRADE_1;
        }
        else if (9 <= pm25 && pm25 <= 15) {
            pm25Grade = MiseConst.MISE_GRADE_2;
        }
        else if (16 <= pm25 && pm25 <= 20) {
            pm25Grade = MiseConst.MISE_GRADE_3;
        }
        else if (21 <= pm25 && pm25 <= 25) {
            pm25Grade = MiseConst.MISE_GRADE_4;
        }
        else if (26 <= pm25 && pm25 <= 37) {
            pm25Grade = MiseConst.MISE_GRADE_5;
        }
        else if (38 <= pm25 && pm25 <= 50) {
            pm25Grade = MiseConst.MISE_GRADE_6;
        }
        else if (51 <= pm25 && pm25 <= 75) {
            pm25Grade = MiseConst.MISE_GRADE_7;
        }
        else if (76 <= pm25 ) {
            pm25Grade = MiseConst.MISE_GRADE_8;
        }

        //둘 중 더 높은 결과값
        result = Math.max(pm10Grade, pm25Grade);
        return result;
    }

    //미세먼지
    public static int getPm10Grade(float pm10){

        if (0 <= pm10 && pm10 <= 15) {
            return MiseConst.MISE_GRADE_1;
        }
        else if (16 <= pm10 && pm10 <= 30) {
            return MiseConst.MISE_GRADE_2;
        }
        else if (31 <= pm10 && pm10 <= 40){
            return MiseConst.MISE_GRADE_3;
        }
        else if (41 <= pm10 && pm10 <= 50){
            return MiseConst.MISE_GRADE_4;
        }
        else if (51 <= pm10 && pm10 <= 75){
            return MiseConst.MISE_GRADE_5;
        }
        else if (76 <= pm10 && pm10 <= 100){
            return MiseConst.MISE_GRADE_6;
        }
        else if (101 <= pm10 && pm10 <= 150){
            return MiseConst.MISE_GRADE_7;
        }
        else if (151 <= pm10){
            return MiseConst.MISE_GRADE_8;
        }

        return 0;
    }

    //초미세먼지
    public static int getPm25Grade(float pm25){

        if (0 <= pm25 && pm25 <= 8) {
            return MiseConst.MISE_GRADE_1;
        }
        else if (9 <= pm25 && pm25 <= 15) {
            return MiseConst.MISE_GRADE_2;
        }
        else if (16 <= pm25 && pm25 <= 20) {
            return MiseConst.MISE_GRADE_3;
        }
        else if (21 <= pm25 && pm25 <= 25) {
            return MiseConst.MISE_GRADE_4;
        }
        else if (26 <= pm25 && pm25 <= 37) {
            return MiseConst.MISE_GRADE_5;
        }
        else if (38 <= pm25 && pm25 <= 50) {
            return MiseConst.MISE_GRADE_6;
        }
        else if (51 <= pm25 && pm25 <= 75) {
            return MiseConst.MISE_GRADE_7;
        }
        else if (76 <= pm25 ) {
            return MiseConst.MISE_GRADE_8;
        }

        return 0;
    }

    //이산화질소
    public static int getNo2Grade(float no2){

        if(0 < no2 && no2 < 0.02){
            return MiseConst.MISE_GRADE_1;  //최고
        }
        else if(0.02 <= no2 && no2 < 0.03){
            return MiseConst.MISE_GRADE_2;
        }
        else if(0.03 <= no2 && no2 < 0.05){
            return MiseConst.MISE_GRADE_3;
        }
        else if(0.05 <= no2 && no2 < 0.06){
            return MiseConst.MISE_GRADE_4;
        }
        else if(0.06 <= no2 && no2 < 0.13){
            return MiseConst.MISE_GRADE_5;
        }
        else if(0.13 <= no2 && no2 < 0.2){
            return MiseConst.MISE_GRADE_6;
        }
        else if(0.2 <= no2 && no2 < 1.1){
            return MiseConst.MISE_GRADE_7;
        }
        else if(1.1 <= no2){
            return MiseConst.MISE_GRADE_8;
        }

        return 0;
    }

    //오존
    public static int getO3Grade(float o3){

        if(0 < o3 && o3 < 0.02){
            return MiseConst.MISE_GRADE_1;  //최고
        }
        else if(0.02 <= o3 && o3 < 0.03){
            return MiseConst.MISE_GRADE_2;
        }
        else if(0.03 <= o3 && o3 < 0.06){
            return MiseConst.MISE_GRADE_3;
        }
        else if(0.06 <= o3 && o3 < 0.09){
            return MiseConst.MISE_GRADE_4;
        }
        else if(0.09 <= o3 && o3 < 0.12){
            return MiseConst.MISE_GRADE_5;
        }
        else if(0.12 <= o3 && o3 < 0.15){
            return MiseConst.MISE_GRADE_6;
        }
        else if(0.15 <= o3 && o3 < 0.38){
            return MiseConst.MISE_GRADE_7;
        }
        else if(0.38 <= o3){
            return MiseConst.MISE_GRADE_8;
        }

        return 0;
    }

    //일산화탄소
    public static int getCoGrade(float co){

        if(0 < co && co < 1){
            return MiseConst.MISE_GRADE_1;  //최고
        }
        else if(1 <= co && co < 2){
            return MiseConst.MISE_GRADE_2;
        }
        else if(2 <= co && co < 5.5){
            return MiseConst.MISE_GRADE_3;
        }
        else if(5.5 <= co && co < 9){
            return MiseConst.MISE_GRADE_4;
        }
        else if(9 <= co && co < 12){
            return MiseConst.MISE_GRADE_5;
        }
        else if(12 <= co && co < 15){
            return MiseConst.MISE_GRADE_6;
        }
        else if(15 <= co && co < 32){
            return MiseConst.MISE_GRADE_7;
        }
        else if(32 <= co){
            return MiseConst.MISE_GRADE_8;
        }

        return 0;
    }

    //아황산가스
    public static int getSo2Grade(float so2){

        if(0 < so2 && so2 < 0.01){
            return MiseConst.MISE_GRADE_1;  //최고
        }
        else if(0.01 <= so2 && so2 < 0.02){
            return MiseConst.MISE_GRADE_2;
        }
        else if(0.02 <= so2 && so2 < 0.04){
            return MiseConst.MISE_GRADE_3;
        }
        else if(0.04 <= so2 && so2 < 0.05){
            return MiseConst.MISE_GRADE_4;
        }
        else if(0.05 <= so2 && so2 < 0.1){
            return MiseConst.MISE_GRADE_5;
        }
        else if(0.1 <= so2 && so2 < 0.15){
            return MiseConst.MISE_GRADE_6;
        }
        else if(0.15 <= so2 && so2 < 0.6){
            return MiseConst.MISE_GRADE_7;
        }
        else if(0.6 <= so2){
            return MiseConst.MISE_GRADE_8;
        }

        return 0;
    }
}

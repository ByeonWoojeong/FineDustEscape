package mise.woojeong.com.mise;

import java.util.ArrayList;

/**
 * Created by apple on 2018. 7. 21..
 */

public class MiseInfoVO {

    String pm10Value = "";  //미세먼지 농도
    String pm25Value = "";  //초미세먼지 농도

    String no2Value = "";   //이산화질소 농도
    String o3Value = "";    //오존 농도
    String coValue = "";    //일산화탄소 농도
    String so2Value = "";   //아황산가스 농도

    public String getPm10Value() {
        return pm10Value;
    }

    public void setPm10Value(String pm10Value) {
        this.pm10Value = pm10Value;
    }

    public String getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(String pm25Value) {
        this.pm25Value = pm25Value;
    }

    public String getNo2Value() {
        return no2Value;
    }

    public void setNo2Value(String no2Value) {
        this.no2Value = no2Value;
    }

    public String getO3Value() {
        return o3Value;
    }

    public void setO3Value(String o3Value) {
        this.o3Value = o3Value;
    }

    public String getCoValue() {
        return coValue;
    }

    public void setCoValue(String coValue) {
        this.coValue = coValue;
    }

    public String getSo2Value() {
        return so2Value;
    }

    public void setSo2Value(String so2Value) {
        this.so2Value = so2Value;
    }

}

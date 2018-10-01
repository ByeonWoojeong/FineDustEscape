package mise.woojeong.com.mise;

/**
 * Created by apple on 2018. 7. 28..
 */

public class IntegerUtil {

    public static int parseIntDefault(String str, int defaultValue) {

        int result = defaultValue;

        try {
            result = Integer.parseInt(str);
        }
        catch (Exception e) {
            result = defaultValue;
        }

        return result;
    }

    public static float parseFloatDefault(String str, float defaultValue){

        float result = defaultValue;

        try {
            result = Float.parseFloat(str);
        }catch(Exception e){
            result = defaultValue;
        }

        return result;
    }
}

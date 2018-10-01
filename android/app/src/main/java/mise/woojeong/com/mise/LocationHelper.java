package mise.woojeong.com.mise;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 2018. 8. 4..
 */

public class LocationHelper {

    private MyLocation location;

    Context context;

    Timer timer;
    LocationManager lm;
    boolean gps_enabled=false;
    boolean network_enabled=false;
    ILocation activity;

    private LocationHelper(){
    }

    public LocationHelper(Context context){
        this();

        this.context = context;
        location = new MyLocation();
    }

    public LocationHelper(Context context, ILocation activity){
        this(context);

        this.activity = activity;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            gotLocation(location);
            // gps will keep going
            lm.removeUpdates(locationListenerNetwork);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);

            Location net_loc=null, gps_loc=null;

            //
            try {

                if (gps_enabled) {
                    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
                if (network_enabled) {
                    if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
            }catch(SecurityException e) {
            }
            //

            //if there are both values use the latest one
            if(gps_loc!=null && net_loc!=null){
                if(gps_loc.getTime() > net_loc.getTime()){
                    gotLocation(gps_loc);
                }else{
                    gotLocation(net_loc);
                }
                return;
            }

            if(gps_loc!=null){
                gotLocation(gps_loc);
                return;
            }
            if(net_loc!=null){
                gotLocation(net_loc);
                return;
            }

            gotLocation(null);
        }
    }

    private void gotLocation(Location newLocation){
        if(newLocation != null){
            if(activity != null){
                activity.updateDirectionOnObjects(newLocation);
            }

            location.setLatitude(newLocation.getLatitude());
            location.setLongitude(newLocation.getLongitude());
        }
    }

    public MyLocation getLocation() {
        return location;
    }

    public void register() {
        if(lm==null){
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        //exceptions will be thrown if provider is not permitted.
        try{gps_enabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
        try{network_enabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

        //don't start listeners if no provider is enabled
        if(gps_enabled || network_enabled){

            try {

                if (gps_enabled) {
                    if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 12000, 0, locationListenerGps);
                    }
                }
                if (network_enabled) {
                    if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
                    }
                }
            }catch(SecurityException e) {
                e.printStackTrace();
            }


            timer=new Timer();
            timer.schedule(new GetLastLocation(), 20000);
        }
    }

    public void unregister() {
        lm.removeUpdates(locationListenerGps);
        lm.removeUpdates(locationListenerNetwork);
    }
}


package com.binding.model.util.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by pc on 2017/8/22.
 */

public class LocationUtil implements LocationListener{
    private Activity activity;
    public void init(Activity activity){
        this.activity = activity;
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) return;
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 定位的精准度
        criteria.setAltitudeRequired(false);          // 海拔信息是否关注
        criteria.setBearingRequired(false); // 对周围的事物是否关心
        criteria.setCostAllowed(true);  // 是否支持收费查询
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 是否耗电
        criteria.setSpeedRequired(false); // 对速度是否关注
        String provider = locationManager.getBestProvider(criteria, true);
        if(!checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION))return;
            locationManager.requestLocationUpdates(provider, 5000, 0, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if(location == null)return;
        Geocoder gc = new Geocoder(activity, Locale.CHINA);
        List<Address> lstAddress = null;
        try {
            lstAddress = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (lstAddress.size() > 0) {
                Address  result = lstAddress.get(0);
                Timber.i("Address: %1s ,%2s", result.getCountryName(),result.getLocality());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new RxPermissions(activity)
//                .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
//                .subscribe(granted -> {
//                    if (granted) {
//
//
//                    }
//                });
    }


    private boolean checkSelfPermission(Activity activity,String... permissions){
        for(String permission : permissions){
            if(ActivityCompat.checkSelfPermission(activity,permission) !=  PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
//        Address address = location.
        Timber.i("Latitude:%1d Longitude:%2d",location.getLatitude(),location.getLatitude());

        Geocoder gc = new Geocoder(activity, Locale.CHINA);
        List<Address> lstAddress = null;
        try {
            lstAddress = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (lstAddress.size() > 0) {
                Address  result = lstAddress.get(0);
                Timber.i("Address: %1s ,%2s", result.getCountryName(),result.getLocality());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

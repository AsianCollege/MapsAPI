package com.asmt.asmtmaps;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

	public GoogleMap mMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	    mapFragment.getMapAsync(this);
	   
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);		
        mMap.setTrafficEnabled(true);
        
        mMap.getUiSettings().setCompassEnabled(true);  
        
        LatLng latlngKtm = getLatLng("kathmandu");
        mMap.addMarker(new MarkerOptions().position(latlngKtm).title(getLocale(latlngKtm)));
        
        LatLng latlngPatan = getLatLng("Patan");
        LatLng latlngBkt = getLatLng("Bhaktapur");
        mMap.addMarker(new MarkerOptions().position(latlngPatan).title(getLocale(latlngPatan)));
        mMap.addMarker(new MarkerOptions().position(latlngBkt).title(getLocale(latlngBkt)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngKtm,10));
		
        
	}
	public LatLng getLatLng(String city){		
		Geocoder geocoder = new Geocoder(this);
        List<Address> adresList = null;
        try {
            adresList = geocoder.getFromLocationName(city,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
      
        Address address = adresList.get(0);
        LatLng latlng = new LatLng(address.getLatitude(),address.getLongitude());
		return latlng;
	}
	public void zoomIn(View v){
		mMap.animateCamera(CameraUpdateFactory.zoomIn());
	}
	public void zoomOut(View v){
		mMap.animateCamera(CameraUpdateFactory.zoomOut());
	}
	public String getLocale(LatLng latlng){
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		String finalAddress = "";
		StringBuilder builder = new StringBuilder();
		try {
		    List<Address> address = geoCoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
		    int maxLines = address.get(0).getMaxAddressLineIndex();
		    for (int i=0; i<maxLines; i++) {
			    String addressStr = address.get(0).getAddressLine(i);
			    builder.append(addressStr);
			    builder.append(" ");
		    }

		    finalAddress = builder.toString(); //This is the complete address.
		} catch (IOException e) {}
		  catch (NullPointerException e) {}
		return finalAddress;
	}
	
	public void gotoActivityB(View v){
		startActivity(new Intent(this,NearByLocationActivity.class));
	}

}

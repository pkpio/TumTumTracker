package in.co.praveenkumar.tumtumtracker.task;

import in.co.praveenkumar.tumtumtracker.R;
import in.co.praveenkumar.tumtumtracker.helper.MapHelper;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.helper.Session;
import in.co.praveenkumar.tumtumtracker.model.TTTMarker;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("UseSparseArrays")
public class MapHandler {
	FragmentManager mFragmentManager;
	GoogleMap mMap;

	// Last open windows detection
	HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
	Integer lastOpenWindowsId = 0;

	public MapHandler(FragmentManager mFragmentManager) {
		this.mFragmentManager = mFragmentManager;
		setUpMapIfNeeded();
	}

	/**
	 * Overlay current markers. This will clear all old markers.
	 */
	public void overlayMarkers() {
		if (Session.response == null)
			if (!Session.init())
				return;
		List<TTTMarker> mMarkers = Session.response.getMarkers();
		TTTMarker mark;
		Marker marker;
		mMap.clear();
		for (int i = 0; i < mMarkers.size(); i++) {
			mark = mMarkers.get(i);
			marker = mMap.addMarker(new MarkerOptions()
					.position(new LatLng(mark.getLat(), mark.getLng()))
					.title(mark.getDescription())
					.snippet(mark.getLastupdated())
					.icon(MapHelper.MarkerIcon(mark.getType())));
			if (mark.getMarkerid() == lastOpenWindowsId)
				marker.showInfoWindow();
			hashMap.put(mark.getMarkerid(), marker.getId());
		}
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated map
		if (mMap == null) {
			this.mMap = ((SupportMapFragment) mFragmentManager
					.findFragmentById(R.id.map)).getMap();

			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
				mMap.setOnMapClickListener(mapClickListener);
				mMap.setOnMarkerClickListener(ttClickListener);
			} else
				return;
		}

		// Set map center and other params if possible
		Boolean useParams = false;
		if (Session.response == null)
			if (!Session.init())
				useParams = true;

		LatLng center = Param.center;
		if (!useParams) {
			double lat = Session.response.getCenter().getLat();
			double lng = Session.response.getCenter().getLng();
			center = new LatLng(lat, lng);
		}

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(center).zoom(Param.zoom).bearing(Param.bearing)
				.tilt(Param.tilt).build();
		mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

	}

	OnMarkerClickListener ttClickListener = new OnMarkerClickListener() {

		@Override
		public boolean onMarkerClick(Marker marker) {
			lastOpenWindowsId = getKeyByValue(marker.getId());
			return false;
		}
	};

	OnMapClickListener mapClickListener = new OnMapClickListener() {

		@Override
		public void onMapClick(LatLng arg0) {
			// Reset last open window
			lastOpenWindowsId = 0;
		}
	};

	public Integer getKeyByValue(String value) {
		for (Entry<Integer, String> entry : hashMap.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
}

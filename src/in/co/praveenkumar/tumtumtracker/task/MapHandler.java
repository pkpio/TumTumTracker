package in.co.praveenkumar.tumtumtracker.task;

import java.util.List;

import in.co.praveenkumar.tumtumtracker.R;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.helper.Session;
import in.co.praveenkumar.tumtumtracker.model.TTTMarker;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapHandler {
	FragmentManager mFragmentManager;
	GoogleMap mMap;

	public MapHandler(FragmentManager mFragmentManager) {
		this.mFragmentManager = mFragmentManager;
		setUpMapIfNeeded();
	}

	/**
	 * Overlay current markers
	 */
	public void overlayMarkers() {
		if (Session.response == null)
			if (!Session.init())
				return;
		List<TTTMarker> mMarkers = Session.response.getMarkers();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated map
		if (mMap == null) {
			this.mMap = ((SupportMapFragment) mFragmentManager
					.findFragmentById(R.id.map)).getMap();

			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.
				// mMap.setOnMapClickListener(mapClickListener);
				// mMap.setOnMarkerClickListener(ttClickListener);
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
}

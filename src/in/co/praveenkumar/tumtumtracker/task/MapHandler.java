package in.co.praveenkumar.tumtumtracker.task;

import in.co.praveenkumar.tumtumtracker.R;
import in.co.praveenkumar.tumtumtracker.helper.MapHelper;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.helper.Session;
import in.co.praveenkumar.tumtumtracker.model.TTTMarker;
import in.co.praveenkumar.tumtumtracker.model.TTTOverviewPoly;
import in.co.praveenkumar.tumtumtracker.model.TTTRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

@SuppressLint("UseSparseArrays")
public class MapHandler {
	FragmentManager mFragmentManager;
	GoogleMap mMap;
	List<Polyline> mRoute;

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

	/**
	 * Plots a route on the map. This clears any route plot that already on map.
	 * 
	 * @param route
	 */
	public void drawRoute(TTTRoute route) {
		System.out.println("Plotting route");
		if (route == null)
			return;

		List<TTTOverviewPoly> polylines = route.getOverviewpolylines();
		if (polylines == null)
			return;

		// Clear existing route plot
		if (mRoute != null)
			for (int i = 0; i < mRoute.size(); i++)
				mRoute.get(i).remove();
		mRoute = new ArrayList<Polyline>();

		// Loop through each path in the route
		for (int i = 0; i < polylines.size(); i++) {

			// Get points that make up a path
			List<LatLng> points = decodePoly(polylines.get(i).getLine());

			// Plot each point as a polyline
			for (int z = 0; z < points.size() - 1; z++) {
				LatLng src = points.get(z);
				LatLng dest = points.get(z + 1);
				Polyline line = mMap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(2).color(Color.BLUE).geodesic(true));

				// Add this to the list of points in route.
				mRoute.add(line);
			}
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
				mMap.setOnMarkerClickListener(markerClickListener);
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

	OnMarkerClickListener markerClickListener = new OnMarkerClickListener() {

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

	public List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;

	}
}

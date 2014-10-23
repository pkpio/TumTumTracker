package in.co.praveenkumar.tumtumtracker;

import in.co.praveenkumar.tumtumtracker.helper.MapHelper;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.helper.Session;
import in.co.praveenkumar.tumtumtracker.helper.TimeFormat;
import in.co.praveenkumar.tumtumtracker.model.TTTMarker;
import in.co.praveenkumar.tumtumtracker.model.TTTOverviewPoly;
import in.co.praveenkumar.tumtumtracker.model.TTTRoute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
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
	TrackerActivity trackerActivity;
	FragmentManager mFragmentManager;
	GoogleMap mMap;
	List<Polyline> mRoute;
	List<TTTMarker> mMarkers;
	TTTRoute currentRoute = null;

	// Last open windows detection
	HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
	Integer lastOpenWindowsId = 0;

	public MapHandler(TrackerActivity trackerActivity) {
		this.trackerActivity = trackerActivity;
		this.mFragmentManager = trackerActivity.getSupportFragmentManager();
		setUpMapIfNeeded();
	}

	/**
	 * Overlay current markers. This will clear all old markers.
	 */
	public void overlayMarkers() {
		// Possible if the device doesn't have playservices installed
		if (mMap == null)
			return;

		if (Session.response == null)
			if (!Session.init())
				return;
		mMarkers = Session.response.getMarkers();
		TTTMarker mark;
		Marker marker;

		// Clear map and draw any previous route plots
		mMap.clear();
		drawRoute(currentRoute);

		for (int i = 0; i < mMarkers.size(); i++) {
			mark = mMarkers.get(i);

			// Position of this marker in the List is saved in snippet for
			// retrieval in infoWindow
			marker = mMap.addMarker(new MarkerOptions()
					.position(new LatLng(mark.getLat(), mark.getLng()))
					.title(mark.getRoute()).snippet(String.valueOf(i))
					.icon(MapHelper.MarkerIcon(mark.getType())));

			// Add this to the hashMap
			hashMap.put(mark.getMarkerid(), marker.getId());

			// Reopen infowindow if this was opened before
			if (mark.getMarkerid() == lastOpenWindowsId)
				marker.showInfoWindow();
		}
	}

	/**
	 * Plots a route on the map. This clears any route plot that already on map.
	 * 
	 * @param route
	 */
	public void drawRoute(TTTRoute route) {
		// Possible if the device doesn't have playservices installed
		if (mMap == null)
			return;

		if (route == null)
			return;

		List<TTTOverviewPoly> polylines = route.getOverviewpolylines();
		if (polylines == null)
			return;

		// Clear existing route plot and set current route
		clearRoutes();
		currentRoute = route;

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
						.width(4).color(MapHelper.RouteColor(route.getColor()))
						.geodesic(true));

				// Add this to the list of points in route.
				mRoute.add(line);
			}
		}
	}

	public void clearRoutes() {
		if (mRoute != null)
			for (int i = 0; i < mRoute.size(); i++)
				mRoute.get(i).remove();
		mRoute = new ArrayList<Polyline>();
		currentRoute = null;
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
				mMap.setInfoWindowAdapter(new TTTInfoWindowAdapter());
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

	class TTTInfoWindowAdapter implements InfoWindowAdapter {

		private final View markerInfoWindow;

		@SuppressLint("InflateParams")
		TTTInfoWindowAdapter() {
			markerInfoWindow = trackerActivity.getLayoutInflater().inflate(
					R.layout.infowindow, null);
		}

		@Override
		public View getInfoContents(Marker marker) {
			if (marker == null)
				return null;

			int pos = Integer.parseInt(marker.getSnippet());
			if (pos < 0 || pos > mMarkers.size())
				return null;

			TTTMarker mark = mMarkers.get(pos);

			TextView route = ((TextView) markerInfoWindow
					.findViewById(R.id.marker_route));
			TextView desc = ((TextView) markerInfoWindow
					.findViewById(R.id.marker_desc));
			TextView speed = ((TextView) markerInfoWindow
					.findViewById(R.id.marker_speed));
			TextView updated = ((TextView) markerInfoWindow
					.findViewById(R.id.marker_updated));

			route.setText(mark.getRoute());
			desc.setText(mark.getDescription());
			speed.setText(mark.getSpeed() + " kmph");
			updated.setText(TimeFormat.RelativeTime(mark.getLastupdated()));

			return markerInfoWindow;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			return null;
		}

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

	private Integer getKeyByValue(String value) {
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

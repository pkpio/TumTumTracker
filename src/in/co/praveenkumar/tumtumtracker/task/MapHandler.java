package in.co.praveenkumar.tumtumtracker.task;

import in.co.praveenkumar.tumtumtracker.R;
import in.co.praveenkumar.tumtumtracker.helper.MapHelper;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.helper.Session;
import in.co.praveenkumar.tumtumtracker.model.TTTMarker;

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
import com.google.android.gms.maps.model.PolylineOptions;

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

		// Testing poly lines
		List<LatLng> list = decodePoly("covsBqm`|L@@e@Ro@V_A\\]Ne@P{Bp@{@Ni@D_AB_@?}AEq@AmA?wIF_@Bm@Ja@Ne@NmAp@]X]n@MZWdAMl@");
		List<LatLng> list2 = decodePoly("aqvsBe__|LXNZJVFT@P?b@GVI^OZQJINUFYFa@Dc@D[@_@@U@S?]Ak@A]Ek@Gq@Ea@M]Yk@Sc@GQCe@Ei@Cm@@QFQFMJKLM??FO@UKSy@i@QKAa@Ak@G{@A[@e@Da@Fi@]aA_@a@aAa@cHjCsE\\EgC");
		List<LatLng> list3 = decodePoly("g|wsB{}_|L}FnU");
		List<LatLng> list4 = decodePoly("edxsBkg_|L}@jDa@~AYpAUz@]fBYhBYtCYzB");
		List<LatLng> list5 = decodePoly("edxsBkg_|LqJoAPuDI_DBu@^qDByAMoBMw@");
		List<LatLng> list6 = decodePoly("coxsB{c`|L~GxAzHdB");
		List<LatLng> list7 = decodePoly("coxsB{c`|L_Dm@cCQ");
		List<LatLng> list8 = decodePoly("gxxsB{e`|LmCwAQMIOCU@UJ_AH_@Ne@LYzAyBnB{B^Ul@G`JEZAJCDGFGFMFG^OzEFF@@B@BLhABBXF|Dc@JCFo@Cq@@OFQHMJKLGFAzJ[RA");
		List<LatLng> list9 = decodePoly("ugwsBy~`|LThO@vA?Z?zA");

		for (int z = 0; z < list.size() - 1; z++) {
			LatLng src = list.get(z);
			LatLng dest = list.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
		}

		for (int z = 0; z < list2.size() - 1; z++) {
			LatLng src = list2.get(z);
			LatLng dest = list2.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
		}

		for (int z = 0; z < list3.size() - 1; z++) {
			LatLng src = list3.get(z);
			LatLng dest = list3.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
		}

		for (int z = 0; z < list4.size() - 1; z++) {
			LatLng src = list4.get(z);
			LatLng dest = list4.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
		}

		for (int z = 0; z < list5.size() - 1; z++) {
			LatLng src = list5.get(z);
			LatLng dest = list5.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
		}

		for (int z = 0; z < list6.size() - 1; z++) {
			LatLng src = list6.get(z);
			LatLng dest = list6.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
		}

		for (int z = 0; z < list7.size() - 1; z++) {
			LatLng src = list7.get(z);
			LatLng dest = list7.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
		}

		for (int z = 0; z < list8.size() - 1; z++) {
			LatLng src = list8.get(z);
			LatLng dest = list8.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
		}

		for (int z = 0; z < list9.size() - 1; z++) {
			LatLng src = list9.get(z);
			LatLng dest = list9.get(z + 1);
			mMap.addPolyline(new PolylineOptions()
					.add(new LatLng(src.latitude, src.longitude),
							new LatLng(dest.latitude, dest.longitude)).width(2)
					.color(Color.BLUE).geodesic(true));
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

	// -TODO-testing code
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

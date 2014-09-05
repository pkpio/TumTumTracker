package in.co.praveenkumar.tumtumtracker.task;

import in.co.praveenkumar.tumtumtracker.R;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MapHandler {
	FragmentManager mFragmentManager;
	GoogleMap mMap;

	public MapHandler(FragmentManager mFragmentManager) {
		this.mFragmentManager = mFragmentManager;
		setUpMapIfNeeded();
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
			}
		}
	}

}

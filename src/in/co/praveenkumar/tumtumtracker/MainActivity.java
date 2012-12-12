package in.co.praveenkumar.tumtumtracker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends MapActivity {
	// url to make request
	private static String url = "http://home.iitb.ac.in/~praveendath92/TTT/markers.json";

	// JSON Node names
	// private static final String TAG_TITLE = "title";
	// private static final String TAG_CENTER = "id";
	// private static final String TAG_CENTER_LAT = "lat";
	// private static final String TAG_CENTER_LNG = "lng";
	private static final String TAG_MARKERS = "markers";
	// private static final String TAG_ID = "id";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_LAST_UPDATED = "lastupdated";

	// contacts JSONArray
	JSONArray markers = null;

	// Mapview variables
	MapView mapView;
	List<Overlay> mapOverlays;
	Drawable drawable;
	ItemizedMarkersOverlay itemizedoverlay;
	private MapController myMapController;

	// Define default center point
	GeoPoint centerGeoPoint = new GeoPoint((int) (19.134786 * 1E6),
			(int) (72.914584 * 1E6));

	// Other declarations
	ProgressDialog firstTimeDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapView = (MapView) findViewById(R.id.mapview);
		myMapController = mapView.getController();
		myMapController.animateTo(centerGeoPoint);
		myMapController.setZoom(17);

		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		itemizedoverlay = new ItemizedMarkersOverlay(drawable, this);

		if (lastKnownLocations()) {
			extractToItemizedoverlayFromJSONFile();
			new tryAsyncJSONextract().execute(url);
		} else {
			// Setting up dialogs
			firstTimeDialog = new ProgressDialog(MainActivity.this);
			firstTimeDialog.setMessage("Loading for the first time.");
			firstTimeDialog.setIndeterminate(true);
			firstTimeDialog.setCancelable(false);
			firstTimeDialog.show();
			new tryAsyncJSONextract().execute(url);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			showDialog(0);
			break;
		case R.id.credits:
			showDialog(1);
			break;
		}
		return true;
	}

	public void extractToItemizedoverlayFromJSONUrl(String url) {
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromUrl(url);

		try {
			// Getting Array of Markers
			markers = json.getJSONArray(TAG_MARKERS);

			// Clear existing Markers -- mapView.invalidate() is done on post
			// execute
			clearExistingMarkers();

			// looping through All Markers
			for (int i = 0; i < markers.length(); i++) {
				JSONObject c = markers.getJSONObject(i);

				// Storing each json item in variable
				// String id = c.getString(TAG_ID);
				double lat = c.getDouble(TAG_LAT);
				double lng = c.getDouble(TAG_LNG);
				String description = c.getString(TAG_DESCRIPTION);
				String lastupdated = c.getString(TAG_LAST_UPDATED);

				GeoPoint point = new GeoPoint((int) (lat * 1E6),
						(int) (lng * 1E6));
				OverlayItem overlayitem = new OverlayItem(point, description,
						"Last updated : " + lastupdated);
				itemizedoverlay.addOverlay(overlayitem);
			}
			mapOverlays.add(itemizedoverlay);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void extractToItemizedoverlayFromJSONFile() {
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		JSONObject json = jParser.getJSONFromFile();

		try {
			// Getting Array of Contacts
			markers = json.getJSONArray(TAG_MARKERS);

			// Remove existing markers
			clearExistingMarkers();

			// looping through All Contacts
			for (int i = 0; i < markers.length(); i++) {
				JSONObject c = markers.getJSONObject(i);

				// Storing each json item in variable
				// String id = c.getString(TAG_ID);
				double lat = c.getDouble(TAG_LAT);
				double lng = c.getDouble(TAG_LNG);
				String description = c.getString(TAG_DESCRIPTION);
				String lastupdated = c.getString(TAG_LAST_UPDATED);

				GeoPoint point = new GeoPoint((int) (lat * 1E6),
						(int) (lng * 1E6));
				OverlayItem overlayitem = new OverlayItem(point, description,
						"Last updated : " + lastupdated);
				itemizedoverlay.addOverlay(overlayitem);
			}
			mapOverlays.add(itemizedoverlay);

			// Updating last modified location...
			String jsonRelPath = jParser.jsonFile;
			File filePath = new File(Environment.getExternalStorageDirectory(),
					jsonRelPath);
			Date lastModDate = new Date(filePath.lastModified());
			SimpleDateFormat format = new SimpleDateFormat("MM/dd hh:mm:ss a");
			String lastModDateformatted = format.format(lastModDate);

			TextView lastUpdatedTextView = (TextView) this
					.findViewById(R.id.lastUpdatedView);
			lastUpdatedTextView.setText("last updated : "
					+ lastModDateformatted);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	// Async thread for network activity
	private class tryAsyncJSONextract extends AsyncTask<String, Integer, Long> {
		protected Long doInBackground(String... url) {
			extractToItemizedoverlayFromJSONUrl(url[0]);
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected void onPostExecute(Long result) {
			mapView.invalidate();
			try {
				firstTimeDialog.dismiss();
			} catch (Exception e) {
				// nothing
			}
			updateLastUpdated();
			new tryAsyncJSONextract().execute(url);
		}
	}

	private void updateLastUpdated() {
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("MM/dd hh:mm:ss a");
		String lastModDateformatted = format.format(now);
		TextView lastUpdatedTextView = (TextView) this
				.findViewById(R.id.lastUpdatedView);
		lastUpdatedTextView.setText("last updated : " + lastModDateformatted);
	}

	private void clearExistingMarkers() {
		if (itemizedoverlay.size() != 0)
			itemizedoverlay.removeAllOverlays();
		if (!mapOverlays.isEmpty())
			mapOverlays.clear();
	}

	private boolean lastKnownLocations() {

		// Check if folder exist. Create one if it doesn't
		String file = android.os.Environment.getExternalStorageDirectory()
				.getPath() + "/TumTumTracker";
		File f = new File(file);
		if (!f.exists()) {
			if (!f.mkdirs()) {
				Log.e("TravellerLog :: ", "Problem creating course folder");
				Toast.makeText(getBaseContext(),
						"failed to create folder " + file, Toast.LENGTH_SHORT)
						.show();
			}
			return false;
		}
		file = android.os.Environment.getExternalStorageDirectory().getPath()
				+ "/TumTumTracker/lastKnownLocations.txt";
		f = new File(file);
		if (!f.exists() || f.length() <= 0) {
			return false;
		} else {
			return true;
		}

	}

	public Dialog onCreateDialog(int id) {
		final Dialog dialog = new Dialog(this);

		switch (id) {
		case 0:
			dialog.setContentView(R.layout.about);
			dialog.setTitle("About TumTumTracker");
			break;

		case 1:
			dialog.setContentView(R.layout.credits);
			dialog.setTitle("Credits");
			break;

		case 2:

			break;
		}
		return dialog;
	}
}

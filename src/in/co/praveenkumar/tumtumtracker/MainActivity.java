package in.co.praveenkumar.tumtumtracker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {
	// Settings
	private static String url = "http://home.iitb.ac.in/~praveendath92/TTT/markers.json";
	final int updateDelay = 1500; // In milliseconds

	// JSON Node names
	private static final String TAG_MARKERS = "markers";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_LAST_UPDATED = "lastupdated";
	private static final String TAG_TYPE = "type";

	// JSON variables
	JSONArray markers = null;
	JSONObject json = null;

	// Map variables
	GoogleMap mMap;
	private static final LatLng defaultCenter = new LatLng(19.131481, 72.915296);

	// Other declarations
	ProgressDialog firstTimeDialog;
	Marker mapMarkers[] = null;
	Boolean updateLastUpdated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setUpMapIfNeeded();

		// Setting up Camera params..center, zoom, tilt, bearing
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(defaultCenter) // center map to IITB campus
				.zoom(17) // zoom level 15
				.bearing(0) // orientation of the camera to north(default)
				.tilt(60) // tilt of the camera to 60 degrees
				.build(); // Create a CameraPosition from the builder
		mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

		if (lastKnownLocations()) {
			getJsonFromFile();
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

	public void getJsonFromFile() {
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from File
		json = jParser.getJSONFromFile();

		// Extract Markers from Json and plot
		overlayMarkersFromJson(json);

		// Update last updated time
		updateLastUpdated(false);// False => Update for file
	}

	public void getJsonFromUrl(String url) {
		// Creating JSON Parser instance
		JSONParser jParser = new JSONParser();

		// getting JSON string from URL
		json = jParser.getJSONFromUrl(url);

		// Plotting can't be done here as it is executed in Background thread
	}

	public Boolean overlayMarkersFromJson(JSONObject json) {

		if (json != null) {
			updateLastUpdated = true;
			try {
				// Getting Array of Markers
				markers = json.getJSONArray(TAG_MARKERS);

				// Clear existing markers
				mMap.clear();

				// looping through All Markers
				for (int i = 0; i < markers.length(); i++) {
					JSONObject c = markers.getJSONObject(i);

					// Storing each json item in variable
					// String id = c.getString(TAG_ID);
					double lat = c.getDouble(TAG_LAT);
					double lng = c.getDouble(TAG_LNG);
					String description = c.getString(TAG_DESCRIPTION);
					String lastupdated = c.getString(TAG_LAST_UPDATED);
					int type = c.getInt(TAG_TYPE);

					switch (type) {
					case 1:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lng))
								.title(description)
								.snippet("Last updated : " + lastupdated)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.androidmarker_blue)));
						break;
					case 2:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lng))
								.title(description)
								.snippet("Last updated : " + lastupdated)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.androidmarker_green)));
						break;
					case 3:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lng))
								.title(description)
								.snippet("Last updated : " + lastupdated)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.androidmarker_red)));
						break;
					case 4:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lng))
								.title(description)
								.snippet("Last updated : " + lastupdated)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.androidmarker_yellow)));
						break;
					// Arbitrarily chosen type = 10 for bus stops
					case 10:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lng))
								.title(description)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.bus_stop)));
						break;
					default:
						mMap.addMarker(new MarkerOptions()
								.position(new LatLng(lat, lng))
								.title(description)
								.snippet("Last updated : " + lastupdated)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.androidmarker_black)));
						break;

					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} else {
			updateLastUpdated = false;
		}

		return null;

	}

	// Async thread for network activity
	private class tryAsyncJSONextract extends AsyncTask<String, Integer, Long> {

		protected Long doInBackground(String... url) {
			getJsonFromUrl(url[0]);
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
		}

		protected void onPostExecute(Long result) {
			try {
				firstTimeDialog.dismiss();
			} catch (Exception e) {
				// nothing
			}
			overlayMarkersFromJson(json);
			updateLastUpdated(true); // True => Update for URL
			// Wait before trying for next update..
			Handler myHandler = new Handler();
			myHandler.postDelayed(delayedUpdateLooper, updateDelay);
		}
	}

	private Runnable delayedUpdateLooper = new Runnable() {
		@Override
		public void run() {
			new tryAsyncJSONextract().execute(url);
		}
	};

	@SuppressLint("SimpleDateFormat")
	private void updateLastUpdated(Boolean updateType) {
		if (updateLastUpdated) {
			if (updateType) {
				Date now = new Date(System.currentTimeMillis());
				SimpleDateFormat format = new SimpleDateFormat(
						"MM/dd hh:mm:ss a");
				String lastModDateformatted = format.format(now);
				TextView lastUpdatedTextView = (TextView) this
						.findViewById(R.id.lastUpdatedView);
				lastUpdatedTextView.setText("last updated : "
						+ lastModDateformatted);
			} else {
				// Creating JSON Parser instance to get json file location
				/****
				 * JSON file location is a constant string defined in JSONParser class
				 * We are getting file to update last updated as file updated time as we
				 * are loading from file right now
				****/
				JSONParser jParser = new JSONParser();
				String jsonRelPath = jParser.jsonFile;
				File filePath = new File(
						Environment.getExternalStorageDirectory(), jsonRelPath);
				Date lastModDate = new Date(filePath.lastModified());
				SimpleDateFormat format = new SimpleDateFormat(
						"MM/dd hh:mm:ss a");
				String lastModDateformatted = format.format(lastModDate);

				TextView lastUpdatedTextView = (TextView) this
						.findViewById(R.id.lastUpdatedView);
				lastUpdatedTextView.setText("last updated : "
						+ lastModDateformatted);
			}
		}
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
		case R.id.help:
			showDialog(2);
			break;
		case R.id.changelog:
			showDialog(3);
			break;
		}
		return true;
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
				     .findFragmentById(R.id.map)).getMap();

			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.

			}
		}
	}

	public Dialog onCreateDialog(int id) {
		final Dialog dialog = new Dialog(this);

		switch (id) {
		case 0:
			dialog.setContentView(R.layout.about);
			dialog.setTitle("About TumTumTracker");
			PackageInfo pInfo;
			String appVersion = null;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				appVersion = pInfo.versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getBaseContext(), "unable get app version",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			TextView version = (TextView) dialog.findViewById(R.id.version);
			version.setText("Version : " + appVersion);

			break;

		case 1:
			dialog.setContentView(R.layout.credits);
			dialog.setTitle("Credits");
			break;

		case 2:
			dialog.setContentView(R.layout.help);
			dialog.setTitle("Help");

			break;

		case 3:
			dialog.setContentView(R.layout.change_log);
			dialog.setTitle("Change log");

			break;
		}
		return dialog;
	}

}
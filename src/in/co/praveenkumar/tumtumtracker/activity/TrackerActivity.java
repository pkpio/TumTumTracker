package in.co.praveenkumar.tumtumtracker.activity;

import in.co.praveenkumar.tumtumtracker.R;
import in.co.praveenkumar.tumtumtracker.adapter.NavigationDrawer;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.task.MapHandler;
import in.co.praveenkumar.tumtumtracker.task.MarkerSync;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public class TrackerActivity extends NavigationDrawer {
	MapHandler mapHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracker);
		setUpDrawer();
		mapHandler = new MapHandler(getSupportFragmentManager());
		mapHandler.overlayMarkers();
		new AsyncMarkerSync().execute("");
	}

	/**
	 * Network sync thread
	 * 
	 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
	 */
	private class AsyncMarkerSync extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... url) {
			MarkerSync ms = new MarkerSync();
			return ms.syncMarkers();
		}

		@Override
		protected void onPostExecute(Boolean syncStatus) {
			// Start next update after some wait.
			Handler myHandler = new Handler();
			myHandler.postDelayed(syncLooper, Param.frequency);
		}

	}

	/**
	 * A runnable to support looping sync task.
	 */
	private Runnable syncLooper = new Runnable() {
		@Override
		public void run() {
			new AsyncMarkerSync().execute("");
		}
	};

}

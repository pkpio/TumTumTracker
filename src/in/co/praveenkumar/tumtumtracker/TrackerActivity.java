package in.co.praveenkumar.tumtumtracker;

import in.co.praveenkumar.tumtumtracker.AppInterface.RoutePlotter;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.model.TTTRoute;
import in.co.praveenkumar.tumtumtracker.task.MarkerSync;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class TrackerActivity extends AppNavigationDrawer implements
		RoutePlotter {
	Context context;
	MapHandler mapHandler;
	LoadingMessageDialog loadMessage;
	int fails = 0;
	Boolean stopSync = false;
	AsyncMarkerSync markerSync;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;

		// Loading message setup
		loadMessage = new LoadingMessageDialog(this);
		loadMessage.show();

		setContentView(R.layout.activity_tracker);
		setUpDrawer();

		// MapHandler setup
		mapHandler = new MapHandler(this);
	}

	/**
	 * Network sync thread
	 * 
	 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
	 */
	private class AsyncMarkerSync extends AsyncTask<String, Boolean, Boolean> {

		@Override
		protected Boolean doInBackground(String... url) {
			while (true) {
				MarkerSync ms = new MarkerSync();
				publishProgress(ms.syncMarkers());

				// Stop sync if it needs to be.
				if (stopSync)
					return true;

				// Start next update after some wait.
				try {
					if (fails == 0)
						Thread.sleep(Param.frequency);
					else
						Thread.sleep(Param.failWait);
				} catch (InterruptedException e) {
					System.out.println("Marker sync sleep interrupted!");
				}
			}
		}

		@Override
		protected void onProgressUpdate(Boolean... progress) {
			if (progress[0])
				fails = 0;
			else
				fails++;

			if (fails == 3)
				Toast.makeText(context,
						"Update failed. Data may not be real time!",
						Toast.LENGTH_LONG).show();

			if (fails == 0 || fails == 3)
				loadMessage.dismiss();

			mapHandler.overlayMarkers();
		}
	}

	@Override
	public void plotRoute(TTTRoute route) {
		mapHandler.drawRoute(route);
		this.getLayoutInflater();
	}

	@Override
	public void onResume() {
		super.onResume();
		startSync();
	}

	@Override
	public void onPause() {
		super.onPause();
		stopSync();
	}

	@Override
	public void onStop() {
		super.onStop();
		stopSync();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopSync();
	}

	void stopSync() {
		stopSync = true;
	}

	void startSync() {
		stopSync = false;
		markerSync = new AsyncMarkerSync();
		markerSync.execute("");
	}
}

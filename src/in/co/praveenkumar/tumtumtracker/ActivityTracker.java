package in.co.praveenkumar.tumtumtracker;

import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.model.TTTOverviewPoly;
import in.co.praveenkumar.tumtumtracker.model.TTTRoute;
import in.co.praveenkumar.tumtumtracker.task.MapHandler;
import in.co.praveenkumar.tumtumtracker.task.MarkerSync;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class ActivityTracker extends AppNavigationDrawer {
	Context context;
	MapHandler mapHandler;
	DialogLoadingMessage loadMessage;
	int fails = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;

		// Loading message setup
		loadMessage = new DialogLoadingMessage(this);
		loadMessage.show();

		setContentView(R.layout.activity_tracker);
		setUpDrawer();

		// MapHandler setup
		mapHandler = new MapHandler(getSupportFragmentManager());
		mapHandler.overlayMarkers();

		new AsyncMarkerSync().execute("");

		// Testing
		TTTRoute route = TTTRoute.listAll(TTTRoute.class).get(0);
		List<TTTOverviewPoly> lines = TTTOverviewPoly.find(
				TTTOverviewPoly.class, "parentid = ?", route.getId() + "");
		route.setOverviewpolylines(lines);
		mapHandler.drawRoute(route);
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
			if (syncStatus) {
				fails = 0;
				mapHandler.overlayMarkers();
				loadMessage.dismiss();

				// Start next update after some wait.
				Handler myHandler = new Handler();
				myHandler.postDelayed(syncLooper, Param.frequency);
			} else {
				fails++;
				if (fails == 3) {
					loadMessage.dismiss();
					Toast.makeText(context,
							"Updated failed. Data may not be real time!",
							Toast.LENGTH_LONG).show();
				}

				// Start next attempt after some wait.
				Handler myHandler = new Handler();
				myHandler.postDelayed(syncLooper, Param.failWait);
			}
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

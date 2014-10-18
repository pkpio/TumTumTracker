package in.co.praveenkumar.tumtumtracker;

import in.co.praveenkumar.tumtumtracker.R;
import in.co.praveenkumar.tumtumtracker.helper.GsonExclude;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.model.TTTOverviewPoly;
import in.co.praveenkumar.tumtumtracker.model.TTTRoute;
import in.co.praveenkumar.tumtumtracker.model.TTTRouteResponse;
import in.co.praveenkumar.tumtumtracker.task.MapHandler;
import in.co.praveenkumar.tumtumtracker.task.MarkerSync;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActivityTracker extends AppNavigationDrawer {
	Context context;
	MapHandler mapHandler;
	DialogLoadingMessage loadMessage;
	int fails = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		setupRoutes();

		// Loading message setup
		loadMessage = new DialogLoadingMessage(this);
		loadMessage.show();

		setContentView(R.layout.activity_tracker);
		setUpDrawer();

		// MapHandler setup
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

	void setupRoutes() {
		List<TTTRoute> routes = TTTRoute.listAll(TTTRoute.class);
		if (routes.size() != 0)
			return;

		AssetManager assetManager = context.getAssets();
		try {
			InputStreamReader reader = new InputStreamReader(
					assetManager.open("routes.json"));
			GsonExclude ex = new GsonExclude();
			Gson gson = new GsonBuilder()
					.addDeserializationExclusionStrategy(ex)
					.addSerializationExclusionStrategy(ex).create();
			TTTRouteResponse response = gson.fromJson(reader,
					TTTRouteResponse.class);
			reader.close();

			routes = response.getRoutes();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<TTTOverviewPoly> polylines = new ArrayList<TTTOverviewPoly>();

		for (int i = 0; i < routes.size(); i++) {
			TTTRoute route = routes.get(i);
			route.save();
			polylines = route.getOverviewpolylines();
			for (int j = 0; j < polylines.size(); j++) {
				polylines.get(j).setParentid(route.getId());
				polylines.get(j).save();
			}
		}
	}
}

package in.co.praveenkumar.tumtumtracker.task;

import in.co.praveenkumar.tumtumtracker.helper.GsonExclude;
import in.co.praveenkumar.tumtumtracker.helper.Param;
import in.co.praveenkumar.tumtumtracker.helper.Session;
import in.co.praveenkumar.tumtumtracker.model.TTTMarker;
import in.co.praveenkumar.tumtumtracker.model.TTTSiteResponse;

import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Sync the local marker data with the TTT site
 * 
 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
 * 
 */
public class MarkerSync {
	static final String DEBUG_TAG = "MarkerSync";

	/**
	 * Perform sync. This does network activity so calls should be made only
	 * from a background thread.
	 * 
	 * @return syncStatus
	 */
	public Boolean syncMarkers() {
		NetworkCall nc = new NetworkCall();
		try {
			InputStreamReader reader = nc.execute(Param.dataUrl);
			GsonExclude ex = new GsonExclude();
			Gson gson = new GsonBuilder()
					.addDeserializationExclusionStrategy(ex)
					.addSerializationExclusionStrategy(ex).create();
			TTTSiteResponse response = gson.fromJson(reader,
					TTTSiteResponse.class);
			if (response == null)
				return false;

			// Save to db
			response.setId(Param.responseDbId);
			response.save();

			List<TTTMarker> markers = response.getMarkers();
			if (markers == null)
				return false;
			if (markers.size() == 0)
				return false;
			Session.response = response;
			List<TTTMarker> dbMarkers;
			TTTMarker marker;
			for (int i = 0; i < markers.size(); i++) {
				marker = markers.get(i);
				dbMarkers = TTTMarker.find(TTTMarker.class, "markerid = ?",
						marker.getMarkerid() + "");
				if (dbMarkers.size() > 0)
					marker.setId(dbMarkers.get(0).getId());
				marker.save();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

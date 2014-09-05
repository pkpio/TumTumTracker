package in.co.praveenkumar.tumtumtracker.helper;

import in.co.praveenkumar.tumtumtracker.model.TTTSiteResponse;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

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
		Log.d(DEBUG_TAG, "Sync started!");
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) new URL(Param.dataUrl).openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "application/xml");
			con.setRequestProperty("Content-Language", "en-US");
			con.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(
					con.getOutputStream());
			writer.write("");
			writer.flush();
			writer.close();

			InputStreamReader reader = new InputStreamReader(
					con.getInputStream());
			GsonExclude ex = new GsonExclude();
			Gson gson = new GsonBuilder()
					.addDeserializationExclusionStrategy(ex)
					.addSerializationExclusionStrategy(ex).create();
			Session.response = gson.fromJson(reader, TTTSiteResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}

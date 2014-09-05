package in.co.praveenkumar.tumtumtracker.helper;

import java.util.List;

import in.co.praveenkumar.tumtumtracker.model.TTTMarker;
import in.co.praveenkumar.tumtumtracker.model.TTTSiteResponse;

public class Session {
	public static TTTSiteResponse response;

	/**
	 * Initializes the Session.response with data from Sql db. Returns false
	 * when init failed. Generally, Init fails if Sync never happened before or
	 * the last sync has no markers.
	 * 
	 * @return initStatus
	 */
	public static Boolean init() {
		List<TTTSiteResponse> responses = TTTSiteResponse
				.listAll(TTTSiteResponse.class);
		if (responses == null)
			return false;
		if (responses.size() == 0)
			return false;
		response = responses.get(0);

		List<TTTMarker> markers = TTTMarker.listAll(TTTMarker.class);
		if (markers == null)
			return false;
		if (markers.size() == 0)
			return false;

		response.setMarkers(markers);
		return true;
	}

}

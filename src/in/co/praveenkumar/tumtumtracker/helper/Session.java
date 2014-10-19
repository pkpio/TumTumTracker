package in.co.praveenkumar.tumtumtracker.helper;

import in.co.praveenkumar.tumtumtracker.model.TTTMarker;
import in.co.praveenkumar.tumtumtracker.model.TTTSiteResponse;

import java.util.List;

public class Session {
	public static TTTSiteResponse response = null;

	/**
	 * Initializes the Session.response with data from Sql db. Returns false
	 * when init failed. Generally, Init fails if Sync never happened before or
	 * the last sync has no markers.
	 * 
	 * @return initStatus
	 */
	public static Boolean init() {
		response = TTTSiteResponse.findById(TTTSiteResponse.class,
				Param.responseDbId);
		if (response == null)
			response = new TTTSiteResponse();

		List<TTTMarker> markers = TTTMarker.listAll(TTTMarker.class);
		if (markers == null)
			return false;
		if (markers.size() == 0)
			return false;

		response.setMarkers(markers);
		return true;
	}

}

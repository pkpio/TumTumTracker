package in.co.praveenkumar.tumtumtracker.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Object representing a marker data
 * 
 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
 * 
 */
public class Marker extends SugarRecord<Marker> {

	// since id is a reserved field in SugarRecord
	@SerializedName("id")
	int markerid;

	@SerializedName("type")
	int type;

	@SerializedName("lat")
	double lat;

	@SerializedName("lng")
	double lng;

	@SerializedName("idle")
	int idle;

	@SerializedName("lastupdated")
	String lastupdated;

	@SerializedName("changed")
	String changed;

	@SerializedName("route")
	String route;

	@SerializedName("description")
	String description;

	/**
	 * Get the unique marker id given by TTT site
	 * 
	 * @return markerid
	 */
	public int getMarkerid() {
		return markerid;
	}

	/**
	 * Get the marker type. This is used for marker color choosing.
	 * 
	 * @return type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Get the latitude of the marker
	 * 
	 * @return latitude
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Get the longitude of the marker
	 * 
	 * @return longitude
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * Get if the marker is idle or not. The idle status if defined by TTT site. <br/>
	 * 0 - Not idle<br/>
	 * 1 - Idle
	 * 
	 * @return idle
	 */
	public int getIdle() {
		return idle;
	}

	/**
	 * Get the lastupdated timestamp of the marker.<br/>
	 * Time stamp is formatted as YYYY-MM-DD HH:mm:SS (24-hour format).
	 * 
	 * @return lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * Get the timestamp at which last position change occurred.<br/>
	 * Time stamp is formatted as YYYY-MM-DD HH:mm:SS (24-hour format).
	 * 
	 * @return changed
	 */
	public String getChanged() {
		return changed;
	}

	/**
	 * Get the route name of the marker
	 * 
	 * @return route
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * Get the description of the current marker.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

}

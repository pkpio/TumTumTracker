package in.co.praveenkumar.tumtumtracker.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Object representing a marker data
 * 
 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
 * 
 */
public class TTTMarker extends SugarRecord<TTTMarker> {

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

	@SerializedName("course")
	int course;

	@SerializedName("lastupdated")
	int lastupdated;

	@SerializedName("changed")
	int changed;

	@SerializedName("route")
	String route;

	@SerializedName("speed")
	int speed;

	@SerializedName("description")
	String description;

	@SerializedName("full")
	int full;

	public TTTMarker() {

	}

	public TTTMarker(LatLng position) {
		this.lat = position.latitude;
		this.lng = position.longitude;
	}

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
	 * Get the lastupdated timestamp of the marker.
	 * 
	 * @return lastupdated
	 */
	public int getLastupdated() {
		return lastupdated;
	}

	/**
	 * Get the timestamp at which last position change occurred.
	 * 
	 * @return changed
	 */
	public int getChanged() {
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

	/**
	 * Get bus course
	 * 
	 * @return course
	 */
	public int getCourse() {
		return course;
	}

	/**
	 * Get marker speed in KMPH
	 * 
	 * @return speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Get bus full status. 0 - not full; 1 - full
	 * 
	 * @return fullStatus
	 */
	public int getFull() {
		return full;
	}
}

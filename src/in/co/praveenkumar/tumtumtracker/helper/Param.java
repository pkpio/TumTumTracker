package in.co.praveenkumar.tumtumtracker.helper;

import com.google.android.gms.maps.model.LatLng;

public class Param {
	/**
	 * URL for fetching json data
	 */
	public static final String dataUrl = "http://tumtum-iitb.org/ttt_data/";

	/**
	 * Data update frequency in milliseconds
	 */
	public static final int frequency = 3000;

	/**
	 * Wait time in milliseconds on last update failure
	 */
	public static final int failWait = 1500;

	/**
	 * The sql db id of the site response field. We will be using only 1 column
	 * in response table to save our response so, we might as well fix the id.
	 */
	public static final long responseDbId = 2;
	/**
	 * Default map center. Use when server data is not available.
	 */
	public static final LatLng center = new LatLng(19.131481, 72.915296);
	/**
	 * Default map zoom level. Server may not send this value.
	 */
	public static final int zoom = 17;
	/**
	 * Default map bearing value. Server may not send this value.
	 */
	public static final int bearing = 0; // Orientation to north
	/**
	 * Default map tilt value. Server may not send this value.
	 */
	public static final int tilt = 60;

}

package in.co.praveenkumar.tumtumtracker.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TTTRouteResponse {
	
	@SerializedName("title")
	String title;
	
	@SerializedName("license")
	String license;

	@SerializedName("routes")
	List<TTTRoute> routes;

	@SerializedName("version")
	String version;

	/**
	 * Get routes data title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get routes data license
	 * 
	 * @return license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * Get list of routes
	 * 
	 * @return routes
	 */
	public List<TTTRoute> getRoutes() {
		return routes;
	}

	/**
	 * Get version of route data
	 * 
	 * @return version
	 */
	public String getVersion() {
		return version;
	}
}

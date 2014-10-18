package in.co.praveenkumar.tumtumtracker.model;

import java.util.List;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * TTT route object
 * 
 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
 * 
 */
public class TTTRoute extends SugarRecord<TTTRoute> {
	int routeid;
	String title;
	String description;
	int color;

	@Ignore
	List<TTTOverviewPoly> overviewpolylines;

	// Temp
	public TTTRoute(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public TTTRoute() {

	}

	/**
	 * Route id given by TTT site
	 * 
	 * @return routeid
	 */
	public int getRouteid() {
		return routeid;
	}

	/**
	 * Route title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Route description
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Color code integer. Get actual color value from getColor(colorCode)
	 * method.
	 * 
	 * @return colorcode
	 */
	public int getColor() {
		return color;
	}

	public List<TTTOverviewPoly> getOverviewpolylines() {
		return overviewpolylines;
	}

}

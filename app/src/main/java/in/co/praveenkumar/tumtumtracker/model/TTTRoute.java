package in.co.praveenkumar.tumtumtracker.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * TTT route object
 * 
 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
 * 
 */
public class TTTRoute extends SugarRecord<TTTRoute> {

	// since id is a reserved field in SugarRecord
	@SerializedName("id")
	int routeid;

	@SerializedName("title")
	String title;

	@SerializedName("description")
	String description;

	@SerializedName("color")
	int color;

	@Ignore
	@SerializedName("overviewpolylines")
	List<TTTOverviewPoly> overviewpolylines;

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

	/**
	 * Get list of overviewpoly lines.
	 * 
	 * @return overviewpolyline
	 */
	public List<TTTOverviewPoly> getOverviewpolylines() {
		return overviewpolylines;
	}

	/**
	 * Set list of overviewpoly lines.
	 * 
	 * @return overviewpolyline
	 */
	public void setOverviewpolylines(List<TTTOverviewPoly> overviewpolylines) {
		this.overviewpolylines = overviewpolylines;
	}

	@Override
	public void save() {
		super.save();
		if (overviewpolylines == null)
			return;
		for (int i = 0; i < overviewpolylines.size(); i++) {
			overviewpolylines.get(i).setParentid(getId());
			overviewpolylines.get(i).save();
		}
	}
}

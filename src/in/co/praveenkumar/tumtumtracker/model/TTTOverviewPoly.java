package in.co.praveenkumar.tumtumtracker.model;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class TTTOverviewPoly extends SugarRecord<TTTOverviewPoly> {
	long parentid;

	@SerializedName("line")
	String line;

	/**
	 * Get overviewpoly string
	 * 
	 * @return overviewpolyline
	 */
	public String getLine() {
		return line;
	}

	/**
	 * Get database id of the parent route to which this overviewpolyline belong
	 * 
	 * @return parentid
	 */
	public long getParentid() {
		return parentid;
	}

	/**
	 * Set database id of the parent route to which this overviewpolyline belong
	 * 
	 * @return parentid
	 */
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
}

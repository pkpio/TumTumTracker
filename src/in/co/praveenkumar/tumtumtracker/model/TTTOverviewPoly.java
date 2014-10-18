package in.co.praveenkumar.tumtumtracker.model;

import com.orm.SugarRecord;

public class TTTOverviewPoly extends SugarRecord<TTTOverviewPoly> {
	long parentid;
	String data;

	/**
	 * Get overviewpoly string
	 * 
	 * @return overviewpolyline
	 */
	public String getData() {
		return data;
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

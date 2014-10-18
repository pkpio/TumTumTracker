package in.co.praveenkumar.tumtumtracker;

import in.co.praveenkumar.tumtumtracker.model.TTTRoute;

public class AppInterface {
	/**
	 * Gives an interface for plotting a route selected from left navigation
	 * 
	 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
	 * 
	 */
	public interface RoutePlotter {
		/**
		 * Plot the route on map.
		 * 
		 * @param Route
		 *            Route to be plotted
		 */
		public void plotRoute(TTTRoute route);
	}
}

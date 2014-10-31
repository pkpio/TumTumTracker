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

	/**
	 * Gives an interface for refreshing markers overlay from left navigation.
	 * Use when toggling idle show state.
	 * 
	 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
	 * 
	 */
	public interface MarkerRefresh {
		/**
		 * Replot markers on map. Use when any session settings are updated.
		 */
		public void refreshMarkers();
	}

	/**
	 * Gives an interface for changing the state of the app drawers
	 * 
	 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
	 * 
	 */
	public interface DrawerStateChanger {
		/**
		 * Set navigation drawers state
		 * 
		 * @param state
		 *            True: open. False: close
		 */
		public void setDrawerState(Boolean state);
	}
}

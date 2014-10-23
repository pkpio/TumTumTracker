package in.co.praveenkumar.tumtumtracker.helper;

import in.co.praveenkumar.tumtumtracker.R;

import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MapHelper {
	static final String ORANGE = "#f25f0f";
	static final String BLUE = "#43adf1";
	static final String RED = "#f10f00";
	static final String YELLOW = "#f4b400";
	static final String GREEN = "#2f8a78";
	static final String BLACK = "#000000";

	public static BitmapDescriptor MarkerIcon(int type) {
		switch (type) {
		case 1:
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_bus_orange);
		case 2:
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_bus_blue);
		case 3:
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_bus_green);
		case 4:
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_bus_red);
		case 5:
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_bus_yellow);
		case 6:
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_bus_black);
		default:
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_bus_black);
		}

	}

	public static int RouteColor(int color) {
		switch (color) {
		case 1:
			return Color.parseColor(ORANGE);
		case 2:
			return Color.parseColor(BLUE);
		case 3:
			return Color.parseColor(GREEN);
		case 4:
			return Color.parseColor(RED);
		case 5:
			return Color.parseColor(YELLOW);
		case 6:
			return Color.parseColor(BLACK);

		default:
			return Color.parseColor(BLACK);
		}
	}

	public static int RouteIcon(int color) {
		switch (color) {
		case 1:
			return R.drawable.icon_gesture_orange;
		case 2:
			return R.drawable.icon_gesture_blue;
		case 3:
			return R.drawable.icon_gesture_green;
		case 4:
			return R.drawable.icon_gesture_red;
		case 5:
			return R.drawable.icon_gesture_yellow;
		case 6:
			return R.drawable.icon_gesture_black;

		default:
			return R.drawable.icon_gesture_black;
		}
	}

}

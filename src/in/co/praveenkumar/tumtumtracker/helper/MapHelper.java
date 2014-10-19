package in.co.praveenkumar.tumtumtracker.helper;

import in.co.praveenkumar.tumtumtracker.R;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MapHelper {

	public static BitmapDescriptor MarkerIcon(int type) {
		switch (type) {
		case 1:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_blue);
		case 2:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_green);
		case 3:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_orange);
		case 4:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_lime);
		case 5:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_brown);
		case 6:
			return BitmapDescriptorFactory
					.fromResource(R.drawable.bus_lightblue);
		case 7:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_pink);
		case 8:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_purple);
		case 9:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_red);
		case 10:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_stop);
		default:
			return BitmapDescriptorFactory.fromResource(R.drawable.bus_yellow);
		}

	}

}

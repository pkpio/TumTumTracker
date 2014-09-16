package in.co.praveenkumar.tumtumtracker.fragment;

import in.co.praveenkumar.tumtumtracker.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * -TODO-
 * Remove this fragment and simply add this as listview in Navigation drawer adapter
 * @author praveen
 *
 */
public class LeftNavigationFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.frag_left_navigation,
				container, false);
		return rootView;
	}

}

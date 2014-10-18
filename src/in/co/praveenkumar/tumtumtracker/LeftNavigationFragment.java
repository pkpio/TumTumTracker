package in.co.praveenkumar.tumtumtracker;

import in.co.praveenkumar.tumtumtracker.helper.GsonExclude;
import in.co.praveenkumar.tumtumtracker.model.TTTOverviewPoly;
import in.co.praveenkumar.tumtumtracker.model.TTTRoute;
import in.co.praveenkumar.tumtumtracker.model.TTTRouteResponse;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * -TODO- Remove this fragment and simply add this as listview in Navigation
 * drawer adapter
 * 
 * @author praveen
 * 
 */
public class LeftNavigationFragment extends Fragment {
	ListView navListView;
	LeftNavListAdapter navListAdapter;
	Context context;
	List<TTTRoute> routes = new ArrayList<TTTRoute>();

	// App menu items
	String[] appMenuItems = new String[] { "About", "Website", "Developer",
			"Rate" };

	int[] appMenuIcons = new int[] { R.drawable.icon_info_greyscale,
			R.drawable.icon_people_greyscale,
			R.drawable.icon_settings_greyscale,
			R.drawable.icon_people_greyscale };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.frag_left_navigation,
				container, false);
		this.context = getActivity();

		// Set routelist with empty data
		navListView = (ListView) rootView.findViewById(R.id.left_nav_list);
		navListAdapter = new LeftNavListAdapter(context);
		navListView.setAdapter(navListAdapter);

		// Get route data from sql db and also sync from server
		new AsyncRouteSync().execute("");

		return rootView;
	}

	private class AsyncRouteSync extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... arg0) {
			// Init route data if not done already
			InitRoutesIfRequired();

			// Get all routes in sql db
			routes = TTTRoute.listAll(TTTRoute.class);

			// Set their polyline data from polyline table
			for (int i = 0; i < routes.size(); i++) {
				List<TTTOverviewPoly> lines = TTTOverviewPoly.find(
						TTTOverviewPoly.class, "parentid = ?", routes.get(i)
								.getId() + "");
				routes.get(i).setOverviewpolylines(lines);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean syncStatus) {
			navListAdapter.notifyDataSetChanged();
		}
	}

	public class LeftNavListAdapter extends BaseAdapter {
		private static final int TYPE_ROUTE = 0;
		private static final int TYPE_MENUITEM = 1;
		private static final int TYPE_COUNT = 2;

		private final Context context;

		public LeftNavListAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getViewTypeCount() {
			return TYPE_COUNT;
		}

		@Override
		public int getItemViewType(int position) {
			if (position >= routes.size())
				return TYPE_MENUITEM;
			return TYPE_ROUTE;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			int type = getItemViewType(position);

			if (convertView == null) {
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				// Choose layout
				switch (type) {
				case TYPE_ROUTE:
					convertView = inflater.inflate(R.layout.list_item_route,
							parent, false);

					viewHolder.routeTitle = (TextView) convertView
							.findViewById(R.id.list_route_title);
					viewHolder.routeDesc = (TextView) convertView
							.findViewById(R.id.list_route_desc);
					break;
				case TYPE_MENUITEM:
					convertView = inflater.inflate(R.layout.list_item_app_menu,
							parent, false);

					viewHolder.menuName = (TextView) convertView
							.findViewById(R.id.nav_menuitem);
					viewHolder.menuIcon = (ImageView) convertView
							.findViewById(R.id.nav_menuicon);
				}
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// Assign values
			switch (type) {
			case TYPE_ROUTE:
				viewHolder.routeTitle.setText(routes.get(position).getTitle());
				viewHolder.routeDesc.setText(routes.get(position)
						.getDescription());
				break;
			case TYPE_MENUITEM:
				position = position - routes.size();
				viewHolder.menuName.setText(appMenuItems[position]
						.toUpperCase(Locale.ENGLISH));
				viewHolder.menuIcon.setImageResource(appMenuIcons[position]);
				break;
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return routes.size() + appMenuItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	static class ViewHolder {
		TextView routeTitle;
		TextView routeDesc;
		ImageView menuIcon;
		TextView menuName;
	}

	void InitRoutesIfRequired() {
		List<TTTRoute> routes = TTTRoute.listAll(TTTRoute.class);
		if (routes.size() != 0)
			return;

		AssetManager assetManager = context.getAssets();
		try {
			InputStreamReader reader = new InputStreamReader(
					assetManager.open("routes.json"));
			GsonExclude ex = new GsonExclude();
			Gson gson = new GsonBuilder()
					.addDeserializationExclusionStrategy(ex)
					.addSerializationExclusionStrategy(ex).create();
			TTTRouteResponse response = gson.fromJson(reader,
					TTTRouteResponse.class);
			reader.close();

			routes = response.getRoutes();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<TTTOverviewPoly> polylines = new ArrayList<TTTOverviewPoly>();

		for (int i = 0; i < routes.size(); i++) {
			TTTRoute route = routes.get(i);
			route.save();
			polylines = route.getOverviewpolylines();
			for (int j = 0; j < polylines.size(); j++) {
				polylines.get(j).setParentid(route.getId());
				polylines.get(j).save();
			}
		}
	}

}
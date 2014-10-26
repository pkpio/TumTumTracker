package in.co.praveenkumar.tumtumtracker;

import in.co.praveenkumar.tumtumtracker.AppInterface.DrawerStateChanger;
import in.co.praveenkumar.tumtumtracker.AppInterface.RoutePlotter;
import in.co.praveenkumar.tumtumtracker.helper.GsonExclude;
import in.co.praveenkumar.tumtumtracker.helper.MapHelper;
import in.co.praveenkumar.tumtumtracker.model.TTTOverviewPoly;
import in.co.praveenkumar.tumtumtracker.model.TTTRoute;
import in.co.praveenkumar.tumtumtracker.model.TTTRouteResponse;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * All work on Left navigation here
 * 
 * @author Praveen Kumar Pendyala (praveen@praveenkumar.co.in)
 * 
 */
public class LeftNavigationFragment extends Fragment {
	static final String DEBUG_TAG = "LeftNavigationFragment";
	RoutePlotter mRoutePlotter;
	DrawerStateChanger mDrawerStateChanger;
	ListView navListView;
	LeftNavListAdapter navListAdapter;
	Context context;
	List<TTTRoute> routes = new ArrayList<TTTRoute>();

	// App menu items
	String[] appMenuItems = new String[] { "About", "Website", "Report bug",
			"Rate", "Open source licences" };

	int[] appMenuIcons = new int[] { R.drawable.icon_info_greyscale,
			R.drawable.icon_public, R.drawable.icon_bug_report,
			R.drawable.icon_star, R.drawable.icon_opensource };

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.frag_left_navigation,
				container, false);
		this.context = getActivity();

		// Set routelist with empty data
		navListView = (ListView) rootView.findViewById(R.id.left_nav_list);
		navListAdapter = new LeftNavListAdapter(context);

		// Added header to the list
		LinearLayout listHeaderView = (LinearLayout) inflater.inflate(
				R.layout.list_item_leftnav_header, null);
		navListView.addHeaderView(listHeaderView);

		// Note: Set adapter only after adding headers. Refer issue #6
		navListView.setAdapter(navListAdapter);

		// Menu item select actions
		navListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Because there is a header
				position--;
				if (position < 0)
					return;

				switch (navListAdapter.getItemViewType(position)) {
				case LeftNavListAdapter.TYPE_ROUTE:
					mRoutePlotter.plotRoute(routes.get(position));
					break;
				case LeftNavListAdapter.TYPE_MENUITEM:
					position = position - routes.size();
					switch (position) {
					case 0:
						Intent aboutIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse("http://tumtum-iitb.org/track/about/"));
						startActivity(aboutIntent);
						break;
					case 1:
						Intent browserIntent = new Intent(Intent.ACTION_VIEW,
								Uri.parse("http://tumtum-iitb.org/track"));
						startActivity(browserIntent);
						break;
					case 2:
						Intent bugIntent = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("https://github.com/praveendath92/TumTumTracker/issues"));
						startActivity(bugIntent);
						break;
					case 3:
						Intent rateIntent = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("market://details?id=in.co.praveenkumar.tumtumtracker"));
						startActivity(rateIntent);
						break;
					case 4:
						Intent i = new Intent(context, BrowserActivity.class);
						i.putExtra("url",
								"file:///android_asset/os_licenses.html");
						i.putExtra("title", "Open Source Licences");
						startActivity(i);
						break;
					}
					break;
				}
				mDrawerStateChanger.setDrawerState(false);
			}
		});

		// Get route data from sql db and also sync from server
		new AsyncRouteSync().execute("");

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		navListView.setAdapter(null); // Additional precautions for issue #6
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mRoutePlotter = (RoutePlotter) activity;
			mDrawerStateChanger = (DrawerStateChanger) activity;
		} catch (ClassCastException castException) {
			Log.d(DEBUG_TAG, "The activity does not implement the listener");
		}
	}

	private class AsyncRouteSync extends AsyncTask<String, Integer, Boolean> {
		List<TTTRoute> mRoutes = new ArrayList<TTTRoute>();

		@Override
		protected Boolean doInBackground(String... arg0) {
			// Init route data if not done already
			InitRoutesIfRequired();

			// Get all routes in sql db
			mRoutes = TTTRoute.listAll(TTTRoute.class);

			// Set their polyline data from polyline table
			for (int i = 0; i < mRoutes.size(); i++) {
				List<TTTOverviewPoly> lines = TTTOverviewPoly.find(
						TTTOverviewPoly.class, "parentid = ?", mRoutes.get(i)
								.getId() + "");
				mRoutes.get(i).setOverviewpolylines(lines);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean syncStatus) {
			if (syncStatus)
				routes = mRoutes;
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

					viewHolder.routeIcon = (ImageView) convertView
							.findViewById(R.id.list_item_route_image);
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
				viewHolder.routeIcon.setImageResource(MapHelper
						.RouteIcon(routes.get(position).getColor()));
				viewHolder.routeTitle.setText(routes.get(position).getTitle());
				viewHolder.routeDesc.setText(lineBreakedDesc(routes.get(
						position).getDescription()));
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
		ImageView routeIcon;
		TextView routeTitle;
		TextView routeDesc;
		ImageView menuIcon;
		TextView menuName;
	}

	String lineBreakedDesc(String description) {
		return description.replace(" | ", System.getProperty("line.separator"));
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

		for (int i = 0; i < routes.size(); i++)
			routes.get(i).save();
	}

}

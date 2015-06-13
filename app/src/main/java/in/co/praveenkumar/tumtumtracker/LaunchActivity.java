package in.co.praveenkumar.tumtumtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LaunchActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		// Start from Tutorial otherwise
		Intent i = new Intent(this, TrackerActivity.class);
		this.startActivity(i);

	}

}

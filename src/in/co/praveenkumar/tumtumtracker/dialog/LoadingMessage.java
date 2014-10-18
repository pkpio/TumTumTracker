package in.co.praveenkumar.tumtumtracker.dialog;

import in.co.praveenkumar.tumtumtracker.R;
import in.co.praveenkumar.tumtumtracker.helper.RandomMessage;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class LoadingMessage extends Dialog {
	Context context;

	public LoadingMessage(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_loading);

		// Update text
		TextView loadText = (TextView) findViewById(R.id.dialog_loading_message);
		loadText.setText(RandomMessage.getLoadText());

		// Set dialog background, width and cancellable
		setCancelable(false);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		LayoutParams params = getWindow().getAttributes();
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		getWindow().setAttributes((LayoutParams) params);
	}

}

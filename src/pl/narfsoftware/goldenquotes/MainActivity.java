package pl.narfsoftware.goldenquotes;

import java.lang.reflect.Field;
import java.util.Random;

import org.apache.http.protocol.HTTP;

import pl.narfsoftware.goldenquotes.model.Quote;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

public class MainActivity extends Activity {

	/**
	 * Tag used for logging and testing purposes.
	 */
	private static final String TAG = "MainActivity";

	/**
	 * Path to the font used as quotes' font.
	 */
	public static final String FONT_PATH = "Chantelli_Antiqua.ttf";

	/**
	 * Constant being a key transferred to AuthorInfoActivity in order to get
	 * selected author row in database.
	 */
	public static final String EXTRA_AUTHOR_ID = "pl.narfsoftware.goldenquotes.EXTRA_AUTHOR_ID";

	/**
	 * Not used
	 */
	private static final String KEY_SAVE_QUOTE = "pl.narfsoftware.goldenquotes.QUOTE_INSTANCE_SAVE";

	private DbHelper db;
	private static Quote quote;

	private TextView quoteTextView;
	private TextView authorTextView;
	private Button favouriteBtn;

	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);

		quoteTextView = (TextView) findViewById(R.id.text_quote);

		quoteTextView.setTypeface(Typeface.createFromAsset(getAssets(),
				FONT_PATH));

		authorTextView = (TextView) findViewById(R.id.text_author);

		favouriteBtn = (Button) findViewById(R.id.btn_favourite);

		if (quote == null) {
			(findViewById(R.id.stacked_buttons)).setVisibility(View.INVISIBLE);
		}
		db = ((GoldenQuotesApp) getApplication()).getDatabase();

		getOverflowMenu();
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
		LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);
		// layout.setBackgroundColor(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(R,
		// defValue))
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String color = prefs.getString("bg_colors_list", "#EEEEEE");
		layout.setBackgroundColor(Color.parseColor(color));
	}

	@Override
	protected void onStop() {
		super.onStop();
		uiHelper.onStop();
		db.close();
	}

	@Override
	protected void onStart() {
		super.onStart();
		db.openDataBase();
		if (quote != null) {
			fillWithData();
			if (quote.isFavourite()) {
				favouriteBtn.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.ic_action_important, 0, 0, 0);
			} else {
				favouriteBtn.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.ic_action_not_important, 0, 0, 0);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
					}
				});
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_authors_list:
			startActivity(new Intent(this, AuthorList.class));
			return true;
		case R.id.action_share:

			// FacebookDialog fbDialog = new FacebookDialog.ShareDialogBuilder(
			// this).setDescription("Sumfink").setLink("ASD").build();
			// uiHelper.trackPendingDialogCall(fbDialog.present());

			Intent shareIntent = new Intent(Intent.ACTION_SEND);
			shareIntent.setType(HTTP.PLAIN_TEXT_TYPE);
			shareIntent.putExtra(Intent.EXTRA_TEXT, this.quoteTextView
					.getText().toString() + "\n by GoldenQuotes app!");
			Intent chooser = Intent.createChooser(shareIntent, getResources()
					.getString(R.string.share_chooser_title));
			startActivity(chooser);
			return true;
		case R.id.action_about:
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void onClickBtn(View v) {
		(findViewById(R.id.stacked_buttons)).setVisibility(View.VISIBLE);

		Random r = new Random();
		int id = r.nextInt(this.db.getQuotesCount()) + 1;
		quote = Quote.getQuote(id, db);

		fillWithData();

		if (quote.isFavourite()) {
			favouriteBtn.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.ic_action_important, 0, 0, 0);
		} else {
			favouriteBtn.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.ic_action_not_important, 0, 0, 0);
		}

		Log.d(TAG, "QUOTE: " + quoteTextView.getText().toString());
		Log.d(TAG, "AUTHOR: " + authorTextView.getText().toString());

	}

	private void fillWithData() {
		quoteTextView.setText(quote.getContent());
		authorTextView.setText(quote.getAuthor().getName());
	}

	/**
	 * Interaction logic for FavouriteButton.
	 * 
	 * @param v
	 *            Clicked button.
	 */
	public void onClickBtnFavourite(View v) {
		if (quote.isFavourite()) {
			((Button) v).setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.ic_action_not_important, 0, 0, 0);
			quote.setFavourite(false, db);

		} else {
			((Button) v).setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.ic_action_important, 0, 0, 0);
			quote.setFavourite(true, db);
		}
	}

	/**
	 * Interaction logic for AuthorInfoButton.
	 * 
	 * @param v
	 *            Clicked button.
	 */
	public void onClickAuthorInfo(View v) {
		// Perform any action only if there's quote on the screen.
		if (quote != null) {
			Intent intent = new Intent(this, AuthorInfoActivity.class);

			// This extra is used in order to select proper Author in stared
			// AuthorInfoActivity.
			intent.putExtra(EXTRA_AUTHOR_ID, quote.getAuthor().get_id());
			startActivity(intent);
		}
	}

	private void getOverflowMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

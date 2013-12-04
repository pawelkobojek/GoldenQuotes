package pl.narfsoftware.goldenquotes;

import java.lang.reflect.Field;
import java.util.Random;

import pl.narfsoftware.goldenquotes.model.Quote;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private static final String FONT_PATH = "Chantelli_Antiqua.ttf";

	public static final String EXTRA_AUTHOR_ID = "pl.narfsoftware.goldenquotes.EXTRA_AUTHOR_ID";

	private static final String KEY_SAVE_QUOTE = "pl.narfsoftware.goldenquotes.QUOTE_INSTANCE_SAVE";

	private DbHelper db;
	private static Quote quote;

	private TextView quoteTextView;
	private TextView authorTextView;
	private Button favouriteBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		quoteTextView = (TextView) findViewById(R.id.text_quote);

		quoteTextView.setTypeface(Typeface.createFromAsset(getAssets(),
				FONT_PATH));

		authorTextView = (TextView) findViewById(R.id.text_author);

		favouriteBtn = (Button) findViewById(R.id.btn_favourite);

		if (quote == null) {
			(findViewById(R.id.stacked_buttons)).setVisibility(View.INVISIBLE);
		}
		db = ((GoldenQuotesApp) getApplication()).getDatabase();

		LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);
		// layout.setBackgroundColor(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(R,
		// defValue))

		getOverflowMenu();
	}

	@Override
	protected void onStop() {
		super.onStop();
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
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_authors_list:
			startActivity(new Intent(this, AuthorList.class));
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

	public void onClickAuthorInfo(View v) {
		if (quote != null) {
			Intent intent = new Intent(this, AuthorInfoActivity.class);
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

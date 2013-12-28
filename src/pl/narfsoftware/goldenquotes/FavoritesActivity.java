package pl.narfsoftware.goldenquotes;

import pl.narfsoftware.goldenquotes.model.Quote;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FavoritesActivity extends ListActivity {

	private DbHelper db;

	private AsyncFavoritesLoader taskLoader;
	private AsyncFavoriteSetter taskSetter;

	public static final String[] FROM = { Quote.C_CONTENT };
	public static final int[] TO = { R.id.text_quote };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourites);
		// Show the Up button in the action bar.
		setupActionBar();

		// db = ((GoldenQuotesApp) getApplication()).getDatabase();
		// db.openDataBase();
		db = new DbHelper(this);
		db.open();

		taskLoader = new AsyncFavoritesLoader();
		taskLoader.execute();
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		// super.onListItemClick(l, v, position, id);
		taskSetter = new AsyncFavoriteSetter();
		taskSetter.execute(v);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		db.open();
		LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String color = prefs.getString("bg_colors_list", "#EEEEEE");
		layout.setBackgroundColor(Color.parseColor(color));
	}

	@Override
	protected void onPause() {
		super.onPause();
		db.close();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (taskLoader != null)
			taskLoader.cancel(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favourites, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class AsyncFavoritesLoader extends
			AsyncTask<Void, Void, SimpleCursorAdapter> {

		@Override
		protected SimpleCursorAdapter doInBackground(Void... params) {
			final Cursor c = db.getFavouriteQuotes();
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(
					FavoritesActivity.this, R.layout.row_quote, c, FROM, TO, 0) {
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					View view = super.getView(position, convertView, parent);
					view.setTag(c.getInt(c.getColumnIndex(Quote.C_ID)));
					TextView quote = (TextView) view
							.findViewById(R.id.text_quote);
					quote.setTypeface(Typeface.createFromAsset(getAssets(),
							MainActivity.FONT_PATH));

					return view;
				}
			};
			return adapter;
		}

		@Override
		protected void onPostExecute(SimpleCursorAdapter result) {
			super.onPostExecute(result);
			getListView().setAdapter(result);
		}

	}

	private class AsyncFavoriteSetter extends AsyncTask<View, Void, View> {

		private Quote q;

		@Override
		protected View doInBackground(View... params) {
			int quoteId = (Integer) params[0].getTag();
			q = Quote.getQuote(quoteId, db);
			q.setFavourite(!q.isFavourite(), db);
			return params[0];
		}

		@Override
		protected void onPostExecute(View result) {
			super.onPostExecute(result);
			ImageView fav = (ImageView) result.findViewById(R.id.img_favourite);
			if (q.isFavourite()) {
				fav.setImageDrawable(getResources().getDrawable(
						R.drawable.ic_action_important));
			} else {
				fav.setImageDrawable(getResources().getDrawable(
						R.drawable.ic_action_not_important));
			}
		}

	}
}

package pl.narfsoftware.goldenquotes;

import pl.narfsoftware.goldenquotes.logic.Author;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AuthorList extends ListActivity {

	public static final String[] FROM = { Author.C_NAME };
	public static final int[] TO = { R.id.text_author_name_row };
	private static final String TAG = "pl.narfsoftware.GoldenQuotes.ListActivity";

	private DbHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_list);
		// Show the Up button in the action bar.
		setupActionBar();

		db = ((GoldenQuotesApp) getApplication()).getDatabase();
		db.openDataBase();
		ListView list = getListView();

		final Cursor c = db.getAuthorsList();

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.row_author_info, c, FROM, TO, 0) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				view.setTag(c.getInt(c.getColumnIndex(Author.C_ID)));
				return view;
			}
		};

		list.setAdapter(adapter);

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		// super.onListItemClick(l, v, position, id);
		int authorId = (Integer) v.getTag();
		Intent intent = new Intent(this, AuthorInfoActivity.class);
		intent.putExtra(MainActivity.EXTRA_AUTHOR_ID, authorId);
		startActivity(intent);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */

	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.author_list, menu);
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

}

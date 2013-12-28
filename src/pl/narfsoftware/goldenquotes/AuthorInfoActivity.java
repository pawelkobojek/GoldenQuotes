package pl.narfsoftware.goldenquotes;

import pl.narfsoftware.goldenquotes.model.Author;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class AuthorInfoActivity extends Activity {

	private static final String STATE_AUTHOR_ID = "Author ID";

	private DbHelper db;
	private TextView textName;
	private TextView textBirth;
	private TextView textDeath;
	private TextView textNation;
	private TextView textProfession;
	private TextView textFeaturedQuote;
	private TextView textWikipediaLink;

	private Author author;

	private AsyncAuthorLoader taskLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author_info);
		// Show the Up button in the action bar.
		setupActionBar();

		textName = (TextView) findViewById(R.id.text_author_name);
		textBirth = (TextView) findViewById(R.id.text_birth_value);
		textDeath = (TextView) findViewById(R.id.text_death_value);
		textNation = (TextView) findViewById(R.id.text_nationality_value);
		textProfession = (TextView) findViewById(R.id.text_profession_value);
		textWikipediaLink = (TextView) findViewById(R.id.text_wikipedia_link);
		textWikipediaLink.setMovementMethod(LinkMovementMethod.getInstance());
		textFeaturedQuote = (TextView) findViewById(R.id.text_featured_quote);
		textFeaturedQuote.setTypeface(Typeface.createFromAsset(getAssets(),
				MainActivity.FONT_PATH));

		db = new DbHelper(this);
		db.open();
		int authorId;

		if (savedInstanceState != null) {
			authorId = savedInstanceState.getInt(STATE_AUTHOR_ID);
		} else {
			authorId = getIntent().getExtras().getInt(
					MainActivity.EXTRA_AUTHOR_ID);
		}
		taskLoader = new AsyncAuthorLoader();
		taskLoader.execute(authorId);
	}

	@Override
	protected void onResume() {
		super.onResume();
		db.open();
		ScrollView layout = (ScrollView) findViewById(R.id.main_layout);
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
		if (taskLoader != null) {
			taskLoader.cancel(true);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// Save current author id to load the author from db
		outState.putInt(STATE_AUTHOR_ID, this.author.get_id());

		super.onSaveInstanceState(outState);
	}

	/**
	 * Populate TextViews with information about the Author.
	 */
	private void fillWithData() {
		// Every Author has to have name specified.
		textName.setText(author.getName());

		if (author.getBirthDate() == null) {
			(findViewById(R.id.text_birth_date)).setVisibility(View.INVISIBLE);
			textBirth.setVisibility(View.INVISIBLE);
		} else {
			textBirth.setText(author.getBirthDate());
		}

		if (author.getDeathDate() == null) {
			(findViewById(R.id.text_death_date)).setVisibility(View.INVISIBLE);
			textDeath.setVisibility(View.INVISIBLE);
		} else {
			textDeath.setText(author.getDeathDate());
		}

		if (author.getNationality() == null) {
			(findViewById(R.id.text_nationality)).setVisibility(View.INVISIBLE);
			textNation.setVisibility(View.INVISIBLE);
		} else {
			textNation.setText(author.getNationality());
		}

		if (author.getProfession() == null) {
			(findViewById(R.id.text_profession)).setVisibility(View.INVISIBLE);
			textProfession.setVisibility(View.INVISIBLE);
		} else {
			textProfession.setText(author.getProfession());
		}

		// No null-checking based on assumption that every Author has at least
		// one quote.
		textFeaturedQuote.setText(author.getFeaturedQuote());

		if (author.getWikipediaUrl() == null) {
			textWikipediaLink.setVisibility(View.INVISIBLE);
		} else {
			textWikipediaLink.setText(Html.fromHtml(getResources().getString(
					R.string.wiki_more_info)
					+ " <a href=\""
					+ author.getWikipediaUrl()
					+ "\">"
					+ author.getName() + "</a>"));
		}
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
		getMenuInflater().inflate(R.menu.author_info, menu);
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
			onBackPressed();
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class AsyncAuthorLoader extends AsyncTask<Integer, Void, Author> {

		@Override
		protected Author doInBackground(Integer... params) {
			int authorId = params[0];
			return Author.getAuthor(authorId, db);
		}

		@Override
		protected void onPostExecute(Author result) {
			super.onPostExecute(result);
			author = result;
			fillWithData();
		}

	}
}

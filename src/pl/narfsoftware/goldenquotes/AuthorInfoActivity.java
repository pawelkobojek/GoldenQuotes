package pl.narfsoftware.goldenquotes;

import pl.narfsoftware.goldenquotes.model.Author;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AuthorInfoActivity extends Activity {

	private static final String STATE_AUTHOR_ID = "Author ID";

	private DbHelper db;
	private TextView textName;
	private TextView textBirth;
	private TextView textDeath;
	private TextView textNation;
	private TextView textProfession;

	private static Author author;

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

		this.db = ((GoldenQuotesApp) getApplication()).getDatabase();
		int authorId;

		if (savedInstanceState != null) {
			authorId = savedInstanceState.getInt(STATE_AUTHOR_ID);
		} else {
			authorId = getIntent().getExtras().getInt(
					MainActivity.EXTRA_AUTHOR_ID);
		}
		author = Author.getAuthor(authorId, this.db);

		fillWithData();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_AUTHOR_ID, this.author.get_id());

		super.onSaveInstanceState(outState);
	}

	private void fillWithData() {
		textName.setText(author.getName());
		textBirth.setText(author.getBirthDate());
		textDeath.setText(author.getDeathDate());
		textNation.setText(author.getNationality());
		textProfession.setText(author.getProfession());
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

}

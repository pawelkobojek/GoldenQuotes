package pl.narfsoftware.goldenquotes;

import java.util.Random;

import pl.narfsoftware.goldenquotes.logic.Quote;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private static final String FONT_PATH = "Chantelli_Antiqua.ttf";

	public static final String EXTRA_AUTHOR_ID = "pl.narfsoftware.goldenquotes.EXTRA_AUTHOR_ID";

	private DbHelper db;
	private static Quote quote;

	private TextView quoteTextView;
	private TextView authorTextView;
	private Button favouriteBtn;

	private static boolean started = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		quoteTextView = (TextView) findViewById(R.id.text_quote);

		quoteTextView.setTypeface(Typeface.createFromAsset(getAssets(),
				FONT_PATH));

		authorTextView = (TextView) findViewById(R.id.text_author);

		favouriteBtn = (Button) findViewById(R.id.btn_favourite);

		if (!started) {
			(findViewById(R.id.stacked_buttons)).setVisibility(View.INVISIBLE);
			started = true;
		}
		db = ((GoldenQuotesApp) getApplication()).getDatabase();
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
}

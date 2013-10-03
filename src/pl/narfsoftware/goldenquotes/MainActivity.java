package pl.narfsoftware.goldenquotes;

import java.util.Random;

import pl.narfsoftware.goldenquotes.logic.Quote;
import android.app.Activity;
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

	private DbHelper db;
	private Quote quote;

	private TextView quoteTextView;
	private TextView authorTextView;

	private static boolean started = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		quoteTextView = (TextView) findViewById(R.id.text_quote);

		quoteTextView.setTypeface(Typeface.createFromAsset(getAssets(),
				FONT_PATH));

		authorTextView = (TextView) findViewById(R.id.text_author);

		if (!started) {
			(findViewById(R.id.stacked_buttons)).setVisibility(View.INVISIBLE);
			started = true;
		}

		db = ((GoldenQuotesApp) getApplication()).getDatabase();
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

		quoteTextView.setText(quote.getContent());

		authorTextView.setText(quote.getAuthor().getName());

		Button favouriteBtn = (Button) findViewById(R.id.btn_favourite);
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
}

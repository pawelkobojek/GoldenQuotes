package pl.narfsoftware.goldenquotes;

import java.io.IOException;

import android.app.TabActivity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	private static final String TAG = "MainActivity";

	private static final String FONT_PATH = "Chantelli_Antiqua.ttf";

	private DbHelper db;

	private TextView quoteTextView;
	private TextView authorTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab_RandomQuote")
				.setIndicator("Random Quote").setContent(R.id.tabRandomQuote));
		tabHost.addTab(tabHost.newTabSpec("tab_Authors")
				.setIndicator("Authors").setContent(R.id.tabAuthors));

		tabHost.setCurrentTab(0);

		quoteTextView = (TextView) findViewById(R.id.text_quote);

		quoteTextView.setTypeface(Typeface.createFromAsset(getAssets(),
				FONT_PATH));

		authorTextView = (TextView) findViewById(R.id.text_author);

		this.db = new DbHelper(this);
		try {
			this.db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.db.openDataBase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void onClickBtn(View v) {
		Cursor c = this.db.gamble();

		if (c != null) {
			c.moveToFirst();
		}

		quoteTextView
				.setText(c.getString(c.getColumnIndex(DbHelper.C_CONTENT)));

		authorTextView.setText(c.getString(c.getColumnIndex(DbHelper.C_NAME)));

		Log.d(TAG, "QUOTE: " + quoteTextView.getText().toString());
		Log.d(TAG, "AUTHOR: " + authorTextView.getText().toString());

	}

}

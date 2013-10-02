package pl.narfsoftware.goldenquotes;

import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private static final String FONT_PATH = "Chantelli_Antiqua.ttf";

	private DbHelper db;

	private TextView quoteTextView;
	private TextView authorTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

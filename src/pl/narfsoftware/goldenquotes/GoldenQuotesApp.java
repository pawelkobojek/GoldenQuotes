package pl.narfsoftware.goldenquotes;

import java.io.IOException;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class GoldenQuotesApp extends Application implements OnSharedPreferenceChangeListener {

	private DbHelper db;

	private SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
		this.db = new DbHelper(this);

		this.prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		this.prefs.registerOnSharedPreferenceChangeListener(this);

		try {
			this.db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DbHelper getDatabase() {
		this.db.openDataBase();
		return this.db;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		db.close();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		
	}

}

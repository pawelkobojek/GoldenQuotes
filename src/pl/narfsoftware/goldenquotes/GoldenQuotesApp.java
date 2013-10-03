package pl.narfsoftware.goldenquotes;

import java.io.IOException;

import android.app.Application;

public class GoldenQuotesApp extends Application {

	private DbHelper db;

	@Override
	public void onCreate() {
		super.onCreate();
		this.db = new DbHelper(this);

		try {
			this.db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.db.openDataBase();
	}

	public DbHelper getDatabase() {
		return this.db;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		db.close();
	}

}

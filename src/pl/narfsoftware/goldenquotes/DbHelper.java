package pl.narfsoftware.goldenquotes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DB_PATH = "/data/data/pl.narfsoftware.goldenquotes/databases/";
	private static final String DB_NAME = "quotes";

	public static final String TABLE_QUOTES = "Quotes";
	public static final String C_ID = "_id";
	public static final String C_CONTENT = "content";
	public static final String C_AUTHOR = "author";
	public static final String C_LIKE_COUNT = "like_count";

	private SQLiteDatabase db;

	private final Context context;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
	}

	public void createDataBase() throws IOException {
		if (!dbExists()) {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private boolean dbExists() {
		SQLiteDatabase checkDB = null;
		try {
			String path = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// db doesnt exist
		}

		if (checkDB != null) {
			checkDB.close();
			return true;
		}
		return false;
	}

	private void copyDataBase() throws IOException {
		InputStream input = context.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream output = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = input.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}

		output.flush();
		output.close();
		input.close();
	}

	public void openDataBase() throws SQLException {
		String path = DB_PATH + DB_NAME;
		this.db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
	}

	public Cursor gamble() {

		Cursor c = db.rawQuery("select count(*) from " + TABLE_QUOTES, null);
		c.moveToFirst();
		int count = c.getInt(0);

		Random r = new Random();
		Integer id = r.nextInt(count) + 1;

		return db.query(true, TABLE_QUOTES,
				new String[] { C_CONTENT, C_AUTHOR }, C_ID + "=?",
				new String[] { id.toString() }, null, null, null, null);
	}

	@Override
	public synchronized void close() {
		if (this.db != null)
			this.db.close();

		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

package pl.narfsoftware.goldenquotes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import pl.narfsoftware.goldenquotes.logic.Author;
import pl.narfsoftware.goldenquotes.logic.Quote;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DB_PATH = "/data/data/pl.narfsoftware.goldenquotes/databases/";
	private static final String DB_NAME = "quotes";

	private SQLiteDatabase db;

	private final Context context;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
	}

	public void createDataBase() throws IOException {
		//if (!dbExists()) {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		//}
	}

	private boolean dbExists() {
		SQLiteDatabase checkDB = null;
		try {
			String path = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READWRITE);
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
				SQLiteDatabase.OPEN_READWRITE);
	}

	public int getQuotesCount() {
		Cursor c = db
				.rawQuery("select count(*) from " + Quote.TABLE_NAME, null);
		c.moveToFirst();
		return c.getInt(0);
	}

	public Cursor gamble() {
		Cursor c = db
				.rawQuery("select count(*) from " + Quote.TABLE_NAME, null);
		c.moveToFirst();
		int count = c.getInt(0);

		Random r = new Random();
		Integer id = r.nextInt(count) + 1;

		final String QUERY = "select " + Quote.TABLE_NAME + "."
				+ Quote.C_CONTENT + ", " + Author.TABLE_NAME + "."
				+ Author.C_NAME + " from " + Quote.TABLE_NAME + " join "
				+ Author.TABLE_NAME + " on " + Quote.TABLE_NAME + "."
				+ Quote.C_AUTHOR + "=" + Author.TABLE_NAME + "." + Quote.C_ID
				+ " where " + Quote.TABLE_NAME + "." + Quote.C_ID + "=?";

		return db.rawQuery(QUERY, new String[] { id.toString() });
	}

	public Cursor getQuote(Integer id) {
		return db.query(true, Quote.TABLE_NAME, Quote.ALL_COLUMNS, Quote.C_ID
				+ "=?", new String[] { id.toString() }, null, null, null, null);
	}

	public Cursor getAuthor(Integer id) {

		return db.query(true, Author.TABLE_NAME, Author.ALL_COLUMNS, Quote.C_ID
				+ "=?", new String[] { id.toString() }, null, null, null, null);
	}

	public int updateFavourite(Integer id, boolean fav) {
		ContentValues values = new ContentValues();
		values.put(Quote.C_LIKE_COUNT, fav ? 1 : 0);
		return db.update(Quote.TABLE_NAME, values, Quote.C_ID + "=?",
				new String[] { id.toString() });
	}

	@Override
	public synchronized void close() {

		super.close();
		if (this.db != null)
			this.db.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Cursor getAuthorsList() {
		return db.query(true, Author.TABLE_NAME, Author.ALL_COLUMNS, null,
				null, null, null, null, null);
	}

}

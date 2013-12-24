package pl.narfsoftware.goldenquotes;

import java.util.Random;

import pl.narfsoftware.goldenquotes.model.Author;
import pl.narfsoftware.goldenquotes.model.Quote;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DbHelper extends SQLiteAssetHelper {

	private static final String DB_NAME = "quotes";
	private static final int DB_VERSION = 1;

	private SQLiteDatabase db;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public void open() {
		db = getWritableDatabase();
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
		return db.query(true, Quote.TABLE_NAME, null, Quote.C_ID + "=?",
				new String[] { id.toString() }, null, null, null, null);
	}

	public Cursor getAuthor(Integer id) {

		return db.query(true, Author.TABLE_NAME, null, Quote.C_ID + "=?",
				new String[] { id.toString() }, null, null, null, null);
	}

	/**
	 * Changes Quote with the given id state to either favorite or not favorite.
	 * 
	 * @param id
	 *            Id of Quote which state is to be changed.
	 * @param fav
	 *            True if Quote is going to be favorite, else false.
	 * @return Number of modified rows (1 or 0).
	 */
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
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * Retrieves all authors.
	 * 
	 * @return Cursor containing all authors.
	 */
	public Cursor getAuthorsList() {
		return db.query(true, Author.TABLE_NAME, null, null, null, null, null,
				null, null);
	}

	/**
	 * Retrieves all favorite quotes.
	 * 
	 * @return Cursor containing all favorite quotes.
	 */
	public Cursor getFavouriteQuotes() {
		return db.query(Quote.TABLE_NAME, null, Quote.C_LIKE_COUNT + ">?",
				new String[] { "0" }, null, null, null);
	}

	public long insertQuote(String content, int authorId) {
		ContentValues values = new ContentValues();
		values.put(Quote.C_AUTHOR, authorId);
		values.put(Quote.C_CONTENT, content);
		values.put(Quote.C_LIKE_COUNT, 0);

		return db.insert(Quote.TABLE_NAME, null, values);
	}
}

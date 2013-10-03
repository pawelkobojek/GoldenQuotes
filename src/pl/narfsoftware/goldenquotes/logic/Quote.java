package pl.narfsoftware.goldenquotes.logic;

import pl.narfsoftware.goldenquotes.DbHelper;
import android.database.Cursor;

public class Quote {

	public static final String TABLE_NAME = "Quotes";
	public static final String C_ID = "_id";
	public static final String C_CONTENT = "content";
	public static final String C_AUTHOR = "author";
	public static final String C_LIKE_COUNT = "like_count";

	public static final String[] ALL_COLUMNS = { C_ID, C_CONTENT, C_AUTHOR,
			C_LIKE_COUNT };

	private int _id;
	private String content;
	private Author author;
	private boolean isFavourite;

	public int get_id() {
		return _id;
	}

	public String getContent() {
		return content;
	}

	public Author getAuthor() {
		return author;
	}

	public boolean isFavourite() {
		return isFavourite;
	}

	public void setFavourite(boolean favourite, DbHelper db) {
		isFavourite = favourite;
		db.updateFavourite(this._id, isFavourite);
	}

	public Quote(int id, String content, Author author, boolean favourite) {
		this._id = id;
		this.content = content;
		this.author = author;
		this.isFavourite = favourite;
	}

	public static Quote getQuote(int id, DbHelper db) {
		Cursor c = db.getQuote(id);

		if (c == null) {
			return null;
		}

		c.moveToFirst();
		String content = c.getString(c.getColumnIndex(C_CONTENT));
		int authorId = c.getInt(c.getColumnIndex(C_AUTHOR));
		boolean isFavourite = c.getInt(c.getColumnIndex(C_LIKE_COUNT)) > 0;

		Author author = Author.getAuthor(authorId, db);

		return new Quote(id, content, author, isFavourite);
	}
}

package pl.narfsoftware.goldenquotes.model;

import pl.narfsoftware.goldenquotes.DbHelper;
import android.database.Cursor;

public class Author {
	public static final String TABLE_NAME = "Authors";
	public static final String C_ID = "_id";
	public static final String C_NAME = "name";
	public static final String C_DESCRIPTION = "description";
	public static final String C_BIRTH_DATE = "birth_date";
	public static final String C_DEATH_DATE = "death_date";
	public static final String C_NATIONALITY = "nationality";
	public static final String C_PROFESSION = "profession";
	public static final String C_WIKIPEDIA_URL = "wikipedia_link";
	public static final String C_FEATURED_QUOTE = "featured_quote";

	// Appears to be useless.
	public static final String[] ALL_COLUMNS = { C_ID, C_NAME, C_DESCRIPTION,
			C_BIRTH_DATE, C_DEATH_DATE, C_NATIONALITY, C_PROFESSION,
			C_WIKIPEDIA_URL };

	private int _id;
	private String name;
	private String description;
	private String birthDate;
	private String deathDate;
	private String nationality;
	private String profession;
	private String wikiepdiaURL;
	private String featuredQuote;

	public int get_id() {
		return _id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public String getNationality() {
		return nationality;
	}

	public String getProfession() {
		return profession;
	}

	public String getWikipediaUrl() {
		return wikiepdiaURL;
	}

	public String getFeaturedQuote() {
		return featuredQuote;
	}

	public Author(int id, String name, String desc, String birth, String death,
			String nationality, String profession, String wikiUrl,
			String featured) {
		this._id = id;
		this.name = name;
		this.description = desc;
		this.birthDate = birth;
		this.deathDate = death;
		this.nationality = nationality;
		this.profession = profession;
		this.wikiepdiaURL = wikiUrl;
		this.featuredQuote = featured;
	}

	public static Author getAuthor(int id, DbHelper db) {
		Cursor c = db.getAuthor(id);

		if (c == null) {
			return null;
		}
		c.moveToFirst();

		String name = c.getString(c.getColumnIndex(C_NAME));
		String desc = c.getString(c.getColumnIndex(C_DESCRIPTION));
		String birth = c.getString(c.getColumnIndex(C_BIRTH_DATE));
		String death = c.getString(c.getColumnIndex(C_DEATH_DATE));
		String nationality = c.getString(c.getColumnIndex(C_NATIONALITY));
		String profession = c.getString(c.getColumnIndex(C_PROFESSION));
		String wikiUrl = c.getString(c.getColumnIndex(C_WIKIPEDIA_URL));
		int featuredQuoteId = c.getInt(c.getColumnIndex(C_FEATURED_QUOTE));
		c = db.getQuote(featuredQuoteId);
		c.moveToFirst();
		String featuredQuote = (desc == null) ? c.getString(c
				.getColumnIndex(Quote.C_CONTENT)) : desc;

		return new Author(id, name, desc, birth, death, nationality,
				profession, wikiUrl, featuredQuote);

	}
}

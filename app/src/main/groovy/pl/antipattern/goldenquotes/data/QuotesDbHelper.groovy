package pl.antipattern.goldenquotes.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import groovy.transform.CompileStatic
import pl.antipattern.goldenquotes.data.model.Author
import pl.antipattern.goldenquotes.data.model.Quote

import static nl.qbusict.cupboard.CupboardFactory.cupboard

@CompileStatic
class QuotesDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1
    private static final String DB_NAME = "Quotes.db"

    QuotesDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION)
    }

    static {
        cupboard().register(Author)
        cupboard().register(Quote)
    }

    @Override
    void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables()

        // sample data
        db.beginTransaction()
        try {
            def dbc = cupboard().withDatabase(db)
            def a = new Author(name: "Pliny the Elder")
            dbc.put(a)
            dbc.put(new Quote(text: "True glory consists in doing what deserves to be written; in writing what deserves to be read.",
                    author: a))
            dbc.put(new Quote(text: "Fortune favours the brave.", author: a))
            dbc.put(new Quote(text: "It is ridiculous to suppose that the great head of things, whatever it be, pays any regard to human affairs.",
                    author: a))
            dbc.put(new Quote(text: "With man, most of his misfortunes are occasioned by man.", author: a))
            dbc.put(new Quote(text: "It is asserted that the dogs keep running when they drink at the Nile, for fear of becoming a prey to the voracity of the crocodile.",
                    author: a))
            dbc.put(new Quote(text: "It has become quite a common proverb that in wine there is truth.", author: a))
            dbc.put(new Quote(text: "The best plan is to profit by the folly of others.", author: a))

            a = new Author(name: "Oscar Wilde")
            dbc.put(a)
            dbc.put(new Quote(text: "The bureaucracy is expanding to meet the needs of the expanding bureaucracy.", author: a))
            dbc.put(new Quote(text: "The only thing worse than being talked about is not being talked about.", author: a))
            dbc.put(new Quote(text: "A thing is not necessarily true because a man dies for it.", author: a))
            dbc.put(new Quote(text: "A poet can survive everything but a misprint.", author: a))
            dbc.put(new Quote(text: "And, after all, what is a fashion? From the artistic point of view, it is usually a form of ugliness so intolerable that we have to alter it every six months.",
                    author: a))
            dbc.put(new Quote(text: "It is always a silly thing to give advice, but to give good advice is absolutely fatal.", author: a))
            dbc.put(new Quote(text: "All art is immoral.", author: a))
            dbc.put(new Quote(text: "The mystery of love is greater than the mystery of death.", author: a))
            dbc.put(new Quote(text: "I put all my genius into my life; I put only my talent into my works.", author: a))
            dbc.put(new Quote(text: "It is better to have a permanent income than to be fascinating.", author: a))
            dbc.put(new Quote(text: "One can survive everything nowadays except death.", author: a))
            dbc.put(new Quote(text: "Prayer must never be answered: if it is, it ceases to be prayer and becomes correspondence.", author: a))
            dbc.put(new Quote(text: "Be yourself; everyone else is already taken.", author: a))
            dbc.put(new Quote(text: "Keep love in your heart. A life without it is like a sunless garden when the flowers are dead. The consciousness of loving and being loved brings warmth and richness to life that nothing else can bring.",
                    author: a))
            dbc.put(new Quote(text: "It is always the unreadable that occurs.", author: a))
            dbc.put(new Quote(text: "Life imitates art far more than art imitates Life.", author: a))
            dbc.put(new Quote(text: "No great artist ever sees things as they really are. If he did, he would cease to be an artist.", author: a))
            dbc.put(new Quote(text: "Anybody can make history. Only a great man can write it.", author: a))
            dbc.put(new Quote(text: "Oh! journalism is unreadable, and literature is not read.", author: a))
            dbc.put(new Quote(text: "Education is an admirable thing. But it is well to remember from time to time that nothing that is worth knowing can be taught.",
                    author: a))
            db.setTransactionSuccessful()
        }
        finally {
            db.endTransaction()
        }
    }

    @Override
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables()
    }
}

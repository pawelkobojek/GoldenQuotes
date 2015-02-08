package pl.antipattern.goldenquotes.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.antipattern.goldenquotes.data.QuotesData;
import pl.antipattern.goldenquotes.data.QuotesDbHelper;
import pl.antipattern.goldenquotes.ui.MainActivity;
import pl.antipattern.goldenquotes.ui.RandomQuoteFragment;

@Module(
        injects = {
                MainActivity.class,
                RandomQuoteFragment.class
        },
        library = true
)
public class QuotesModule {

    private final Context context;

    public QuotesModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public SQLiteDatabase provideDatabase() {
        return new QuotesDbHelper(context).getWritableDatabase();
    }

    @Provides
    @Singleton
    public QuotesData provideData(SQLiteDatabase db) {
        return new QuotesData(db);
    }
}

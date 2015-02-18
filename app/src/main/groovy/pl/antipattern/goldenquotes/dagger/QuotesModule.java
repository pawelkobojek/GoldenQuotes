package pl.antipattern.goldenquotes.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import pl.antipattern.goldenquotes.data.QuotesData;
import pl.antipattern.goldenquotes.data.RealmData;
import pl.antipattern.goldenquotes.event.bus.EventBus;
import pl.antipattern.goldenquotes.event.bus.GreenRobotEventBus;
import pl.antipattern.goldenquotes.ui.AuthorInfoFragment;
import pl.antipattern.goldenquotes.ui.MainActivity;
import pl.antipattern.goldenquotes.ui.RandomQuoteFragment;

@Module(
        injects = {
                MainActivity.class,
                RandomQuoteFragment.class,
                AuthorInfoFragment.class
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
    public EventBus provideEventBus() {
        // TODO - perhaps implement and use RxBus?
        return new GreenRobotEventBus();
    }

    @Provides
    @Singleton
    public Realm provideRealm() {
        return Realm.getInstance(context);
    }

    @Provides
    @Singleton
    public QuotesData provideData(Realm realm) {
        return new RealmData(realm);
    }
}

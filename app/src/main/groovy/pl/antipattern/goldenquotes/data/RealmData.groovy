package pl.antipattern.goldenquotes.data

import io.realm.Realm
import pl.antipattern.goldenquotes.data.model.Quote
import rx.Subscriber
import rx.schedulers.Schedulers

class RealmData implements QuotesData {

    private final Realm realm

    public RealmData(Realm realm) {
        this.realm = realm
    }

    rx.Observable<Quote> randomQuote() {
        rx.Observable.create({ Subscriber<? super Quote> subscriber ->
            try {
                def count = realm.where(Quote).count() as int
                def random = new Random()
                def q = realm.where(Quote).findAll().get(random.nextInt(count))

                subscriber.onNext(q)
                subscriber.onCompleted()
            } catch (Throwable e) {
                subscriber.onError(e)
            }
        }).subscribeOn(Schedulers.io())
    }

    @Override
    rx.Observable<List<Quote>> favoriteQuotes() {
        rx.Observable.create({ Subscriber<? super List<Quote>> subscriber ->
            try {
                subscriber.onNext(realm.where(Quote).equalTo("favorite", true).findAll())
                subscriber.onCompleted()
            } catch (Throwable e) {
                subscriber.onError(e)
            }
        }).subscribeOn(Schedulers.io())
    }
}

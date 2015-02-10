package pl.antipattern.goldenquotes.data

import io.realm.Realm
import pl.antipattern.goldenquotes.data.model.Quote
import rx.Subscriber
import rx.schedulers.Schedulers

final class QuotesData {
    private final Realm realm

    public QuotesData(Realm realm) {
        this.realm = realm
    }

    rx.Observable<Quote> randomQuote() {
        rx.Observable.create(new rx.Observable.OnSubscribe<Quote>() {
            @Override
            void call(Subscriber<? super Quote> subscriber) {
                try {
                    def count = realm.where(Quote).count() as int
                    def random = new Random()
                    def q = realm.where(Quote).findAll().get(random.nextInt(count))
                    subscriber.onNext(q)
                    subscriber.onCompleted()
                } catch (Throwable e) {
                    subscriber.onError(e)
                }
            }
        }).subscribeOn(Schedulers.newThread())
    }
}

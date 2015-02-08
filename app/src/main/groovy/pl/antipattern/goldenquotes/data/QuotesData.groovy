package pl.antipattern.goldenquotes.data

import android.database.sqlite.SQLiteDatabase
import groovy.transform.CompileStatic
import pl.antipattern.goldenquotes.data.model.Quote
import rx.Subscriber
import rx.schedulers.Schedulers

import static nl.qbusict.cupboard.CupboardFactory.cupboard

@CompileStatic
class QuotesData {

    private final SQLiteDatabase db

    QuotesData(SQLiteDatabase db) {
        this.db = db
    }

    rx.Observable<Quote> randomQuote() {
        rx.Observable.create(new rx.Observable.OnSubscribe<Quote>() {
            @Override
            void call(Subscriber subscriber) {
                try {
                    def quote = cupboard().withDatabase(db).query(Quote).orderBy("RANDOM()").get()
                    subscriber.onNext(quote)
                    subscriber.onCompleted()
                } catch (Exception e) {
                    subscriber.onError(e)
                }
            }
        }).subscribeOn(Schedulers.newThread())
    }
}

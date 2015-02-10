package pl.antipattern.goldenquotes

import android.app.Application
import groovy.transform.CompileStatic
import pl.antipattern.goldenquotes.dagger.Injector

@CompileStatic
class QuotesApp extends Application {

    @Override
    void onCreate() {
        super.onCreate()
        Injector.init(this)
    }

    // TODO - find some nice way to initialize database, sample code below
//    static Quote Quote(Realm realm, String content, Author author) {
//        def q = realm.createObject(Quote)
//        q.content = content
//        q.author = author
//        q
//    }
//
//    void populate() {
//        realm.beginTransaction()
//        def author = realm.createObject(Author)
//        author.name = "Pliny the Elder"
//        List<Quote> quotes = []
//        quotes << Quote(realm, "True glory consists in doing what deserves to be written; in writing what deserves to be read.", author)
//        quotes << Quote(realm, "Fortune favours the brave.", author)
//        quotes << Quote(realm, "It is ridiculous to suppose that the great head of things, whatever it be, pays any regard to human affairs.", author)
//        quotes << Quote(realm, "With man, most of his misfortunes are occasioned by man.", author)
//        quotes << Quote(realm, "It is asserted that the dogs keep running when they drink at the Nile, for fear of becoming a prey to the voracity of the crocodile.",
//                author)
//        quotes << Quote(realm, "It has become quite a common proverb that in wine there is truth.", author)
//        quotes << Quote(realm, "The best plan is to profit by the folly of others.", author)
//        quotes.each { Quote it ->
//            author.quotes.add(it)
//        }
//
//        author = realm.createObject(Author)
//        author.name = "Oscar Wilde"
//        quotes = []
//
//        quotes << Quote(realm, "The bureaucracy is expanding to meet the needs of the expanding bureaucracy.", author)
//        quotes << Quote(realm, "The only thing worse than being talked about is not being talked about.", author)
//        quotes << Quote(realm, "A thing is not necessarily true because a man dies for it.", author)
//        quotes << Quote(realm, "A poet can survive everything but a misprint.", author)
//        quotes << Quote(realm, "And, after all, what is a fashion? From the artistic point of view, it is usually a form of ugliness so intolerable that we have to alter it every six months.",
//                author)
//        quotes << Quote(realm, "It is always a silly thing to give advice, but to give good advice is absolutely fatal.", author)
//        quotes << Quote(realm, "All art is immoral.", author)
//        quotes << Quote(realm, "The mystery of love is greater than the mystery of death.", author)
//        quotes << Quote(realm, "I put all my genius into my life; I put only my talent into my works.", author)
//        quotes << Quote(realm, "It is better to have a permanent income than to be fascinating.", author)
//        quotes << Quote(realm, "One can survive everything nowadays except death.", author)
//        quotes << Quote(realm, "Prayer must never be answered: if it is, it ceases to be prayer and becomes correspondence.", author)
//        quotes << Quote(realm, "Be yourself; everyone else is already taken.", author)
//        quotes << Quote(realm, "Keep love in your heart. A life without it is like a sunless garden when the flowers are dead. The consciousness of loving and being loved brings warmth and richness to life that nothing else can bring.",
//                author)
//        quotes << Quote(realm, "It is always the unreadable that occurs.", author)
//        quotes << Quote(realm, "Life imitates art far more than art imitates Life.", author)
//        quotes << Quote(realm, "No great artist ever sees things as they really are. If he did, he would cease to be an artist.", author)
//        quotes << Quote(realm, "Anybody can make history. Only a great man can write it.", author)
//        quotes << Quote(realm, "Oh! journalism is unreadable, and literature is not read.", author)
//        quotes << Quote(realm, "Education is an admirable thing. But it is well to remember from time to time that nothing that is worth knowing can be taught.",
//                author)
//
//        quotes.each { Quote it ->
//            author.quotes.add(it)
//        }
//
//        realm.commitTransaction()
//    }
}

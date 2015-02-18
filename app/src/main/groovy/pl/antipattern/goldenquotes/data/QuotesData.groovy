package pl.antipattern.goldenquotes.data

import pl.antipattern.goldenquotes.data.model.Author
import pl.antipattern.goldenquotes.data.model.Quote

interface QuotesData {
    rx.Observable<Quote> randomQuote()

    rx.Observable<List<Quote>> favoriteQuotes()

    rx.Observable<Author> authorByName(String name)
}

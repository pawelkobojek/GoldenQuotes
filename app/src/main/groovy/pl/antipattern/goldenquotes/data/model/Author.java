package pl.antipattern.goldenquotes.data.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Author extends RealmObject {
    private String name;
    private RealmList<Quote> quotes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(RealmList<Quote> quotes) {
        this.quotes = quotes;
    }
}

package pl.antipattern.goldenquotes.data.model;

import io.realm.RealmObject;
import pl.antipattern.goldenquotes.data.model.Author;

public class Quote extends RealmObject {
    private String content;
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package pl.antipattern.goldenquotes.data.model;

import io.realm.RealmObject;

public class Quote extends RealmObject {
    private String content;
    private Author author;
    private boolean favorite;

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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}

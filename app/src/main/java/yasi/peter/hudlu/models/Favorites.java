package yasi.peter.hudlu.models;
import io.realm.RealmObject;

/**
 * Created by peter.yasi on 11/29/15.
 */
public class Favorites extends RealmObject{

    private String title;
    private String author;
    private String link;
    private String image;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

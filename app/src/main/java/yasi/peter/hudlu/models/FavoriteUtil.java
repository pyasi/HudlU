package yasi.peter.hudlu.models;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by peter.yasi on 11/29/15.
 */
public class FavoriteUtil {

    public static void addFavorite(Context context, MashableNewsItem newsItem){

        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();

        Favorites favorite = realm.createObject(Favorites.class); // Create a new object
        favorite.setAuthor(newsItem.author);
        favorite.setImage(newsItem.image);
        favorite.setLink(newsItem.link);
        favorite.setTitle(newsItem.title);

        realm.commitTransaction();
    }

    public static void removeFavorite(Context context, MashableNewsItem newsItem){
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();

        RealmResults<Favorites> result = realm.where(Favorites.class).equalTo("link", newsItem.link).findAll();

        result.clear();
        realm.commitTransaction();
    }

    public static boolean isFavorite(Context context, MashableNewsItem newsItem){

        Realm realm = Realm.getInstance(context);

        RealmResults<Favorites> result = realm.where(Favorites.class).equalTo("link", newsItem.link).findAll();
        if(result.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public static List<Favorites> getAllFavorites(Context context){
        Realm realm = Realm.getInstance(context);

        return realm.where(Favorites.class).findAll();
    }

}

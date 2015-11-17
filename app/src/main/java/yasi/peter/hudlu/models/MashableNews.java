package yasi.peter.hudlu.models;
import java.util.List;
import com.google.gson.annotations.SerializedName;


/**
 * Created by peter.yasi on 11/16/15.
 */
public class MashableNews{

    @SerializedName("new")
    public List<MashableNewsItem> newsItems;
}

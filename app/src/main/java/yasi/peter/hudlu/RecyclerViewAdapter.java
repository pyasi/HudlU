package yasi.peter.hudlu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import android.graphics.Bitmap;

import java.util.List;

import yasi.peter.hudlu.models.MashableNews;
import yasi.peter.hudlu.models.MashableNewsItem;

/**
 * Created by peter.yasi on 11/9/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<MashableNewsItem> mDataSet;
    private Context mContext;
    private AdapterInterface mListener;
    private RequestQueue mRequestQueue;

    public RecyclerViewAdapter(Context context, List<MashableNewsItem> list) {
        this.mDataSet = list;
        this.mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        mListener = (AdapterInterface) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_layout2, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        MashableNewsItem currentItem = mDataSet.get(position);

        holder.mTitleTextView.setText(currentItem.title);
        holder.mAuthorTextView.setText(currentItem.author);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(v, position);
            }
        });

        ImageRequest request = new ImageRequest(currentItem.image,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.mImageView.setImageBitmap(bitmap);
                        holder.mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HudlU", "Error requesting image");
                    }
                });
        mRequestQueue.add(request);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleTextView;
        public TextView mAuthorTextView;
        public ImageView mImageView;

        public MyViewHolder(View v) {
            super(v);
            mTitleTextView = (TextView) v.findViewById(R.id.item_title);
            mAuthorTextView = (TextView) v.findViewById(R.id.item_author);
            mImageView = (ImageView) v.findViewById(R.id.item_image);
        }
    }

    public interface AdapterInterface {
        void onItemClicked(View view, int position);
    }
}

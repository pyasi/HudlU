package yasi.peter.hudlu;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Context;
import java.util.List;

/**
 * Created by peter.yasi on 11/9/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private List<String> mStringList;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<String> stringList) {
        this.mStringList = stringList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

}

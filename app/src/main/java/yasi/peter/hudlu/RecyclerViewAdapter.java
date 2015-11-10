package yasi.peter.hudlu;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Context;
import java.util.List;
import android.view.*;

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

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_layout, parent, false);

        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mStringList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public MyViewHolder(TextView v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.my_text_view);
        }
    }

}

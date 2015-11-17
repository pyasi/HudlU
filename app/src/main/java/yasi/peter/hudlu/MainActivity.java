package yasi.peter.hudlu;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.content.Context;
import android.net.NetworkInfo;
import android.widget.Toast;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.*;
import yasi.peter.hudlu.models.MashableNews;
import yasi.peter.hudlu.models.MashableNewsItem;
import java.util.ArrayList;
import android.view.animation.*;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.AdapterInterface {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private final List<MashableNewsItem> myData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, myData);
        mRecyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fetchLatestNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("HudlU", "Settings menu item clicked.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Snackbar.make(view, myData.get(position).author, Snackbar.LENGTH_SHORT).show();
    }

    public void fetchLatestNews(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            Toast toast = Toast.makeText(getApplicationContext(), "Fetching latest news...", Toast.LENGTH_SHORT);
            toast.show();

            StringRequest request = new StringRequest(Request.Method.GET,
                    "http://mashable.com/stories.json?hot_per_page=0&new_per_page=5&rising_per_page=0",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            MashableNews news = new Gson().fromJson(response, MashableNews.class);
                            Log.d("HudlU", news.newsItems.get(0).title);
                            myData.addAll(news.newsItems);
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast errorToast = Toast.makeText(getApplicationContext(), "Error has occurred", Toast.LENGTH_SHORT);
                            errorToast.show();
                            Log.e("HudlU", "Error while requesting string");
                        }
                    });
            requestQueue.add(request);

        } else {
            Toast connectivityFailedToast = Toast.makeText(getApplicationContext(), "No internet connectivity", Toast.LENGTH_SHORT);
            connectivityFailedToast.show();
        }

    }
}

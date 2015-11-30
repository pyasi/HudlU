package yasi.peter.hudlu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import yasi.peter.hudlu.models.FavoriteUtil;
import yasi.peter.hudlu.models.Favorites;
import yasi.peter.hudlu.models.MashableNews;
import yasi.peter.hudlu.models.MashableNewsItem;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;


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

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        Boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if(isFirstTime) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Hello!");
            builder.setMessage("Welcome to HudlU");
            builder.create();
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstTime", false);
        editor.apply();

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
        if (id == R.id.action_favorites) {
            Log.d("HudlU", "Favorite menu item clicked.");

            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Snackbar.make(view, myData.get(position).author, Snackbar.LENGTH_SHORT).show();

        Uri webpage = Uri.parse(myData.get(position).link);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    @Override
    public void onFavoriteClicked(View view, MashableNewsItem item){
        FavoriteUtil util = new FavoriteUtil();

        if(!util.isFavorite(this, item)) {
            util.addFavorite(this, item);
        }
        else{
            util.removeFavorite(this, item);
        }

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

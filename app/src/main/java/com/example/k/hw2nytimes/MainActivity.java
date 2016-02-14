package com.example.k.hw2nytimes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    NewsItemAdapter nad;
    private ArrayList<NewsItem> nList = new ArrayList<NewsItem>();
    private SearchOptionsFragment filterDialog;
    private SearchOptions filter;
    private RecyclerView rv;

    public void searchPerform(final Context context, int page, final boolean append) {

        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String query = filter.getQuery();
        params.put("q", query);
        params.put("api-key", "129316eb90753114eeb097b8e4a96309:8:74355823");
        params.put("sort", filter.isOldestFirst() ? "oldest" : "newest");
        params.put("page", page);

        //Construct the filter
        String filterContent = "(";
        filterContent = filterContent.concat(filter.isCategory_art() ? "\"Art\" " : "");
        filterContent = filterContent.concat(filter.isCategory_fashion() ? "\"Fashion\" " : "");
        filterContent = filterContent.concat(filter.isCategory_sports() ? "\"Sports\" " : "");
        filterContent = filterContent.concat(")");

        String filterOptions = "news_desk:" + filterContent;

        Log.d("filter", filterOptions);
        Log.d("filter", filter.getBeginDate());

        Date end = new Date();
        SimpleDateFormat endStr = new SimpleDateFormat("yyyyMMdd");
        String endDate = endStr.format(end);

        params.put("fq", filterOptions);
        params.put("begin_date", filter.getBeginDate());
        params.put("end_date", endDate);

        Log.d("params", params.toString());

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                // Handle resulting parsed JSON response here

                int length = 0;
                ArrayList<NewsItem> nList1 = new ArrayList<NewsItem>();

                try {
                    JSONObject resp = response.getJSONObject("response");
                    JSONArray docs = resp.getJSONArray("docs");
                    length = docs.length();
                    JSONObject item;

                    for (int i = 0; i < length; i++) {


                        item = docs.getJSONObject(i);
                        String permalink = item.getString("web_url");
                        JSONObject headLineObj = item.getJSONObject("headline");
                        String headline = headLineObj.getString("main");

                        Log.d("kartik", headline);


                        JSONArray mediaObjArray = item.getJSONArray("multimedia");

                        int mediaLen = 0;
                        mediaLen = mediaObjArray.length();

                        String mediaURL = "null.com";

                        if (mediaLen > 0) {

                            JSONObject mediaObj = mediaObjArray.getJSONObject(0);
                            mediaURL = mediaObj.getString("url");

                        }
                        NewsItem nItem = new NewsItem(mediaURL, headline, permalink);
                        nList1.add(nItem);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (append) {

                    nList.addAll(nList1);
                    int cursize = nad.getItemCount();
                    nad.notifyItemRangeChanged(cursize, nList.size() - 1);

                } else {
                    nList.clear();
                    nList.addAll(nList1);
                    nad.notifyDataSetChanged();

                }


                Toast toast = Toast.makeText(context, "Network Success " + length, Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast toast = Toast.makeText(context, "Network Failure", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Setup action bar


        //Setup grid view
        rv = (RecyclerView) findViewById(R.id.rv);

        // Create Adapter
        nad = new NewsItemAdapter(nList, this);

        //Bind the adapter
        rv.setAdapter(nad);

        //Set layout Manager
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(sglm);

        rv.addOnScrollListener(new EndlessRecyclerViewScrollListener(sglm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                customLoadMoreDataFromApi(page);
            }
        });

        //Default Options
        this.filter = new SearchOptions(false, 100, true, true, true);

        filterDialog = SearchOptionsFragment.getInstance(filter);

        searchPerform(getApplicationContext(), 0, false);


    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(int offset) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        // Deserialize API response and then construct new objects to append to the adapter
        // Add the new objects to the data source for the adapter

        searchPerform(this, (offset / 10) + 1, true);
        // For efficiency purposes, notify the adapter of only the elements that got changed
        // curSize will equal to the index of the first element inserted because the list is 0-indexed


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem searchFilter = menu.findItem(R.id.action_filter);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchFilter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                filterDialog.show(getSupportFragmentManager(), "Filter");


                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                filter.setQuery(query);
                searchPerform(getApplicationContext(), 0, false);


                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used

                // see https://code.google.com/p/android/issues/detail?id=24599

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

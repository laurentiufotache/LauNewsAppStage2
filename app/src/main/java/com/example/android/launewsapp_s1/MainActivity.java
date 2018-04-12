package com.example.android.launewsapp_s1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String GUARDIAN_NEWS_REQUEST_URL =
            "https://content.guardianapis.com/search?show-tags=contributor&api-key=c9e8c1f0-c76d-43bb-95fc-b13d7474e0ac";

    // Constant value for the news loader ID. We can choose any integer.
    private static final int NEWS_LOADER_ID = 1;

    // Adapter for news
    private NewsAdapter newsAdapter;

    // mEmptyTextView is displayed when the list has not have data to display
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find newsList ListView in activity_main.xml
        ListView newsListView = findViewById(R.id.newsList);

        // Find noNews TextView in activity_main.xml and setEmptyViewState
        mEmptyStateTextView = findViewById(R.id.noNews);
        newsListView.setEmptyView(mEmptyStateTextView);

        // Create new adapter that takes an empty list of news as input
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the ListView, so the list can be populated in the user interface
        newsListView.setAdapter(newsAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser to open the Guardian website with the selected news
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Find the news that was clicked on
                News clickedNews = newsAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsURI = Uri.parse(clickedNews.getUrl());

                // Create a new intent to view the news URI
                Intent webNewsIntent = new Intent(Intent.ACTION_VIEW, newsURI);

                // Send the intent to launch a new activity
                startActivity(webNewsIntent);

            }
        });

        // Get a reference to the ConnectivityManager to check state of the network connectivity
        ConnectivityManager connectivityMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_progress_bar);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no internet connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection_error);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new NewsLoader(this, GUARDIAN_NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        //Hide loading progress bar indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No news found."
        mEmptyStateTextView.setText(R.string.no_news_error);

        // Clear the adapter of previous news data
        newsAdapter.clear();

        // If there is a valid list of news, then add them to the adapter's data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            newsAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        // Loader reset, so we can clear out our existing data.
        newsAdapter.clear();
    }
}
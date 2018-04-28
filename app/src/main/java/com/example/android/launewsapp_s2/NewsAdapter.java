package com.example.android.launewsapp_s2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lfotache on 06.04.2018.
 */

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each news in the data source (a list of {@link News} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView to be displayed to the user.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param news    is the list of news, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    /**
     * Returns a list item view that displays information about the news at the given position in the list of news.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse, otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the news at the given position in the list of news
        News currentNews = getItem(position);

        // Find the TextView for the news title
        TextView newsTitleTextView = listItemView.findViewById(R.id.newsTitle);

        // Display the current news title
        newsTitleTextView.setText(currentNews.getTitle());

        // Find the TextView for the news category
        TextView newsCategoryTextView = listItemView.findViewById(R.id.newsCategory);

        // Display the current news category
        newsCategoryTextView.setText(currentNews.getCategory());

        // Find the TextView for news author
        TextView newsAuthorTextView = listItemView.findViewById(R.id.newsAuthor);

        // Display the current news category
        newsAuthorTextView.setText(currentNews.getAuthor());

        // Find the TextView for news date
        TextView currentNewsDate = listItemView.findViewById(R.id.newsDate);

        // Extract, format and display the current date news
        SimpleDateFormat dateFormatJSON = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        SimpleDateFormat myDateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US);

        try {
            Date dateNews = dateFormatJSON.parse(currentNews.getDate());
            String date = myDateFormat.format(dateNews);
            currentNewsDate.setText(date);
        } catch (ParseException e) {
            Log.e("News Date Parsing Error", "Error parsing json date:" + e.getMessage());
            e.printStackTrace();
        }

        // Return the news list
        return listItemView;
    }
}


package com.example.android.launewsapp_s1;

/**
 * Created by lfotache on 06.04.2018.
 */

public class News {
    // News title
    private String mTitle;

    // News sectionName
    private String mCategory;

    // Author name
    private String mAuthor;

    // News date
    private String mDate;

    // News webURL
    private String mUrl;

    /**
     * Constructs a new News object.
     */

    public News(String title, String category, String author, String date, String url) {
        mTitle = title;
        mCategory = category;
        mAuthor = author;
        mDate = date;
        mUrl = url;
    }

    /**
     * Returns the news title.
     */
    public String getTitle() {

        return mTitle;
    }

    /**
     * Returns the news category.
     */
    public String getCategory() {

        return mCategory;
    }

    /**
     * Returns the news author name.
     */
    public String getAuthor() {

        return mAuthor;
    }

    /**
     * Returns the news date.
     */
    public String getDate() {

        return mDate;
    }

    /**
     * Returns news website URL.
     */
    public String getUrl() {

        return mUrl;
    }
}

package com.example.hossam.news;

public class Item {
    private String mSection;
    private String mTitle;
    private String mDate;
    private String mUrl;

    public Item(String Section, String Title, String Date, String url) {
        mSection = Section;
        mTitle = Title;
        mDate = Date;
        mUrl = url;
    }

    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}

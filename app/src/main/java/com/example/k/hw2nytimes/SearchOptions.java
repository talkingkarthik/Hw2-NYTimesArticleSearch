package com.example.k.hw2nytimes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Komal on 2/12/2016.
 */
public class SearchOptions {

    private boolean oldestFirst;
    private long beginDate;
    private boolean category_art;
    private boolean category_sports;
    private boolean category_fashion;
    private String query = "hockey";

    public SearchOptions(boolean oldestFirst, long beginDate, boolean category_art, boolean category_fashion, boolean category_sports) {
        this.category_art = category_art;
        this.category_fashion = category_fashion;
        this.category_sports = category_sports;
        this.beginDate = beginDate;
        this.oldestFirst = oldestFirst;
    }

    public boolean isCategory_fashion() {
        return category_fashion;
    }

    public void setCategory_fashion(boolean category_fashion) {
        this.category_fashion = category_fashion;
    }

    public boolean isCategory_sports() {
        return category_sports;
    }

    public void setCategory_sports(boolean category_sports) {
        this.category_sports = category_sports;
    }

    public boolean isCategory_art() {
        return category_art;
    }

    public void setCategory_art(boolean category_art) {
        this.category_art = category_art;
    }

    public String getBeginDate() {

        Date date = new Date(beginDate);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
        String dateText = df2.format(date);
        return dateText;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public boolean isOldestFirst() {
        return oldestFirst;
    }

    public void setOldestFirst(boolean oldestFirst) {
        this.oldestFirst = oldestFirst;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

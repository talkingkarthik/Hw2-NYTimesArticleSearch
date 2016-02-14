package com.example.k.hw2nytimes;

/**
 * Created by Komal on 2/9/2016.
 */
public class NewsItem {
    private String imageUrl;
    private String headLine;
    private String newsUrl;

    public NewsItem(String ImgUrl, String headLine, String nwsUrl) {
        this.imageUrl = ImgUrl;
        this.headLine = headLine;
        this.newsUrl = nwsUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}

package com.qarepo.models;

public class GoogleSearchResult {

    private String title;
    private String URL;
    private String description;

    public GoogleSearchResult(){};

    public GoogleSearchResult(String title, String URL, String description) {
        this.title = title;
        this.URL = URL;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GoogleSearchResult{" +
                "title='" + title + '\'' +
                ", URL='" + URL + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

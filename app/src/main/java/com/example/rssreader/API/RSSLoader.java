package com.example.rssreader.API;

import com.example.rssreader.API.Data.Rss;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RSSLoader {

    @GET("{page}/feed")
    Call<Rss> getNews(@Path(value = "page", encoded = true) String page);

}

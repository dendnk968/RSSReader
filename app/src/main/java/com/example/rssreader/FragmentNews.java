package com.example.rssreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rssreader.API.Data.News;
import com.example.rssreader.API.Data.Rss;
import com.example.rssreader.API.RSSLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class FragmentNews extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView list;
    private List<News> news_list;
    private static String HOST = "https://courseburg.ru/";
    private SwipeRefreshLayout refreshLayout;

    public static FragmentNews newInstance() {
        FragmentNews fragment = new FragmentNews();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            news_list = (List<News>) savedInstanceState.getSerializable("list");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", (Serializable) news_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        list = view.findViewById(R.id.news_wall);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        refreshLayout = view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        if (news_list == null) {
            onRefresh();
        } else {
            list.setAdapter(new CardAdapter(news_list));
        }

        return view;
    }

    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(HOST)
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .build();
        RSSLoader rssLoader = retrofit.create(RSSLoader.class);
        Call<Rss> call = rssLoader.getNews(getArguments().getString("page"));
        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                news_list = response.body().getChannel().getNews();
                list.setAdapter(new CardAdapter(news_list));
                Log.d("DEBAG", "onResponse: OK");
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d("DEBAG", t.getMessage());
                refreshLayout.setRefreshing(false);
                news_list = new ArrayList<>();
                list.setAdapter(new CardAdapter(news_list));
                Toast.makeText(getActivity().getApplicationContext(),
                        "Превышенно время ожидания. Попробуйте чуточку позже.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}

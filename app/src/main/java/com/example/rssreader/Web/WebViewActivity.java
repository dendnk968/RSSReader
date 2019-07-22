package com.example.rssreader.Web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.rssreader.R;

public class WebViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    WebView webView;
    public final static String CONTENT = "content";
    public final static String URL = "URL";
    private String content;
    private String url;
    private SwipeRefreshLayout refresh;
    private ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        content = getIntent().getStringExtra(CONTENT);
        url = getIntent().getStringExtra(URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString(URL);
            content = savedInstanceState.getString(CONTENT);
        }
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        webView = findViewById(R.id.wev_view);
        refresh = findViewById(R.id.refresh);
        progressBar = findViewById(R.id.progress);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.setWebChromeClient(new CustomWebChromClient(progressBar));
        refresh.setOnRefreshListener(this);
        if (savedInstanceState == null) {
            if (url != null)
                webView.loadUrl(url);
            else {
                webView.loadDataWithBaseURL(null, (content), "text/html", "ru", null);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CONTENT, content);
        outState.putString(URL, url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        webView.reload();
    }


}

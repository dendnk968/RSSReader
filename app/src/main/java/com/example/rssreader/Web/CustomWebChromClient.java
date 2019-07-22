package com.example.rssreader.Web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class CustomWebChromClient extends WebChromeClient {
    ProgressBar progressBar;

    public CustomWebChromClient(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int progress) {
        super.onProgressChanged(view, progress);
        if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        progressBar.setProgress(progress);
        if (progress == 100) {
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }
}

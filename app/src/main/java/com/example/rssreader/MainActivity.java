package com.example.rssreader;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.rssreader.Web.WebViewActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TextView.OnEditorActionListener {
    private static final String ABOUT = "https://courseburg.ru/news/contact";
    private final static String CURS = "https://courseburg.ru";
    private final static String WRITER = "https://courseburg.ru/news/kak_stat_avtorom";
    private final static String SEARCH = "https://courseburg.ru/news/?s=";
    private final static String POPULAR = "https://courseburg.ru/news/popular";
    private DrawerLayout drawer;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setScrimColor(Color.parseColor("#fef0f3f6"));
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextColor(new ColorStateList(new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{}
        },
                new int[]{
                        getApplicationContext().getResources().getColor(R.color.textColorPrimary),
                        getApplicationContext().getResources().getColor(R.color.textColor)
        }));
        View nView = navigationView.getHeaderView(0);
        editText = nView.findViewById(R.id.search_field);
        editText.setOnEditorActionListener(this);

        nView.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new) {
            Bundle bundle = new Bundle();
            bundle.putString("page", "news");
            Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_self, bundle);// Handle the camera action
        } else if (id == R.id.nav_pop) {
            Bundle bundle = new Bundle();
            bundle.putString(WebViewActivity.URL, POPULAR);
            Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_to_webViewActivity, bundle);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        } else if (id == R.id.nav_career) {
            Bundle bundle = new Bundle();
            bundle.putString("page", "news/career");
            Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_self, bundle);// Handle the camera action

        } else if (id == R.id.nav_education) {
            Bundle bundle = new Bundle();
            bundle.putString("page", "news/samoobrazovanie");
            Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_self, bundle);// Handle the camera action

        } else if (id == R.id.nav_rede) {
            Bundle bundle = new Bundle();
            bundle.putString("page", "news/sovetyi_na_kazhdyiy_den");
            Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_self, bundle);// Handle the camera action

        } else if (id == R.id.nav_curs) {
            Bundle bundle = new Bundle();
            bundle.putString(WebViewActivity.URL, CURS);
            Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_to_webViewActivity, bundle);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        } else if (id == R.id.nav_writer) {
            Bundle bundle = new Bundle();
            bundle.putString(WebViewActivity.URL, WRITER);
            Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_to_webViewActivity, bundle);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        } else if (id == R.id.nav_about) {
            Bundle bundle = new Bundle();
            bundle.putString(WebViewActivity.URL, ABOUT);
            Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_to_webViewActivity, bundle);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSearch() {
        editText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        Bundle bundle = new Bundle();
        bundle.putString(WebViewActivity.URL, SEARCH + editText.getText());
        editText.setText("");
        drawer.closeDrawer(GravityCompat.START);
        Navigation.findNavController(this, R.id.news_wall).navigate(R.id.action_fragmentNews_to_webViewActivity, bundle);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onSearch();
            return true;
        }
        return false;
    }
}

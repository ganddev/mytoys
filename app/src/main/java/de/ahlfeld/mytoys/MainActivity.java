package de.ahlfeld.mytoys;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import de.ahlfeld.mytoys.data.NavigationEntry;
import de.ahlfeld.mytoys.data.source.NavigationEntriesRepository;
import de.ahlfeld.mytoys.data.source.remote.NavigationEntriesDao;
import de.ahlfeld.mytoys.data.source.remote.NavigationEntriesDaoProvider;
import de.ahlfeld.mytoys.data.source.remote.NavigationRemoteDataSource;
import de.ahlfeld.mytoys.data.source.remote.OkHttpClientProvider;
import de.ahlfeld.mytoys.databinding.ActivityMainBinding;
import de.ahlfeld.mytoys.navigation.MainActivityViewModel;
import de.ahlfeld.mytoys.navigation.MainActivityViewModelFactory;
import de.ahlfeld.mytoys.navigation.NavigationEntryAdapter;
import de.ahlfeld.mytoys.navigation.NavigationEntryItemNavigator;

public class MainActivity extends AppCompatActivity implements NavigationEntryItemNavigator {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DEFAULT_URL = "https://www.mytoys.de";
    private ActivityMainBinding mActivityBinding;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationEntryAdapter mNavigationEntryAdapter;

    private MainActivityViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupToolbar();
        setupDrawerToggle();
        setupNavigation();
        setupWebView();

        OkHttpClientProvider httpClientProvider = new OkHttpClientProvider();
        factory = new MainActivityViewModelFactory(
                new NavigationEntriesRepository(
                        new NavigationRemoteDataSource(
                                NavigationEntriesDaoProvider.get(
                                        httpClientProvider.get().build(),
                                        NavigationEntriesDao.ENDPOINT))));

        final MainActivityViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(MainActivityViewModel.class);

        viewModel.getNavigationEntries().observe(this, new Observer<List<NavigationEntry>>() {
            @Override
            public void onChanged(@Nullable List<NavigationEntry> navigationEntries) {
                mNavigationEntryAdapter.replaceData(viewModel.getParentNavigationEntry(),
                        navigationEntries);
            }
        });
        mActivityBinding.setViewModel(viewModel);
    }

    private void setupWebView() {
        WebView myWebView = mActivityBinding.webView;
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(DEFAULT_URL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNavigationEntryAdapter != null) {
            mNavigationEntryAdapter.onDestroy();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onNavigationEntryClick(@NonNull NavigationEntry navigationEntry) {
        Log.d(TAG, "clicked on navigation entry: " + navigationEntry.getLabel());
        MainActivityViewModel model = ViewModelProviders.of(this, factory)
                .get(MainActivityViewModel.class);
        model.navigationDown(navigationEntry);
    }

    @Override
    public void onLinkClicked(@NonNull String url) {
        Log.d(TAG, "Clicked on link: " + url);
        mActivityBinding.webView.loadUrl(url);
        closeDrawer();
    }

    @Override
    public void onCloseDrawerClicked() {
        MainActivityViewModel model = ViewModelProviders.of(this, factory)
                .get(MainActivityViewModel.class);
        model.reset();
        closeDrawer();
    }

    @Override
    public void onUpClicked() {
        MainActivityViewModel model = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);
        model.naivagtionUp();
    }

    @Override
    public void onBackPressed() {
        closeDrawer();
    }

    private void setupToolbar() {
        setSupportActionBar(mActivityBinding.toolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mActivityBinding.drawerLayout,
                mActivityBinding.toolbar.toolbar,
                R.string.app_name,
                R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    private void setupNavigation() {
        mNavigationEntryAdapter = new NavigationEntryAdapter(this, this);
        mActivityBinding.leftDrawer.setAdapter(mNavigationEntryAdapter);
    }

    private void closeDrawer() {
        if (this.mActivityBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mActivityBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
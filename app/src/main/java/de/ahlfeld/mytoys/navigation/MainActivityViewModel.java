package de.ahlfeld.mytoys.navigation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import de.ahlfeld.mytoys.data.NavigationEntry;
import de.ahlfeld.mytoys.data.source.NavigationEntriesDataSource;
import de.ahlfeld.mytoys.data.source.NavigationEntriesRepository;
import de.ahlfeld.mytoys.data.source.remote.NavigationEntriesDaoProvider;
import de.ahlfeld.mytoys.data.source.remote.NavigationRemoteDataSource;
import de.ahlfeld.mytoys.data.source.remote.OkHttpClientProvider;

/**
 * Created by bjornahlfeld on 01.02.18.
 */

public class MainActivityViewModel extends ViewModel implements NavigationEntriesDataSource.LoadNavigationEntriesCallback {
    private final NavigationEntriesRepository mNavigationEntriesRepository;
    private NavigationEntry mParentNavigationEntry;
    private final MutableLiveData<List<NavigationEntry>> mNavigationEntries = new MutableLiveData<>();
    private Stack<NavigationEntry> mNavigationStack = new Stack<>();

    public MainActivityViewModel() {
        mParentNavigationEntry = null;
        mNavigationEntriesRepository = new NavigationEntriesRepository(new NavigationRemoteDataSource(NavigationEntriesDaoProvider.get(OkHttpClientProvider.get())));
        loadNavigationEntries();
    }

    @NonNull
    public LiveData<List<NavigationEntry>> getNavigationEntries() {
        return mNavigationEntries;
    }

    @Nullable
    public NavigationEntry getParentNavigationEntry() {
        return mParentNavigationEntry;
    }

    public void naivagtionUp() {
        mNavigationStack.pop();
        if (mNavigationStack.isEmpty()) {
            reset();
        } else {
            mParentNavigationEntry = mNavigationStack.peek();
            mNavigationEntries.setValue(mParentNavigationEntry.getChildren());
        }
    }

    public void navigationDown(@NonNull NavigationEntry navigationEntry) {
        mNavigationStack.push(navigationEntry);
        mParentNavigationEntry = navigationEntry;
        mNavigationEntries.setValue(navigationEntry.getChildren());
    }

    public void reset() {
        mParentNavigationEntry = null;
        mNavigationStack.removeAllElements();
        loadNavigationEntries();
    }

    private void loadNavigationEntries() {
        mNavigationEntriesRepository.getNavigationEntries(this);
    }

    @Override
    public void onNavigationEntriesLoaded(@NonNull List<NavigationEntry> navigationEntries) {
        mNavigationEntries.setValue(navigationEntries);
    }

    @Override
    public void onDataNotAvailable() {
        mNavigationEntries.setValue(Collections.<NavigationEntry>emptyList());
    }
}

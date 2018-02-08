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

/**
 * Created by bjornahlfeld on 01.02.18.
 */

public class MainActivityViewModel extends ViewModel implements NavigationEntriesDataSource.LoadNavigationEntriesCallback {
    private final NavigationEntriesRepository mNavigationEntriesRepository;
    private NavigationEntry mParentNavigationEntry;
    private final MutableLiveData<List<NavigationEntry>> mNavigationEntries = new MutableLiveData<>();
    private Stack<NavigationEntry> mNavigationStack = new Stack<>();

    public MainActivityViewModel(@NonNull NavigationEntriesRepository navigationEntriesRepository) {
        mParentNavigationEntry = null;
        mNavigationEntriesRepository = navigationEntriesRepository;
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
            setNavigationEntries(mParentNavigationEntry.getChildren());
        }
    }

    public void setNavigationEntries(List<NavigationEntry> children) {
        mNavigationEntries.setValue(children);
    }

    public void navigationDown(@NonNull NavigationEntry navigationEntry) {
        mNavigationStack.push(navigationEntry);
        mParentNavigationEntry = navigationEntry;
        setNavigationEntries(navigationEntry.getChildren());
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
        setNavigationEntries(navigationEntries);
    }

    @Override
    public void onDataNotAvailable() {
        setNavigationEntries(Collections.<NavigationEntry>emptyList());
    }
}

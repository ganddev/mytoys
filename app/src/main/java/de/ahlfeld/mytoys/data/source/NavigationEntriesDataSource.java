package de.ahlfeld.mytoys.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import de.ahlfeld.mytoys.data.NavigationEntry;

/**
 * Created by bjornahlfeld on 01.02.18.
 */

public interface NavigationEntriesDataSource {

    interface LoadNavigationEntriesCallback {

        void onNavigationEntriesLoaded(@NonNull List<NavigationEntry> navigationEntries);

        void onDataNotAvailable();
    }

    void getNavigationEntries(@NonNull LoadNavigationEntriesCallback callback);
}

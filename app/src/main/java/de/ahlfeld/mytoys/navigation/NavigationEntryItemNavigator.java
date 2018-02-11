package de.ahlfeld.mytoys.navigation;

import android.support.annotation.NonNull;

import de.ahlfeld.mytoys.data.NavigationEntry;

/**
 * Created by bjornahlfeld on 02.02.18.
 */
public interface NavigationEntryItemNavigator {
    void onNavigationEntryClick(@NonNull NavigationEntry navigationEntry);

    void onLinkClicked(@NonNull String url);

    void onCloseDrawerClicked();

    void onUpClicked();
}

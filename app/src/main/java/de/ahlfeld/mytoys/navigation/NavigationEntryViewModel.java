package de.ahlfeld.mytoys.navigation;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import de.ahlfeld.mytoys.data.NavigationEntry;

/**
 * Created by bjornahlfeld on 02.02.18.
 */

public class NavigationEntryViewModel extends ViewModel {
    private WeakReference<NavigationEntryItemNavigator> mItemNavigator;
    private ObservableField<NavigationEntry> mNavigationEntry = new ObservableField<>();
    private ObservableField<String> mLabel = new ObservableField();
    private ObservableBoolean isSection = new ObservableBoolean();
    private ObservableBoolean hasChildren = new ObservableBoolean();

    public NavigationEntryViewModel(@NonNull NavigationEntry navigationEntry) {
        mNavigationEntry.set(navigationEntry);
        mLabel.set(navigationEntry.getLabel());
        isSection.set(navigationEntry.getType() == "section");
        hasChildren.set(!navigationEntry.getChildren().isEmpty());
    }

    public ObservableField<String> getLabel() {
        return mLabel;
    }

    public ObservableBoolean isSection() {
        return isSection;
    }

    public ObservableBoolean hasChildren() {
        return hasChildren;
    }

    public void setItemNavigator(NavigationEntryItemNavigator navigationEntryItemNavigator) {
        this.mItemNavigator = new WeakReference<>(navigationEntryItemNavigator);
    }


    public void onNavigationEntryClick() {
        if (mItemNavigator != null && mItemNavigator.get() != null) {
            navigateTo(mNavigationEntry.get());
        }
    }

    public void setNavigationEntry(NavigationEntry navigationEntry) {
        mNavigationEntry.set(navigationEntry);
        mLabel.set(navigationEntry.getLabel());
        isSection.set(navigationEntry.getType() == "section");
        hasChildren.set(!navigationEntry.getChildren().isEmpty());
    }

    private void navigateTo(@NonNull NavigationEntry navigationEntry) {
        switch (navigationEntry.getType()) {
            case "section":
                return;
            case "node":
                mItemNavigator.get().onNavigationEntryClick(navigationEntry);
                break;
            case "link":
                mItemNavigator.get().onLinkClicked(navigationEntry.getUrl());
                break;
            default:
                throw new IllegalStateException("type is not supported: " + navigationEntry.getType());
        }
    }
}
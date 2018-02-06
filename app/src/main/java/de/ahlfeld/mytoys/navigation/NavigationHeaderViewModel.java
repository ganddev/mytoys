package de.ahlfeld.mytoys.navigation;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import de.ahlfeld.mytoys.data.NavigationEntry;

/**
 * Created by bjornahlfeld on 03.02.18.
 */

public class NavigationHeaderViewModel extends ViewModel {
    private WeakReference<NavigationEntryItemNavigator> mItemNavigator;
    private ObservableField<String> mLabel = new ObservableField<>();
    private ObservableBoolean mIsUpProvided = new ObservableBoolean();

    public NavigationHeaderViewModel(@Nullable NavigationEntry header) {
        mLabel.set(header != null ? header.getLabel() : "");
        mIsUpProvided.set(header != null);
    }

    public ObservableField<String> getLabel() {
        return mLabel;
    }

    public ObservableBoolean upIsProvided() {
        return mIsUpProvided;
    }

    public void onClose() {
        if (mItemNavigator != null && mItemNavigator.get() != null) {
            mItemNavigator.get().onCloseDrawerClicked();
        }
    }

    public void setmItemNavigator(NavigationEntryItemNavigator itemNavigator) {
        this.mItemNavigator = new WeakReference<>(itemNavigator);
    }

    public void onUp() {
        if (mItemNavigator != null && mItemNavigator.get() != null) {
            mItemNavigator.get().onUpClicked();
        }
    }
}

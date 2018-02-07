package de.ahlfeld.mytoys.navigation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import de.ahlfeld.mytoys.data.source.NavigationEntriesRepository;

/**
 * Created by bjornahlfeld on 06.02.18.
 */

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private NavigationEntriesRepository mNavigationEntryRepository;


    public MainActivityViewModelFactory(@NonNull NavigationEntriesRepository mNavigationEntryRepository) {
        this.mNavigationEntryRepository = mNavigationEntryRepository;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(mNavigationEntryRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

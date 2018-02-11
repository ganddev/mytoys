package de.ahlfeld.mytoys.data.source.remote;

import android.support.annotation.NonNull;

import de.ahlfeld.mytoys.data.source.NavigationEntriesDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bjornahlfeld on 06.02.18.
 */
public class NavigationRemoteDataSource implements NavigationEntriesDataSource {
    private NavigationEntriesDao mNavigationsEntriesDao;

    public NavigationRemoteDataSource(@NonNull NavigationEntriesDao mNavigationsEntriesDao) {
        this.mNavigationsEntriesDao = mNavigationsEntriesDao;
    }

    @Override
    public void getNavigationEntries(@NonNull final LoadNavigationEntriesCallback callback) {
        mNavigationsEntriesDao.getNavigationEntries().enqueue(new Callback<de.ahlfeld.mytoys.data.source.remote.Response>() {
            @Override
            public void onResponse(Call<de.ahlfeld.mytoys.data.source.remote.Response> call, Response<de.ahlfeld.mytoys.data.source.remote.Response> response) {
                callback.onNavigationEntriesLoaded(response.body().getNavigationEntries());
            }

            @Override
            public void onFailure(Call<de.ahlfeld.mytoys.data.source.remote.Response> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }
}
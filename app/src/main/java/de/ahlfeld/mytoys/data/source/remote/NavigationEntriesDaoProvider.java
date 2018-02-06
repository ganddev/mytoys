package de.ahlfeld.mytoys.data.source.remote;


import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bjornahlfeld on 06.02.18.
 */

public class NavigationEntriesDaoProvider {
    private static NavigationEntriesDao sNavigationsEntriesDao;

    public static NavigationEntriesDao get(@NonNull OkHttpClient httpClient) {
        if (sNavigationsEntriesDao == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NavigationEntriesDao.ENDPOINT)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            sNavigationsEntriesDao = retrofit.create(NavigationEntriesDao.class);
        }
        return sNavigationsEntriesDao;
    }
}

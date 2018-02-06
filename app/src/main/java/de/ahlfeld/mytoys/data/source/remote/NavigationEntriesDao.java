package de.ahlfeld.mytoys.data.source.remote;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bjornahlfeld on 01.02.18.
 */

public interface NavigationEntriesDao {

    String ENDPOINT = "https://mytoysiostestcase1.herokuapp.com/api/";

    @GET("navigation")
    Call<Response> getNavigationEntries();
}

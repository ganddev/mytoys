package de.ahlfeld.mytoys.data.source.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by bjornahlfeld on 06.02.18.
 */

public class OkHttpClientProvider {
    private static OkHttpClient sOkHttpClient = null;

    public static OkHttpClient get() {
        if (sOkHttpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
            httpBuilder.addInterceptor(new RetrofitInterceptor());

            sOkHttpClient = httpBuilder.build();
        }
        return sOkHttpClient;
    }
}

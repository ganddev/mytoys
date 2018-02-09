package de.ahlfeld.mytoys.data.source.remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bjornahlfeld on 06.02.18.
 */

public class RetrofitInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("x-api-key", "hz7JPdKK069Ui1TRxxd1k8BQcocSVDkj219DVzzD");

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

package de.ahlfeld.mytoys.data.source.remote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by bjornahlfeld on 06.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class RetrofitInterceptorTest {

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        server = new MockWebServer();
    }

    @Test
    public void intercept_addsXapiKey() throws Exception {
        // Ask the server for its URL. You'll need this to make HTTP requests.
        HttpUrl baseUrl = server.url(NavigationEntriesDao.ENDPOINT);


        // Optional: confirm that your app made the HTTP requests you were expecting.
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/v1/chat/messages/", request1.getPath());
        assertNotNull(request1.getHeader("Authorization"));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}
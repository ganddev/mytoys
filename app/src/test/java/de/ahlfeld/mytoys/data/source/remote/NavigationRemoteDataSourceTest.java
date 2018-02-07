package de.ahlfeld.mytoys.data.source.remote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import de.ahlfeld.mytoys.data.source.NavigationEntriesDataSource;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by bjornahlfeld on 07.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigationRemoteDataSourceTest {

    @Mock
    private NavigationEntriesDataSource.LoadNavigationEntriesCallback mockCallbacks;

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        server = new MockWebServer();
    }

    @Test
    public void getNavigationEntries_callsNavigationUrlWithXApiKeyHeader() throws Exception {
        // Schedule some responses.
        server.enqueue(new MockResponse().setBody(""));

        // Start the server.
        server.start();

        OkHttpClientProvider clientProvider = new OkHttpClientProvider();
        NavigationRemoteDataSource remoteDataSource = new NavigationRemoteDataSource(
                NavigationEntriesDaoProvider.get(
                        clientProvider.get().build(),
                        server.url("").toString()));

        remoteDataSource.getNavigationEntries(mockCallbacks);

        // Optional: confirm that your app made the HTTP requests you were expecting.
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/navigation", request1.getPath().toString());
        assertNotNull(request1.getHeader("x-api-key"));

    }

    @Test
    public void getNavigationEntries_whenServerResponseWith200() throws Exception {
        // Schedule some responses.
        server.enqueue(new MockResponse().setResponseCode(200).setBody());

        // Start the server.
        server.start();

        OkHttpClientProvider clientProvider = new OkHttpClientProvider();
        NavigationRemoteDataSource remoteDataSource = new NavigationRemoteDataSource(
                NavigationEntriesDaoProvider.get(
                        clientProvider.get().build(),
                        server.url("").toString()));

        remoteDataSource.getNavigationEntries(mockCallbacks);

        // Optional: confirm that your app made the HTTP requests you were expecting.
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/navigation", request1.getPath().toString());
        assertNotNull(request1.getHeader("x-api-key"));

        verify(mockCallbacks,times(1)).onNavigationEntriesLoaded(anyList());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }


    private String positiveBody() {
        return new String(Files.readAllBytes("./"))
        FileInputStream inputStream = new FileInputStream("");
        return '{
            '"navigationEntries": [{'
            "type": "section",
                    "label": "Sortiment",
                    "children": [{
                "type": "node",
                        "label": "Alter",
                        "children": [{
                    "type": "node",
                            "label": "Baby & Kleinkind",
                            "children": [{
                        "type": "link",
                                "label": "0-6 Monate",
                                "url": "http:\/\/www.mytoys.de\/0-6-months\/"
                    }, {
                        "type": "link",
                                "label": "7-12 Monate",
                                "url": "http:\/\/www.mytoys.de\/7-12-months\/"
                    }]
                }]
            }]
        }]
        }'
    }
}
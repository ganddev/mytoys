package de.ahlfeld.mytoys.data.source.remote;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        server.start();
    }

    @Test
    public void getNavigationEntries_callsNavigationUrlWithXApiKeyHeader() throws Exception {
        server.enqueue(new MockResponse().setBody(positiveBody()));

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
        server.enqueue(new MockResponse().setBody(positiveBody()));
        OkHttpClientProvider clientProvider = new OkHttpClientProvider();
        NavigationRemoteDataSource remoteDataSource = new NavigationRemoteDataSource(
                NavigationEntriesDaoProvider.get(
                        clientProvider.get().build(),
                        server.url("").toString()));

        remoteDataSource.getNavigationEntries(mockCallbacks);

        verify(mockCallbacks,times(1)).onNavigationEntriesLoaded(anyList());
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void getNavigationEntries_whenServerResponseWith400() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(400).setBody(""));
        
        OkHttpClientProvider clientProvider = new OkHttpClientProvider();
        NavigationRemoteDataSource remoteDataSource = new NavigationRemoteDataSource(
                NavigationEntriesDaoProvider.get(
                        clientProvider.get().build(),
                        server.url("").toString()));

        remoteDataSource.getNavigationEntries(mockCallbacks);

        verify(mockCallbacks,times(1)).onDataNotAvailable();
    }

    private String positiveBody() throws IOException {
        InputStream inputStream = new FileInputStream(new File("app/src/test/data/navigation_entries.json"));
        BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        return sb.toString();
    }
}
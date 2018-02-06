package de.ahlfeld.mytoys.data.source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by bjornahlfeld on 06.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigationEntriesRepositoryTest {

    @Mock
    NavigationEntriesDataSource mockedDataSource;

    @Mock
    NavigationEntriesDataSource.LoadNavigationEntriesCallback mockedCallback;

    private NavigationEntriesRepository sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new NavigationEntriesRepository(mockedDataSource);
    }

    @Test
    public void getNavigationEntries_callsDatasource() throws Exception {
        sut.getNavigationEntries(mockedCallback);
        verify(mockedDataSource, times(1))
                .getNavigationEntries(any(NavigationEntriesDataSource.LoadNavigationEntriesCallback.class));
    }

}
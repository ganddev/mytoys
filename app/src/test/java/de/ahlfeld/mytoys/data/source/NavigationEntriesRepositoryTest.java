package de.ahlfeld.mytoys.data.source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by bjornahlfeld on 06.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigationEntriesRepositoryTest {
    @Mock
    private NavigationEntriesDataSource mMockedDataSource;
    @Mock
    private NavigationEntriesDataSource.LoadNavigationEntriesCallback mMockedCallback;
    private NavigationEntriesRepository sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new NavigationEntriesRepository(mMockedDataSource);
    }

    @Test
    public void getNavigationEntries_callsDatasource() throws Exception {
        sut.getNavigationEntries(mMockedCallback);
        verify(mMockedDataSource, times(1))
                .getNavigationEntries(any(NavigationEntriesDataSource.LoadNavigationEntriesCallback.class));
    }
}
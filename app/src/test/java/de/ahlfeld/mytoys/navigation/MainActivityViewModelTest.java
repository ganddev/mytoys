package de.ahlfeld.mytoys.navigation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import de.ahlfeld.mytoys.data.NavigationEntry;
import de.ahlfeld.mytoys.data.source.NavigationEntriesDataSource;
import de.ahlfeld.mytoys.data.source.NavigationEntriesRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bjornahlfeld on 06.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {
    @Mock
    private NavigationEntriesRepository mMockedRepository;
    private MainActivityViewModel sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = spy(new MainActivityViewModel(mMockedRepository));
        doNothing().when(sut).setNavigationEntries(anyList());
    }

    @Test
    public void inittialization_callsRespository() {
        verify(mMockedRepository, times(1))
                .getNavigationEntries(
                        any(NavigationEntriesDataSource.LoadNavigationEntriesCallback.class));
    }

    @Test
    public void naivagtionUp_resetDataAndCallsRepository() throws Exception {
        NavigationEntry mockedNavigationEntry = mock(NavigationEntry.class);
        sut.navigationDown(mockedNavigationEntry);

        sut.naivagtionUp();

        assertNull(sut.getParentNavigationEntry());
        verify(mMockedRepository,times(2))
                .getNavigationEntries(any(NavigationEntriesDataSource.LoadNavigationEntriesCallback.class));
    }

    @Test
    public void naivagtionUp_setChildrenFromParent() throws Exception {
        List<NavigationEntry> children = Collections.emptyList();
        NavigationEntry mockedNavigationEntry = mock(NavigationEntry.class);
        NavigationEntry mockedNavigationEntryTwo = mock(NavigationEntry.class);
        when(mockedNavigationEntry.getChildren()).thenReturn(children);

        sut.navigationDown(mockedNavigationEntry);
        sut.navigationDown(mockedNavigationEntryTwo);

        sut.naivagtionUp();

        assertNotNull(sut.getParentNavigationEntry());
        verify(sut,times(3)).setNavigationEntries(children);
    }

    @Test
    public void navigationDown_setsParentNavigationEntry() throws Exception {
        NavigationEntry mockedNavigationEntry = mock(NavigationEntry.class);
        sut.navigationDown(mockedNavigationEntry);

        assertNotNull(sut.getParentNavigationEntry());
    }

    @Test
    public void reset_setsParentToNullAndCallsRepository() throws Exception {
        sut.reset();

        assertNull(sut.getParentNavigationEntry());
        verify(mMockedRepository, times(2))
                .getNavigationEntries(
                        any(NavigationEntriesDataSource.LoadNavigationEntriesCallback.class));
    }

    @Test
    public void onNavigationEntriesLoaded_callsSetNavigationEntries() throws Exception {
        sut.onNavigationEntriesLoaded(Collections.<NavigationEntry>emptyList());
        verify(sut,times(1)).setNavigationEntries(anyList());
    }

    @Test
    public void onDataNotAvailable() throws Exception {
        sut.onDataNotAvailable();
        verify(sut,times(1))
                .setNavigationEntries(Collections.<NavigationEntry>emptyList());
    }
}
package de.ahlfeld.mytoys.navigation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import de.ahlfeld.mytoys.data.NavigationEntry;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bjornahlfeld on 02.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigationEntryViewModelTest {

    @Mock
    private NavigationEntry mockedNavigationEntry;

    @Mock
    private NavigationEntryItemNavigator mockedNavigationEntryItemNavigator;

    private NavigationEntryViewModel sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new NavigationEntryViewModel(mockedNavigationEntry);
        sut.setItemNavigator(mockedNavigationEntryItemNavigator);
    }

    @Test
    public void getLabel() throws Exception {
        String label = "My test label";
        when(mockedNavigationEntry.getLabel()).thenReturn(label);

        assertEquals(label, sut.getLabel());
    }

    @Test
    public void getChildren() throws Exception {
        List<NavigationEntry> children = new ArrayList<>();
        children.add(new NavigationEntry());
        children.add(new NavigationEntry());
        when(mockedNavigationEntry.getChildren()).thenReturn(children);

        assertEquals(children, sut.getChildren());
    }

    @Test
    public void setItemNavigator() throws Exception {

    }

    @Test
    public void onNavigationEntryClick() throws Exception {
        //depends on the type!!!
        //Type is section
        when(mockedNavigationEntry.getType()).thenReturn("section");
        sut.onNavigationEntryClick();
        verify(mockedNavigationEntryItemNavigator, times(1))
                .onNavigationEntryClick(mockedNavigationEntry);

        //Type is node
        when(mockedNavigationEntry.getType()).thenReturn("node");
        sut.onNavigationEntryClick();
        verify(mockedNavigationEntryItemNavigator, times(1))
                .onNavigationEntryClick(mockedNavigationEntry);

        //Type is link
        when(mockedNavigationEntry.getType()).thenReturn("link");
        String url = "http://www.mytoys.de";
        when(mockedNavigationEntry.getUrl()).thenReturn(url);
        sut.onNavigationEntryClick();
        verify(mockedNavigationEntryItemNavigator, times(1))
                .onLinkClicked(url);
    }

    @Test
    public void setNavigationEntry() throws Exception {
        NavigationEntry mockedEntry = mock(NavigationEntry.class);

        sut.setNavigationEntry(mockedEntry);

        assertEquals(mockedEntry, sut.mNavigationEntry.get());
    }
}
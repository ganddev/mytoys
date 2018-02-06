package de.ahlfeld.mytoys.navigation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import de.ahlfeld.mytoys.data.NavigationEntry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bjornahlfeld on 06.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigationEntryViewModelTest {

    @Mock
    private NavigationEntry mockedNavigationEntry;
    @Mock
    NavigationEntryItemNavigator mockedNavigationEntryItemNavigator;

    private NavigationEntryViewModel sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLabel_returnsTheLabelFromTheNavigationEntry() throws Exception {
        String MY_LABEL = "My Label";
        when(mockedNavigationEntry.getLabel()).thenReturn(MY_LABEL);
        sut = new NavigationEntryViewModel(mockedNavigationEntry);

        assertEquals(MY_LABEL, sut.getLabel().get());
    }

    @Test
    public void isSection_whenNavigationEntryTypeReturnsSection() throws Exception {
        when(mockedNavigationEntry.getType()).thenReturn("section");
        sut = new NavigationEntryViewModel(mockedNavigationEntry);

        assertTrue(sut.isSection().get());
    }

    @Test
    public void isSection_whenNavigationEntryTypeReturnsNode() throws Exception {
        when(mockedNavigationEntry.getType()).thenReturn("node");
        sut = new NavigationEntryViewModel(mockedNavigationEntry);

        assertFalse(sut.isSection().get());
    }

    @Test
    public void hasChildren_whenNavigationEntryHasNoChildren() throws Exception {
        when(mockedNavigationEntry.getChildren()).thenReturn(Collections.<NavigationEntry>emptyList());
        sut = new NavigationEntryViewModel(mockedNavigationEntry);

        assertFalse(sut.hasChildren().get());
    }

    @Test
    public void setItemNavigator() throws Exception {
    }

    @Test
    public void onNavigationEntryClick_whenNavigationEntryIsNodeCallsNavigationEntryClicked() throws Exception {
        when(mockedNavigationEntry.getType()).thenReturn("node");
        sut = new NavigationEntryViewModel(mockedNavigationEntry);
        sut.setItemNavigator(mockedNavigationEntryItemNavigator);

        sut.onNavigationEntryClick();

        verify(mockedNavigationEntryItemNavigator,
                times(1))
                .onNavigationEntryClick(mockedNavigationEntry);
    }

    @Test
    public void onNavigationEntryClick_whenNavigationEntryIsSectionCallsNothingNavigationEntryClicked() throws Exception {
        when(mockedNavigationEntry.getType()).thenReturn("section");
        sut = new NavigationEntryViewModel(mockedNavigationEntry);
        sut.setItemNavigator(mockedNavigationEntryItemNavigator);

        sut.onNavigationEntryClick();

        verify(mockedNavigationEntryItemNavigator,
                times(0))
                .onNavigationEntryClick(mockedNavigationEntry);
        verify(mockedNavigationEntryItemNavigator,
                times(0))
                .onLinkClicked(anyString());
        verify(mockedNavigationEntryItemNavigator,
                times(0))
                .onCloseDrawerClicked();
        verify(mockedNavigationEntryItemNavigator,
                times(0))
                .onUpClicked();
    }

    @Test
    public void onNavigationEntryClick_whenNavigationEntryIsLinkCallsOnLickClicked() throws Exception {
        String TEST_URL = "test_url";
        when(mockedNavigationEntry.getType()).thenReturn("link");
        when(mockedNavigationEntry.getUrl()).thenReturn(TEST_URL);
        sut = new NavigationEntryViewModel(mockedNavigationEntry);
        sut.setItemNavigator(mockedNavigationEntryItemNavigator);

        sut.onNavigationEntryClick();

        verify(mockedNavigationEntryItemNavigator,
                times(1))
                .onLinkClicked(TEST_URL);
    }


    @Test
    public void setNavigationEntry() throws Exception {
    }
}
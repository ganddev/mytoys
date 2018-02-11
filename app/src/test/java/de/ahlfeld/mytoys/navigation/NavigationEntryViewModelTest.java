package de.ahlfeld.mytoys.navigation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import de.ahlfeld.mytoys.data.NavigationEntry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bjornahlfeld on 06.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigationEntryViewModelTest {
    @Mock
    private NavigationEntry mMockedNavigationEntry;
    @Mock
    private NavigationEntryItemNavigator mockedNavigationEntryItemNavigator;
    private NavigationEntryViewModel sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLabel_returnsTheLabelFromTheNavigationEntry() throws Exception {
        String MY_LABEL = "My Label";
        when(mMockedNavigationEntry.getLabel()).thenReturn(MY_LABEL);
        sut = new NavigationEntryViewModel(mMockedNavigationEntry);

        assertEquals(MY_LABEL, sut.getLabel().get());
    }

    @Test
    public void isSection_whenNavigationEntryTypeReturnsSection() throws Exception {
        when(mMockedNavigationEntry.getType()).thenReturn("section");
        sut = new NavigationEntryViewModel(mMockedNavigationEntry);

        assertTrue(sut.isSection().get());
    }

    @Test
    public void isSection_whenNavigationEntryTypeReturnsNode() throws Exception {
        when(mMockedNavigationEntry.getType()).thenReturn("node");
        sut = new NavigationEntryViewModel(mMockedNavigationEntry);

        assertFalse(sut.isSection().get());
    }

    @Test
    public void hasChildren_whenNavigationEntryHasNoChildren() throws Exception {
        when(mMockedNavigationEntry.getChildren()).thenReturn(Collections.<NavigationEntry>emptyList());
        sut = new NavigationEntryViewModel(mMockedNavigationEntry);

        assertFalse(sut.hasChildren().get());
    }

    @Test
    public void setItemNavigator() throws Exception {
    }

    @Test
    public void onNavigationEntryClick_whenNavigationEntryIsNodeCallsNavigationEntryClicked() throws Exception {
        when(mMockedNavigationEntry.getType()).thenReturn("node");
        sut = new NavigationEntryViewModel(mMockedNavigationEntry);
        sut.setItemNavigator(mockedNavigationEntryItemNavigator);

        sut.onNavigationEntryClick();

        verify(mockedNavigationEntryItemNavigator,
                times(1))
                .onNavigationEntryClick(mMockedNavigationEntry);
    }

    @Test
    public void onNavigationEntryClick_whenNavigationEntryIsSectionCallsNothingNavigationEntryClicked() throws Exception {
        when(mMockedNavigationEntry.getType()).thenReturn("section");
        sut = new NavigationEntryViewModel(mMockedNavigationEntry);
        sut.setItemNavigator(mockedNavigationEntryItemNavigator);

        sut.onNavigationEntryClick();

        verify(mockedNavigationEntryItemNavigator,
                times(0))
                .onNavigationEntryClick(mMockedNavigationEntry);
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
        when(mMockedNavigationEntry.getType()).thenReturn("link");
        when(mMockedNavigationEntry.getUrl()).thenReturn(TEST_URL);
        sut = new NavigationEntryViewModel(mMockedNavigationEntry);
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
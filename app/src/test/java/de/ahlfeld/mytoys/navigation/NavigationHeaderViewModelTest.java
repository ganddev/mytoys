package de.ahlfeld.mytoys.navigation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import de.ahlfeld.mytoys.data.NavigationEntry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by bjornahlfeld on 06.02.18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigationHeaderViewModelTest {

    @Mock
    private NavigationEntry mockedNavigationEntry;

    @Mock
    private NavigationEntryItemNavigator mockedNavigationEntryItemNavigator;

    private NavigationHeaderViewModel sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLabel_returnsTheLabelOfNavigationEntry() throws Exception {
        String MY_LABEL = "My Label";
        when(mockedNavigationEntry.getLabel()).thenReturn(MY_LABEL);

        sut = new NavigationHeaderViewModel(mockedNavigationEntry);

        assertEquals(MY_LABEL, sut.getLabel().get());
    }

    @Test
    public void upIsProvided_returnsTrueWhenNavigationEntryIsNotNull() throws Exception {
        sut = new NavigationHeaderViewModel(mockedNavigationEntry);
        assertTrue(sut.upIsProvided().get());
    }

    @Test
    public void upIsProvided_returnsFalseWhenNavigationEntryIsNull() throws Exception {
        sut = new NavigationHeaderViewModel(null);
        assertFalse(sut.upIsProvided().get());
    }

    @Test
    public void onClose_callsOnClose() throws Exception {
        sut = new NavigationHeaderViewModel(mockedNavigationEntry);
        sut.setmItemNavigator(mockedNavigationEntryItemNavigator);

        sut.onClose();

        verify(mockedNavigationEntryItemNavigator,times(1)).onCloseDrawerClicked();
    }

    @Test
    public void onUp_callsOnUpClicked() throws Exception {
        sut = new NavigationHeaderViewModel(mockedNavigationEntry);
        sut.setmItemNavigator(mockedNavigationEntryItemNavigator);

        sut.onUp();

        verify(mockedNavigationEntryItemNavigator,times(1)).onUpClicked();
    }
}
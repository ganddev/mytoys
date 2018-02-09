package de.ahlfeld.mytoys.data.source;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import de.ahlfeld.mytoys.data.NavigationEntry;

/**
 * Created by bjornahlfeld on 01.02.18.
 */

public class NavigationEntriesRepository implements NavigationEntriesDataSource {

    private final NavigationEntriesDataSource mNavigationEntriesRemoteDataSource;

    private List<NavigationEntry> mCachedNavigationEntries;

    public NavigationEntriesRepository(@NonNull NavigationEntriesDataSource remoteDataSource) {
        mNavigationEntriesRemoteDataSource = remoteDataSource;
        mCachedNavigationEntries = getInitialNavigationEntries();
    }


    @Override
    public void getNavigationEntries(@NonNull LoadNavigationEntriesCallback callback) {
        if (getNavigationEntriesFromCache().isEmpty()) {
            getNavigationEntriesFromApi(callback);
        } else {
            callback.onNavigationEntriesLoaded(getNavigationEntriesFromCache());
        }
    }

    private List<NavigationEntry> getNavigationEntriesFromCache() {
        return mCachedNavigationEntries;
    }

    private void getNavigationEntriesFromApi(@NonNull LoadNavigationEntriesCallback callback) {
        mNavigationEntriesRemoteDataSource.getNavigationEntries(callback);
    }

    private List<NavigationEntry> getInitialNavigationEntries() {
        List<NavigationEntry> navigationEntries = new ArrayList<>();

        //subsubsection
        List<NavigationEntry> subsubSectionOneEntries = new ArrayList<>();
        NavigationEntry subsubsectionOne = new NavigationEntry();
        subsubsectionOne.setLabel("Baby & Kleinkind");
        subsubsectionOne.setType("node");

        NavigationEntry subsubsectionTwo = new NavigationEntry();
        subsubsectionTwo.setLabel("4-5 Jahre");
        subsubsectionTwo.setType("link");
        subsubsectionTwo.setUrl("http://www.mytoys.de/48-71-months/");

        subsubSectionOneEntries.add(subsubsectionOne);
        subsubSectionOneEntries.add(subsubsectionTwo);

        //subsection
        List<NavigationEntry> subSectionOneEntries = new ArrayList<>();
        NavigationEntry subsectionOne = new NavigationEntry();
        subsectionOne.setLabel("Alter");
        subsectionOne.setType("node");
        subsectionOne.setChildren(subsubSectionOneEntries);
        subSectionOneEntries.add(subsectionOne);

        NavigationEntry subsectionTwo = new NavigationEntry();
        subsectionTwo.setLabel("Kindergarten");
        subsectionTwo.setType("node");
        subSectionOneEntries.add(subsectionTwo);

        //Sections
        NavigationEntry sectionOne = new NavigationEntry();
        sectionOne.setLabel("Sortiment");
        sectionOne.setType("section");
        sectionOne.setChildren(subSectionOneEntries);

        NavigationEntry sectionTwo = new NavigationEntry();
        sectionTwo.setLabel("Schnäppchen");
        sectionTwo.setType("section");
        sectionTwo.setChildren(new ArrayList<NavigationEntry>());

        //links
        List<NavigationEntry> links = new ArrayList<>();
        NavigationEntry linkOne = new NavigationEntry();
        linkOne.setType("link");
        linkOne.setLabel("Hilfe");
        linkOne.setUrl("http://www.mytoys.de/c/faq.html");

        NavigationEntry linkTwo = new NavigationEntry();
        linkTwo.setType("link");
        linkTwo.setLabel("Mein Konto");
        linkTwo.setUrl("https://checkout.mytoys.de/checkout/serviceOverview");

        links.add(linkOne);
        links.add(linkTwo);

        NavigationEntry sectionThree = new NavigationEntry();
        sectionThree.setLabel("Service");
        sectionThree.setType("section");
        sectionThree.setChildren(links);

        navigationEntries.add(sectionOne);
        navigationEntries.add(sectionTwo);
        navigationEntries.add(sectionThree);

        return navigationEntries;
    }
}

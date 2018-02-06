package de.ahlfeld.mytoys.data.source.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import de.ahlfeld.mytoys.data.NavigationEntry;

/**
 * Created by bjornahlfeld on 06.02.18.
 */

public class Response {

    @SerializedName("navigationEntries")
    private List<NavigationEntry> navigationEntries;

    public List<NavigationEntry> getNavigationEntries() {
        return navigationEntries;
    }
}

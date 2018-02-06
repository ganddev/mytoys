package de.ahlfeld.mytoys.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjornahlfeld on 01.02.18.
 */

public class NavigationEntry {

    @SerializedName("type")
    private String type;

    @SerializedName("label")
    private String label;

    @SerializedName("children")
    private List<NavigationEntry> children;

    @SerializedName("url")
    private String url;

    public NavigationEntry() {
        children = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public List<NavigationEntry> getChildren() {
        return children;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setChildren(List<NavigationEntry> children) {
        this.children = children;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

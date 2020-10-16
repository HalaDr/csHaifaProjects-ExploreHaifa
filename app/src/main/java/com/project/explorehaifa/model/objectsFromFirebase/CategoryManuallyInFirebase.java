package com.project.explorehaifa.model.objectsFromFirebase;

import java.util.List;
import java.util.Map;

public class CategoryManuallyInFirebase {


    private String _name;
    private String description;
    private String _type;
    private String _defaultImageUri;
    private String isShowOnMap;



    private List<Map<String, String>> categoryItems;



    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_defaultImageUri() {
        return _defaultImageUri;
    }

    public void set_defaultImageUri(String _defaultImageUri) {
        this._defaultImageUri = _defaultImageUri;
    }

    public List<Map<String, String>> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(List<Map<String, String>> categoryItems) {
        this.categoryItems = categoryItems;
    }
    public String getIsShowOnMap() {
        return isShowOnMap;
    }

    public void setIsShowOnMap(String isShowOnMap) {
        this.isShowOnMap = isShowOnMap;
    }
}

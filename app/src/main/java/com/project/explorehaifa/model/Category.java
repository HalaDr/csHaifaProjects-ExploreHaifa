package com.project.explorehaifa.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String name;
    private String description;
    private String customizedNameTemplate;
    private String _type;
    //Table titles
    private List<String> fieldNamesForDisplay = new ArrayList<>();
    //Table rows
    private List<CategoryItem> items = new ArrayList<>();
    private String isShowOnMap;

    private String _defaultImageUri;






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCustomizedNameTemplate() {
        return customizedNameTemplate;
    }

    public void setCustomizedNameTemplate(String customizedNameTemplate) {
        this.customizedNameTemplate = customizedNameTemplate;
    }

    public List<CategoryItem> getItems() {
        return items;
    }

    public void setItems(List<CategoryItem> items) {
        this.items = items;
    }

    public List<String> getFieldNamesForDisplay() {
        return fieldNamesForDisplay;
    }

    public void setFieldNamesForDisplay(List<String> fieldNamesForDisplay) {
        this.fieldNamesForDisplay = fieldNamesForDisplay;
    }

    public String get_defaultImageUri() {
        return _defaultImageUri;
    }

    public void set_defaultImageUri(String _defaultImageUri) {
        this._defaultImageUri = _defaultImageUri;
    }

    public String getIsShowOnMap() {
        return isShowOnMap;
    }

    public void setIsShowOnMap(String isShowOnMap) {
        this.isShowOnMap = isShowOnMap;
    }

}

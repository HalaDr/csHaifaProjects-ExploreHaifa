package com.project.explorehaifa.model.objectsFromFirebase;

import java.util.ArrayList;
import java.util.List;

public class CategoryJsonInFirebase {


    private String urlForGettingJsonResponse;

    private String _name;
    private String description;
    private String _type;
    private String _defaultImageUri;
    private String isShowOnMap;

    // Keys names in Json path in order to get to the array (received from Firestore)
    private List<String> keysInJsonPathToArray = new ArrayList<>();

    // Key name of the array in Json (received from Firestore)
    private String keyOfJsonArray = "";

    // Keys name in Json for each item in the array of category items (received from Firestore)
    private List<String> keysInEachJsonArrayItem = new ArrayList<>();

    private List<String> customizedNameOptions = new ArrayList<>();


    public CategoryJsonInFirebase() {}

    public CategoryJsonInFirebase(String _name, String description, String _type, String _defaultImageUri,List<String> keysInJsonPathToArray,
                                  String keyOfJsonArray, List<String> keysInEachJsonArrayItem) {
        this._name = _name;
        this.description = description;
        this._type = _type;
        this._defaultImageUri = _defaultImageUri;
        this. keysInJsonPathToArray = keysInJsonPathToArray;
        this.keyOfJsonArray = keyOfJsonArray;
        this.keysInEachJsonArrayItem = keysInEachJsonArrayItem;

    }

    public List<String> getCustomizedNameOptions() {
        return customizedNameOptions;
    }

    public void setCustomizedNameOptions(List<String> customizedNameOptions) {
        this.customizedNameOptions = customizedNameOptions;
    }
    public String getKeyOfJsonArray() {
        return keyOfJsonArray;
    }

    public void setKeyOfJsonArray(String keyOfJsonArray) {
        this.keyOfJsonArray = keyOfJsonArray;
    }

    public List<String> getKeysInEachJsonArrayItemDisplay() {
        return keysInEachJsonArrayItemDisplay;
    }

    public void setKeysInEachJsonArrayItemDisplay(List<String> keysInEachJsonArrayItemDisplay) {
        this.keysInEachJsonArrayItemDisplay = keysInEachJsonArrayItemDisplay;
    }

    // The name to display for each above keysInEachJsonArrayItem
    private List<String> keysInEachJsonArrayItemDisplay = new ArrayList<>();

    public String getUrlForGettingJsonResponse() {
        return urlForGettingJsonResponse.trim();
    }

    public void setUrlForGettingJsonResponse(String urlForJsonResponse) {
        this.urlForGettingJsonResponse = urlForJsonResponse;
    }

    public String get_name() {
        return _name.trim();
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String getDescription() {
        return description.trim();
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

    public List<String> getKeysInJsonPathToArray() {
        for(int i = 0; i < keysInJsonPathToArray.size(); i++)
        {
            String name = keysInJsonPathToArray.get(i);
            if (!name.equals(name.trim()))
            {
                keysInJsonPathToArray.set(i,name.trim());
            }
        }
        return keysInJsonPathToArray;
    }

    public void setKeysInJsonPathToArray(List<String> keysInJsonPathToArray) {
        this.keysInJsonPathToArray = keysInJsonPathToArray;
    }

    public List<String> getKeysInEachJsonArrayItem() {
        for(int i = 0; i < keysInEachJsonArrayItem.size(); i++)
        {
            String name = keysInEachJsonArrayItem.get(i);
            if (!name.equals(name.trim()))
            {
                keysInEachJsonArrayItem.set(i,name.trim());
            }
        }
        return keysInEachJsonArrayItem;
    }

    public void setKeysInEachJsonArrayItem(List<String> keysInEachJsonArrayItem) {
        this.keysInEachJsonArrayItem = keysInEachJsonArrayItem;
    }
    public String getIsShowOnMap() {
        return isShowOnMap;
    }

    public void setIsShowOnMap(String isShowOnMap) {
        this.isShowOnMap = isShowOnMap;
    }
    @Override
    public String toString() {
        return "CategoryInFirebase{" + '\n' +
                "name='" + _name + '\'' + '\n' +
                ", description='" + description + '\'' + '\n' +
                ", keysInJsonPathToArray=" + keysInJsonPathToArray + '\n' +
                ", keyOfJsonArray='" + keyOfJsonArray + '\'' + '\n' +
                ", keysInEachJsonArrayItem=" + keysInEachJsonArrayItem + '\n' +
                ", keysInEachJsonArrayItemDisplay=" + keysInEachJsonArrayItemDisplay + '\n' +
                '}';
    }
}


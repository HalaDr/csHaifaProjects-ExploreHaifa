package com.project.explorehaifa.model.objectsFromFirebase;

import java.util.ArrayList;
import java.util.List;

public class CategoryManuallyItemInFirebase {

    private String _name;
    private String _defaultImageUri;
    private String imageInFirestorage;



    private List<String> fields = new ArrayList<>();


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_defaultImageUri() {
        return _defaultImageUri;
    }

    public void set_defaultImageUri(String _defaultImageUri) {
        this._defaultImageUri = _defaultImageUri;
    }

    public String getImageInFirestorage() {
        return imageInFirestorage;
    }

    public void setImageInFirestorage(String imageInFirestorage) {
        this.imageInFirestorage = imageInFirestorage;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}

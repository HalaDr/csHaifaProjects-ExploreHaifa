package com.project.explorehaifa.model;

import java.util.ArrayList;
import java.util.List;

public class JsonCategoryItemExtraFields {


    private String refCategoryItemName;
    private String refCategoryName;
    private String imageInFirestorage;
    private List<String> fields = new ArrayList<>();

    public String getRefCategoryItemName() {
        return refCategoryItemName;
    }

    public void setRefCategoryItemName(String refCategoryItemName) {
        this.refCategoryItemName = refCategoryItemName;
    }

    public String getRefCategoryName() {
        return refCategoryName;
    }

    public void setRefCategoryName(String refCategoryName) {
        this.refCategoryName = refCategoryName;
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

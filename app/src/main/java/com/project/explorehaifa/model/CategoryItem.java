package com.project.explorehaifa.model;

import java.util.ArrayList;
import java.util.List;

//CategoryItem may be for example: Hotel or Museum
public class CategoryItem {

    // fieldsNames stays empty if field names
    // exist in the Category class
    private List<String> fieldsNames = new ArrayList<>();
    // If fieldsNames is empty then fieldsValues contains
    // values for the list of fields which their field name
    // is defined in the Category class
    private List<String> fieldsValues = new ArrayList<>();
    private Category categoryParent = null;
    private String customizedName = null;
    private boolean isVisible = true;
    private String _name;
    private String photoUri;
    private List<String> extraFields = new ArrayList<>();


// Getters and Setters

    public List<String> getFieldsNames() {
        return fieldsNames;
    }

    public void setFieldsNames(List<String> fieldsNames) {
        this.fieldsNames = fieldsNames;
    }

    public List<String> getFieldsValues() {
        return fieldsValues;
    }

    public void setFieldsValues(List<String> fieldsValues) {
        this.fieldsValues = fieldsValues;
    }

    public Category getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(Category categoryParent) {
        this.categoryParent = categoryParent;
    }

    public String getCustomizedName() {
        return customizedName;
    }

    public void setCustomizedName(String customizedName) {
        this.customizedName = customizedName;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public List<String> getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(List<String> extraFields) {
        this.extraFields = extraFields;
    }
//    @Override
//    public String toString() {
//        String result = "";
//        for (int i = 0; i < fieldsValues.size(); i++ ) {
//            result += categoryParent.getFieldNamesForDisplay().get(i) +  ": " +
//                    this.getFieldsValues().get(i);
//            if(i < fieldsValues.size()-1)
//            {
//                result += ", ";
//            }
//        }
//        return result;
//    }
}

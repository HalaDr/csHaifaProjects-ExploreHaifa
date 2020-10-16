package com.project.explorehaifa.model.objectsFromFirebase;

public class FeedbackInFirebase {

    private String writerName;
    private String message;
    private String date;
    private String rating;
    private String refCategoryName;
    private String refCategoryItemCustomizedName;
    private String photoUri;

    public FeedbackInFirebase() {};

    public FeedbackInFirebase(String writerName, String message, String date, String rating, String refCategoryName, String refCategoryItemCustomizedName,String photoUri) {
        this.writerName = writerName;
        this.message = message;
        this.date = date;
        this.rating = rating;
        this.refCategoryName = refCategoryName;
        this.refCategoryItemCustomizedName = refCategoryItemCustomizedName;
        this.photoUri = photoUri;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRefCategoryName() {
        return refCategoryName;
    }

    public void setRefCategoryName(String refCategoryName) {
        this.refCategoryName = refCategoryName;
    }

    public String getRefCategoryItemCustomizedName() {
        return refCategoryItemCustomizedName;
    }

    public void setRefCategoryItemName(String refCategoryItemCustomizedName) {
        this.refCategoryItemCustomizedName = refCategoryItemCustomizedName;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }




}

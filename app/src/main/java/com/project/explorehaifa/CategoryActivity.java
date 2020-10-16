package com.project.explorehaifa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.explorehaifa.model.Category;
import com.project.explorehaifa.model.CategoryItem;
import com.project.explorehaifa.model.JsonCategoryItemExtraFields;
import com.project.explorehaifa.model.objectsFromFirebase.CategoryInFirebase;
import com.project.explorehaifa.model.objectsFromFirebase.CategoryJsonInFirebase;
import com.project.explorehaifa.model.objectsFromFirebase.CategoryManuallyInFirebase;
import com.project.explorehaifa.model.objectsFromFirebase.CategoryManuallyItemInFirebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "ExpHaifa-Category";

    final CategoryActivity currentActivity = this;

    TextView tvCategoryName;
    EditText etSearchCategoryItem;
    String filterText = "";
    ProgressDialog progressBar;
    //String categoryId = null;
    RequestQueue queue;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private LinearLayoutManager sLayoutManager;
    ArrayList<CategoryItem> listCategoryItems = new ArrayList<>();
    Category currentCategory = null;
    String categoryItemName = null;
    int jsonArrayItemsIterator = 0;

    //String URL=  "https://data.gov.il/api/3/action/datastore_search?resource_id=29b0e043-706d-47d3-b1b4-b6d57c27c450&limit=5";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        String CATEGORY_TYPE = "cagetory Type";
        String categoryId = (String) intent.getExtras().getString(CATEGORY_TYPE,"");

        Log.d(TAG, "Category @Id@: " + "@" + categoryId + "@");
        tvCategoryName=findViewById(R.id.tv_category_item_name);
        recyclerView = findViewById(R.id.recycler_view_feedbacks);

        etSearchCategoryItem = findViewById(R.id.et_search_category_item);
        etSearchCategoryItem.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                CategoryActivity.this.filterText = s.toString();
                Log.d(TAG , "search category item: " + CategoryActivity.this.filterText);

                Category category = CategoryActivity.this.currentCategory;
                markCategoryItemsForDisplay(category, s.toString());
                displayCategoryItemsAsync(category);

            }
        });

        recyclerView.setHasFixedSize(true);
        sLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(sLayoutManager);


        Log.d(TAG, "loading items of a specific category (category may be hotels) from firebase");


        try {
            downloadAndDisplayCategoryItemsFromFirebase(categoryId);
        }
        catch ( Exception e)
        {
            Log.e(TAG, "Error: Failed loading category from Firebase");
        }


    }


    //Downloads JSON from Haifa city website according to information in categoryJsonInFirebase.
    //Json structure example:
    //   result  -->  records array:  [{name: x}, {name: y}]
    public void downloadAndDisplayJsonCategoryItemsFromJsonAsync(final CategoryJsonInFirebase categoryJsonInFirebase)
    {
        final Category category = new Category();
        category.setName(categoryJsonInFirebase.get_name());
        category.setDescription(categoryJsonInFirebase.getDescription());
        category.set_type(categoryJsonInFirebase.get_type());
        category.setIsShowOnMap(categoryJsonInFirebase.getIsShowOnMap());
        category.set_defaultImageUri(categoryJsonInFirebase.get_defaultImageUri());

        for (String fieldName : categoryJsonInFirebase.getKeysInEachJsonArrayItemDisplay())
        {
            category.getFieldNamesForDisplay().add(fieldName);
        }

        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET,
                categoryJsonInFirebase.getUrlForGettingJsonResponse(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String response_json = response.toString();

                Log.d(TAG,"Json from Volley: " + response.toString());

                JSONObject root_obj = null;
                JSONArray records = null;   //[ {name: x}, {name: y} ]
                JSONObject record = null;   //{name: x}
                JSONObject nodeInPath = null;

                try {
                    root_obj = new JSONObject(response_json);
                    // textView.setText(root_obj.getString("success"));
                    //    records = root_obj.getJSONObject("result").getJSONArray("records");
                    nodeInPath = root_obj;
                    // Progress in the json path
                    for(String nodeName : categoryJsonInFirebase.getKeysInJsonPathToArray())
                    {
                        nodeInPath = nodeInPath.getJSONObject(nodeName);
                    }
                    String arrayKeyName = categoryJsonInFirebase.getKeyOfJsonArray();
                    records = nodeInPath.getJSONArray(arrayKeyName);
                    String names = "";
                    int jsonArrayLength = records.length();
                    for( jsonArrayItemsIterator = 0;
                        jsonArrayItemsIterator < jsonArrayLength;
                        jsonArrayItemsIterator++)
                    {
                        record = records.getJSONObject(jsonArrayItemsIterator);
                        CategoryItem categoryItem = new CategoryItem();
                        categoryItem.setCategoryParent(category);
                        List<String> values = new ArrayList<>();
                        for(String fieldName :categoryJsonInFirebase.getKeysInEachJsonArrayItem())
                        {
                            String value = "";
                            try {
                                value = record.getString(fieldName);
                            }
                            catch (Exception e) {
                                value = "";
                            }
                            values.add(value);
                        }
                        categoryItem.setFieldsValues(values);
                        categoryItem.set_name(categoryItem.getFieldsValues().get(0));



                        //  ---- start --- moved -----


                        String customizedName = "";

                            List<String> customizedNameOptions = categoryJsonInFirebase.getCustomizedNameOptions();
                            int optionsElementsFromFirebaseIterator=0;
                            while ( optionsElementsFromFirebaseIterator < customizedNameOptions.size() )
                            {
                                customizedName = "";
                                int optionLength = Integer.valueOf(customizedNameOptions.get(optionsElementsFromFirebaseIterator));
                                optionsElementsFromFirebaseIterator++;
                                if (optionsElementsFromFirebaseIterator + optionLength > customizedNameOptions.size() )
                                {
                                    customizedName = "Error: Invalid customizedOptions in firebase";
                                    break;
                                }

                                boolean emptyJsonValueFound = false;
                                int elementsIteratorInSpecificOption=optionsElementsFromFirebaseIterator;
                                // Iterate a specific option
                                while (
                                        elementsIteratorInSpecificOption - optionsElementsFromFirebaseIterator <
                                                optionLength &&
                                                !emptyJsonValueFound )
                                {
                                    String optionElement = customizedNameOptions.get(elementsIteratorInSpecificOption);
                                    String keysInJsonArrayItem = "";
                                    String valueInJsonArray = "";
                                    if (optionElement.contains("{")) {
                                        int pos1 = optionElement.indexOf("{") + 1;
                                        int pos2 = optionElement.indexOf("}");
                                        keysInJsonArrayItem = optionElement.substring(pos1, pos2);
                                        for (int j = 0;
                                             j < categoryJsonInFirebase.getKeysInEachJsonArrayItem().size();
                                             j++) {
                                            String key = categoryJsonInFirebase.getKeysInEachJsonArrayItem().get(j);
                                            if (keysInJsonArrayItem.equals(key)) {
                                                valueInJsonArray = categoryItem.getFieldsValues().get(j);
                                                if (valueInJsonArray.trim().length() > 0) {
                                                    customizedName += valueInJsonArray.trim();
                                                    break;
                                                } else {
                                                    //Empty value found in json
                                                    //We don't want this empty value in our customized name
                                                    //so we move to the next option (loaded from firebase)
                                                    customizedName = "Error: Empty value in JSon";
                                                    //k=optionLength+1; //end traversing current option

                                                    emptyJsonValueFound = true;

                                                    //end traversing json according to current option from firebase
                                                    //and moving to next option.
                                                    break;
                                                }
                                            }
                                            else
                                            {
                                                //If no key found in the the json array which equals
                                                //to the key written in the firebase
                                                if (j == categoryJsonInFirebase.getKeysInEachJsonArrayItem().size()-1)
                                                {
                                                    String errorMsg = "Error: customizedNameOptions element in  Firebase doesn't match json. " +
                                                            "Element " + elementsIteratorInSpecificOption;
                                                    //    Toast.makeText(CategoryActivity.this,errorMsg,Toast.LENGTH_LONG).show();
                                                    tvCategoryName.setText(errorMsg);
                                                    return;
                                                }
                                            }
                                        }
                                    } else {
                                        customizedName += optionElement;
                                    }

                                    elementsIteratorInSpecificOption++;
                                }

                                //i++;

                                boolean optionInFirebaseWasFullyIterated =
                                        elementsIteratorInSpecificOption-optionsElementsFromFirebaseIterator ==
                                                optionLength;

                                if (optionInFirebaseWasFullyIterated && !emptyJsonValueFound) {
                                    categoryItem.setCustomizedName(customizedName);
                                    break;
                                }
                                else {
                                    optionsElementsFromFirebaseIterator += optionLength;
                                }

                                if (optionsElementsFromFirebaseIterator>=customizedNameOptions.size())
                                {
                                    String errorMsg =
                                            "Error: In category " + category.getName() + ". \n" +
                                                    "More than one empty values in received JSON array item. \n" +
                                                    "CustomizedNameOptions (stored in firebase) handles only one empty value.";
                                    Log.e(TAG, errorMsg);
                                    //tvCategoryName.setText(errorMsg);
                                    emptyJsonValueFound = true;

                                    //end traversing json according to current option from firebase
                                    //and moving to next option.
                                    break;
                                }

                            }

                        // --- end ---- moved   ---------


                        //  ------start----  Add customized picture from firebase to the json information  -------
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference JsonItemsCollectionRef =
                                db.collection("jsonItemExtraFields");
                        Log.d("catName","categoryName: " +category.getName() );
                        Log.d("catName","categoryName: " +category.getName() );

                        JsonItemsCollectionRef
                                .whereEqualTo("refCategoryName", category.getName())
                                .whereEqualTo("refCategoryItemName", categoryItem.get_name())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                JsonCategoryItemExtraFields jsonCategoryItemWithExtraFields = document.toObject(JsonCategoryItemExtraFields.class);
                                                String jsonItemImg = null;
                                                categoryItem.setPhotoUri(jsonItemImg);
                                                if(jsonCategoryItemWithExtraFields != null)
                                                {
                                                    categoryItem.setPhotoUri(jsonCategoryItemWithExtraFields.getImageInFirestorage());
                                                    jsonItemImg = categoryItem.getPhotoUri();}
                                                Log.d(TAG, "Image of json item: "+jsonItemImg);

                                                if(jsonCategoryItemWithExtraFields.getFields() != null)
                                                {
                                                    if(categoryItem.getExtraFields().size() % 2  == 0)
                                                    {
                                                        categoryItem.setExtraFields(jsonCategoryItemWithExtraFields.getFields());

                                                    }
                                                }
                                                if(categoryItem.getCustomizedName() != null)
                                                {
                                                    if (categoryItem.getCustomizedName().trim().length() > 0)
                                                    {
                                                        category.getItems().add(categoryItem);
                                                    }

                                                }
                                            }

                                        } else {
                                            String jsonItemImg = null;
                                            categoryItem.setPhotoUri(jsonItemImg);

                                            Log.d(TAG, "Error getting document: ", task.getException());
                                        }

                                        if(categoryItem.getCustomizedName() != null && categoryItem.getPhotoUri() == null)
                                        {
                                            if (categoryItem.getCustomizedName().trim().length() > 0)
                                            {
                                                category.getItems().add(categoryItem);
                                            }

                                        }
                                        if (jsonArrayItemsIterator == (jsonArrayLength ))
                                        {
                                            CategoryActivity.this.currentCategory = category;
                                            displayCategoryItemsAsync(category);
                                        }

                                    }
                                });
                        //  ------end----  Add customized picture from firebase to the json information  -------

                    }



                } catch (JSONException e) {
                    Toast.makeText(CategoryActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error: Failed to download Category from JSON. Details: " + error.toString());
            }
        });
        queue.add(request);
    }


    private void markCategoryItemsForDisplay(Category category, String filterText)
    {
        for (int i=category.getItems().size()-1; i >= 0; i--)
        {
            CategoryItem categoryItem =  category.getItems().get(i);

            for (int k=0; k < categoryItem.getFieldsValues().size(); k++) {
                String fieldValue = categoryItem.getFieldsValues().get(k);
                if (fieldValue.contains(filterText)) {
                    categoryItem.setVisible(true);
                    break;
                } else {
                    categoryItem.setVisible(false);
                }
            }
        }
    }

    // Runs in background
    private void displayCategoryItemsAsync(final Category category)
    {


//        for(int i = category.getItems().size() - 1; i > 2; i--)
//        {
//            category.getItems().remove(i);
//        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(category != null )
                {
                    tvCategoryName.setText(category.getName());

                    recyclerViewAdapter = new RecyclerViewAdapterCategories(category);
                    DividerItemDecoration dividerItemDecoration =
                            new DividerItemDecoration( recyclerView.getContext(),
                                    sLayoutManager.getOrientation());
                    dividerItemDecoration.setDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    //Download and display Category Items of type "Manually"
    private void downloadAndDisplayManuallyCategoryItemsFromFirebase (
            final String categoryId,
            CategoryManuallyInFirebase categoryManuallyInFirebase)
    {
        final Category category = new Category();
        category.setName( categoryManuallyInFirebase.get_name() );
        category.setDescription(categoryManuallyInFirebase.getDescription());
        category.setFieldNamesForDisplay(null);  // Fields names are not used in Category of type Manually
        category.set_type(categoryManuallyInFirebase.get_type());
        category.setIsShowOnMap(categoryManuallyInFirebase.getIsShowOnMap());
        category.set_defaultImageUri(categoryManuallyInFirebase.get_defaultImageUri());


        Log.d(TAG, "Category.name=" + category.getName());


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d(TAG, "Downloading all documents of a specific category of type MANUALLY from firebase.\n" +
                "   Category id in firebase: " + categoryId + "\n" +
                "   Name: " + categoryManuallyInFirebase.get_name() );


        db.collection("categories")
                .document(categoryId)
                .collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Firebase doc in the specific Manually Category:\n" +
                                        "ID: " + document.getId() + ":\n" +
                                        "Data: " + document.getData());

                                CategoryManuallyItemInFirebase categoryManuallyItemInFirebase = document.toObject(CategoryManuallyItemInFirebase.class);

                                CategoryItem categoryItem = new CategoryItem();
                                categoryItem.setCustomizedName(categoryManuallyItemInFirebase.get_name());
                                // categoryItem.setFieldsNames(categoryManuallyItemInFirebase.getFields());
                                categoryItem.set_name(categoryItem.getCustomizedName());
                                categoryItem.setPhotoUri(categoryManuallyItemInFirebase.getImageInFirestorage());

                                if (categoryManuallyItemInFirebase.getFields().size() % 2 != 0)
                                {
                                    Log.e(TAG,
                                            "Error: Array 'fields' in firebase must have an even size (each field has name and value)");
                                    return;
                                }

                                for (int i=0; i<categoryManuallyItemInFirebase.getFields().size(); i=i+2) {
                                    String fieldName = categoryManuallyItemInFirebase.getFields().get(i);
                                    String fieldValue = categoryManuallyItemInFirebase.getFields().get(i+1);
                                    categoryItem.getFieldsNames().add(fieldName);
                                    categoryItem.getFieldsValues().add(fieldValue);
                                }


                                category.getItems().add(categoryItem);


                                CategoryActivity.this.currentCategory = category;
                                displayCategoryItemsAsync(category);
                            }
                        } else {
                            Log.e(TAG, "Error: Failed to get from Firebase documents of a specific Manually Category.",
                                    task.getException());
                        }
                    }
                });


    }


    private void downloadAndDisplayCategoryItemsFromFirebase(final String categoryId) throws Exception
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d(TAG, "Downloading a specific CATEGORY from firebase. categoryId: " + categoryId);

        db.collection("categories")
                .document(categoryId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d( TAG, "categoryInFirebase str:\n" + documentSnapshot.getData());

                        try {
                            //At first we download from firebase only the common category object.
                            //Only then we will use its type to download the specific category category type.
                            final CategoryInFirebase categoryInFirebase = documentSnapshot.toObject(CategoryInFirebase.class);

                            Log.d(TAG, "Downloaded Category from firebase:\n" +
                                    "   name: " + categoryInFirebase.get_name() + "\n" +
                                    "   type: " + categoryInFirebase.get_type());

                            if (categoryInFirebase == null) {
                                //                    tvCategoryName.setText("אין נתונים");
                                Log.e(TAG, "Error: No data in firebase for this category");

                                return;
                            } else if (categoryInFirebase.get_type().equals("JSON API")) {
                                Log.d(TAG, "Loading items of category type JSON API");
                                final CategoryJsonInFirebase categoryJsonInFirebase =
                                        documentSnapshot.toObject(CategoryJsonInFirebase.class);
                                downloadAndDisplayJsonCategoryItemsFromJsonAsync(categoryJsonInFirebase);
                            } else if (categoryInFirebase.get_type().equals("Manually")) {
                                Log.d(TAG, "loading items of category type MANUALLY");
                                final CategoryManuallyInFirebase categoryManuallyInFirebase =
                                        documentSnapshot.toObject(CategoryManuallyInFirebase.class);

                                Log.d(TAG, "Loading items of Category of type Manually from firebase:\n" +
                                        "   name: " + categoryManuallyInFirebase.get_name());


                                downloadAndDisplayManuallyCategoryItemsFromFirebase(
                                        categoryId,
                                        categoryManuallyInFirebase);

                                // downloadManuallyCategoryItemsFromJson(categoryInFirebase);
                            } else {
                                Log.e(TAG, "Error: Invalid category type in firebase");
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e(TAG, "Error: failed to load category");
                        }

                    }
                });

    }


}




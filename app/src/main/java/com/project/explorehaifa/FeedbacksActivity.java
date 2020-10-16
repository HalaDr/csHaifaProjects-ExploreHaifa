package com.project.explorehaifa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.explorehaifa.model.Feedback;
import com.project.explorehaifa.model.objectsFromFirebase.FeedbackInFirebase;

import java.util.ArrayList;

public class FeedbacksActivity extends AppCompatActivity {

    private static final String TAG = "ExpHaifa-Feedbacks";
    private final ArrayList<Feedback> listFeedbacks =
            new ArrayList<>();
    final FeedbacksActivity currentActivity = this;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    TextView tvCategoryItemName;
    TextView tvNoFeedbacks;
    boolean recyclerViewWasDisplayed = false;
    String categoryName = null;
    String categoryItemName = null;
    String categoryItemCustomizedName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_feedbacks);

        tvCategoryItemName = findViewById(R.id.tv_category_item_name);
        tvNoFeedbacks = findViewById(R.id.tv_no_feedbacks);

        recyclerView = findViewById(R.id.recycler_view_feedbacks);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Log.d(TAG, "in feedbacks activity");


        Intent intent = getIntent();
        categoryName = (String) intent.getExtras().getString("category name","");
       // categoryItemName = (String) intent.getExtras().getString("category item name","");
        categoryItemCustomizedName = (String) intent.getExtras().getString("category item customized name","");

        FloatingActionButton fab = findViewById(R.id.fab_add_feedback);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(MainActivity.loggedInUser == null)
                {
                    Intent intent = new Intent(FeedbacksActivity.this, SignInActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Intent intent = new Intent(FeedbacksActivity.this, AddFeedbackActivity.class);
                    intent.putExtra("refCategoryItemCustomizedName", categoryItemCustomizedName);
                    intent.putExtra("refCategoryName", categoryName);
                    startActivity(intent);
                }

            }
        });

    }

    private void populateAndDisplayRecyclerViewFromFirebase()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference feedbacksCollectionRef =
                db.collection("feedbacks");

        listFeedbacks.clear();

        feedbacksCollectionRef
                .whereEqualTo("refCategoryName", categoryName)
                .whereEqualTo("refCategoryItemCustomizedName", categoryItemCustomizedName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                final FeedbackInFirebase feedbackInFirebase = document.toObject(FeedbackInFirebase.class);
                                Feedback feedback = new Feedback();
                                feedback.setRating(feedbackInFirebase.getRating());
                                feedback.setMessage(feedbackInFirebase.getMessage());
                                feedback.setDate(feedbackInFirebase.getDate());
                                feedback.setWriterName(feedbackInFirebase.getWriterName());
                                feedback.setPhotoUri(feedbackInFirebase.getPhotoUri());
                                listFeedbacks.add(feedback);
                            }
                            Log.d(TAG, "listFeedbacksInFirebase size = " + listFeedbacks.size());

                            //if (!recyclerViewWasDisplayed) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (listFeedbacks != null) {
                                        currentActivity.displayFeedbacks(categoryItemCustomizedName, listFeedbacks);
                                    }
                                    else
                                    {
                                        //  tvNoFeedbacks.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                            //}


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Snackbar.make(findViewById(R.id.feedbacks_layout), "resume", Snackbar.LENGTH_LONG)
//                .setAction("", null).show();
        // The activity has become visible
        // it is now "resumed"
//        if(recyclerViewWasDisplayed)
//        {
        populateAndDisplayRecyclerViewFromFirebase();
//            recyclerViewAdapter.notifyDataSetChanged();
//
//        }

    }




    public void  displayFeedbacks(String customizedCategoryItemName,ArrayList<Feedback> listFeedbacks)
    {

        tvCategoryItemName.setText(customizedCategoryItemName);

        if(listFeedbacks.size() == 0)
        {
            Log.d(TAG, "listFeedbacksInFirebase size check = " + listFeedbacks.size());
            tvNoFeedbacks.setVisibility(View.VISIBLE);
        }

        else {
            recyclerViewAdapter = new RecyclerViewAdapterFeedbacks(listFeedbacks);
            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(recyclerView.getContext(),
                            layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(recyclerViewAdapter);

            //recyclerViewAdapter.notifyDataSetChanged();
            // recyclerViewWasDisplayed = true;

        }
    }
}
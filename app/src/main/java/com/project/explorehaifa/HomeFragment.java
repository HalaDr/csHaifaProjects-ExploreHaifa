package com.project.explorehaifa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.explorehaifa.model.objectsFromFirebase.CategoryInFirebase;

import java.util.Random;

public class HomeFragment extends Fragment {
    HomePage parent;
    final String TAG = "ExpHaifa-HomeFragment";
    public HomeFragment() {
        super();
    }
    View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        parent = (HomePage) getActivity();
        Log.d(TAG, "loading Home page Fragment on create");
        myView = inflater.inflate(R.layout.activity_home_fragment, container, false);


        ScrollView homeFragmentSV = (ScrollView) myView.findViewById( R.id.sc_home_fragment );
        final LinearLayout linearlayout =  homeFragmentSV.findViewById( R.id.ll_home_fragment );
     //   linearlayout.removeAllViews();

        //Setting up LinearLayout Orientation

     //   LinearLayout.LayoutParams linearlayoutlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        // getActivity().setContentView(linearlayout, linearlayoutlayoutparams);

          final LinearLayout.LayoutParams LayoutParamsview = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final ImageView divider = new ImageView(myView.getContext());
        final LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.setMargins(50, 50, 50, 50);
        divider.setLayoutParams(lp);
        divider.setBackgroundColor(Color.WHITE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Log.d(TAG, "loading list of categories from firebase");
        db.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final DocumentSnapshot document : task.getResult()) {
                                final CategoryInFirebase categoryInFirebase =
                                        document.toObject(CategoryInFirebase.class);
                                Log.d(TAG, "Category: " + categoryInFirebase.get_name());

                                parent.runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        if (categoryInFirebase != null) {
                                            Log.d(TAG, "in run");
                                            Button btn = new Button(myView.getContext());
                                            Log.d(TAG, "in run2");
                                            btn.setText(categoryInFirebase.get_name());
                                            btn.setLayoutParams(lp);
                                            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f);
                                            btn.setTextColor(Color.parseColor("#FFFFFF"));

                                            Random color = new Random();
                                            btn.setBackgroundColor(Color.argb(255, color.nextInt(255), color.nextInt(255), color.nextInt(255)));

                                            linearlayout.addView(btn);
                                            final Button currentButton = btn;

                                            btn.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    Log.d(TAG, "Button clicked: " + currentButton.getText());
                                                    Log.d(TAG, "Button clicked categoryInFirebase: " + categoryInFirebase.get_name());

                                                    Log.d(TAG, "stat=rt activity with: " + document.getId());

                                                    // Perform action on click
                                                    Intent activityIntent = new Intent(getActivity(), CategoryActivity.class);
                                                    String CATEGORY_TYPE = "cagetory Type";
                                                    activityIntent.putExtra(CATEGORY_TYPE, document.getId());
                                                    startActivity(activityIntent);
                                                }
                                            });
                                            // linearlayout.addView(divider);

                                        }
                                    }
                                });

                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }

                });
return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "loading Home page Fragment on view created");
        //Creating LinearLayout.
        //   final LinearLayout linearlayout = new LinearLayout(getContext());


    }


}


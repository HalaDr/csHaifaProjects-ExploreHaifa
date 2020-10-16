package com.project.explorehaifa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    HomePage parent;
    final String TAG = "ExpHaifa-Profile";
    View myView;
    String userName = null;
    TextView profileFullName;
    Button btnLogout;
    public ProfileFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        parent = (HomePage) getActivity();
        Log.d(TAG, "loading profile fragment on create");
        return inflater.inflate(R.layout.activity_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "loading Home page Fragment on view created");
        profileFullName = (TextView) getView().findViewById(R.id.tv_profile_name);
        userName = MainActivity.loggedInUser.getFullName();
        Log.d(TAG, "logged in user name1:"+ userName);
        if(userName!= null)
            profileFullName.setText(userName);
        Log.d(TAG, "logged in user name2:"+ userName);
        btnLogout = getView().findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.loggedInUser = null;
                Intent Create = new Intent(getActivity(), MainActivity.class);

                startActivity(Create);
            }

        });

    }

}



package com.project.explorehaifa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.project.explorehaifa.model.User;

public class MainActivity extends AppCompatActivity {

    public static User loggedInUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_main);
           }

    public void useAppAsVisitor(View view) {
        Intent activityIntent = new Intent(MainActivity.this, HomePage.class);
        startActivity(activityIntent);

    }

    public void goToSignIn(View view) {
        Intent activityIntent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(activityIntent);

    }

    public void goToSignUp(View view) {
        Intent activityIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(activityIntent);

    }
}
package com.project.explorehaifa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.explorehaifa.model.User;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "ExpHaifa-SignIn";

    EditText email;
    EditText password;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        tvError = findViewById(R.id.tv_error);
    }

    public void login(View view)
    {
       final String interedEmail = email.getText().toString();
        String interedPass = password.getText().toString();

        Log.d(TAG, "intered info" + interedEmail + interedPass);

        // check if intered info is valid
        if(interedEmail != null && interedPass != null) {

            Log.d(TAG, "intered info in:" + interedEmail +" "+ interedPass);

            FirebaseAuth firebaseAuth;
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(interedEmail,interedPass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        CollectionReference usersCollectionRef =
                                db.collection("users");

                        usersCollectionRef
                                .whereEqualTo("email", interedEmail)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                MainActivity.loggedInUser = document.toObject(User.class);
                                                Log.d(TAG, MainActivity.loggedInUser.getEmail());
//                                                loggedInUser = new User();
//                                                loggedInUser.setFullName(returnedUser.getFullName());
//                                                loggedInUser.setEmail(returnedUser.getEmail());
                                             //   loggedInUser.setPassword(returnedUser.getPassword());
                                            }
                                            Log.d(TAG, "user are valid" + MainActivity.loggedInUser.getFullName());

                                            Intent activityIntent = new Intent(SignInActivity.this, HomePage.class);
                                            startActivity(activityIntent);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG)
                                .show();
                        tvError.setText("הכניסה נכשלה! ודא שאתה רשום ושהנתונים שהזנת תואמים.");

                    }
                }
            });
        }
        else
        {
            tvError.setText("הכנס אימייל וסיסמה");

        }
    }


}
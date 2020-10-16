package com.project.explorehaifa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.explorehaifa.model.User;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "ExpHaifa-SignUp";
    Button btnSignUp;
    EditText fullName;
    EditText email;
    EditText password1;
    EditText password2;
    TextView tvregisteredUserError;
    User user=null;
    int innerFlag = 1;

    String mess1="Field cannot be empty!";
    String mess2 = "Please enter a valid email for example haladr.ac97@gmail.com";
    String mess3 = "Password cannot be longer than 20 characters";
    String mess4 = "Please write your password again";
    String mess5="Passwords must match!";
    int f1 = 0, f2 = 0, f3 = 0;   // flags
    FirebaseAuth firebaseAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_sign_up);
        btnSignUp = findViewById(R.id.btn_sign_up);
        fullName = findViewById(R.id.et_full_name);
        email = findViewById(R.id.et_email);
        password1 = findViewById(R.id.et_password1);
        password2 = findViewById(R.id.et_password2);
        tvregisteredUserError = findViewById(R.id.tv_error);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void signUpSave (View view)
    {
        final String writtenFullName = fullName.getText().toString();
        final String mail = email.getText().toString();
        final String pass1 = password1.getText().toString();
        String pass2 = password2.getText().toString();

        // Email Validation
        f1 = verifyEmail(mail);
        // password Validation
        f2 = verifyPassword(pass1);
        f3 = verifyRePassword(pass1,pass2);
        if((f1 != 0) && (f2 != 0) && (f3 != 0)) {
            // all info are legal
            // save new user to authentication in firebase
            firebaseAuth.createUserWithEmailAndPassword(mail, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "נרשמת בהצלחה", Toast.LENGTH_LONG)
                                .show();

                        // save new user to users collection in cloud firestore
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, String> data = new HashMap<>();
                        data.put("fullName",writtenFullName);
                        data.put("email",mail);
                       // data.put("password",pass1);
                        Log.d(TAG, "you clicked signUp ");

                        db.collection("users")
                                .add(data)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "new user added to firebase with ID: " + documentReference.getId());
                                                      }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding new user to firestore", e);
                                    }
                                });

                    } else {
                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG)
                               .show();
                        innerFlag = 0;

                    }
                    if(innerFlag != 0)
                    {
                        Intent activityIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(activityIntent);
                    }
                }
            });

        }

        }


    public void clickHereToLogIn(View view)
    {
        Intent activityIntent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(activityIntent);

    }
    private int verifyEmail( String str)
    {


    //        email.setText("חשבון עם אימייל זה כבר קיים, נסה עם אימייל שונה או כנס");

        if (str.length() == 0)
        {
            email.setText(mess1);
        }
        else if (!str.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
        {
            email.setText(mess2);
        }
        else
        {
            //all good
            return 1;
        }
        return 0;
    }

    private int verifyPassword(String str1)
    {
        if (str1.length() == 0) {
            password1.setText(mess1);

            return 0;

        } else if (str1.length() > 20) {
            password1.setText(mess3);
            return 0;
        }
        return 1;

    }

    private int verifyRePassword(String str2, String str1)
    {

        if (str2.length() == 0)
        {
            password2.setText(mess4);
        }
        else if (str1.equals(str2))
        {
            return 1;

        }
        else
        {
            password2.setText(mess5);
        }
        return 0;
    }

}





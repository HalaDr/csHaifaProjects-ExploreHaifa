package com.project.explorehaifa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AddFeedbackActivity extends AppCompatActivity {
    final String TAG = "ExpHaifa-AddFeedback";
    private final int PICK_IMAGE_REQUEST = 71;
    //Firebase
    //FirebaseStorage storage;
    //StorageReference storageReference;
    FirebaseImageWrapper wrapper;
    //profile picture part
    private Button chooseButton;
    private ImageView feedbackImageView;
    private Uri filePath;
    public static String feedbackImagePathUriString = null;
    public String feedbackPicture = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);
        Snackbar.make(findViewById(R.id.add_fedback_layout), "Here's a Snackbar", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
     //   Toast.makeText(this,"in add feedback activity", Toast.LENGTH_LONG);
        wrapper = new FirebaseImageWrapper();
        feedbackImageView = findViewById(R.id.img_feedback);
        Log.i(TAG, "in add feedback activity");

        Button btn = findViewById(R.id.btn_save_feedback);
        final Activity currentActivity = this;
   //     final EditText etWriterName = findViewById(R.id.et_writer_name);
        final EditText etMsg = findViewById(R.id.et_feedback_msg);
        final RatingBar rtRatingBar = findViewById(R.id.ratingbar_add_feedback);
        Intent intent = getIntent();
        final String categoryName = (String) intent.getExtras().getString("refCategoryName","");
        final String categoryItemCustomizedName = (String) intent.getExtras().getString("refCategoryItemCustomizedName","");

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "you clicked save feedback");


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, String> data = new HashMap<>();
               data.put("writerName", MainActivity.loggedInUser.getFullName());
                data.put("message", etMsg.getText().toString());
                data.put("rating",String.valueOf(rtRatingBar.getRating()));
                data.put("refCategoryItemCustomizedName",categoryItemCustomizedName);
               data.put("refCategoryName",categoryName);

               String currentDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                       .format(Calendar.getInstance().getTime());
                data.put("date", currentDate);

                    data.put("photoUri", feedbackImagePathUriString);

               Log.d(TAG, "you clicked save info");


                db.collection("feedbacks")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "feedback added to firebase with ID: " + documentReference.getId());
                               currentActivity.finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding feedback to firebase", e);
                               currentActivity.finish();
                            }
                        });
                // Perform action on click

          }


        });
    }
   public void onClickAddPhoto(View view )
   {
       //TBD: Load picture from gallery
       Intent intent = new Intent();
       intent.setType("image/*");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                feedbackImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (filePath != null)
            uploadImage(); ///goes to upload image. Next activity is started from there.
        Log.d(TAG, "feedback string after uploading is " + feedbackPicture);
    }
    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            wrapper.uploadImg(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         //   Uri downloadUrl = taskSnapshot.getResult().getStorage().getDownloadUrl();
                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uri.isComplete());
                            Uri downloadUrl= uri.getResult();
                            Log.d(TAG, "the uri is " + downloadUrl.toString());
                            feedbackImagePathUriString = downloadUrl.toString();
                            Log.d(TAG, "the uri string is " + feedbackImagePathUriString);
                            progressDialog.dismiss();

                            Log.d(TAG,"Uploaded");

                            feedbackPicture = feedbackImagePathUriString;
                            Log.d(TAG, "feedback string is " + feedbackPicture);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                           // Toast.makeText(RegistrationPage2.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });

        }



    }//end of upload method
}
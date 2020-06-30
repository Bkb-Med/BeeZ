package com.example.apiarymange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddAgent extends AppCompatActivity {
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private StorageTask mUploadTask;
    ProgressBar  mProgress;
    Button addagentbtn,cancelbtn;
    EditText fname,adresse,email,phone;
        ImageView addImgAgent;
        CircleImageView imgAgent;
        private static final int PICK_IMAGE_REQUEST = 1;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_addagent);
            fname =  findViewById(R.id.agentFname);
            adresse =  findViewById(R.id.agentAdresse);
            email =  findViewById(R.id.agentEmail);
            phone =  findViewById(R.id.phoneagent);
            addImgAgent =  findViewById(R.id.addimage);
            imgAgent =  findViewById(R.id.img_agent);
            addagentbtn=  findViewById(R.id.btn_Addagent);
            cancelbtn=  findViewById(R.id.btn_cancelagent);
            mProgress  =  findViewById(R.id.prgsaddagent);
            mStorageRef = FirebaseStorage.getInstance().getReference();
            mDatabaseRef = FirebaseDatabase.getInstance().getReference(fAuth.getCurrentUser().getUid()+"/"+"agents");
            addImgAgent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFileChooser();
                }
            });
            addagentbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mProgress.setVisibility(View.VISIBLE);
                    uploadFile();
                }
            });
            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AddAgent.this, ListAgent.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into( imgAgent);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null && !TextUtils.isEmpty(fname.getText().toString())) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgress.setVisibility(View.VISIBLE);
                                    mProgress.setProgress(0);
                                }
                            }, 500);


                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String generatedFilePath = uri.toString();
                                    String id = mDatabaseRef.push().getKey();
                                    Map<String, Object> childUpdates = new HashMap<>();
                                    childUpdates.put(id + "/imageUrl", generatedFilePath);
                                    childUpdates.put(id + "/adresse", adresse.getText().toString());
                                    childUpdates.put(id + "/email", email.getText().toString());
                                    childUpdates.put(id + "/fname", fname.getText().toString());
                                    childUpdates.put(id + "/phone", phone.getText().toString());
                                    mDatabaseRef.updateChildren(childUpdates);
                                    Toast.makeText(AddAgent.this, "agent created Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddAgent.this, ListAgent.class);
                                    startActivity(intent);
                                    finish();
                                    //Do what you need to do with url
                                }
                            });

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgress.setProgress((int) progress);
                        }
                    });

        } else {
            Toast.makeText(this, "Empty Agent name or empty Agent photo !", Toast.LENGTH_SHORT).show();
        }
    }

}

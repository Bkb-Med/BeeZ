package com.example.apiarymange;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAgent extends AppCompatActivity {
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private StorageTask mUploadTask;
    ProgressBar mProgress;
    Button addagentbtn,cancelbtn;
    EditText fname,adresse,email,phone;
    ImageView addImgAgent;
    CircleImageView imgAgent;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editagent);

        Intent intent = getIntent();
        final String agentId = intent.getStringExtra("agentId");
        setAgent(agentId);
        fname =  findViewById(R.id.EDagentFname);
        adresse =  findViewById(R.id.EDagentAdresse);
        email =  findViewById(R.id.EDagentEmail);
        phone =  findViewById(R.id.EDphoneagent);
        addImgAgent =  findViewById(R.id.EDaddimage);
        imgAgent =  findViewById(R.id.EDimg_agent);
        addagentbtn=  findViewById(R.id.EDbtn_Addagent);
        cancelbtn=  findViewById(R.id.EDbtn_cancelagent);
        mProgress  =  findViewById(R.id.EDprgsaddagent);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(fAuth.getCurrentUser().getUid()+"/"+"agents").child(agentId);
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
                Intent intent = new Intent(EditAgent.this, ListAgent.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void setAgent(String agentId){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(fAuth.getCurrentUser().getUid()+"/"+"agents").child(agentId);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                String BDfname = ds.child("fname").getValue(String.class);
                String BDadresse = ds.child("adresse").getValue(String.class);
                String BDemail = ds.child("email").getValue(String.class);
                String BDphone = ds.child("phone").getValue(String.class);
                String BDimageUrl = ds.child("imageUrl").getValue(String.class);
                fname.setText(BDfname);
                adresse.setText(BDadresse);
                email.setText(BDemail);
                phone.setText(BDphone);
                Picasso.get()
                        .load(BDimageUrl)
                        .into(imgAgent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});
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

                                    Map<String, Object> childUpdates = new HashMap<>();
                                    childUpdates.put("imageUrl", generatedFilePath);
                                    childUpdates.put("adresse", adresse.getText().toString());
                                    childUpdates.put("email", email.getText().toString());
                                    childUpdates.put("fname", fname.getText().toString());
                                    childUpdates.put("phone", phone.getText().toString());
                                    mDatabaseRef.updateChildren(childUpdates);
                                    Toast.makeText(EditAgent.this, "agent Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditAgent.this, ListAgent.class);
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

package com.example.wastematepro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wastematepro.Models.UserModel;
import com.example.wastematepro.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    Uri profileUri;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Profile Uploading");
        progressDialog.setMessage("We are uploading your profile");

        loadUserData();

        binding.fetchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });
        binding.BtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loadUserData() {
        firestore.collection("users").document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserModel model = documentSnapshot.toObject(UserModel.class);
                        if(documentSnapshot.exists()){
                            binding.TVUsername2.setText(model.getName());
                            binding.TVEmailAddress2.setText(model.getEmail());
                            binding.TVLocation2.setText(model.getAddress());
                            binding.TVPhonenumber2.setText(model.getPhoneNumber());

                            Picasso.get()
                                    .load(model.getProfile())
                                    .placeholder(R.drawable.user)
                                    .into(binding.IVProfilePic);
                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            if(data!=null){
                profileUri = data.getData();
                binding.IVProfilePic.setImageURI(profileUri);
                updateProfile(profileUri);
            }
        }
    }

    private void updateProfile(Uri profileUri) {
        progressDialog.show();
        final StorageReference reference = storage.getReference().child("profile").child(FirebaseAuth.getInstance().getUid());
        reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        firestore.collection("users").document(FirebaseAuth.getInstance().getUid()).update("profile", uri.toString());
                        Toast.makeText(ProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }


}
package com.example.wastematepro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wastematepro.Models.UserModel;
import com.example.wastematepro.databinding.ActivityEditProfileBinding;
import com.example.wastematepro.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    Uri profileUri;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Profile Updating");
        progressDialog.setMessage("We are updating your profile");

        binding.BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.ETProfilename.getText().toString();
                String phonenumber = binding.ETPhonenumber.getText().toString();
                String address = binding.ETAddress.getText().toString();

                if(name.isEmpty()){
                    binding.ETProfilename.setError("Enter your username");
                } else if (phonenumber.isEmpty()) {
                    binding.ETPhonenumber.setError("Enter your phone number");
                } else if (address.isEmpty()) {
                    binding.ETAddress.setError("Enter your address");
                } else {
                    progressDialog.show();
                    updateUserData(name,phonenumber,address);
                }
            }
        });
    }

    private void updateUserData(String name, String phonenumber, String address) {
        // Get the current user's UID
        String userId = FirebaseAuth.getInstance().getUid();

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("phonenumber", phonenumber);
        updates.put("address", address);

        // Update the user data in Firestore
        firestore.collection("users").document(userId)
                .update(updates)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT ).show();
                    }
                });
    }

}
package com.example.wastematepro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.wastematepro.Models.UserModel;
import com.example.wastematepro.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;
    private String selectedRole;
    String[] roles = {"Admin","User"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating your account");
        progressDialog.setMessage("Your account is created");

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItem = new ArrayAdapter<String>(this, R.layout.list_item, roles);

        autoCompleteTextView.setAdapter(adapterItem);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = parent.getItemAtPosition(position).toString();
            }
        });
        binding.BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.ETName.getText().toString();
                String email = binding.ETEmail.getText().toString();
                String password = binding.ETPassword.getText().toString();

                if(name.isEmpty()){
                    binding.ETName.setError("Enter your name");
                } else if (email.isEmpty()) {
                    binding.ETEmail.setError("Enter email");
                } else if (password.isEmpty()) {
                    binding.ETPassword.setError("Enter password");
                } else {
                    progressDialog.show();
                    storeUserData(name,email,password,selectedRole);
                }
            }
        });
        binding.TVSignupHere.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void storeUserData(String name, String email, String password, String role){

        auth.createUserWithEmailAndPassword(binding.ETEmail.getText().toString(),binding.ETPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String referCode = email.substring(0,email.lastIndexOf("@"));
                            UserModel model = new UserModel(name,email,password,
                                    "https://firebasestorage.googleapis.com/v0/b/wastemate-pro.appspot.com/o/user.png?alt=media&token=416953c3-ac3f-4639-8963-92497beacea9",
                                    referCode,
                                    "false",
                                    role,
                                    "",
                                    "",
                                    5,
                                    5,
                                    5);
                            String id = task.getResult().getUser().getUid();
                            firestore.collection("users").document(id).set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(SignUpActivity.this, VerifyActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }

}



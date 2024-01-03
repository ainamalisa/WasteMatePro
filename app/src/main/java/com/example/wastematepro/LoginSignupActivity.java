package com.example.wastematepro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wastematepro.R;
import com.example.wastematepro.databinding.ActivityLoginSignupBinding;
import com.example.wastematepro.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginSignupActivity extends AppCompatActivity {

    ActivityLoginSignupBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.btnsignup2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginSignupActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.btnlogin2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginSignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
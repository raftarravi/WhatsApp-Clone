package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.Models.Users;
import com.example.whatsappclone.databinding.ActivityMain2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ActivityMain2Binding binding;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main2);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUp.this);

        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account !");

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.signUpUserName.getText().toString().isEmpty() && !binding.signUpEmail.getText().toString().isEmpty() && !binding.signUpPassword.getText().toString().isEmpty())
                {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.signUpEmail.getText().toString() ,binding.signUpPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();

                                        Users users = new Users(binding.signUpUserName.getText().toString(),binding.signUpEmail.getText().toString(),binding.signUpPassword.getText().toString());
                                        String id = task.getResult().getUser().getUid();
                                        firebaseDatabase.getReference().child("Users").child(id).setValue(users);

                                    }else {
                                        Toast.makeText(SignUp.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    Toast.makeText(SignUp.this, "Enter Credential", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.signUptoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });
    }
}
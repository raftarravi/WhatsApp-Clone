/*package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.Models.Users;
import com.example.whatsappclone.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    ActivitySignInBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    BeginSignInRequest signInRequest;

    GoogleSignInClient googleSignInClient;

    private static final int RC_SIGN_IN = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private void setGoogleSignInClient() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        progressDialog= new ProgressDialog(SignIn.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("wait..\n login your id");


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);


        //googleSignInClient = GoogleSignIn.getClient(this,signInRequest);





        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                if(!binding.signInEmail.getText().toString().isEmpty() && !binding.signInPassword.getText().toString().isEmpty()){

                    auth.signInWithEmailAndPassword(binding.signInEmail.getText().toString() , binding.signInPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()){
                                        Intent intent= new Intent(SignIn.this, MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(SignIn.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }else{
                    Toast.makeText(SignIn.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });



        if(auth.getCurrentUser() != null){
            Intent intent= new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
        }





        binding.signInToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });

        binding.signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoogleSignInClient();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                // Signed in successfully, authenticate with Firebase
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Authentication successful, proceed to next activity
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                Users users = new Users();
                                users.setUserId(user.getUid());
                                users.setUserName(user.getDisplayName());
                                users.setProfilePic(user.getPhotoUrl().toString());
                                firebaseDatabase.getReference().child("Users").child(user.getUid()).setValue(users);



                                Intent intent = new Intent(SignIn.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(this , "you are loggedIn", Toast.LENGTH_SHORT).show();
                                // Proceed with your app logic
                            } else {
                                // Authentication failed
                                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }



}

 */
package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.Models.Users;
import com.example.whatsappclone.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    private static final int RC_SIGN_IN = 2;

    private ActivitySignInBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
    private GoogleSignInClient googleSignInClient;
    GoogleSignInAccount account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Wait... Logging in");

        if (auth.getCurrentUser() != null) {
            // User is signed in, navigate to main activity
            startActivity(new Intent(SignIn.this, MainActivity.class));
            finish();
        }

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                signInWithEmailPassword();
                
            }
        });

        binding.signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        binding.signInToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void signInWithEmailPassword() {
        if (!binding.signInEmail.getText().toString().isEmpty() && !binding.signInPassword.getText().toString().isEmpty()) {
            auth.signInWithEmailAndPassword(binding.signInEmail.getText().toString(), binding.signInPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignIn.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(SignIn.this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            if (account != null) {
                firebaseAuthWithGoogle(account.getIdToken());
            }
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                Users users = new Users();
                                users.setUserId(user.getUid());
                                users.setUserName(user.getDisplayName());
                                users.setProfilePic(user.getPhotoUrl().toString());
                                firebaseDatabase.getReference().child("Users").child(user.getUid()).setValue(users);
                                startActivity(new Intent(SignIn.this, MainActivity.class));
                            }

                            finish();
                        } else {
                            Toast.makeText(SignIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}

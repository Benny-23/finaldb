package com.example.dbconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ShareCompat;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class loginActivity extends AppCompatActivity {

    public EditText emailId, password;
    Button signIn;
    TextView tvsignUp;

    FirebaseAuth mfirebaseauth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



         mfirebaseauth = FirebaseAuth.getInstance();
         emailId = findViewById(R.id.editText);
         password = findViewById(R.id.editText2);
         signIn = findViewById(R.id.button);
         tvsignUp = findViewById(R.id.textView);

         mAuthStateListener = new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser mFirebaseUser = mfirebaseauth.getCurrentUser();
                 if( mFirebaseUser != null){
                     Toast.makeText(loginActivity.this, "logged in", Toast.LENGTH_SHORT).show();
                     Intent i = new Intent(loginActivity.this,HomeActivity.class);
                     startActivity(i);
                 }
                 else{
                     Toast.makeText(loginActivity.this,"please login",Toast.LENGTH_SHORT).show();
                 }
             }
         };
         signIn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String email = emailId.getText().toString();
                 String pwd = password.getText().toString();
                 if (email.isEmpty()) {
                     emailId.setError("please enter email");
                     emailId.requestFocus();
                 } else if (pwd.isEmpty()) {
                     password.setError("enter password");
                     password.requestFocus();
                 } else if (email.isEmpty() && pwd.isEmpty()) {
                     Toast.makeText(loginActivity.this, "empty", Toast.LENGTH_SHORT).show();
                 } else if (!(email.isEmpty() && pwd.isEmpty())) {
                     mfirebaseauth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(loginActivity.this,"login error , try again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intToHome = new Intent(loginActivity.this,HomeActivity.class);
                                startActivity(intToHome);
                            }
                         }
                     });

                 }
                 else {
                     Toast.makeText(loginActivity.this, "error", Toast.LENGTH_SHORT).show();
                 }
             }

         });

         tvsignUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intSignup = new Intent(loginActivity.this,MainActivity.class);
                 startActivity(intSignup);
             }
         });

     }
    @Override
    protected void onStart(){
        super.onStart();
        mfirebaseauth.addAuthStateListener(mAuthStateListener);
    }
}



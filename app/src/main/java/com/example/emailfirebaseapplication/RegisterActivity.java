package com.example.emailfirebaseapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {


    private EditText inputEmail, inputPassword;

    private FirebaseAuth mAuth;

    private String userId;
    private String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(this,LogedInActivity.class));
            finish();
        }

        inputEmail = (EditText)findViewById(R.id.editTextTextEmailAddress);
        inputPassword = (EditText)findViewById(R.id.editTextTextPassword);




    }

    public void login(View view) {

        startActivity(new Intent(this,LoginActivity.class));

    }

    public void register(View view) {
        String emailInput = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if(TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "All Fields are Required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailInput,password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),Toast.LENGTH_LONG).show();
                            Log.e("MyTag", task.getException().toString());
                        } else {

                            startActivity(new Intent(RegisterActivity.this, LogedInActivity.class));
                            finish();
                        }


                    }
                });


    }
}
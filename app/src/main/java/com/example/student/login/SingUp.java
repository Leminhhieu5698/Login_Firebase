package com.example.student.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SingUp extends AppCompatActivity implements View.OnClickListener {

    EditText edtSN, edtSP;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        mAuth = FirebaseAuth.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        edtSN = (EditText) findViewById(R.id.edtSName);
        edtSP = (EditText) findViewById(R.id.edtSPass);


        findViewById(R.id.btnsignup).setOnClickListener(this);
        findViewById(R.id.txtSlog).setOnClickListener(this);

    }

    private void registerUser() {
        String email = edtSN.getText().toString().trim();
        String password = edtSP.getText().toString().trim();

        if (email.isEmpty()) {
            edtSN.setError("Email is required");
            edtSP.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtSN.setError("Please enter a valid email");
            edtSP.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtSP.setError("Password is required");
            edtSP.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtSP.setError("Minimum lenght of password should be 6");
            edtSP.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(SingUp.this, MainActivity.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });}
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsignup:
                registerUser();
                break;

            case R.id.txtSlog:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}

package com.hotel.hotelpro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnUseAsGuest, btnSignUp, btnForgotPw, btnLogIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        btnUseAsGuest = (Button) findViewById(R.id.register_btn_use_as_guest);
        btnLogIn = (Button) findViewById(R.id.register_btn_log_in);
        btnSignUp = (Button) findViewById(R.id.register_btn_sign_up);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnForgotPw = (Button) findViewById(R.id.register_btn_forgot_pw);


        //On click of the use as a guest button
        btnUseAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Register.this, DashboardAcrivity.class);
                startActivity(intent);
                finish();
            }
        });


        //On click of the forgot password button
        btnForgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, ResetPasswordActivity.class));
            }
        });


        //On click of the log in button
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                Intent intent;

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || password.length() >= 6) {
                    if (email.equals("admin") && password.equals("password")){
                        intent = new Intent(Register.this, AdminConsole.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intentNew;

                                    intentNew = new Intent(Register.this, DashboardAcrivity.class);
                                    startActivity(intentNew);
                                    finish();

                                }else {
                                    Toast.makeText(getApplicationContext(), "Email or password incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Email or password incorrect", Toast.LENGTH_SHORT).show();

                }
            }
        });


        //On click of the register button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //Creating new user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Password incorrect or account already exist", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Register.this, "New user created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register.this, DashboardAcrivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
package com.hotel.hotelpro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hotel.hotelpro.databinding.ActivityDashboardAcrivityBinding;

public class ResetPasswordActivity extends AppCompatActivity {
    private Button btnSendMail;
    private EditText editTxtEmail;
    private FirebaseAuth auth = FirebaseAuth.getInstance();


    private ActivityDashboardAcrivityBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnSendMail = findViewById(R.id.reset_pw_btn_send_mail);
        editTxtEmail = findViewById(R.id.reset_edit_txt_email);


                  btnSendMail.setOnClickListener(new View.OnClickListener() {
                   @Override
                       public void onClick(View v) {
                     String email = editTxtEmail.getText().toString().trim();

                     if(isValidEmail(email)) {

                    auth.sendPasswordResetEmail(email).addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ResetPasswordActivity.this, "Email has been sent, please check your email", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ResetPasswordActivity.this, "Email Request failed, please check given email", Toast.LENGTH_SHORT).show();

                        }
                    });

                     }else {
                    Toast.makeText(getApplicationContext(), "Enter an valid email", Toast.LENGTH_SHORT).show();
                    }
                   }
                   });
                  }
                  public static boolean isValidEmail(CharSequence target) {
                  return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
                 }
                }

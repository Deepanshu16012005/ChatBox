package com.example.chatbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hbb20.CountryCodePicker;


public class LgoinPhone extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView register_btn,forget_password_btn;
    FirebaseAuth mAuth;
    String mail ,pass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null&&currentUser.isEmailVerified()){
            Intent intent =new Intent(LgoinPhone.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lgoin_phone);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        login=findViewById(R.id.login_login_button);
        register_btn=findViewById(R.id.login_register_button);
        forget_password_btn=findViewById(R.id.login_forget_password);
        mAuth=FirebaseAuth.getInstance();

        forget_password_btn.setOnClickListener(view -> {
            //getting email from edit text
            String reset_email=email.getText().toString();
            if(reset_email.isEmpty()) {
                email.setError("enter email");
                return;
            }
            //sending reset link to email
            mAuth.sendPasswordResetEmail(reset_email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(LgoinPhone.this, "reset link sent", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LgoinPhone.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        if(register_btn!=null){
        register_btn.setOnClickListener(view -> {
            startActivity(new Intent(LgoinPhone.this, LoginSetUsername.class));
            finish();
        });}
        login.setOnClickListener(view -> {

            mail=email.getText().toString();
            if(mail.isEmpty()) {
                email.setError("enter email");

            }else if (!mail.trim().matches(emailPattern)) {
                email.setError("enter valid email");

                }

            pass=password.getText().toString();
            if(pass.isEmpty()) {
                password.setError("enter password");}
            else if(pass.length()<6){
                password.setError("password is too short");
            }else if(pass.length()>15){
                password.setError("password is too long");
            }else if(!pass.matches(".*[A-Z].*")){
                password.setError("password must contain at least one uppercase letter");
            }else if(!pass.matches(".*[a-z].*")){
                password.setError("password must contain at least one lowercase letter");
            }else if(!pass.matches(".*[0-9].*")){
                password.setError("password must contain at least one number");
            }
            if(mail.isEmpty()||pass.isEmpty()){
                Toast.makeText(this, "fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                //checking if email is verified only then going to main else resending verification link
                                if(user!=null&&user.isEmailVerified()){
                                    Toast.makeText(LgoinPhone.this, "Authentication Successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(LgoinPhone.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{

                                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(LgoinPhone.this, "verification email has been sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LgoinPhone.this, "verifiaction email fail"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                                }
                                // Sign in success, update UI with the signed-in user's information


                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(LgoinPhone.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        });


    }
}
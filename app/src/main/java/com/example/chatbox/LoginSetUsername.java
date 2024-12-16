package com.example.chatbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginSetUsername extends AppCompatActivity {
    EditText email, password,username,phone;
    Button register;
    TextView login_btn;
    FirebaseAuth mAuth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
    String mail ,pass,phone_no,username_text,userID;
    //CountryCodePicker countryCodePicker;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();

    @Override
    public void onStart() {

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly and checking if email is verified
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null&&currentUser.isEmailVerified()){
            Intent intent =new Intent(LoginSetUsername.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars()); v.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom); return WindowInsetsCompat.CONSUMED; });
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_set_username);
        email=findViewById(R.id.register_email);
        username=findViewById(R.id.register_username);
        //countryCodePicker=findViewById(R.id.country_code_picker);
        phone=findViewById(R.id.register_phone);
        password=findViewById(R.id.register_password);
        register=findViewById(R.id.register_register_button);
        login_btn=findViewById(R.id.register_login_button);
        mAuth=FirebaseAuth.getInstance();


        //countryCodePicker.registerCarrierNumberEditText(phone);


        //check if phone is valid
//        if(!countryCodePicker.isValidFullNumber()){
//            phone.setError("Enter a valid phone number");
//            return;
//        }


        //login textview click listener

        if(login_btn!=null){
        login_btn.setOnClickListener(view -> {
            startActivity(new Intent(LoginSetUsername.this, LgoinPhone.class));
            finish();
        });}
        //register button click listener
        register.setOnClickListener(view -> {
            //getting email and password from edit text
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
            //getting phone no
            phone_no=phone.getText().toString();
            if(phone_no.length()!=10){
                phone.setError("Enter a 10 digit no");
                return;
            }
            //getting username
            username_text=username.getText().toString();

            if(username_text.isEmpty()){
                username.setError("enter username");
                return;
            }


            //checking if email and password are not empty
            if(mail.isEmpty()||pass.isEmpty()){
                Toast.makeText(this, "fields are empty", Toast.LENGTH_SHORT).show();
                return;
            }
            //creating user with email and password
            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                              // sore data in database
                                DatabaseReference usersRef = databaseReference.child("users");
                                UserModel userModel=new UserModel(mAuth.getCurrentUser().getUid(),username_text,mail,phone_no);
                                usersRef.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
                                Toast.makeText(LoginSetUsername.this, "data added", Toast.LENGTH_SHORT).show();
                                //send verification link to email
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        //email sent
                                        Toast.makeText(LoginSetUsername.this, "verification email has been sent", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginSetUsername.this, LgoinPhone.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    //email not sent
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginSetUsername.this, "verifiaction email fail "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });

                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginSetUsername.this, "Account craeted Successfully", Toast.LENGTH_SHORT).show();


                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(LoginSetUsername.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        });


    }
}
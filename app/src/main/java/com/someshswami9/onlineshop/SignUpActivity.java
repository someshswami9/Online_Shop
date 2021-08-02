package com.someshswami9.onlineshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private Button AccountCreateButton;
    private EditText input_name, input_phone_number, input_password;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AccountCreateButton = (Button) findViewById(R.id.registerButton);
        input_name = (EditText) findViewById(R.id.registerName);
        input_phone_number = (EditText) findViewById(R.id.registerPhoneNumber);
        input_password = (EditText) findViewById(R.id.registerPassword);
        progressDialog = new ProgressDialog(this);

        AccountCreateButton.setOnClickListener(v -> createAccount());
    }

    private void createAccount() {
        String name = input_name.getText().toString();
        String phone = input_phone_number.getText().toString();
        String password = input_password.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, " Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, " Please enter your phoneNumber", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, " Please enter your password ", Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("please Wait, we are checking the credintials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            validateUser(name, phone, password);
        }
    }

    private void validateUser(String name, String phone, String password) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!(snapshot.child("Users").child(phone).exists())){

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("phone",phone);
                    hashMap.put("password", password);
                    hashMap.put("name", name);

                    rootRef.child("Users").child(phone).updateChildren(hashMap).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){

                            Toast.makeText(SignUpActivity.this,"Your Account Created Succesfully.",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignUpActivity.this,"Please Try Again!",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(intent);

                        }

                    });
                }
                else {
                    Toast.makeText(SignUpActivity.this,"This " + phone + " already exists",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


}
package com.example.akshay.groupchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button signin , singup;
    private EditText name,email,password;
    private FirebaseAuth mAuth;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference().child("user");
        name= (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        password= (EditText) findViewById(R.id.Password);
        signin= (Button) findViewById(R.id.signIn);
        singup= (Button) findViewById(R.id.signUp);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


    }

    private void signup() {

        String emails=email.getText().toString().trim();
        String passwords=password.getText().toString().trim();


        mAuth.createUserWithEmailAndPassword(emails , passwords).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Intent i=new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void signin() {

        String emails=email.getText().toString().trim();
        String passwords=password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(emails , passwords).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent i=new Intent(MainActivity.this,Main2Activity.class);
                    finish();
                    startActivity(i);

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){

            Intent i = new Intent(MainActivity.this , Main2Activity.class);
            finish();
            startActivity(i);

        }
    }
}

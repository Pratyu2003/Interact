package com.pratyush.interact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailBox, passwordBox, nameBox;
    Button loginButton, CreateButton;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        emailBox = findViewById(R.id.emailBox);
        nameBox = findViewById(R.id.nameBox);
        passwordBox = findViewById(R.id.passwordBox);

        loginButton = findViewById(R.id.LoginButton);
        CreateButton = findViewById(R.id.CreateButton);

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, name;
                email = emailBox.getText().toString();
                password = passwordBox.getText().toString();
                name = nameBox.getText().toString();

                final User user = new User();
                user.setEmail(email);
                user.setPass(password);
                user.setName(name);

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(Signup.this, Login.class));
                                }
                            });
                            Toast.makeText(Signup.this, "Account is created.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Signup.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
}
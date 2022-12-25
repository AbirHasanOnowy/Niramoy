package com.example.niramoy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    EditText ePassword, eEmail;
    TextInputLayout layoutEmail, layoutPassword;
    MaterialButton logIn, createId;
    String email, password, uid, status, position, name;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private DocumentReference uidRef;

    private static final String KEY_POS = "Position";
    private static final String KEY_NAME = "Name";
    private static final String KEY_VERIFY = "Verified";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ePassword = findViewById(R.id.signInPasswordInput);
        eEmail = findViewById(R.id.signInEmailInput);
        layoutPassword = findViewById(R.id.signInPasswordLayout);
        layoutEmail = findViewById(R.id.signInEmailLayout);
        logIn = findViewById(R.id.logInButton);
        createId = findViewById(R.id.createIdButton);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = eEmail.getText().toString();
                password = ePassword.getText().toString();

                if (email.isEmpty()) {
                    eEmail.setError("Email is required");
                    eEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    ePassword.setError("Password is required");
                    ePassword.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    eEmail.setError("Please provide valid email");
                    eEmail.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    ePassword.setError("Password length min 6");
                    ePassword.requestFocus();
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                            uidRef = fStore.collection("UID").document(uid);
                            uidRef.addSnapshotListener(SignInActivity.this, new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    status = value.getString(KEY_VERIFY);
                                    name = value.getString(KEY_NAME);
                                    position = value.getString(KEY_POS);

                                    if (Objects.equals(status, "Yes")) {
                                        if (Objects.equals(position, "Doctor") || Objects.equals(position, "Receptionist")) {
                                            Toast.makeText(SignInActivity.this, "Welcome " + name, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                        } else if (Objects.equals(position, "Director")) {
                                            Toast.makeText(SignInActivity.this, "Welcome " + name, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SignInActivity.this, DirectorMainActivity.class));
                                        } else if (Objects.equals(position, "Admin")) {
                                            Toast.makeText(SignInActivity.this, "Welcome " + name, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SignInActivity.this, AdminMainActivity.class));
                                        } else {
                                            Toast.makeText(SignInActivity.this, "Welcome " + name, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(SignInActivity.this, NurseMainActivity.class));
                                        }
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Please wait for Director to assign you", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(SignInActivity.this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                createId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(SignInActivity.this, SignUp.class));
                        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));

                    }
                });

            }
        });

    }
}
package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    EditText ePassword,eEmail;
    TextInputLayout layoutEmail,layoutPassword;
    MaterialButton logIn,createId;
    String dMail="abir196600@gmail.com",dPassword="jkl",iMail,iPassword;

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

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(eEmail.length() != 0)  {
                    if (ePassword.length() != 0){

                        iMail = eEmail.getText().toString();
                        iPassword = ePassword.getText().toString();

                        if(Objects.equals(iMail, dMail) && iPassword.equals(dPassword)) {
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        } else {
                            layoutPassword.setError("Enter Correct Password");
                        }
                    } else {
                        layoutPassword.setError("Enter Valid Password");
                    }
                } else {
                    layoutEmail.setError("Enter Valid Email");
                }

            }
        });

        createId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SignInActivity.this, SignUp.class));
                startActivity(new Intent(SignInActivity.this,MainActivity.class));

            }
        });

    }

}
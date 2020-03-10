package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignupLoginActivity extends AppCompatActivity {

    private EditText etSignUpUsername, etSignUpPassword, etLoginUsername, etLoginPassword;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        etSignUpUsername = findViewById(R.id.etSignUpUsername);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(etSignUpUsername.getText().toString());
                parseUser.setPassword(etSignUpPassword.getText().toString());
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Intent intent = new Intent(SignupLoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                            FancyToast.makeText(SignupLoginActivity.this,ParseUser.getCurrentUser().get("username") + " joined the platform",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        }else{
                            FancyToast.makeText(SignupLoginActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(etLoginUsername.getText().toString(),
                                            etLoginPassword.getText().toString(),
                                            new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            Intent intent = new Intent(SignupLoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                            FancyToast.makeText(SignupLoginActivity.this,ParseUser.getCurrentUser().get("username") + " Logged in successfully!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        }else{
                            FancyToast.makeText(SignupLoginActivity.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });

    }
}

package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etAge;
    private TextView tvPerson, tvPersons;
    private String persons;
    private Button btnTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etAge = findViewById(R.id.etAge);
        tvPerson = findViewById(R.id.tvPerson);
        tvPersons = findViewById(R.id.tvPersons);
        btnTransition = findViewById(R.id.btnTransition);

        tvPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> getSinglePerson = ParseQuery.getQuery("Persons");
                getSinglePerson.getInBackground("u1CqkjKq9Z", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null && object != null){
                            tvPerson.setText("My name is "+object.get("name")+". I am "+object.get("age")+" years old");
                        }else{
                            FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                        }
                    }
                });
            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, SignupLoginActivity.class);
                startActivity(i);
            }
        });

    }

    public void saveUserDetails(View view) {
        try {
            persons = "";
            final ParseObject obj = new ParseObject("Persons");
            obj.put("name", etName.getText().toString());
            obj.put("email", etEmail.getText().toString());
            obj.put("password", etPassword.getText().toString());
            obj.put("age", Integer.parseInt(etAge.getText().toString()));
            obj.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this,"saved",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                        ParseQuery<ParseObject> allPersons = ParseQuery.getQuery("Persons");
                        allPersons.whereGreaterThanOrEqualTo("age", 21);
                        allPersons.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null && objects.size() > 0){
                                    for (ParseObject person : objects){
                                        persons += person.get("name") + "\n";
                                        tvPersons.setText(persons);
                                    }
                                }else{
                                    FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                                }
                            }
                        });

                    }else{
                        FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }
            });
        }
        catch (Exception e){
            FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
        }
    }
}

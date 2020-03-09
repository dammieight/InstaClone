package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public void helloWorld(View view) {
        final ParseObject obj = new ParseObject("Members");
        obj.put("name", "Moronkola Oluwadamilare");
        obj.put("gender", "Male");
        obj.put("occupation", "Programmer");
        obj.put("age", 24);
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null)
                    Toast.makeText(MainActivity.this,"My name is "+ obj.get("name")+ ", "+ obj.get("age"), Toast.LENGTH_LONG).show();
            }
        });
    }
}

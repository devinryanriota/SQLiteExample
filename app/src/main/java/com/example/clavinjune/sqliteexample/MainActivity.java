package com.example.clavinjune.sqliteexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;
    private EditText nimField, nameField, gpaField;
    private Button submitButton, viewButton;

    // helper buat ambil text dari textView
    private String getText(TextView component) {
        return component.getText().toString();
    }

    // buat refresh page
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        nimField = findViewById(R.id.nimField);
        nameField = findViewById(R.id.nameField);
        gpaField = findViewById(R.id.gpaField);

        submitButton = findViewById(R.id.submitButton);
        viewButton = findViewById(R.id.viewButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String nim = getText(nimField);
                    String name = getText(nameField);

                    // karena double harus di parse dari string ke double
                    double gpa = Double.parseDouble(getText(gpaField));

                    db.insertStudent(nim, name, gpa);

                    // toast sukses
                    Toast.makeText(MainActivity.this, "Data Submitted!", Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    // kalo error munculin errornya dari e.getMessage()
                    Toast.makeText(MainActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pindah activity ke ManageStudentActivity
                startActivity( new Intent(MainActivity.this, ManageStudentActivity.class) );
            }
        });
    }
}

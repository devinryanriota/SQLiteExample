package com.example.clavinjune.sqliteexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateStudentActivity extends AppCompatActivity {


    private DBHelper db;
    private EditText nimField, nameField, gpaField;
    private Button updateButton;
    private String nimExtra;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        db = new DBHelper(this);

        nimField = findViewById(R.id.nimField);
        nameField = findViewById(R.id.nameField);
        gpaField = findViewById(R.id.gpaField);

        updateButton = findViewById(R.id.updateButton);

        nimExtra = getIntent().getStringExtra("nim");

        student = db.getStudent(nimExtra);

        nimField.setText(student.getNim());
        nameField.setText(student.getName());
        gpaField.setText(student.getGpa()+"");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = nameField.getText().toString();
                    double gpa = Double.parseDouble(gpaField.getText().toString());
                    db.updateStudent(nimExtra, name, gpa);
                    Toast.makeText(UpdateStudentActivity.this, "Update Success!", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception e) {
                    Toast.makeText(UpdateStudentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

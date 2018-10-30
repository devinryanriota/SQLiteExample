package com.example.clavinjune.sqliteexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class ManageStudentActivity extends AppCompatActivity {

    private DBHelper db;
    private ListView studentList;
    private TextView nimField;
    private Button updateButton, deleteButton, backButton;


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
        setContentView(R.layout.activity_manage_student);

        db = new DBHelper(this);
        studentList = findViewById(R.id.studentList);
        nimField = findViewById(R.id.nimField);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        backButton = findViewById(R.id.backButton);

        // ubah tampilan studentList jadi customadapter kita
        // parameternya data yang mau ditampilin
        // semua data students diambil dari database
        studentList.setAdapter(new CustomAdapter(db.selectStudents()));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kalo nimnya belom diisi
                if( nimField.getText().toString().equals("") ) {
                    Toast.makeText(ManageStudentActivity.this, "Fill NIM", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(ManageStudentActivity.this, UpdateStudentActivity.class);
                intent.putExtra("nim", nimField.getText().toString());

                startActivity(intent);
            }
        });

        // click delete kalo nimnya udah diisi
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kalo nimnya belom diisi
                if( nimField.getText().toString().equals("") ) {
                    Toast.makeText(ManageStudentActivity.this, "Fill NIM", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    db.deleteStudent(nimField.getText().toString());
                    Toast.makeText(ManageStudentActivity.this, nimField.getText().toString()+ " deleted!", Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(ManageStudentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                studentList.setAdapter(new CustomAdapter(db.selectStudents()));
                nimField.setText("");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        // kita mau munculin semua data studentnya
        // tampung ke arraylist
        private ArrayList<Student> students;

        public CustomAdapter(ArrayList<Student> students) {
            this.students = students;
        }

        @Override
        public int getCount() {
            // ubah get count ke size dari arraylistnya
            return students.size();
        }

        @Override
        public Object getItem(int position) {
            // ubah ke position
            return position;
        }

        @Override
        public long getItemId(int position) {
            // ubah ke position
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // manipulasi tampilannya

            LayoutInflater inflater = getLayoutInflater();

            // ubah view nya jadi custom layout yang kita bikin
            convertView = inflater.inflate(R.layout.list_student_custom, null);

            // buat convertViewnya kalo di click munculin NIM dari data yang di click
            // biar bisa di update / delete
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nimField.setText(students.get(position).getNim());
                }
            });

            // isi data yang ada di custom layout
            // nim diisi nim dari arraylist
            ((TextView)convertView.findViewById(R.id.nimText)).setText( students.get(position).getNim() );
            ((TextView)convertView.findViewById(R.id.nameText)).setText( students.get(position).getName() );

            // karena dia terimanya string
            // gpa kan double
            // cara ubahnya tinggal ditambah + "" biar dia jadi string
            ((TextView)convertView.findViewById(R.id.gpaText)).setText( students.get(position).getGpa()+"" );

            // return convertView yang tadi udah dimanipulasi tampilannya
            return convertView;
        }
    }
}

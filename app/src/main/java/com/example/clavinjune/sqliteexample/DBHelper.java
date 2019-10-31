package com.example.clavinjune.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Context context nanti akan diisi oleh Activity yang memanggil DBHelper
    public DBHelper(Context context) {
        // example = nama database
        // 1 = version (bebas)
        super(context, "example", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // buat table students
        //execSQL soalnya CREATE TABLE ga ngasilin apa2 cuma sukses / failed doang. kita gamau ambil resultnya
        db.execSQL("CREATE TABLE students(nim TEXT PRIMARY KEY, name TEXT NOT NULL, gpa DOUBLE NOT NULL)");

        // kalo mau bikin table lain panggil lagi aja caranya sama, contoh
        // db.execSQL("CREATE TABLE course(code TEXT PRIMARY KEY, name TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    // insert student
    public void insertStudent(String nim, String name, double gpa) {

        // getwritabledatabase soalnya kita mau insert kedalam tablenya
        SQLiteDatabase db = getWritableDatabase();

        // ContentValue membantu buat insert per kolom
        ContentValues values = new ContentValues();

        // kolom nim diisi dengan nim yang dari parameter
        values.put("nim", nim);
        values.put("name", name);
        values.put("gpa", gpa);
        values.put("email", name+nim+"@mail.com");

        // students = nama table
        // values = isi insertnya
        // kalau query manual kan
        // INSERT INTO students VALUES('2001539682', 'Clavin June', 4.99);
        db.insert("students", null, values);
    }

    // select all students
    public ArrayList<Student> selectStudents() {
        ArrayList<Student> students = new ArrayList<>();

        // getreadabledatabase soalnya kita cuma mau select
        SQLiteDatabase db = getReadableDatabase();

        // rawQuery soalnya result dari querynya kita mau pake
        Cursor result = db.rawQuery("SELECT * FROM students", null);

        // check kalo data nya kosong langsung return
        if( result.getColumnCount() == 0 ) return students;

        // selama masih ada data ambil datanya per kolom
        while( result.moveToNext() ) {
            // getstring soalnya nim bentuknya string, 0 soalnya nim ada dikolom pertama
            String nim = result.getString(0);

            // misal kita gatau index kolomnya kita bisa panggilnya dengan nama kolomnya aja
            String name = result.getString( result.getColumnIndex("name") );

            // getDouble soalnya gpa bentuknya double
            double gpa = result.getDouble(2);
            
            //get email hardcode
            String email = result.getString(3);

            // bikin modelnya
            Student student = new Student(nim, name, gpa);

            //masukkin modelnya kedalem arraylist
            students.add(student);
        }

        return students;
    }

    // delete student by nim
    public void deleteStudent(String nim) {
        // writeable karena kita mau hapus data dari table
        SQLiteDatabase db = getWritableDatabase();

        // delete students where nim = 'nim'
        db.delete("students", "nim = "+ nim, null);
    }

    // select student by nim
    public Student getStudent(String nim) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM students WHERE nim = " + nim, null);

        if( result.getColumnCount() == 0 ) return null;

        // ambil data pertama
        result.moveToFirst();

        String name = result.getString(1);
        double gpa = result.getDouble(2);

        return new Student(nim, name, gpa);
    }

    // update student by nim
    public void updateStudent(String nim, String name, double gpa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("gpa", gpa);

        db.update("students", values, "nim="+nim, null);
    }
}

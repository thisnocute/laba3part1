package com.example.laba3new;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    Button viewStudentsButton;
    Button addStudentButton;
    Button deleteAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        dbHelper.clearAndInsertInitialData();

        viewStudentsButton = findViewById(R.id.button_view_students);
        viewStudentsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ViewStudentsActivity.class);
            startActivity(intent);
        });

        addStudentButton = findViewById(R.id.button_add_student);
        addStudentButton.setOnClickListener(v -> addStudent());

        deleteAllButton = findViewById(R.id.button_delete_all);
        deleteAllButton.setOnClickListener(v -> deleteAllStudents());


        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }


    private void addStudent() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FIO, "Новое Имя Фамилия");
        values.put(DatabaseHelper.COLUMN_DATE, String.valueOf(System.currentTimeMillis()));
        db.insert(DatabaseHelper.TABLE_NAME, null, values);
        db.close();
        Toast.makeText(this, "Запись добавлена", Toast.LENGTH_SHORT).show();
    }

    private void deleteAllStudents() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DatabaseHelper.TABLE_NAME);
        db.close();
        Toast.makeText(this, "Все записи удалены", Toast.LENGTH_SHORT).show();
    }
}
package com.example.laba3new;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewStudentsActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        TextView textView = findViewById(R.id.textview_students);
        if (textView == null) {
            throw new NullPointerException("TextView с id 'textview_students' не найден в макете!");
        }

        dbHelper = new DatabaseHelper(this);
        displayStudents();

        EdgeToEdge.enable(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();
    }

    private void displayStudents() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FIO);
            int dateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE);

            if (nameIndex != -1 && dateIndex != -1) {
                String name = cursor.getString(nameIndex);
                String date = cursor.getString(dateIndex);
                stringBuilder.append(name).append(" - ").append(date).append("\n");
            }
        }
        cursor.close();
        db.close();

        TextView textView = findViewById(R.id.textview_students);
        if (textView != null) {
            textView.setText(stringBuilder.toString());
        } else {
            // Логирование ошибки
            throw new NullPointerException("TextView с id 'textview_students' не найден после вызова findViewById.");
        }
    }
}

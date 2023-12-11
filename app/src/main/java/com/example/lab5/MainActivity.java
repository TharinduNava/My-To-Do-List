package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper DB;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private EditText name, details, date, status, id;

    private Button Date, Insert, View, Update, Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB = new DatabaseHelper(this);

        Date = findViewById(R.id.btnDate);
        Insert = findViewById(R.id.btnInsert);
        View = findViewById(R.id.btnView);
        Update = findViewById(R.id.btnUpdate);
        Delete = findViewById(R.id.btnDelete);

        name = findViewById(R.id.taskName);
        details = findViewById(R.id.taskDetails);
        date = findViewById(R.id.taskDate);
        status = findViewById(R.id.taskStatus);
        id = findViewById(R.id.taskID);
        calendar = Calendar.getInstance();

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                date.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInsert = DB.insertData(

                        name.getText().toString(),
                        details.getText().toString(),
                        date.getText().toString(),
                        status.getText().toString());

                if (isInsert == true) {
                    Toast.makeText(MainActivity.this, "Task Inserted!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Task Not Inserted",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer isInsert = DB.deleteData(id .getText().toString());
                if (isInsert > 0) {
                    Toast.makeText(MainActivity.this, "Task Deleted!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No Task Available to Delete!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInsert = DB.updateData(id.getText().toString(),

                        name.getText().toString(),
                        details.getText().toString(),
                        date.getText().toString(),
                        status.getText().toString());

                if (isInsert == true) {
                    Toast.makeText(MainActivity.this, "Task Updated!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No Task Available to Update!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = DB.getAll();
                if (result.getCount() == 0) {
                    showBox("List of Tasks", "No Tasks Available Yet!");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append(
                            "Task ID :- " + result.getString(0) +
                            "\nTask Name :- " + result.getString(1) +
                            "\nTask Details :- " + result.getString(2) +
                            "\nTask Date :- " + result.getString(3) +
                            "\nTask Status :- " + result.getString(4) +
                            "\n\n\n");
                }
                showBox("List of Tasks", buffer.toString());
            }
        });
    }

    public void showBox(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
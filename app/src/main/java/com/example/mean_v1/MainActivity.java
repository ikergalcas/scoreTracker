package com.example.mean_v1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mean_v1.db.AppContract;
import com.example.mean_v1.db.AppDbHelper;

import java.util.ArrayList;
import java.util.List;

//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
public class MainActivity extends AppCompatActivity {
    private static final String[] subjects = {
            "Ingenieria Web",
            "Sistemas Empotrados",
            "Gestion de Proyectos",
            "Metodos Formales"
    };

    private static final float[] scores = {
            0.5f, 0.4f,1.5f, 2.0f
    };

    private static final float[] max_score = {
            1.0f, 2.0f,2.0f, 2.0f
    };
    private static final float[] weight = {
            0.2f, 0.35f, 0.35f, 0.5f
    };

    private AppDbHelper dbHelper;
    private SQLiteDatabase db;
    private RecyclerView recyclerView_subjects;
    private EditText editText;

    private SubjectAdapter subjectAdapter;
    private List<Subject> subjectList;
    private Button button2;
    private Button resetButton;

    private void initDb() {
        ContentValues values = new ContentValues();
        for (int i = 0; (i < scores.length - 1) ; ++i) {
            values.put(AppContract.DictEntry.COLUMN_NAME_SUBJECT, subjects[0]);
            values.put(AppContract.DictEntry.COLUMN_NAME_STUDENT_SCORE, scores[i]);
            values.put(AppContract.DictEntry.COLUMN_NAME_MAX_SCORE, max_score[i]);
            values.put(AppContract.DictEntry.COLUMN_NAME_WEIGHT, weight[i]);
            db.insert(AppContract.DictEntry.TABLE_NAME, null, values);
        }
        values.put(AppContract.DictEntry.COLUMN_NAME_SUBJECT, subjects[1]);
        values.put(AppContract.DictEntry.COLUMN_NAME_STUDENT_SCORE, scores[3]);
        values.put(AppContract.DictEntry.COLUMN_NAME_MAX_SCORE, max_score[3]);
        values.put(AppContract.DictEntry.COLUMN_NAME_WEIGHT, weight[3]);
        db.insert(AppContract.DictEntry.TABLE_NAME, null, values);

    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void getAllSubjects() {
        String[] projection = {
                AppContract.DictEntry.COLUMN_NAME_SUBJECT,
                "SUM(" + AppContract.DictEntry.COLUMN_NAME_STUDENT_SCORE + " * " +
                        AppContract.DictEntry.COLUMN_NAME_WEIGHT + ") AS TotalScore",
                "SUM(" + AppContract.DictEntry.COLUMN_NAME_MAX_SCORE + " * " +
                        AppContract.DictEntry.COLUMN_NAME_WEIGHT + ") AS TotalMaxScore"
        };

        // No selection string or arguments needed for all subjects
        String groupBy = AppContract.DictEntry.COLUMN_NAME_SUBJECT;

        Cursor cursor = db.query(
                AppContract.DictEntry.TABLE_NAME,
                projection,
                null, // No selection string for all subjects
                null, // No selection arguments for all subjects
                groupBy,
                null,
                null);

        while (cursor.moveToNext()) {
            String subject_str = cursor.getString(
                    cursor.getColumnIndexOrThrow(AppContract.DictEntry.COLUMN_NAME_SUBJECT));
            float totalScore = cursor.getFloat(cursor.getColumnIndexOrThrow("TotalScore"));
            float totalMaxScore = cursor.getFloat(cursor.getColumnIndexOrThrow("TotalMaxScore"));

            Subject subject = new Subject(subject_str, totalScore, totalMaxScore);
            subjectList.add(subject);
        }
        cursor.close();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subjectList = new ArrayList<>();

        dbHelper = AppDbHelper.getInstance(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        recyclerView_subjects = findViewById(R.id.RecyclerView_subjects);
        editText = findViewById(R.id.editText);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubject();
            }
        });

        resetButton = findViewById(R.id.button3);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBD();
            }
        });

        recyclerView_subjects.setLayoutManager(new LinearLayoutManager(this));

//        textView1 = findViewById(R.id.textView1);
//        textView2 = findViewById(R.id.textView2);

        //initDb()
        getAllSubjects();
        subjectAdapter = new SubjectAdapter(subjectList);
        recyclerView_subjects.setAdapter(subjectAdapter);
        subjectAdapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Subject subject) {
                Intent intent = new Intent(MainActivity.this, ScoresActivity.class);

                // Assuming Subject class has methods to get its properties
                intent.putExtra("SUBJECT_NAME", subject.getName());

                startActivity(intent);
            }
        });
    }

    private void resetBD() {
        dbHelper.cleanDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; (i < subjects.length) ; ++i) {
            values.put(AppContract.DictEntry.COLUMN_NAME_SUBJECT, subjects[i]);
            db.insert(AppContract.DictEntry.TABLE_NAME, null, values);
        }
        Toast.makeText(this,R.string.reset, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void addSubject() {
        String new_subject = String.valueOf(editText.getText());
        if (new_subject.isEmpty()) {
            Toast.makeText(this,R.string.badInput, Toast.LENGTH_LONG).show();
        } else {
            editText.setText("");
            showFormDialog(new_subject);
        }
    }


    private void showFormDialog(String new_subject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pop_up_form, null);
        builder.setView(dialogView);

        EditText editTextYourScore = dialogView.findViewById(R.id.editTextYourScore);
        EditText editTextMaxScore = dialogView.findViewById(R.id.editTextMaxScore);
        EditText editTextWeight = dialogView.findViewById(R.id.editTextWeight);

        builder.setPositiveButton("Accept", (dialog, which) -> {
            float yourScore = Float.parseFloat(editTextYourScore.getText().toString());
            float maxScore = Float.parseFloat(editTextMaxScore.getText().toString());
            float weight = Float.parseFloat(editTextWeight.getText().toString());

            // Perform action with these values
            Subject nSubject = new Subject(new_subject, yourScore * weight,maxScore * weight);
            subjectList.add(nSubject);
            addNewSubjectScore(new_subject, yourScore, maxScore, weight);
            subjectAdapter.updateSubjects();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void addNewSubjectScore(String subjectName, float score, float maxScore, float weight) {
        ContentValues values = new ContentValues();
        values.put(AppContract.DictEntry.COLUMN_NAME_SUBJECT, subjectName);
        values.put(AppContract.DictEntry.COLUMN_NAME_STUDENT_SCORE, score);
        values.put(AppContract.DictEntry.COLUMN_NAME_MAX_SCORE, maxScore);
        values.put(AppContract.DictEntry.COLUMN_NAME_WEIGHT, weight);

        try {
            db.insertOrThrow(AppContract.DictEntry.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("MainActivity", "Error while adding subject score", e);
            // Handle the exception, e.g., show a Toast to the user
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }
}
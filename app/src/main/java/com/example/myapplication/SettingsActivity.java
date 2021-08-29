package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs =
                getSharedPreferences("settings", Context.MODE_PRIVATE);

        TextView textViewEmail = findViewById(R.id.editTextTextEmailAddress);

        if(prefs.contains("myEmail")){
            // Получаем число из настроек
            String email = prefs.getString("myEmail",  "");
            // Выводим на экран данные из настроек
            textViewEmail.setText(email);
        }

    }

    public void onbuttonSaveClick(View view)
    {
        TextView textViewEmail = findViewById(R.id.editTextTextEmailAddress);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("myEmail", textViewEmail.getText().toString()).apply();

        startActivity(new Intent(this, MainActivity.class));
    }
}
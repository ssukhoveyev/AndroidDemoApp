package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onbuttonNavigationClick(View view)
    {
        Intent i = new Intent(this, NavigationActivity.class);
        startActivity(i);
    }

    public void onbuttonToastClick(View view)
    {
        Toast.makeText(this, "Сообщение Toast.", Toast.LENGTH_SHORT).show();
    }

    public void onbuttonSnackbarClick(View view)
    {
        Snackbar.make(view, "Сообщение Snackbar.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    //Notification
    public void onbuttonNotificationClick(View view)
    {
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void onbuttonGetPhotoClick(View view)
    {
        startActivity(new Intent(this, GetPhotoActivity.class));
    }

    public void onbuttonGetSensorsClick(View view)
    {
        startActivity(new Intent(this, SensorActivity.class));
    }

    public void onbuttonSmsClick(View view)
    {
        startActivity(new Intent(this, SmsActivity.class));
    }

    //Добавляем меню main_menu к активити
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Обработка нажатий пунктов меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.exit:
                finish();
                return true;
        }
        //headerView.setText(item.getTitle());
        return super.onOptionsItemSelected(item);
    }
}
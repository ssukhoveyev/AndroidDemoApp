package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private TextView textViewSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        textViewSensors = findViewById(R.id.textViewSensors);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> listSensor = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> listSensorType = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listSensor.size(); i++) {
            sb.append("Sensor: " + listSensor.get(i).getName() + System.lineSeparator());
            sb.append("Vendor: " + listSensor.get(i).getVendor()+ System.lineSeparator());
            sb.append("Version: " + listSensor.get(i).getVersion() + System.lineSeparator());
            sb.append(System.lineSeparator());
        }

        textViewSensors.setText(sb.toString());

//        setListAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, listSensorType));
//        getListView().setTextFilterEnabled(true);
    }
}
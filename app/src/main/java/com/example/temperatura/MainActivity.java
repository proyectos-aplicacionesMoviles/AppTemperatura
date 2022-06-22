package com.example.temperatura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView vistaValorTemp;
    private TextView sinSensor;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private ProgressBar progressBar;
    private Boolean DisponibilidadSensor;
    private int nivelTemperatura =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        vistaValorTemp =findViewById(R.id.textView1);
        sinSensor =findViewById(R.id.textView2);

        // se debe crear una instancia de la clase SensorManager, que se puede usar para obtener una instancia de un sensor físico
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        progressBar =(ProgressBar) findViewById(R.id.progressBar);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null){
            tempSensor=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            DisponibilidadSensor = true;

        }else{
            sinSensor.setText("su dispositivo no " +
                    "cuenta con sensor" +
                    " de temperatura");
            vistaValorTemp.setVisibility(View.GONE);
            DisponibilidadSensor =false;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        nivelTemperatura = (int) event.values[0];
        vistaValorTemp.setText(nivelTemperatura+"°C");
        progressBar.setProgress(nivelTemperatura);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        // Registra un oyente para el sensor.
        super.onResume();
        sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Se asegura de cancelar el registro del sensor cuando la actividad se detenga.
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
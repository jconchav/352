package cl.jccv.a352;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        textView = (TextView)findViewById(R.id.textView);
        imageView = (ImageView)findViewById(R.id.image);


        //final MediaPlayer mp = MediaPlayer.create(this, R.raw.cerca);

        if(proximitySensor == null){
            Toast.makeText(this,"Proximity Sensor is not available",Toast.LENGTH_LONG).show();
            finish();
        }

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    textView.setText("NO MAS");
                    textView.setTextSize(100);
                    imageView.setImageResource(R.drawable.eyeoff);
                }else {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    textView.setText("JUSTICIA PARA LXS 352");
                    textView.setTextSize(45);
                    imageView.setImageResource(R.drawable.eye);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(proximitySensorListener,proximitySensor,
                2*1000*1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }
}

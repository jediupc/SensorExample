package joandev.sensorexample;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class IntelService extends Service implements SensorEventListener, MediaPlayer.OnCompletionListener {


    SensorManager sensorManager;
    Sensor sensor;
    MediaPlayer mPlayer;

    public IntelService() {

    }

    @Override
    public void onCreate() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        } else {
            Toast.makeText(getApplicationContext(), "SHIT", Toast.LENGTH_SHORT).show();
        }
        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.up);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("sensorData", "detached");
        sensorManager.unregisterListener(this);
        mPlayer.release();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Float lux = sensorEvent.values[0];
        Log.v("sensorData", lux.toString());
        if (lux < 50) {
            mPlayer.start();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
    }
}

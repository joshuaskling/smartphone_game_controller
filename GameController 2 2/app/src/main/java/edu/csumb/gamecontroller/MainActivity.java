package edu.csumb.gamecontroller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import org.json.JSONObject;

public class MainActivity extends Activity implements SensorEventListener {


    private IOSocket socket;
    private SensorManager mSensorManager;
    static long time = System.currentTimeMillis();
    //long positionTimer = System.currentTimeMillis();
    public static Vibrator vibrator;
    public LinearLayout mLinearLayout;

    public final String DEBUGMSG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout = new LinearLayout(this);

        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        //set orientation to landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //get size of screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int screenWidth = size.x;
        final int screenHeight = size.y;

        // Connection to server
        //connect();

        // Attempt to draw EdgeEffect on view
        //setContentView(new DrawDemo(this));
        setContentView(R.layout.activity_main);

        // Controller buttons
        final ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        final ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        final ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        final ImageButton button4 = (ImageButton) findViewById(R.id.button4);

        final Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        final Button startBtn = (Button) findViewById(R.id.startBtn);
        final Button selectBtn = (Button) findViewById(R.id.selectBtn);



        final View touchView = findViewById(R.id.joystickLayout);

        //joystick controls
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (System.currentTimeMillis() - time > 100) {

                    float x = (event.getRawX() - (screenWidth / 5));
                    float y = (event.getRawY() - (screenHeight / 2)) * -1;

                    Log.d(DEBUGMSG, "(" + Float.toString(x) + "," + Float.toString(y) + ")");
                    sendMovement(x, y);
                    time = System.currentTimeMillis();
                }
                return true;
            }
        });



        startBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(DEBUGMSG, "Start btn touched");
                return false;
            }
        });

        selectBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(DEBUGMSG, "Select btn touched");
                return false;
            }
        });

        button1.setOnTouchListener(
                new View.OnTouchListener(){
                    @Override
                    public boolean onTouch (View v, MotionEvent event){
                        //darken button when pressed
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                //button1.setBackgroundResource(R.drawable.x_button_small_pressed);
                                //vibrator.vibrate(30);
                                sendFire("btn1", "down");
                                //Log.i("AIPSERVER", "Message sent to server: fire!");
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                //v.getBackground().clearColorFilter();
                                //v.invalidate();
                                //button1.setImageResource(R.drawable.x_button_small);
                                sendFire("btn1", "up");
                                break;
                            }
                        }
                    return true;
                    }
                }
        );
        button2.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //darken button when pressed
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                //v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                                //v.invalidate();
                                vibrator.vibrate(30);
                                sendFire("btn2", "down");
                                //Log.i("AIPSERVER", "Message sent to server: fire!");
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                //v.getBackground().clearColorFilter();
                                //v.invalidate();
                                sendFire("btn2", "up");
                                break;
                            }
                        }
                        return true;
                    }
                }
        );
        button3.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        //darken button when pressed
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                //v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                                //v.invalidate();
                                vibrator.vibrate(30);
                                sendFire("btn3", "down");
                                //Log.i("AIPSERVER", "Message sent to server: fire!");
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                //v.getBackground().clearColorFilter();
                                //v.invalidate();
                                sendFire("btn3", "up");
                                break;
                            }
                        }

                        return true;
                    }
                }
        );
        button4.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //darken button when pressed
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                //v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                                //v.invalidate();
                                vibrator.vibrate(30);
                                sendFire("btn4", "down");
                                //Log.i("AIPSERVER", "Message sent to server: fire!");
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                //v.getBackground().clearColorFilter();
                                //v.invalidate();
                                sendFire("btn4", "up");
                                break;
                            }
                        }
                        return true;
                    }
                }
        );

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    public void loadSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }




    //stick listeners
    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
        // for the system's orientation sensor registered listeners

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        // to stop the listener and save battery

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    void sendFire(String input, String state) {
        // get the angle around the z-axis rotated
        Log.d(DEBUGMSG, "Input: " + input + ", State: " + state);

    }

    void sendOrientation(float deltaAlpha, float deltaBeta, float deltaGamma) {
            try {

                String messageContent = new String("{alpha: " + deltaAlpha + ",beta: " + deltaBeta + ", gamma:" + deltaGamma + "}");

                JSONObject message = new JSONObject(messageContent);

                if (socket.isConnected()) {
                    socket.emit("orientation", message);
                    Log.i("AIPSERVER", "Message sent to server: " + message.getString("alpha"));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    void send2dMovement(String direction){
        try{
            String messageContent = new String("{direction: " + direction + "}");
            JSONObject message = new JSONObject(messageContent);

            if (socket.isConnected()) {
                socket.emit("joystick", message);
                //Log.i("AIPSERVER", "Message sent to server: " + message.getString("movement"));
                System.out.println(direction);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void sendMovement(float x, float y) {

    }

}

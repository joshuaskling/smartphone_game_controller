package edu.csumb.gamecontroller;

import android.app.Activity;
<<<<<<< HEAD
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
=======
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.preference.PreferenceManager;
=======
>>>>>>> parent of 4ad417e... Preference listener done
import android.provider.Settings;
>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
<<<<<<< HEAD
=======
import android.widget.Button;
import android.widget.EdgeEffect;
>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
<<<<<<< HEAD
=======
import android.widget.Toast;
//import android.support.v4.app.DialogFragment;
>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73

import org.json.JSONObject;

public class MainActivity extends Activity implements SensorEventListener {


    private IOSocket socket;
    private SensorManager mSensorManager;
    static long time = System.currentTimeMillis();
    //long positionTimer = System.currentTimeMillis();
    public static Vibrator vibrator;
<<<<<<< HEAD

    public final String DEBUGMSG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
=======
    public LinearLayout mLinearLayout;

    public final String DEBUGMSG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout = new LinearLayout(this);

>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        //set orientation to landscape
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

<<<<<<< HEAD
<<<<<<< HEAD
=======

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            // Detects changes in Preference values
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals(KEY_PREF_BTN_SIZE)) {
                    Log.d(DEBUGMSG, sharedPreferences.getString(s, ""));
                }
                else if(s.equals(KEY_PREF_BTN_COLOR)) {
                    Log.d(DEBUGMSG, sharedPreferences.getString(s, ""));
                }
                else if(s.equals(KEY_PREF_BACKGROUND)) {
                    Log.d(DEBUGMSG, sharedPreferences.getString(s, ""));
                }
                else if(s.equals(KEY_PREF_VOLUME)) {
                    Log.d(DEBUGMSG, sharedPreferences.getString(s, ""));
                }
            }
        };
        sharedPref.registerOnSharedPreferenceChangeListener(listener);



>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
=======
>>>>>>> parent of 4ad417e... Preference listener done
        //get size of screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int screenWidth = size.x;
        final int screenHeight = size.y;

        // Connection to server
        //connect();

<<<<<<< HEAD
        setContentView(R.layout.activity_main);

=======
        // Attempt to draw EdgeEffect on view
        //setContentView(new DrawDemo(this));
        setContentView(R.layout.activity_main);

        // Controller buttons
>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
        final ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        final ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        final ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        final ImageButton button4 = (ImageButton) findViewById(R.id.button4);

<<<<<<< HEAD
        //radio buttons
//        final RadioButton radio1 = (RadioButton) findViewById(R.id.radioButton1);
//        final RadioButton radio2 = (RadioButton) findViewById(R.id.radioButton2);
//        final RadioButton radio3 = (RadioButton) findViewById(R.id.radioButton3);

        //setContentView(R.layout.main);
        final RelativeLayout textView = (RelativeLayout)findViewById(R.id.joystickLayout);
        // this is the view on which you will listen for touch events
        final View touchView = findViewById(R.id.joystickLayout);

        final RelativeLayout bgElement = (RelativeLayout) findViewById(R.id.background);

        //radio controls
        /*
        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgElement.setBackgroundResource(R.drawable.nes_controller);
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgElement.setBackgroundResource(R.drawable.xbox);
            }
        });


        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgElement.setBackgroundResource(R.drawable.wood_texture);
            }
        });
        */

=======
        final Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        final Button startBtn = (Button) findViewById(R.id.startBtn);
        final Button selectBtn = (Button) findViewById(R.id.selectBtn);

        final View touchView = findViewById(R.id.joystickLayout);

>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
        //joystick controls
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (System.currentTimeMillis() - time > 100) {

<<<<<<< HEAD
                    //textView.setText("Touch coordinates : " +
                    //        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
                    //float x = (event.getRawX() - 250);
                    //float y = (event.getRawY() - 250);
                    float x = (event.getRawX()-(screenWidth/5));
                    float y = (event.getRawY()-(screenHeight/2))*-1;
=======
                    float x = (event.getRawX() - (screenWidth / 5));
                    float y = (event.getRawY() - (screenHeight / 2)) * -1;
>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73

                    Log.d(DEBUGMSG, "(" + Float.toString(x) + "," + Float.toString(y) + ")");
                    sendMovement(x, y);
                    time = System.currentTimeMillis();
                }
                return true;
            }
        });

<<<<<<< HEAD
=======


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

>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
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


<<<<<<< HEAD
    //stick listeners


=======
    public void loadSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void displayAndroidBluetoothMenu(View view) {
        // Display a dialog box when device is not connected to bluetooth host
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No bluetooth connection");
        builder.setMessage("Please connect to a bluetooth host before playing");

        builder.setPositiveButton(R.string.alert_connect, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent androidBluetoothMenu = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(androidBluetoothMenu);
            }
        });

        builder.setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }




    //stick listeners
>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
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

<<<<<<< HEAD
        Log.d(DEBUGMSG, event.toString());
        //position resources COMMENT OUT FOR SENSOR CHANGES
        /*
        if (System.currentTimeMillis()-positionTimer > 200) {
            sendOrientation(event.values[0], event.values[1], event.values[2]);
            // get the angle around the z-axis rotated
            positionTimer = System.currentTimeMillis();
        }
        */

        /*if (System.currentTimeMillis()-time > 500){
            send2dMovement("stop");
        }*/
    }

=======
    }


>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    void sendFire(String input, String state) {
        // get the angle around the z-axis rotated
        Log.d(DEBUGMSG, "Input: " + input + ", State: " + state);
<<<<<<< HEAD
        /*
        try {

            JSONObject message = new JSONObject(new String("{state: " + state + "}"));

            if (socket.isConnected()) {
                socket.emit(input, message);
                //Log.i("AIPSERVER", "Message sent to server: fire!");
                System.out.println(input);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }
=======

    }

<<<<<<< HEAD

>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73

=======
>>>>>>> parent of 4ad417e... Preference listener done
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
<<<<<<< HEAD
        /*
        try {
            String messageContent = new String("{x: "+x+ ",y: " + y + "}");

            JSONObject message = new JSONObject(messageContent);

            if (socket.isConnected()) {
                socket.emit("joystick", message);
                //Log.i("AIPSERVER", "Message sent to server: " + message.getString("movement"));
                System.out.println("X: " + x + " Y: " + y);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }
    /*
    void connect() {

        socket = new IOSocket("http://aipservers.com:3000", new MessageCallback() {

            @Override
            public void onMessage(String message) {
                // Handle simple messages
            }

            @Override
            public void onConnect() {
                // Socket connection opened
            }

            @Override
            public void onDisconnect() {
                // Socket connection closed
            }

            @Override
            public void on(String event, JSONObject... data) {

            }

            @Override
            public void onMessage(JSONObject json) {

            }

            @Override
            public void onConnectFailure() {

            }
        });

        socket.connect();
    }
    */
=======

    }
>>>>>>> 885b9c0c8f74af190a459dd1ff5abb1bc01cbe73

}

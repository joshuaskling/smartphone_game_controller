package edu.csumb.gamecontroller;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
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
import android.widget.Toast;
//import android.support.v4.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements SensorEventListener {


    private IOSocket socket;
    private SensorManager mSensorManager;
    static long time = System.currentTimeMillis();
    //long positionTimer = System.currentTimeMillis();
    public static Vibrator vibrator;
    public LinearLayout mLinearLayout;
    private JoystickView leftJoystick;
    private RelativeLayout leftDPad;
    private RelativeLayout leftButtonLayout;
    private JoystickView rightJoystick;
    private RelativeLayout rightDPad;
    private RelativeLayout rightButtonLayout;
    private JSONObject controler_status;

    private boolean leftJoystickVisible = true;
    private boolean rightJoystickVisble = false;

    public Intent mServiceIntent;
    public SharedPreferences prefs;
    public SharedPreferences.OnSharedPreferenceChangeListener listener;

    // Log tag
    public final String DEBUGMSG = "MainActivity";

    // Preference keys
    public final String KEY_PREF_BTN_SIZE = "button_size_preference";
    public final String KEY_PREF_BTN_COLOR = "button_color_preference";
    public final String KEY_PREF_BACKGROUND = "background_preference";
    public final String KEY_PREF_VOLUME = "volume_preference";
    public final String KEY_PREF_CTRL_POS = "layout_preference";
    public final String KEY_PREF_STICK_LAYOUT = "layout_stick";
    public final String KEY_PREF_BTN_STYLE = "button_style";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout = new LinearLayout(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

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

        // Listens to changes in settings menu
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            // Detects changes in Preference values
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals(KEY_PREF_CTRL_POS)) {
                    Log.d(DEBUGMSG, "Control Position changed");
                    swapJoystickAndButtons(mLinearLayout);
                }
                else if(s.equals(KEY_PREF_STICK_LAYOUT)) {
                    Log.d(DEBUGMSG, "Stick layout changed");
                    swapJoystickControl(mLinearLayout);
                }
                else if(s.equals(KEY_PREF_BTN_STYLE)) {
                    Log.d(DEBUGMSG, sharedPreferences.getString(s, ""));
                }
                else if(s.equals(KEY_PREF_BTN_SIZE)) {
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
        prefs.registerOnSharedPreferenceChangeListener(listener);

        // Initialize payload of controller status
        controler_status = new JSONObject();
        try {
            controler_status.put("x", 0);
            controler_status.put("y", 0);
            controler_status.put("btn1", false);
            controler_status.put("btn2", false);
            controler_status.put("btn3", false);
            controler_status.put("btn4", false);
            controler_status.put("startBtn", false);
            controler_status.put("selectBtn", false);
        }
        catch(JSONException error) {
            Log.d(DEBUGMSG, error.getMessage());
        }

        // References controls on both sides of the app
        leftJoystick = (JoystickView) findViewById(R.id.leftJoystickView);
        leftDPad = (RelativeLayout) findViewById(R.id.leftDpadView);
        leftButtonLayout = (RelativeLayout) findViewById(R.id.leftButtonLayout);
        rightJoystick = (JoystickView) findViewById(R.id.rightJoystickView);
        rightDPad = (RelativeLayout) findViewById(R.id.rightDpadView);
        rightButtonLayout = (RelativeLayout) findViewById(R.id.rightButtonLayout);

        leftJoystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(double x, double y) {
                // TODO Auto-generated method stub
                Log.d(DEBUGMSG, String.valueOf(x) + ","  +String.valueOf(y));
                try {
                    controler_status.put("x", x);
                    controler_status.put("y", y);
                }
                catch(JSONException error) {
                    Log.d(DEBUGMSG, "Left Joystick: " + error.getMessage());
                }
                sendControllerStatus();
                Log.d(DEBUGMSG, controler_status.toString());

            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);

        rightJoystick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(double x, double y) {
                Log.d(DEBUGMSG, String.valueOf(x) + ","  +String.valueOf(y));

                try {
                    controler_status.put("x", x);
                    controler_status.put("y", y);
                }
                catch(JSONException error) {
                    Log.d(DEBUGMSG, "Right Joystick: " + error.getMessage());
                }
                sendControllerStatus();
            }
        }, JoystickView.DEFAULT_LOOP_INTERVAL);

        // Left controller buttons
        final ImageButton button1 = (ImageButton) findViewById(R.id.r_button1);
        final ImageButton button2 = (ImageButton) findViewById(R.id.r_button2);
        final ImageButton button3 = (ImageButton) findViewById(R.id.r_button3);
        final ImageButton button4 = (ImageButton) findViewById(R.id.r_button4);

        //Right controller buttons
        final ImageButton r_button1 = (ImageButton) findViewById(R.id.l_button1);
        final ImageButton r_button2 = (ImageButton) findViewById(R.id.l_button2);
        final ImageButton r_button3 = (ImageButton) findViewById(R.id.l_button3);
        final ImageButton r_button4 = (ImageButton) findViewById(R.id.l_button4);

        final Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        final Button startBtn = (Button) findViewById(R.id.startBtn);
        final Button selectBtn = (Button) findViewById(R.id.selectBtn);

        leftDPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (System.currentTimeMillis() - time > 100) {

                    // Used integer precision to conform to JoysitckView output
                    int x = (int)(event.getRawX() - (screenWidth / 5));
                    int y = (int)(event.getRawY() - (screenHeight / 2)) * -1;

                    try {
                        controler_status.put("x", x);
                        controler_status.put("y", y);
                    }
                    catch(JSONException error) {
                        Log.d(DEBUGMSG, "Left DPad: " + error.getMessage());
                    }

                    Log.d(DEBUGMSG, "(" + Integer.toString(x) + "," + Integer.toString(y) + ")");
                    sendControllerStatus();
                    time = System.currentTimeMillis();
                }
                return true;
            }
        });

        rightDPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (System.currentTimeMillis() - time > 100) {

                    int x = (int)(event.getRawX() - (screenWidth / 5));
                    int y = (int)(event.getRawY() - (screenHeight / 2)) * -1;

                    try {
                        controler_status.put("x", x);
                        controler_status.put("y", y);
                    }
                    catch(JSONException error) {
                        Log.d(DEBUGMSG, "Right DPad: " + error.getMessage());
                    }

                    Log.d(DEBUGMSG, "(" + Integer.toString(x) + "," + Integer.toString(y) + ")");
                    sendControllerStatus();
                    time = System.currentTimeMillis();
                }
                return true;

            }
        });

        startBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(DEBUGMSG, "Start btn touched");
                Intent mIntent = new Intent(MainActivity.this, BluetoothActivity.class);
                startActivity(mIntent);
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
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                Log.d(DEBUGMSG, "btn 1 down");
                                try {
                                    controler_status.put("btn1", true);
                                }
                                catch(JSONException error) {
                                    Log.d(DEBUGMSG, "Left btn1: " + error.getMessage());
                                }
                                sendControllerStatus();
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                Log.d(DEBUGMSG, "btn 1 up");
                                try {
                                    controler_status.put("btn1", false);
                                }
                                catch(JSONException error) {
                                    Log.d(DEBUGMSG, "Left btn1: " + error.getMessage());
                                }
                                sendControllerStatus();
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
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                Log.d(DEBUGMSG, "btn 2 down");
                                try {
                                    controler_status.put("btn2", true);
                                }
                                catch(JSONException error) {
                                    Log.d(DEBUGMSG, "Left btn2: " + error.getMessage());
                                }
                                sendControllerStatus();
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                Log.d(DEBUGMSG, "btn 2 up");
                                try {
                                    controler_status.put("btn2", false);
                                }
                                catch(JSONException error) {
                                    Log.d(DEBUGMSG, "Left btn2: " + error.getMessage());
                                }
                                sendControllerStatus();
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
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                Log.d(DEBUGMSG, "btn 3 down");
                                try {
                                    controler_status.put("btn3", true);
                                }
                                catch(JSONException error) {
                                    Log.d(DEBUGMSG, "Left btn3: " + error.getMessage());
                                }
                                sendControllerStatus();
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                Log.d(DEBUGMSG, "btn 3 up");
                                try {
                                    controler_status.put("btn3", false);
                                }
                                catch(JSONException error) {
                                    Log.d(DEBUGMSG, "Left btn3: " + error.getMessage());
                                }
                                sendControllerStatus();
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
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN: {
                                Log.d(DEBUGMSG, "btn 4 down");
                                try {
                                    controler_status.put("btn4", true);
                                }
                                catch(JSONException error) {
                                    Log.d(DEBUGMSG, "Left btn4: " + error.getMessage());
                                }
                                sendControllerStatus();
                                break;
                            }
                            case MotionEvent.ACTION_UP: {
                                Log.d(DEBUGMSG, "btn 4 up");
                                try {
                                    controler_status.put("btn4", false);
                                }
                                catch(JSONException error) {
                                    Log.d(DEBUGMSG, "Left btn4: " + error.getMessage());
                                }
                                sendControllerStatus();
                                break;
                            }
                        }
                        return true;
                    }
                }
        );

        r_button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.d(DEBUGMSG, "btn 1 down");
                        try {
                            controler_status.put("btn1", true);
                        }
                        catch(JSONException error) {
                            Log.d(DEBUGMSG, "Right btn1: " + error.getMessage());
                        }
                        sendControllerStatus();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d(DEBUGMSG, "btn 1 up");
                        try {
                            controler_status.put("btn1", false);
                        }
                        catch(JSONException error) {
                            Log.d(DEBUGMSG, "Right btn1: " + error.getMessage());
                        }
                        sendControllerStatus();
                        break;
                    }
                }
                return true;
            }
        });
        r_button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.d(DEBUGMSG, "btn 2 down");
                        try {
                            controler_status.put("btn2", true);
                        }
                        catch(JSONException error) {
                            Log.d(DEBUGMSG, "Right btn2: " + error.getMessage());
                        }
                        sendControllerStatus();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d(DEBUGMSG, "btn 2 up");
                        try {
                            controler_status.put("btn2", false);
                        }
                        catch(JSONException error) {
                            Log.d(DEBUGMSG, "Right btn2: " + error.getMessage());
                        }
                        sendControllerStatus();
                        break;
                    }
                }
                return true;
            }
        });
        r_button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.d(DEBUGMSG, "btn 3 down");
                        try {
                            controler_status.put("btn3", true);
                        }
                        catch(JSONException error) {
                            Log.d(DEBUGMSG, "Right btn3: " + error.getMessage());
                        }
                        sendControllerStatus();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d(DEBUGMSG, "btn 3 up");
                        try {
                            controler_status.put("btn3", false);
                        }
                        catch(JSONException error) {
                            Log.d(DEBUGMSG, "Right btn3: " + error.getMessage());
                        }
                        sendControllerStatus();
                        break;
                    }
                }
                return true;
            }
        });
        r_button4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Log.d(DEBUGMSG, "btn 4 down");
                        try {
                            controler_status.put("btn4", true);
                        }
                        catch(JSONException error) {
                            Log.d(DEBUGMSG, "Right btn4: " + error.getMessage());
                        }
                        sendControllerStatus();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Log.d(DEBUGMSG, "btn 4 up");
                        try {
                            controler_status.put("btn4", false);
                        }
                        catch(JSONException error) {
                            Log.d(DEBUGMSG, "Right btn4: " + error.getMessage());
                        }
                        sendControllerStatus();
                        break;
                    }
                }
                return true;
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Load Settings values
        String controlPositionPref = prefs.getString(KEY_PREF_CTRL_POS, "");
        String stickPositionPref = prefs.getString(KEY_PREF_STICK_LAYOUT, "");
        if(controlPositionPref.equals("Buttons, Stick")) {
            swapJoystickAndButtons(mLinearLayout);
        }
        if(stickPositionPref.equals("DPad")) {
            swapJoystickControl(mLinearLayout);
        }
    }

    // Display settings activity
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

    // Sends status of controller to Bluetooth
    public void sendControllerStatus() {
        mServiceIntent = new Intent(MainActivity.this, SendActivity.class);
        mServiceIntent.putExtra("payload", controler_status.toString());
        MainActivity.this.startService(mServiceIntent);
    }

    // Swaps joystick or DPad
    public void swapJoystickControl(View view) {

        if(leftButtonLayout.getVisibility() == View.GONE) {
            if(leftJoystick.getVisibility() == View.VISIBLE) {
                leftJoystick.setVisibility(View.GONE);
                leftDPad.setVisibility(View.VISIBLE);
                leftJoystickVisible = false;
            }
            else {
                leftJoystick.setVisibility(View.VISIBLE);
                leftDPad.setVisibility(View.GONE);
                leftJoystickVisible = true;
            }
        }
        else if(rightButtonLayout.getVisibility() == View.GONE) {
            if(rightJoystick.getVisibility() == View.VISIBLE) {
                rightJoystick.setVisibility(View.GONE);
                rightDPad.setVisibility(View.VISIBLE);
                rightJoystickVisble = false;
            }
            else {
                rightJoystick.setVisibility(View.VISIBLE);
                rightDPad.setVisibility(View.GONE);
                rightJoystickVisble = true;
            }
        }
    }

    // Inverses controls
    public void swapJoystickAndButtons(View view) {

        if(rightButtonLayout.getVisibility() == View.VISIBLE) {
            leftJoystick.setVisibility(View.GONE);
            leftDPad.setVisibility(View.GONE);
            leftButtonLayout.setVisibility(View.VISIBLE);

            rightButtonLayout.setVisibility(View.GONE);
            if(leftJoystickVisible) {
                rightJoystick.setVisibility(View.VISIBLE);
                rightDPad.setVisibility(View.GONE);
                rightJoystickVisble = true;
            }
            else {
                rightJoystick.setVisibility(View.GONE);
                rightDPad.setVisibility(View.VISIBLE);
                rightJoystickVisble = false;
            }

        }
        else if(leftButtonLayout.getVisibility() == View.VISIBLE) {
            rightJoystick.setVisibility(View.GONE);
            rightDPad.setVisibility(View.GONE);
            rightButtonLayout.setVisibility(View.VISIBLE);

            leftButtonLayout.setVisibility(View.GONE);
            if(rightJoystickVisble) {
                leftJoystick.setVisibility(View.VISIBLE);
                leftDPad.setVisibility(View.GONE);
                leftJoystickVisible = true;
            }
            else {
                leftJoystick.setVisibility(View.GONE);
                leftDPad.setVisibility(View.VISIBLE);
                leftJoystickVisible = false;
            }
        }

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

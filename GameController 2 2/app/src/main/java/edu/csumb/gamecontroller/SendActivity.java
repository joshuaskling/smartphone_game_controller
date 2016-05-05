package edu.csumb.gamecontroller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sean Gardner on 4/8/2016.
 */
public class SendActivity extends IntentService {
    private BluetoothSocket mmSocket;
    EditText mEdit;
    private OutputStream mmOutStream;
    private boolean sent = false;
    private boolean start = false;
    BluetoothDevice mmDevice;
    //try to change to final
    byte[] bytes  = new byte[1024];
    public static UUID MY_UUID;
    String s;
    String tag = "debugging";
    protected static final int MESSAGE_READ = 1;
    protected static final int MESSAGE_SEND = 2;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.i(tag, "in handler");
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String string = new String(readBuf);
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                    Log.i(tag, "msg");
                    break;

                case MESSAGE_SEND:
                    try
                    {
                        Log.i(tag, msg.toString());
                        bytes = msg.toString().getBytes("UTF-8");;
                        mmOutStream.write(bytes);
                    } catch (IOException e)
                    {

                    }
                    break;
            }
        }
    };
    public SendActivity() {
        super("SendActivity");
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    JSONObject jsonObj;
    public final String DEBUGMSG = "SendActivity";

    @Override
    protected void onHandleIntent(Intent intent)
    {
        // Get data from the incoming intent
        String controllerStatus = intent.getDataString();
        try {
            jsonObj = new JSONObject(intent.getStringExtra("product"));
        }
        catch(JSONException error) {
            Log.d(DEBUGMSG, "Left btn1: " + error.getMessage());
        }
        Log.d("SendActivity", controllerStatus);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped
        Log.i(tag, "test-send-4");
        if (start)
        {
            Log.i(tag, "test-2");

            s = "test send-2";
            try
            {
                Log.i(tag, s);
                bytes = s.getBytes();
                mmOutStream.write(bytes);

            }
            catch (IOException e)
            {
                e.getMessage();
                Log.i(tag, e.getMessage());
            }
            // mHandler.obtainMessage(MESSAGE_SEND, s).sendToTarget();
        }
        else
        {
            Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
            Log.i(tag, "test-1");
            Bundle extras = intent.getExtras();
            MY_UUID = UUID.fromString(extras.getString("uuid"));
            mmDevice = extras.getParcelable("bluedivice");
            try {
                mmSocket = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
                mmSocket.connect();

            } catch (IOException e) {
                Log.i(tag, "get socket failed");
            }
            Log.i(tag, "test-3");
            OutputStream tmpOut = null;
            try
            {
                mmOutStream = mmSocket.getOutputStream();
                Log.i(tag, "test-Out-success");
                s = "test send-3";
                bytes = s.getBytes();
                mmOutStream.write(s.getBytes(),0,s.getBytes().length);
            }
            catch (IOException e)
            {
                Log.i(tag, "test-Out-fail");
                Log.i(tag, bytes.toString());
                Log.i(tag, e.getMessage());
            }
            start = true;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, " Destroyed", Toast.LENGTH_LONG).show();
        Log.i(tag, "test-send-end");

    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        Log.i(tag, "test-send-6");

        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        Log.i(tag, "test-send-7");

        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}
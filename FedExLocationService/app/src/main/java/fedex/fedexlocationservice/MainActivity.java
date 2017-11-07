package fedex.fedexlocationservice;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.arubanetworks.meridian.Meridian;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public String address= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configure Meridian
        Meridian.configure(this);
        //mBTDevices = new ArrayList<>();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth Not Active");
            builder.setMessage("Please enable Bluetooth");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(enableBtIntent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
        mBluetoothAdapter.startDiscovery();
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }

    /*Handler h = new Handler();
    int delay = 15000; //15 seconds
    Runnable runnable;
    @Override
    protected void onResume() {
        //start handler as activity become visible
        h.postDelayed(new Runnable() {
            public void run() {
                IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(mBroadcastReceiver, discoverDevicesIntent);
                showID();
                runnable=this;
                h.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }*/

    /* Probably don't need a pause, but just in case
    @Override
    protected void onPause() {
        h.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }*/

    /** Called when the user taps the button */
    public void generateID() {
        Generator generator = new Generator();
        // Create 20byte sequence
        String ID = generator.generateID();

        // Set the view
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(ID);
        ConstraintLayout bg = (ConstraintLayout)findViewById(R.id.bg);

        // Vibrate
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

        //Set colors to change
        int switchColor = generator.bgColor(ID);
        int switchTextColor = generator.textColor(switchColor);
        // Change colors
        bg.setBackgroundColor(switchColor);
        textView.setTextColor(switchTextColor);
    }

    /*private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                address = device.getAddress();
                //mBTDevices.add(device);
            }
        }
    };*/

    /** Show location ID automatically **/
    public void showID() {
        //bluetoothSetup btSetup = new bluetoothSetup();
        XMLparser parser = new XMLparser();

        String campaignID = address;
        String ID = parser.getLocationID(campaignID);

        // Set the view
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(ID);
        //ConstraintLayout bg = (ConstraintLayout)findViewById(R.id.bg);

        // Vibrate
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }
}

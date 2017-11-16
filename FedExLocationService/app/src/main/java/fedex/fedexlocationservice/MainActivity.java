package fedex.fedexlocationservice;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.campaigns.CampaignBroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configure Meridian
        Meridian.configure(this);
        // It seems bluetooth does not need to be active
        /*BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
        }*/
        // Activate location services
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
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
        final XMLparser parser = new XMLparser();
        CampaignBroadcastReceiver mBroadcastReceiver = new CampaignBroadcastReceiver() {
            @Override
            protected void onReceive(Context context, Intent intent, String s, String s1) {
                final String campaignID = getCampaignId(intent);
                final String ID = parser.getLocationID(campaignID);
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(ID);
                //ConstraintLayout bg = (ConstraintLayout)findViewById(R.id.bg);

                // Vibrate
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);
            }
        };
    }

    /**
     * Called when the user taps the button
     * NOT NEEDED, WILL ELIMINATE LATER
     */
    public void generateID() {
        Generator generator = new Generator();
        // Create 20byte sequence
        String ID = generator.generateID();

        // Set the view
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(ID);
        ConstraintLayout bg = (ConstraintLayout) findViewById(R.id.bg);

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
}


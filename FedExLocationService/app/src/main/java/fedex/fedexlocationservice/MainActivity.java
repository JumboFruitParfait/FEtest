package fedex.fedexlocationservice;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.campaigns.CampaignsService;
import com.arubanetworks.meridian.editor.EditorKey;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configure Meridian
        Meridian.configure(this);
        // Activate Bluetooth
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
        //CampaignsService monitor = new CampaignsService();
        EditorKey appKey = EditorKey.forApp("5427610480279552");
        //monitor.startMonitoring(this, appKey);
        CampaignsService.startMonitoring(MainActivity.this, appKey);
        CampaignReceiver recv = new CampaignReceiver();
        //IntentFilter intentFilter = new IntentFilter();
        //registerReceiver(recv, intentFilter);
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


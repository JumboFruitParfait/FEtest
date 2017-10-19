package fedex.fedexlocationservice;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.arubanetworks.meridian.Meridian;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configure Meridian
        Meridian.configure(this);
    }

    /** Called when the user taps the button */
    public void generateID(View view) {
        Generator generator = new Generator();
        // Create 20byte sequence
        String ID = generator.generateID();

        // Get beacon info
        generator.beaconID();

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
}

package com.arubanetworks.meridiansamples;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.directions.DirectionsDestination;
import com.arubanetworks.meridian.maps.directions.DirectionsSource;

/**
 * Demonstrates the use of the directions API to request a route and display the steps as text.
 */

public class DirectionsActivity extends AppCompatActivity {

    public static final String APP_KEY = "meridian.AppKey";
    public static final String DIRECTIONS_SOURCE = "meridian.DirectionsSource";
    public static final String DIRECTIONS_DESTINATION = "meridian.DirectionsDestination";

    public static Intent createIntent(Context context, EditorKey appKey, DirectionsSource source, DirectionsDestination destination) {
        return new Intent(context, DirectionsActivity.class)
                .putExtra(APP_KEY, appKey)
                .putExtra(DIRECTIONS_SOURCE, source)
                .putExtra(DIRECTIONS_DESTINATION, destination);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditorKey appKey = (EditorKey) getIntent().getSerializableExtra(APP_KEY);
        DirectionsSource source = (DirectionsSource) getIntent().getSerializableExtra(DIRECTIONS_SOURCE);
        DirectionsDestination destination = (DirectionsDestination) getIntent().getSerializableExtra(DIRECTIONS_DESTINATION);

        if (savedInstanceState == null) {
            MapFragment mapFragment = new MapFragment.Builder()
                    .setAppKey(appKey)
                    .setSource(source)
                    .setDestination(destination)
                    .build();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, mapFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // this will pass through to our map fragment
        super.onActivityResult(requestCode, resultCode, data);
    }

}

package com.arubanetworks.meridiansamples;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.editor.Placemark;
import com.arubanetworks.meridian.internal.util.Strings;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianLocationManager;
import com.arubanetworks.meridian.maps.directions.DirectionsDestination;
import com.arubanetworks.meridian.maps.directions.DirectionsSource;
import com.arubanetworks.meridian.search.LocalSearch;
import com.arubanetworks.meridian.search.LocalSearchResponse;
import com.arubanetworks.meridian.search.LocalSearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the use of the search API to list nearby placemarks.
 */

public class NearbyFragment extends android.support.v4.app.ListFragment
        implements SearchView.OnQueryTextListener, LocalSearch.LocalSearchListener, MeridianLocationManager.LocationUpdateListener {

    private static final String APP_KEY_KEY = "meridian.AppKey";

    private static final String TAG = "NearbyFragment";
    private EditorKey appKey;
    private List<LocalSearchResult> results = new ArrayList<>();
    private LocalSearch localSearch;
    private ProgressBar spinner;
    private MeridianLocationManager locationHelper;
    private ArrayAdapter<LocalSearchResult> adapter;

    /**
     * Constructs a NearbyFragment for the given Meridian AppKey.
     */
    public static NearbyFragment newInstance(@NonNull EditorKey appKey) {
        NearbyFragment nf = new NearbyFragment();
        Bundle b = nf.getArguments();
        if (b == null) b = new Bundle();
        b.putSerializable(APP_KEY_KEY, appKey);
        nf.setArguments(b);
        return nf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appKey = (EditorKey) getArguments().getSerializable(APP_KEY_KEY);

        // init meridian location
        locationHelper = new MeridianLocationManager(getContext(), appKey, this);

        adapter = new ArrayAdapter<LocalSearchResult>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, results) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                LocalSearchResult result = results.get(position);

                String timeString = "";
                double seconds = (double) result.getTime() / 1000d;
                if (seconds > 0) {
                    if (seconds >= 60)
                        timeString = String.format("%.0f min", seconds / 60);
                    else
                        timeString = String.format("%.0f sec", seconds);
                }

                Placemark placemark = results.get(position).getPlacemark();
                if (!Strings.isNullOrEmpty(placemark.getName()))
                    text1.setText(placemark.getName());
                else
                    text1.setText(placemark.getTypeName());
                text2.setText(timeString);
                return view;
            }
        };

        setListAdapter(adapter);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_nearby, container, false);

        spinner = (ProgressBar) layout.findViewById(R.id.progress_bar);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (locationHelper != null) {
            locationHelper.startListeningForLocation();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationHelper != null) {
            locationHelper.stopListeningForLocation();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nearby, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.query_hint));
        searchView.setOnQueryTextListener(this);
    }

    private String currentSearchTerm;

    @Override
    public boolean onQueryTextChange(String s) {

        currentSearchTerm = s;

        // kick off a new search query
        updateNearby(currentSearchTerm, locationHelper != null ? locationHelper.getLastLocation() : null);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        LocalSearchResult result = results.get(position);
        DirectionsSource testSource = DirectionsSource.forPlacemarkKey(results.get(results.size() - 1).getPlacemark().getKey());
        DirectionsDestination destination = DirectionsDestination.forPlacemarkKey(result.getPlacemark().getKey());
        startActivity(DirectionsActivity.createIntent(getActivity(), appKey, testSource, destination));
    }

    private MeridianLocation lastLocation;

    private void updateNearby(String term, MeridianLocation location) {

        if (location == null || location.getMapKey() == null)
            return;

        if (localSearch != null)
            localSearch.cancel();

        spinner.setVisibility(View.VISIBLE);

        localSearch = new LocalSearch.Builder()
                .setQuery(term)
                .setLimit(10)
                .setApp(appKey)
                .setLocation(location)
                .setListener(this)
                .build();

        localSearch.start();

        lastLocation = location;
    }

    @Override
    public void onLocationUpdate(MeridianLocation location) {
        if (location == null)
            return;
        if (lastLocation != null) {
            double distanceToNewLocation = lastLocation.distanceTo(location);
            if (lastLocation != null && distanceToNewLocation >= 0 && distanceToNewLocation < 10)
                return;
        }
        updateNearby(currentSearchTerm, location);
    }

    @Override
    public void onLocationError(Throwable tr) {

    }

    @Override
    public void onEnableBluetoothRequest() {

    }

    @Override
    public void onEnableGPSRequest() {

    }

    @Override
    public void onEnableWiFiRequest() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSearchComplete(LocalSearchResponse response) {

        spinner.setVisibility(View.INVISIBLE);

        adapter.clear();
        adapter.addAll(response.getResults());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchError(Throwable tr) {
        spinner.setVisibility(View.INVISIBLE);

        Log.e(TAG, "search error: " + tr);
    }
}

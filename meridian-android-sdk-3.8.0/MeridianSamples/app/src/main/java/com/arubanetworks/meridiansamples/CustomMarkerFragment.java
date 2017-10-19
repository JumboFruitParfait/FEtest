package com.arubanetworks.meridiansamples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.editor.Placemark;
import com.arubanetworks.meridian.log.MeridianLogger;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapInfo;
import com.arubanetworks.meridian.maps.Marker;
import com.arubanetworks.meridian.maps.PlacemarkMarker;
import com.arubanetworks.meridian.maps.Transaction;

import java.util.ArrayList;
import java.util.Random;


public class CustomMarkerFragment extends MapFragment {

    private static final MeridianLogger LOG = MeridianLogger.forTag("TEST").andFeature(MeridianLogger.Feature.MAPS);

    public static CustomMarkerFragment newInstance(EditorKey mapKey){
        CustomMarkerFragment f = new CustomMarkerFragment();
        MapFragment mapFragment = new MapFragment.Builder()
                .setMapKey(mapKey)
                .build();
        f.setArguments(mapFragment.getArguments());
        return f;
    }

    @Override
    public Marker markerForPlacemark(Placemark placemark) {
        Context c = getActivity();
        if(c == null)return null;
        if(placemark.getType().contains("lounge")){
            PlacemarkMarker pm = new PlacemarkMarker.Builder(c, placemark)
                    //.setIcon(R.drawable.my_icon) // use your custom icon
                    //.setIconColor(R.color.my_color) // sets the background color
                    //.setIconPadding(R.dimen.my_padding) // sets the padding for the icon
                    .build();
            //pm.setWeight(2f); // Make lounges show up on top of other placemarks.
            //pm.setCollision(false); // Exclude lounges from collision checks.
            //pm.setScaleFixedToMap(true);// Make lounges with fixed scale on the map surface.
            return pm;
        }
        return super.markerForPlacemark(placemark);
    }

    @Override
    public void onMapRenderFinish() {
        super.onMapRenderFinish();
        // Remove an existing marker named lobby.
        for (Marker m : getMapView().getAllMarkers()) {
            if (m instanceof PlacemarkMarker && ((PlacemarkMarker) m).getPlacemark().getName().equalsIgnoreCase("lobby")) {
                getMapView().commitTransaction(new Transaction.Builder().setType(Transaction.Type.REMOVE).addMarker(m).setListener(new Transaction.Listener() {
                    @Override
                    public void onTransactionAnimationComplete() {
                        LOG.d("Animation complete removal.");
                    }

                    @Override
                    public void onTransactionComplete() {
                        LOG.d("Completed removal.");
                    }

                    @Override
                    public void onTransactionCanceled() {
                        LOG.d("Canceled removal.");
                    }

                    @Override
                    public void onTransactionFailed() {
                        LOG.d("Failed removal.");
                    }
                }).build());
                break;
            }
        }

        // Create 20 random Markers
        Random random = new Random(18923501986340l);
        ArrayList<Marker> markerList = new ArrayList<>();
        for(int i=0;i<20;i++) {
            Context c = getActivity();
            if (c == null) break;
            RandMarker marker = new RandMarker(getActivity(), getMapView().getMapInfo(), random);
            marker.setName("Custom marker " + i);
            markerList.add(marker);
        }

        // Add them to the map the markers.
        getMapView().commitTransaction(new Transaction.Builder().setAnimationDuration(500).addMarkers(markerList).build());
    }

    /**
     * An class the extends Marker and displays a copy of the apps launcher icon to use as an
     * example.
     */
    private static class RandMarker extends Marker{

        private final Context appContext;
        public RandMarker(@NonNull Context context, @NonNull MapInfo mapInfo, @NonNull Random random){
            super(random.nextInt((int)mapInfo.getWidth()),random.nextInt((int)mapInfo.getHeight()));
            this.appContext = context.getApplicationContext();
            setWeight(2.1f);
        }
        @Override
        public Bitmap getBitmap() {
            return BitmapFactory.decodeResource(appContext.getResources(), R.drawable.ic_launcher);
        }
    }
}

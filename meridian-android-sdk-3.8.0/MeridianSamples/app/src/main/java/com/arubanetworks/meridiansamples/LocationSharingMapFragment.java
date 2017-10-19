package com.arubanetworks.meridiansamples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.webkit.URLUtil;

import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.internal.util.Dev;
import com.arubanetworks.meridian.location.LocationRequest;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.locationsharing.Friend;
import com.arubanetworks.meridian.locationsharing.LocationSharing;
import com.arubanetworks.meridian.locationsharing.LocationSharingException;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;
import com.arubanetworks.meridian.maps.Marker;
import com.arubanetworks.meridian.maps.PlacemarkMarker;
import com.arubanetworks.meridian.maps.Transaction;
import com.arubanetworks.meridian.maps.directions.Directions;
import com.arubanetworks.meridian.maps.directions.DirectionsDestination;
import com.arubanetworks.meridian.maps.directions.DirectionsSource;
import com.arubanetworks.meridian.maps.directions.Route;
import com.arubanetworks.meridiansamples.utils.CropCircleTransformation;
import com.arubanetworks.meridiansamples.utils.Lists;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings({"UnusedDeclaration", "ConstantConditions"})
public class LocationSharingMapFragment extends MapFragment implements MapView.DirectionsEventListener, MapView.MapEventListener {

    private static final String APP_KEY = "meridian.AppKey";
    private static final String MAP_KEY = "meridian.MapKey";
    private static final String ROUTE_KEY = "meridian.RouteKey";
    private static final String ROUTE_STEP_KEY = "meridian.RouteStepKey";
    private static final String ROUTE_REROUTE_KEY = "meridian.RouteReroute";
    private static final String FROM_KEY = "meridian.FromKey";
    private static final String PENDING_DESTINATION_KEY = "meridian.PendingDestinationKey";
    private static final String CONTROLS_OPTIONS_KEY = "meridian.mapOptions";
    private static final int SOURCE_REQUEST_CODE = "meridian.source_request".hashCode() & 0xFF;

    private MapView mapView;
    private MapOptions mapOptions = null;

    private EditorKey appKey;
    private EditorKey mapKey;

    private Route savedRoute;
    private int savedRouteStepIndex = 0;
    private boolean isRerouting = false;

    private Directions directions;
    private DirectionsSource fromItem;
    private DirectionsDestination pendingDestination;
    private LocationRequest locationRequest;

    /**
     * Friends
     **/
    private List<Friend> friendsList = new ArrayList<>();
    private List<LocationSharingMarker> friendsMarker = new ArrayList<>();

    Map<String, Marker> friendMarkers = new HashMap<>();
    Timer friendsTimer;
    TimerTask friendsTimerTask;

    @Override
    public void onResume() {
        super.onResume();

        if (LocationSharing.shared().isUploadingServiceRunning()) {
            friendsTimer = new Timer();
            friendsTimerTask = new TimerTask() {
                @Override
                public void run() {
                    LocationSharing.shared().getFriends(new LocationSharing.Callback<List<Friend>>() {
                        @Override
                        public void onSuccess(List<Friend> result) {
                            renderFriends(result);
                        }

                        @Override
                        public void onError(LocationSharingException lse) {
                            // TODO do something
                        }
                    });
                }
            };
            friendsTimer.schedule(friendsTimerTask, new Date(), 5000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (friendsTimer != null) {
            friendsTimer.cancel();
            friendsTimer = null;
        }

        if (friendsTimerTask != null) {
            friendsTimerTask.cancel();
            friendsTimerTask = null;
        }
    }

    private class LocationSharingMarker extends Marker {

        private static final int SIZE_AVATAR_DP = 40;

        private Context context;
        private Friend friend;
        private Bitmap bitmap;

        public LocationSharingMarker(Context context, Friend friend) {
            super(friend.getLocation().getX(), friend.getLocation().getY());
            this.friend = friend;
            this.context = context.getApplicationContext();
            setWeight(1.1f);
            setName(friend.getFullName());
        }

        public Friend getFriend() {
            return friend;
        }

        @Override
        public boolean getCollision() {
            return false;
        }

        public String getFriendId() {
            return friend.getKey();
        }

        @Override
        public Bitmap getBitmap() {
            if (bitmap != null) {
                return bitmap;
            }

            if (URLUtil.isValidUrl(friend.getPhotoUrl())) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getActivity().getApplicationContext())
                                .load(friend.getPhotoUrl())
                                .asBitmap()
                                .centerCrop()
                                .transform(new CropCircleTransformation(Glide.get(getActivity().getApplicationContext()).getBitmapPool(), Dev.get().dpToPix(2)))
                                .into(new SimpleTarget<Bitmap>(Math.round(Dev.get().dpToPix(SIZE_AVATAR_DP)), Math.round(Dev.get().dpToPix(SIZE_AVATAR_DP))) {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                        LocationSharingMarker.this.bitmap = bitmap;
                                        LocationSharingMarker.this.invalidate(true);
                                    }
                                });
                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap initialsBitmap = friend.getInitialsBitmap(Dev.get().dpToPix(SIZE_AVATAR_DP), Dev.get().dpToPix(SIZE_AVATAR_DP), Dev.get().dpToPix(24), getResources().getColor(R.color.mr_callout_blue));

                        // convert to byte array
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        initialsBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                        // draw
                        Glide.with(getActivity().getApplicationContext())
                                .fromBytes()
                                .load(stream.toByteArray())
                                .bitmapTransform(new CropCircleTransformation(Glide.get(getActivity().getApplicationContext()).getBitmapPool(), Dev.get().dpToPix(2)))
                                .into(new SimpleTarget<GlideDrawable>(Dev.get().dpToPix(SIZE_AVATAR_DP), Dev.get().dpToPix(SIZE_AVATAR_DP)) {
                                    @Override
                                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                        LocationSharingMarker.this.bitmap = ((GlideBitmapDrawable) drawable).getBitmap();
                                        LocationSharingMarker.this.invalidate(true);
                                    }
                                });
                    }
                });
            }

            // still loading
            return null;

        }
    }

    private void renderFriends(final List<Friend> friends) {

        if (mapView == null || mapView.getMapKey() == null || friends == null) {
            if (friendMarkers != null) friendMarkers.clear();
        }

        // remove friends who are gone or in another map
        List<Friend> friendsToRemove = Lists.filter(friendsList, new Lists.IPredicate<Friend>() {
            @Override
            public boolean apply(Friend friend) {
                return !friends.contains(friend) || !friend.isInSameMapAndSharing(mapView.getMapKey());
            }
        });
        List<Marker> markersToRemove = new ArrayList<>();
        for (Friend friend : friendsToRemove) {
            if (friendMarkers.get(friend.getKey()) != null) {
                markersToRemove.add(friendMarkers.get(friend.getKey()));
                friendMarkers.remove(friend.getKey());
            }
        }
        if (!markersToRemove.isEmpty()) {
            mapView.commitTransaction(new Transaction.Builder().setType(Transaction.Type.REMOVE).setAnimationDuration(0).addMarkers(markersToRemove).build());
        }
        friends.removeAll(friendsToRemove);

        // add the new friends
        List<Friend> friendsToAdd = Lists.filter(friends, new Lists.IPredicate<Friend>() {
            @Override
            public boolean apply(Friend friend) {
                return friendMarkers.get(friend.getKey()) == null && friend.isInSameMapAndSharing(mapView.getMapKey());
            }
        });
        for (Friend friend : friendsToAdd) {
            if (friend.getLocation().getMapKey().equals(mapView.getMapKey())) {
                friendMarkers.put(friend.getKey(), new LocationSharingMarker(getActivity(), friend));
            }
        }

        // update the existing friends with the location
        List<Friend> friendsToUpdate = Lists.filter(friends, new Lists.IPredicate<Friend>() {
            @Override
            public boolean apply(Friend friend) {
                return friendsList.contains(friend) && friend.isInSameMapAndSharing(mapView.getMapKey());
            }
        });
        for (Friend friend : friendsToUpdate) {
            friendMarkers.get(friend.getKey()).setPosition(friend.getLocation().getX(), friend.getLocation().getY());
        }
        if (!friendMarkers.isEmpty()) {
            mapView.commitTransaction(new Transaction.Builder().setAnimationDuration(250).addMarkers(friendMarkers.values()).build());
        }

        friendsList = friends;
    }

    //
    // MapViewListener methods
    //
    @Override
    public void onMapLoadStart() {
        super.onMapLoadStart();

        // time to remove our markers
        if (friendMarkers != null) friendMarkers.clear();
    }

    @Override
    public void onMapLoadFinish() {
        super.onMapLoadFinish();
    }

    @Override
    public void onPlacemarksLoadFinish() {
        super.onPlacemarksLoadFinish();
    }

    @Override
    public void onMapRenderFinish() {
        super.onMapRenderFinish();
    }

    @Override
    public void onMapLoadFail(Throwable tr) {
        super.onMapLoadFail(tr);
    }

    @Override
    public void onMapTransformChange(Matrix transform) {
        super.onMapTransformChange(transform);
    }

    @Override
    public void onDirectionsReroute() {
        super.onDirectionsReroute();
    }

    @Override
    public boolean onDirectionsClick(Marker marker) {
        if (marker instanceof PlacemarkMarker) {
            return super.onDirectionsClick(marker);

        } else if (marker instanceof LocationSharingMarker) {
            Friend friend = ((LocationSharingMarker) marker).getFriend();
            if (friend != null && friend.getLocation() != null) {
                startDirections(DirectionsDestination.forMapPoint(friend.getLocation().getMapKey(),
                        new PointF(friend.getLocation().getX(), friend.getLocation().getY())));
            }
        }
        return false;
    }

    @Override
    public boolean onDirectionsStart() {
        return super.onDirectionsStart();
    }

    @Override
    public boolean onRouteStepIndexChange(int index) {
        return super.onRouteStepIndexChange(index);
    }

    @Override
    public boolean onDirectionsClosed() {
        return super.onDirectionsClosed();
    }

    @Override
    public boolean onDirectionsError(Throwable tr) {
        return super.onDirectionsError(tr);
    }

    @Override
    public void onLocationUpdated(MeridianLocation meridianLocation) {
        super.onLocationUpdated(meridianLocation);
    }

    @Override
    public void onUseAccessiblePathsChange() {
        super.onUseAccessiblePathsChange();
    }
}

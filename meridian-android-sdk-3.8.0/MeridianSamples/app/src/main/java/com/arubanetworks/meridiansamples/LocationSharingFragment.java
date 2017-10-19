package com.arubanetworks.meridiansamples;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.internal.util.Strings;
import com.arubanetworks.meridian.locationsharing.Friend;
import com.arubanetworks.meridian.locationsharing.Invite;
import com.arubanetworks.meridian.locationsharing.LocationSharing;
import com.arubanetworks.meridian.locationsharing.LocationSharingException;
import com.arubanetworks.meridian.locationsharing.User;

import java.util.List;

public class LocationSharingFragment extends Fragment {

    private static final int IMAGE_REQUEST_FOR_USER_IMAGE = 0x0000000F;

    public static LocationSharingFragment newInstance() {
        LocationSharingFragment fragment = new LocationSharingFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // we need to initialize Location Sharing first
        if (!Strings.isNullOrEmpty(Meridian.getShared().getEditorToken())) {
            LocationSharing.initWithAppKey(Application.APP_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_sharing_example, container, false);

        final LinearLayout loggedOutLayout = (LinearLayout) rootView.findViewById(R.id.location_sharing_logged_out);
        final LinearLayout loggedInLayout = (LinearLayout) rootView.findViewById(R.id.location_sharing_logged_in);

        Button createProfileButton = (Button) rootView.findViewById(R.id.location_sharing_create_profile);
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Strings.isNullOrEmpty(Meridian.getShared().getEditorToken())) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("You need to provide a valid editor token")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    User sampleUser = new User();
                    sampleUser.setFullName("Sample User");

                    LocationSharing.shared().createUser(sampleUser, new LocationSharing.Callback<User>() {
                        @Override
                        public void onSuccess(User user) {

                            loggedOutLayout.setVisibility(View.GONE);
                            loggedInLayout.setVisibility(View.VISIBLE);

                            new AlertDialog.Builder(getActivity())
                                    .setMessage("User created successfully!")
                                    .setPositiveButton("OK", null)
                                    .show();
                        }

                        @Override
                        public void onError(LocationSharingException t) {
                            new AlertDialog.Builder(getActivity())
                                    .setMessage("Unable to create user")
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    });
                }
            }
        });

        if (Strings.isNullOrEmpty(Meridian.getShared().getEditorToken())) {
            return rootView;
        }
        final Button startPostingLocationUpdates = (Button) rootView.findViewById(R.id.location_sharing_start_updating_location);

        // optionally, we can set a listener so we know when the service is running
        LocationSharing.shared().addListener(new LocationSharing.Listener() {
            @Override
            public void onPostingLocationUpdatesStarted() {
                startPostingLocationUpdates.setText(getString(R.string.location_sharing_stop_updating_location));
            }

            @Override
            public void onPostingLocationUpdatesStopped() {
                startPostingLocationUpdates.setText(getString(R.string.location_sharing_start_updating_location));
            }
        });

        startPostingLocationUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // we are using the same button to start/stop posting, so we need to check if we are already posting or not
                // before starting/stopping it
                if (LocationSharing.shared().isUploadingServiceRunning()) {
                    LocationSharing.shared().stopPostingLocationUpdates(getActivity().getApplicationContext());
                } else {
                    LocationSharing.shared().startPostingLocationUpdates(getActivity().getApplicationContext());
                }
            }
        });

        final Button createInviteButton = (Button) rootView.findViewById(R.id.location_sharing_create_invite);

        createInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSharing.shared().createInvite(new LocationSharing.Callback<Invite>() {
                    @Override
                    public void onSuccess(Invite result) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Invited created. URL: " + result.getShareUrl())
                                .setPositiveButton("OK", null)
                                .show();

                        // you can share the invite URL here
                    }

                    @Override
                    public void onError(LocationSharingException e) {
                        // do something
                    }
                });
            }
        });

        final Button retrieveFriendsButton = (Button) rootView.findViewById(R.id.location_sharing_retrieve_friends);

        retrieveFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSharing.shared().getFriends(new LocationSharing.Callback<List<Friend>>() {
                    @Override
                    public void onSuccess(List<Friend> result) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Success! You have " + result.size() + " friends")
                                .setPositiveButton("OK", null)
                                .show();
                    }

                    @Override
                    public void onError(LocationSharingException e) {
                        // do something
                    }
                });


            }
        });

        final Button uploadUserImageButton = (Button) rootView.findViewById(R.id.location_sharing_upload_user_image);
        uploadUserImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_REQUEST_FOR_USER_IMAGE);
            }
        });

        return rootView;
    }

    private void uploadURIasUserImage(Uri uri){
        if(uri == null || getContext() == null)return;
        LocationSharing.shared().uploadUserPhoto(getContext(), uri, new LocationSharing.Callback<User>() {
            @Override
            public void onSuccess(User result) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Success! image uploaded.")
                        .setPositiveButton("OK", null)
                        .show();
            }

            @Override
            public void onError(LocationSharingException t) {
                new AlertDialog.Builder(getActivity())
                        .setMessage(String.format("Error uploading image. %s", t.getErrorMessage()))
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK)return;
        switch (requestCode){
            case IMAGE_REQUEST_FOR_USER_IMAGE:
                uploadURIasUserImage(data.getData());
                break;
        }
    }
}

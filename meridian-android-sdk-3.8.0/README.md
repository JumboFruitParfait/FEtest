Meridian SDK for Android
========================

This SDK package will assist you in embedding Meridian's maps and directions features into your own Android apps. You create your location-based data and maps at http://edit.meridianapps.com then use this SDK to display them in your app. The SDK provides all the necessary functionality of downloading the relevant location data and presenting the UI for maps & wayfinding.

By using the Meridian SDK, you hereby agree to the terms of the [Meridian SDK License](https://edit.meridianapps.com/users/sdk_tac)

What's In The Box?
==================

- README.md: this file!
- meridian-x.y.z.aar: The Android AAR library you'll be referencing in your app.
- MeridianSamples: An Android project demonstrating a simple app that uses the SDK.


Using the SDK in an existing app
================================

Adding a 3rd-party library to an Android project can be tricky. We've tried to make it as simple as possible by bundling the SDK code into an AAR file which is a binary distribution of an "Android Library Project".

In short, here are the steps you'll need to take:

1. Move `meridian-x.y.z.aar` where you'd like it

2. Edit your app's `build.gradle` file

3. Add the relative path to where you placed the AAR file to the root-level `repositories` element like this:

```
repositories {
    mavenCentral() // optional, probably exists in your project already

    flatDir {
        dirs '../'
    }
}
```

4. Compile the AAR into your app in the `dependencies` section (make sure to replace the `x.y.z` with the correct version):

```
dependencies {
    compile 'com.arubanetworks.meridian:meridian:x.y.z@aar'

    // 3rd party dependencies used by meridian
    compile 'com.mcxiaoke.volley:library:1.0.9@aar'
    compile 'com.squareup:otto:1.3.5'
}
```

5. In your relevant source files, you can now import the `com.arubanetworks.meridian` packages to access the Meridian classes.

You can examine the MeridianSamples project to see what the finished `build.gradle` should look like.

Simulating Location (SDK 2.14.5)
=====================================

Simulating location works by ignoring the device driven methods for deriving
location and instead requesting a static location from the Editor. This can be
toggled on/off at build or runtime.

To set it at build time you can add
```
buildConfigField "boolean", "MERIDIAN_FORCE_SIMULATED_LOCATION", "true"
```
to any of the configs in your project's `build.gradle`. It is recommended that
you avoid using this in the release config as to not inadvertently use the
simulated location in the production APK.

You can also set toggle this at runtime with
`Meridian.getShared().setForceSimulatedLocation(true)`.

In order to set the actual location to be simulated you will need to go into the
placemarks view of one of your maps in the editor and press the `l` key. It will
set the location of your mouse pointer as the location to be simulated.



Setting your application token (SDK 3.3.0)
==========================================

The application token used by the location sharing feature has been moved to the `Meridian` class. Two methods have been removed from the `LocationSharing` class: `init(String editorToken)` and `init(String editorToken, String appId)`. Now in order to use the location sharing feature a token needs to be set through `Meridian.getShared().setEditorToken(String editorToken)`. Then location sharing can be initialized calling `LocationSharing.initWithAppId(String appId)`.

Configuration (SDK 2.13.11)
=====================================

Configuring the SDK has changed in this version. The ```Meridian.configure(context)```
call is still valid, however config properties are now stored in a singleton
that is generated in that call. Now after calling ```Meridian.configure(context)```
you will want to use the ```Meridian.getShared()``` and set the properties on
the returned singleton.

Below is an example of this usage:

```
// Configure Meridian
Meridian.configure(this);

// Set configuration properties
Meridian.getShared().setOverrideCacheHeaders(true);
Meridian.getShared().setCacheOverrideTimeout(1000*60*60);
```

Map Loading Events (SDK 2.13.11)
=====================================

Map loading events have changed slightly in this version to allow for more
visibility into what is currently being seen by the user.

There are five events:
```
onMapLoadStart()
onMapLoadFinish()
onPlacemarksLoadFinish()
onMapRenderFinish()
onMapLoadFail(Throwable tr)
```

They will be called in that order with the exception of ```onMapLoadFail``` which
can come any time after ```onMapLoadStart()``` and will only be called if no map
can be displayed.

```onMapLoadStart()``` will be called first and indicates that the outbound
requests to get data associated with the map have started.

```onMapLoadFinish()``` will be called after the request for the map image has
finished. The map image will be rendered to the screen immediately after this call.


```onPlacemarksLoadFinish()``` will be called after the request for placemark
data has finished. The placemarks have not been rendered to the screen at this
point and now is a good time to add your own markers or modify the placemarks
before Markers are generated from them.

```onMapRenderFinish()``` will be called once all the Markers have been
generated. At this point both the map image system Markers and any custom
Markers you added will be visible to the user.

Campaign Push (SDK 2.13.10)
=====================================

Campaign Push is a feature of the SDK and Editor that allows for an ALE that is
registered with a location in the Editor to directly send Push notifications to the
SDK. To enable this behavior in the SDK a GCM registration key must be set using the
`CampaignPush.setGCMRegistrationKey(Context applicationContext, String registrationKey)`
method. Recieving the Push is handled in the same way that beacon push is by extending
the `CampaignBroadcastReceiver` class. An example of this can be found with the
`CampaignReciever` of the MeridianSamples project.


Listeners reorganization (SDK 2.13.6)
=====================================

`MapViewListener` has been split into:

- `MapEventListener` - Handles events from the MapView
  - `onMapLoadStart`
  - `onMapLoadFinish`
  - `onMapLoadFail(Throwable)`
- `MarkerEventListener` - Handles marker events
  - `onMarkerSelect(Marker)`
  - `markerForPlacemark(Placemark)`
  - `onCalloutClick(Marker)`
- `DirectionsEventListener` - Directions events
  - `onDirectionsClick(Marker)`
  - `onRouteStepIndexChange(int)`
  - `onDirectionsClosed`
  - `onUseAccessiblePathsChange`
  - `onDirectionsError(Throwable)`

`MapFragmentListener` is gone, instead, use `MapView` listeners:

- `MapFragment.setMapEventListener`
- `MapFragment.setMarkerEventListener`
- `MapFragment.setDirectionsEventListener`

See the examples project for examples on how to use these new listeners.


Questions, Help, etc.
=====================

More documentation and code snippets can be found on the our [site](http://docs.meridianapps.com/developers/sdk_guide_android).

Please email any support requests, comments, and concerns to developers@meridianapps.com.

Thanks so much for using Meridian!

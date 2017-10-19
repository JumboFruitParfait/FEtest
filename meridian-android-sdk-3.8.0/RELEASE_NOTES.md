Version 3.8.0 - September 28, 2017
-----------------------------------

* Fixed a bug where the reorientation message would appear even when the compass was off.
* Fixed a bug with paginated requests.
* Fixed a bug where the accessibility icon would appear in the wrong location.
* Additional bug fixes and performance improvements.

Version 3.7.0 - August 24, 2017
-----------------------------------

* Added custom dimensions to Google analytics.
* Changed GPS accuracy threshold to 10 meters.
* Additional bug fixes.

Version 3.6.0 - July 28, 2017
-----------------------------------

* Added support for Mapless Campaigns.
* Migrated additional APIs to new endpoints.
* Fixed a bug where the map label wouldn't show the map name correctly.
* Additional bug fixes.

Version 3.5.0 - June 20, 2017
-----------------------------------

* Added support for Active Exit Campaigns.
* TagBeacon new property: externalID.
* Migrated to new API endpoints.
* Fixed a bug where 4.x devices wouldn't show the loading animation when tapping on the location button.

Version 3.4.0 - May 31, 2017
-----------------------------------

* New search API allows searching for placemarks, maps and tags.
* TalkBack / accessibility support.
* Samples app uses new dedicated Meridian location.
* The SDK uses new Meridian endpoints now for some APIs.
* Fixed a bug where campaign dwell times were not being reported.
* Fixed a bug where the app would crash when switching locations while a route was plotted.

Version 3.3.0 - April 18, 2017
-----------------------------------

* Breaking change: application token is set through the `Meridian` class now (see README file).
* Added authentication to Tag tracking via application token.
* Labels are included in Tag data.
* Improved placemark zoom levels.
* Other bug fixes and performance improvements.

Version 3.2.0 - March 23, 2017
-----------------------------------

* New orientation alerts while navigating.
* Location button behavior can now be customized.
* Fixed a bug that occurred when manually advancing directions.
* Other bug fixes and performance improvements.

Version 3.1.0 - February 22, 2017
-----------------------------------

* Fixed delay for blue dot.
* Fixed a bug where compass would flip if the device was rotated downwards past the horizontal point.
* Fixed bug rendering default inaccessible levels.
* Double-tap zoom in is now smooth.
* Added two-finger single-tap gesture to zoom out.
* SDK now checks if all required dependencies have been added to the project.

Version 3.0.0 - January 27, 2017
-----------------------------------

* Compass is now represented by a cone instead of an arrow.
* Fixed some issues with compass calibration and accuracy.
* Fix for occasional crash related to loading Placemarks.
* Consolidated loading spinner and MapLabel for Placemark loading.
* Ellipse elements are now rendered.
* Fixed a bug where certain BuildConfig values were being ignored.
* Fix for issue with push notifications not being received on Android 7
* Zoom levels adjusted to match iOS SDK.
* Added customizable reroute parameters.

Version 2.19.0 - December 14, 2016
-----------------------------------

* More consistent zoom while navigating.
* New asynchronous loading for large sets of placemarks.
* Added the Map Label back in.
* Adjusted text size on default PlacemarkMarker.
* More consistent results for the new PlacemarkRequest.
* MapFragment and Mapview can now be initialized with a placemark key. This
  will cause them to load only a single placemark.

Version 2.18.0 - November 4, 2016
-----------------------------------

* Location accuracy improvements
* Minimum supported Android API is now 16
* New methods for fetching placemarks from Meridian servers
* Placemark UID is now accessible.
* Map Controls are now hidden correctly.
* My Location button now starts a loading spinner instead of immediately causing
  an error when a location has not been generated yet.
* onMapRenderFinished is now called after initial centering of the MapView.


Version 2.17.0 - September 29, 2016
-----------------------------------

 * Map picker sorting updated. Buildings are sorted alphabetically and map levels are sorted in descending order.
 * Fixed a bug where the location button wasn't hidden properly.
 * The GPS accuracy threshold value is now modifiable.

Version 2.16.1 - September 22, 2016
-----------------------------------

 * Fix for a crash when a user tapped on a location sharing user icon.

Version 2.16.0 - September 20, 2016
-----------------------------------

 * Updated map UI design.
 * Compass can now be enabled/disabled.
 * Fix for level picker not showing up for some overview maps.

Version 2.15.3 - July 29, 2016
----------------------------------

 * The New Search Level Picker is now the default floor selector.
 * Added zoom level display in diagnostics.
 * Added support for reading per map max zoom values from editor.
 * Fix for compass not rotating in relation to map.
 * Routing advance improvements.

Version 2.15.2 - June 29, 2016
----------------------------------

 * Added compass arrow on location dot when available, gps coordinates are
   required for the arrow to show up.
 * Fix for a Bluetooth stack crash on some Samsung devices.

Version 2.15.1 - June 21, 2016
----------------------------------

 * Added `getCampaignId(Intent i)` in the CampaignBroadcastReciever.
 * Fix for intermittent stalling of map rendering.
 * Fix for some placemarks not showing on the map when they lack a name.
 * New language updates.
 * Fix for a crash related to beacon RSSIs being updated while beacons are
   being sorted.
 * Fix for location generation data not being updated appropriately when
   switching between locations.
 * Fix for GPS exclusion areas not being calculated correctly when multiple maps
   contain areas.
 * Fix for default GPS accuracy being set to zero.

Version 2.15.0 - April 25, 2016
----------------------------------

 * New feature Location Sharing, see the Meridian Samples project for example usage.
 * New map level picker, designed for searching large campuses. It can be
   enabled through Meridian.getShared().setPickerStyle()
 * Max zoom level can now be set globally through Meridian.getShared().setDefaultMaxMapZoom()
 * Default routing snap to route range can now be set through Meridian.getShared().setRouteSnapDistance()
 * Map rendering is now done on demand instead of continuously.
 * General location generation and smoothing improvements.
 * Transparent markers are no longer considered for collision.
 * Fix for crashes cause by some maps being generated incorrectly.

Version 2.14.6 - January 28, 2016
----------------------------------

 * Fixes and improvements to location including better smoothing and beacon detection.
 * Device identifiers are more reliably unique.
 * Updated to API 23 style permissions, manifest permissions will still work.
 * Added a onLocationUpdated callback to the MapFragment.
 * Added native library supports for armabi-v8a.
 * Routing step distances now update with changes in user location.
 * Added Email crash reporting to the MeridianSamples app.
 * Fix for crash when changing maps while routing.
 * Fix for blue dot occasionally vanishing during map transitions.
 * Fix for BLE scans blocking the UI thread on some LG devices running Android 4.x.
 * Fix for BLE scans blocking WiFi on some shared WiFi/BT Antennae.

Version 2.14.5 - November 20, 2015
----------------------------------

 * User location can now be simulated for development builds. See Simulating
   Location in the README.
 * Fix for ZoomLevels being calculated incorrectly with some screen densities.
 * Fix for units per meter being incorrectly calculated for maps with a rare
   arrangement of gps reference points.
 * Fix for a failure to render a Marker that was being moved to the bottom of
   the weight priority after being added to the map.
 * Improvements to campaign reporting.
 * Fix for a crash due to some listeners unexpectedly being null.
 * Fix for a native crash when removing Markers in a specific order.

Version 2.14.4 - September 8, 2015
----------------------------------

 * Added support for Alpha and Scale animations for Markers.
 * Added support for minimum and maximum visible zoom levels for Markers.
 * Fixed areas not being set on Placemarks.
 * Fixed a crash caused by rapidly switching back and forth between maps while animations were active.

Version 2.14.3 - August 17, 2015
----------------------------------

 * Added methods to MapInfo to easily convert between Latitude/Longitude and Map coordinates.
 * Fixes crash related to adding markers and removing them before their animation completes.
 * Fixed crash related to loading two identical maps at once.
 * Fixed rare ANR when adding Markers to the MapView.
 * Fixed issue with transparency being corrupted when the same bitmap is used for multiple Markers.
 * Scaled back some overly broad proguard exceptions.

Version 2.14.2 - July 27, 2015
----------------------------------

 * Markers now have invalidate methods, they will update the properties as well as the bitmaps of the Markers.
 * Support for right to left languages.
 * Support for manually setting the icon on the PlacemarkMarkers.
 * Proguard rules should now propagate.
 * Improvements to auto re-routing to make for a much smoother user experience.
 * Fix for issues with added Markers vanishing on map reload.
 * Fix for crashes related to concurrent adding and removing of the same Marker.
 * Fix for crashes related to map images being loaded concurrently.
 * Fix for crash related to committing Transactions from the GLThread.
 * Fix for a crash when the location had outdoor maps and the device lacked GPS support.

Version 2.14.1 - July 1, 2015
----------------------------------

 * Added ability to report analytics to the Meridian Editor.
 * Map Markers now have 2 new properties, ScaleFixedToMap and RotationFixedToMap.
   They will allow you to lock Markers to the scale and rotation of the map as
   opposed to the screen.
 * Example proguard rules have been added to the MeridianSamples project.
 * Stability fixes for the MapView when setting keys when not attached to a
   window.
 * Moved all MapView and MapFragment listener callbacks to the main thread.
   However the Marker's getBitmap and all Transaction callbacks are on the
   GLThread, so make sure to return quickly and catch exceptions to avoid a
   native crash.
 * Fixes to the marker weights to order both colliding and none colliding
   Markers properly.
 * Native layer memory improvements.


Version 2.14.0 - June 16, 2015
----------------------------------

 * Map Markers have been revamped to allow for Transactions of markers to be
   added, modified or removed at once. Animation times are now configurable for
   the new Transactions. As well as callbacks for when they are added to the map
   and their initial animation has completed. See CustomMarkerFragment for
   examples.
 * Added method to the MapEventListener to allow for easier notification of the
   map transform changing.
 * Pausing and Resuming on Android 5+ devices now conservers battery more
   effectively.
 * Collision is now togglable on Marker objects.
 * Weights can now be set on  marker objects to adjust priority during collision.
 * Improved Marker rendering performance.

Version 2.13.12 - May 28, 2015
----------------------------------

 * Added support for setting the background color of a MapView.
 * Added frame time limiter on MapView. Capped at 16 milliseconds of frame time.
   This should help with battery use.
 * Fixed a crashing issue when pausing and resuming on Android 5+ devices.
 * Fixed an issue exit campaigns firing when a proximity beacon was never seen.
 * Fixed an issue with OpenGL causing a lot of log spam.
 * Improved Marker rendering performance.

Version 2.13.11 - April 13, 2015
----------------------------------

 * Moved initial configuration into a Meridian singleton. (see README)
 * Added Snap to route and auto rerouting, these are disabled by default and
   must be enabled in the Meridian class.
 * MapEventListener has changed to indicate when a map is loaded and rendered.
   (see README)
 * onDirectionsStart has been added to MapDirectionsListener to indicate when
   directions have finished loading and are being displayed.
 * Added support for hiding PlacemarkMarker names.
 * Fixed black screen when animating a MapView.
 * Fixed routing bug that was causing route not found errors when the device's
   language was set to Spanish.

Version 2.13.10 - March 17, 2015
----------------------------------

 * Added Support for Campaign Push through the editor. (see README)
 * Added support for clearing a MapView by setting its MapKey to null.
 * Fixed placemarks not showing when more than 500 were visible on a single map.
 * Fixed issue with prompting for bluetooth to be turned on when it already was.
 * Fixed MapOptions not being propagated correctly to the MapFragment.
 * Fixed a crash related to lack of network connectivity.
 * Fixed an bug that was causing reseting campaigns to take a long time to take effect.

Version 2.13.9 - February 24, 2015
----------------------------------

 * Fix Campaign notifications not triggering correctly.
 * Fix for route vanishing after onPause/onResume when using MapView directly.
 * Fix for crash when clearing the map in a MapView during onPause/onResume.
 * Added support for directions arrows when using non English language.

Version 2.13.8 - February 13, 2015
----------------------------------

 * Fix for a crash related to poor network connectivity.
 * Fix for a NullPointerException caused by a threading issue in MeridianLocationService.

Version 2.13.7 - February 10, 2015
----------------------------------
 * MapItem has been removed in favor or explicit DirectionsSource and DirectionsDestination parameters.
 * Added a new location issue reporting screen (hold down 2 fingers on any map)
 * Added caching support for all web requests.
 * Added cache timeout override properties to the Meridian class.
 * Added loading spinner to the MapFragment while the map is loading.
 * Fix for dialogs on samsung devices not canceling correctly.
 * Fix for related maps on placemarks not being followed when tapped.
 * Fix for some map controls being too small or miss aligned.

Version 2.13.6 - January 12, 2015
----------------------------------

 * Reorganized listeners on MapView and MapFragment to make implementations simpler (see README).
 * Added more EditorKey static constructors.
 * Added ability to exclude specific placemark keys from SearchFragment results.
 * Fixed crash when switching rapidly between maps.
 * Fixed bug where routes were not clearing appropriately on fragment resume.
 * Fixed bug where Directions using a single placemark as a source were not generating a route.
 * Improved Samples

Version 2.13.5 - December 16, 2014
----------------------------------

 * Added support for custom Markers to be added to the MapView, see CustomMarkerFragment.java in the samples app.
 * Added manual scrolling methods to MapView, see ScrollingFragment.java in the samples app.
 * Added onDirectionsError and onDirectionsClosed callbacks to the MapViewListener.
 * Added Hebrew translations.
 * Fixed issues with PlacemarkMarker splitting words across lines.
 * Fixed route line not being refreshed on pause and resume.
 * Improved bluetooth based location and messaging when location is not found.
 * Improved rendering performance for all Markers.
 * Upgraded Volley version to 1.0.9.

Version 2.13.2 - November 24, 2014
----------------------------------

 * Fix for Outdoor Areas being merged acrossed maps.
 * Added Reset method for CampaignsService.

Version 2.13.1 - November 18, 2014
----------------------------------

 * Allow Placemark properties to be set; add public constructor

Version 2.13.0 - November 18, 2014
----------------------------------

 * Added support for outdoor areas.
 * MapItems for Directions now support a list of placemark keys as a destination.
 * Better BLE location smoothing.
 * Fixes for BLE location finding not working after some time on certain devices.

Version 2.12.0 - November 10, 2014
----------------------------------

 * Added PlacemarkMarker Options and Improved PlacemarkMarker rendering.
 * Added Campaign support and proximity push.
 * Added listener method for callout taps.
 * Reworked how directions are retrieved to allow for more customization of that flow.
 * Reworked SearchFragment to allow for better styling.
 * Fixes for the initial zoom state of maps.
 * Fixes to animations when direction steps change.
 * Fixes to BLE location finding to allow for more accurate locations across maps.

Version 2.11.0 - September 29, 2014
----------------------------------

 * Added getMapView and method to the MapFragment
 * Added ability to override PlacemarkMarkers for individual placemarks
 * Added error handling to make it more clear when Meridian.configure has not been called
 * Added dialog for informing the user when location is not enabled
 * Fixes to location services to prevent stalling when settings are changed
 * Fixed placemarks that are lacking names but not icons are now being drawn
 * Changed the maifest requirement for OpenGL 2.0 to a runtime check

Version 2.10.1 - September 19, 2014
----------------------------------

 * Fixed Gradle dependencies for the MeridianSamples project
 * Added gzip encoding support for internal API calls

Version 2.10.0 - September 2, 2014
----------------------------------

 * Document public methods and include reference documentation online

Version 2.9.1 - August 7, 2014
------------------------------

 * Fix for crash when route is set but map is not yet loaded
 * Fix for unnecessary logging of client location data

Version 2.9 - August 7, 2014
----------------------------

 * Fix issues with multiple simultaneous active instances of MapView
 * Fix for location dot re-animating in place
 * Fix Volley dependency
 * Fix for errors in design view if map view is added via xml.
 * Fix for unnecessary logging of scanned beacons

Version 2.8.2 - August 5, 2014
------------------------------

 * Fix issues with multiple simultaneous active instances of `MeridianLocationManager`
 * Use more data to calculate location
 * Displays a message when the user click the Location button and no location is available

Version 2.8.1 - July 31, 2014
-----------------------------

 * Add `onRouteStepIndexChange()` method to `MapViewListener`
 * Add `MapActivity` sample activity using `MapView` directly

Version 2.8 - July 31, 2014
---------------------------

 * New graphics and animation for the "blue dot"
 * Bug fixes

Version 2.7.2 - July 30, 2014
-----------------------------

 * Fix beacon RSSI threshold being set too high by default

Version 2.7.1 - July 29, 2014
-----------------------------

 * New MapView listener method `onMarkerSelect()`
 * Can enable/disable bottom "callout" using Marker.setShowsCallout()
 * Fix for `NullPointerException` during Beacon scanning

Version 2.7 - July 24, 2014
---------------------------

 * Fix for UI thread hanging when Bluetooth is available but turned off
 * Possible fix for LocationSmoother NullPointerException
 * Fix for proper viewport padding while stepping through directions

Version 2.6 - July 23, 2014
---------------------------

 * Ability to toggle "levels control" visibility
 * Ability to toggle "overview button" visibility
 * Ability to toggle "map label" visibility
 * Ability to toggle "location button" visibility

Version 2.5 - July 22, 2014
---------------------------

 * Adds "auto advance" for directions
 * Adds public static field "tweaks" in `com.arubanetworks.meridian.service.Meridian`
 * Enables "location button" on map
 * Fixes for selecting points of interest
 * Fixes for location manager
 * Known issue: Map controls are not yet able to be hidden like iOS
 * Known issue: "Blue dot" graphic is a placeholder

Version 2.4 - July 18, 2014
---------------------------

 * New LocationManager supports Beacons, System location, and Simulated location
 * Placemarks fade in and out properly during zoom and don't collide
 * Fix for map "flickering"
 * Fixes for panning/zooming issues
 * Placemarks are selectable with slide-up bottom info panel
 * Misc. fixes for directions flow
 * Known issue: "Blue dot" graphic is a placeholder
 * Known issue: Crash on startup for certain device types
 * Known issue: "Current Location" button is not wired up

Version 2.3.4 - July 3, 2014
----------------------------

 * Fixes some map drawing issues associated with panning and zooming to the extents of a map.
 * Fixes syncing issues between Placemarks and Maps while panning and zooming.
 * Fixes inconsistent routing line widths.
 * Known issue: Placemark collision and hit detection is disabled.
 * Known issue: Direction steps don't always animate to the correct map region

Version 2.3.3 - June 24, 2014
----------------------------

 * Fixes many map drawing issues
 * Fixes for loading Placemarks
 * Known issue: Placemarks are not "synced" with the map drawing
 * Known issue: Direction steps don't always animate to the correct map region
 * Known issue: Direction "routing line" can be drawn too thin

Version 2.3.0 - June 6, 2014
----------------------------

 * MapView displays Placemarks on the map
 * Known issue: Placemark drawing is not "synced" with the map drawing

Version 2.2.0 - June 4, 2014
----------------------------

 * MapFragment now supports turn-by-turn directions
 * See DirectionsActivity in MeridianSamples project for details
 * Many fixes for map rendering
 * Known issue: Not all SVG shapes are rendered properly yet
 * Known issue: Routing line can be drawn at wrong thickness


Version 2.1.0 - May 27, 2014
----------------------------

 * New `MapView` with new OpenGL-powered vector rendering
 * Can load and view maps in `MapView`; switch levels
 * Some SVG details are not yet rendered (e.g. map "text"), this will be fixed soon


Version 2.0.1 - May 20, 2014
----------------------------

 * `Placemark` and `Location` constructors are now `public` so you can create them programmatically.

Version 2.0.0 - May 20, 2014
----------------------------

 * Bump version number to 2.x.x to fit with the legacy Meridian SDK
 * Directions API classes
 * Maps buildout; `MapInfo` loading and Map UI

Version 0.2.0 - April 29, 2014
------------------------------

 * Distributed as an AAR package
 * Local Search API classes
 * Sample project included
 * Demonstrates local search list

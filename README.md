TumTumTracker
=============
Android application to track the TumTums - internal transportation vehicles at IIT Bombay.


Setup
------------------
1. Clone the repository and Import to ```Android Studio```
2. Do ```Playservices setup``` if the map doesn't load while testing


Playservices setup
--------------------
1. Google Map APIs requires an API key sepecific to your apk signing key. Refer [new API key][2]
2. Add your ```new API key``` to ```AndroidManifest.xml``` file

Tip: 
* Try to obtain an API key for the debug keystore used by Android studio.


Libraries and tools used
-------------------
- [Json][3] 
	- Data representation

- [Gson][4]
	- Json decoding
		
- [Sugar][6]
	- ORM database on top of Sqllite for data persistance
		
- [v7-appcompact][6]
	- Android support library for older devices


License
----------------------
(c) 2013 - 2015 Praveen Kumar Pendyala and colloborators. 
Licensed under the [GPL v3][1]


[1]: https://tldrlegal.com/license/gnu-general-public-license-v3-%28gpl-3%29
[2]: https://developers.google.com/maps/documentation/android/start#obtain_a_google_maps_api_key
[3]: https://en.wikipedia.org/wiki/JSON
[4]: https://github.com/google/gson
[5]: https://github.com/satyan/sugar
[6]: https://developer.android.com/tools/support-library/features.html#v7-appcompat


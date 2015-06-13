TumTumTracker
=============
Android application to track the TumTums - internal transportation vehicles at IIT Bombay.


Setup
------------------
1. Clone the repository ```git clone https://github.com/praveendath92/TumTumTracker```
2. Import the project into Android Studio
3. Do ```Playservices setup``` if the map doesn't load while testing


Playservices setup
--------------------
1. Google Map APIs requires an API key sepecific to your apk signing key. So, you need get a [new API key][2]
2. Add the API key to the manifest file as instructed in the above url and you should be good to go.

Tip: 
* Try to obtain an API key for the debug keystore used by Android studio.


Libraries and tools used
-------------------
- Json 
		Data representation

- Gson
		Json decoding
		
- Sugar
		ORM database on top of Sqllite for data persistance
		
- AppCompact
		Android support library for older devices


License
----------------------
(c) 2013 - 2015 Praveen Kumar Pendyala and colloborators. 
Licensed under the [GPL v3][1]


[1]: https://tldrlegal.com/license/gnu-general-public-license-v3-%28gpl-3%29
[2]: https://developers.google.com/maps/documentation/android/start#obtain_a_google_maps_api_key


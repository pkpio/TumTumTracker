TumTumTracker
=============
Android application to track the tumtums - internal transportation vehicles at IIT Bombay.


Setup
------------------
1. Clone the repository ```git clone https://github.com/praveendath92/TumTumTracker```
2. Import the project into eclipse
3. Refer to External dependencies
4. Import add them as project dependencies ```project -> properties -> add libraries```
5. Complete Playservices setup instructions


External dependencies
---------------------
- ```Google playservices``` imported from ```sdk/extras/google/google_play_services```
- ```v7 appcompact``` imported from ```sdk/extras/android/support/v7/appcompact```


Playservices setup
--------------------
1. Google Map APIs requires an API key sepecific to your apk signing key. So, you need get a [new API key][2]
2. Add the API key to the manifest file as instructed in the above url and you should be good to go.

Tip: 
* Try to obtain an API key for the debug keystore used by eclipse for easy development.
* If there are any issues with initial setup, write to me, I will try to help you out.


License
----------------------
(c) 2013 - 2015 Praveen Kumar Pendyala and colloborators. 
Licensed under the [GPL v3][1]

Happy dev'ing :)


[1]: https://tldrlegal.com/license/gnu-general-public-license-v3-%28gpl-3%29
[2]: https://developers.google.com/maps/documentation/android/start#obtain_a_google_maps_api_key


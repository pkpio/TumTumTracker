TumTumTracker
=============
Android application to track the tumtums - internal transportation vehicles at IIT Bombay.


Setup
------------------
- Clone the repository ```git clone https://github.com/praveendath92/TumTumTracker```
- Refer Import to Eclipse section below


Import to Eclipse
-------------------
1. Import the project into eclipse.
2. Import ```google-play-services_lib``` from ```/libs``` if not imported along in the previous step
3. Download Google Play Services using the SDK manager. You will need this when the play-service in ```/libs``` didn't work
4. Google Map APIs requires an API key sepecific to your apk signing key. So, you need get a new API key. Refer,<br/>
   <https://developers.google.com/maps/documentation/android/start#obtain_a_google_maps_api_key> <br/>
5. Add the API key to the manifest file as instructed in the above url and you should be good to go.

Note: 
* Try to obtain an API key for the debug keystore used by eclipse for easy development.
* If there are any issues with initial setup, write to me, I will try to help you out.


License
----------------------
(c) 2013 - 2015 Praveen Kumar Pendyala and colloborators. 
Licensed under the [GPL v3][1]

Happy dev'ing :)


[1]: https://tldrlegal.com/license/gnu-general-public-license-v3-%28gpl-3%29


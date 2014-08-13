TumTumTracker
=============
Android application to track the tumtums - internal transportation vehicles at IIT Bombay.

<br/>

Author
----------------------
Praveen Kumar Pendyala <<praveen@praveenkumar.co.in>><br/>
http://praveenkumar.co.in

<br/>

License
----------------------
Licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported license.

Read the complete license at,
http://creativecommons.org/licenses/by-nc-sa/3.0/

<br/>

Developing upon TumTumTracker
------------------------
You need to obtain a local copy of the repository to start developing. TO do this,

* Fork this repository using the 'fork' icon at the top right and clone to your PC using, <br/>
```git clone https://github.com/<your-git-username>/TumTumTracker.git```
		
     or
		
* Download a copy of the project as a zip
 
After this proceed to the 'Importing to Eclipse' section

<br/>

Import to Eclipse
-------------------
1. Import the project into eclipse.
2. Import google-play-services_lib from ```/lib``` if not imported along in the previous step
3. Download Google Play Services using the SDK manager. You will need this when the play-service in ```/libs``` didn't work
4. Google Map APIs requires an API key sepecific to your apk signing key. So, you need get a new API key. Refer,<br/>
   ```https://developers.google.com/maps/documentation/android/start#obtain_a_google_maps_api_key```
5. Add the API key to the manifest file as instructed in the above url and you should be good to go.

Note: 
* Try to obtain an API key for the debug keystore used by eclipse for easy development.
* If there are any issues with initial setup, write to me, I will try to help you out.

Happy dev'ing :)


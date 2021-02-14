# AppUpdateChecker
App Update Checker for android developers

Features:
1.Automatic check on app startup
2.Manual Check whenever required with a dialog box

How to use:

Step-1 : 
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency in build.gradle file of module level

	dependencies {
	        implementation 'com.github.shashifreeze:AppUpdateChecker:1.0.2'
	}
  
Step-3: Call checkForUpdate method to get popup if the app has update on play store

	I. Checking update manually: Means checking if there's any update on playstore or not when user performs any click or event.
	   Calling the method with an argument (true) will show a popup with the message 'checking for update' and if update will be available then
	   it will show another popup will be shown with message 'An update [Current app version on play store] is available on the play store'.
	   And if there is no update available on the play store then a toast will be shown as 'No update available'.
	   For eg: User click a button to check for update, then call the method with argument true
	  
	     // Create instance of class AppUpdateChecker
       	     	AppUpdateChecker appUpdateChecker = new AppUpdateChecker(activity);
	     // Call for manual check
             	appUpdateChecker.checkForUpdate(true);
	    
	 II. Checking update on app startup : The library will check for update in the background and will show a popup with the message 'checking for update' and if update  		     will be available then it will show another popup will be shown with message 'An update [Current app version on play store] is available on the play store'.
	 
	     // Create instance of class AppUpdateChecker
       	     	AppUpdateChecker appUpdateChecker = new AppUpdateChecker(activity);
	     // Call for automatic check
             	appUpdateChecker.checkForUpdate(false);
	     
	 Screenshots:
	 
	 
	     
	     



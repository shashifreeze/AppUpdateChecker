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
	        implementation 'com.github.shashifreeze:AppUpdateChecker:1.0.0'
	}
  
Step-2:



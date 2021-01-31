package com.shash.updatechecker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.jsoup.Jsoup;

import java.lang.ref.WeakReference;

public class AppUpdateChecker {

    private static WeakReference<Activity> activityWeakReference;
    private static String currentVersionName;

    public AppUpdateChecker(Activity activity,String currentVersion) {
        activityWeakReference = new WeakReference<>(activity);
        currentVersionName = currentVersion;
    }

   static class GetLatestAppVersionFromPlayStore extends AsyncTask<String, String, String> {
        private String latestAppVersionFromPlayStore;
        private ProgressDialog progressDialog;
        private boolean manualCheck;

       private GetLatestAppVersionFromPlayStore(boolean manualCheck) {
            this.manualCheck = manualCheck;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (manualCheck)
            {
                if ((activityWeakReference.get()!=null)&&progressDialog!=null &&( !activityWeakReference.get().isFinishing())&&(!activityWeakReference.get().isDestroyed()))
                {
                    if (progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }
            }


            String installedAppVersion = currentVersionName;

            //If the versions are not the same
            if(!installedAppVersion.equals(latestAppVersionFromPlayStore)&& latestAppVersionFromPlayStore !=null){
                final AlertDialog.Builder builder = new AlertDialog.Builder(activityWeakReference.get());
                builder.setTitle("An Update("+latestAppVersionFromPlayStore+") is Available on Play Store.");
                builder.setMessage("Installed App Version:"+installedAppVersion+"\n\nStrongly recommend to update.");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Click button action
                        activityWeakReference.get().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+activityWeakReference.get().getPackageName())));
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cancel button action
                    }
                });

                builder.setCancelable(false);
                // to avoid the bad token exception
                if ((activityWeakReference.get()!=null)&&(!activityWeakReference.get().isFinishing())&&(!activityWeakReference.get().isDestroyed()))
                    builder.show();
            }else {
                if (manualCheck) {
                    Toast.makeText(activityWeakReference.get(), "No Update Available", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (manualCheck) {
                progressDialog=new ProgressDialog(activityWeakReference.get());
                progressDialog.setMessage("Checking For Update.....");
                progressDialog.setCancelable(false);
                if(!activityWeakReference.get().isFinishing())
                    progressDialog.show();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                //It retrieves the latest version by scraping the content of current version from play store at runtime
                latestAppVersionFromPlayStore = Jsoup.connect("https://play.google.com/store/apps/details?id=" + activityWeakReference.get().getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
                return latestAppVersionFromPlayStore;
            } catch (Exception e) {
                return latestAppVersionFromPlayStore;
            }
        }
    }
    public void checkForUpdate(boolean manualCheck)
    {
        new GetLatestAppVersionFromPlayStore(manualCheck).execute();
    }
}

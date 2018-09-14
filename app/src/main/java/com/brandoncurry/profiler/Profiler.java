package com.brandoncurry.profiler;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.brandoncurry.profiler.R;
import com.brandoncurry.profiler.ui.landing.LandingActivity;
import com.brandoncurry.profiler.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


public class Profiler extends Application {

    public static FirebaseAuth globalAuth;
    public static FirebaseAuth.AuthStateListener globalAuthListener;
    public static FirebaseDatabase globalDatabase;
    public static FirebaseStorage globalStorage;
    public static FirebaseUser currentLoggedInUser;
    public static Context globalContext;


    @Override
    public void onCreate() {
        super.onCreate();

        globalAuth = FirebaseAuth.getInstance();
        globalStorage = FirebaseStorage.getInstance();
        globalDatabase = FirebaseDatabase.getInstance();
        globalContext = this;

        globalAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                currentLoggedInUser = firebaseAuth.getCurrentUser();
                if (currentLoggedInUser != null) {
                } else {
                }
            }
        };
    }

    public static void showLoginDialog(final Context context, String requestedAction){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent landing = new Intent(context, LandingActivity.class);
                        context.startActivity(landing);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.must_be_logged_in) + requestedAction).setPositiveButton("Log In", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    public static boolean checkIfAlreadyhavePermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestPhotoPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

}

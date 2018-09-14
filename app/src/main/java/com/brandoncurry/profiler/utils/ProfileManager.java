package com.brandoncurry.profiler.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.brandoncurry.profiler.R;
import com.brandoncurry.profiler.constants.Constants;
import com.brandoncurry.profiler.models.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {

    private static DatabaseReference userProfileRef;
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public ProfileManager(){
    }

    public static void createNewProfile (String userId, String imageUrl, String name, String age, String gender, String hobbies, String bgColor) {
        Profile profile = new Profile(UUID.randomUUID().toString(), userId, imageUrl, name, age, gender, hobbies, bgColor);
        DatabaseManager.getDatabase().child("Profile").child(userId).setValue(profile);
        Log.d("Firebase User", "Created Profile");
    }

    public static void setCustomBackgroundColor(String color, String userId){
        userProfileRef = mDatabase.child("Profile").child(userId);
        Map<String, Object> colorMap = new HashMap<>();
        colorMap.put("bgColor", color);
        userProfileRef.updateChildren(colorMap);
    }

    public static void updateHobbies(String hobbies, String userId){
        userProfileRef = mDatabase.child("Profile").child(userId);
        Map<String, Object> colorMap = new HashMap<>();
        colorMap.put("hobbies", hobbies);
        userProfileRef.updateChildren(colorMap);
    }

    public static void removeProfile(String profileId){
        mDatabase.child("Profile").child(profileId).removeValue();

    }


    public static int getBackgroundColor(Context context, String color){
        switch (color){
            case (Constants.CUSTOM_BACKGROUND_COLOR_RED):
                return ContextCompat.getColor(context, R.color.profile_background_red);
            case (Constants.CUSTOM_BACKGROUND_COLOR_ORANGE):
                return ContextCompat.getColor(context, R.color.profile_background_orange);
            case (Constants.CUSTOM_BACKGROUND_COLOR_YELLOW):
                return ContextCompat.getColor(context, R.color.profile_background_yellow);
            case (Constants.CUSTOM_BACKGROUND_COLOR_GREEN):
                return ContextCompat.getColor(context, R.color.profile_background_green);
            case (Constants.CUSTOM_BACKGROUND_COLOR_BLUE):
                return ContextCompat.getColor(context, R.color.profile_background_blue);
            case (Constants.CUSTOM_BACKGROUND_COLOR_PURPLE):
                return ContextCompat.getColor(context, R.color.profile_background_purple);
            case (Constants.CUSTOM_BACKGROUND_COLOR_BLACK):
                return ContextCompat.getColor(context, R.color.profile_background_black);
            case (Constants.DEFAULT_BACKGROUND_COLOR_PINK):
                return ContextCompat.getColor(context, R.color.profile_background_female);
            default:
                return ContextCompat.getColor(context, R.color.profile_background_blue);
        }
    }

}

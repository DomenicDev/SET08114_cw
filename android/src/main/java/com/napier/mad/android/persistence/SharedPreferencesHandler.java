package com.napier.mad.android.persistence;

import android.app.Activity;
import android.content.SharedPreferences;

import com.jme.game.R;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesHandler {

    private static SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler();

    private static final String FILE_NAME = "com.napier.mad.SuburbRunner";

    private SharedPreferencesHandler() {
        // private constructor
    }

    public static SharedPreferencesHandler getInstance() {
        return sharedPreferencesHandler;
    }

    public void savePlayerName(Activity activity, String inputName) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(activity.getString(R.string.saved_player_name_key), inputName);
        editor.apply();
    }

    public String readPlayerName(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String playerNameKey = activity.getResources().getString(R.string.saved_player_name_key);
        String defaultName = activity.getResources().getString(R.string.saved_player_name_default);
        return preferences.getString(playerNameKey, defaultName);
    }

}

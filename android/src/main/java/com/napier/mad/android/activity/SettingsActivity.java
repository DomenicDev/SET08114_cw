package com.napier.mad.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jme.game.R;
import com.napier.mad.android.persistence.SharedPreferencesHandler;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText playerNameEditText = findViewById(R.id.settings_playerName_editText);
        playerNameEditText.setText(readPlayerName());

        Button saveButton = findViewById(R.id.settings_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText playerNameEditText = findViewById(R.id.settings_playerName_editText);
                String inputName = playerNameEditText.getText().toString();
                if (inputName.isEmpty()) {
                    Toast.makeText(SettingsActivity.this, getString(R.string.settings_no_empty_playername_allowed), Toast.LENGTH_LONG).show();
                    return;
                }
                if (inputName.length() >= 13) {
                    Toast.makeText(SettingsActivity.this, getString(R.string.settings_too_long_playername), Toast.LENGTH_LONG).show();
                    return;
                }
                if (inputName.length() <= 2) {
                    Toast.makeText(SettingsActivity.this, getString(R.string.settings_too_short_playername), Toast.LENGTH_LONG).show();
                    return;
                }

                // save player name by using Shared Preferences
                savePlayerName(inputName);

                // go back to previous activity
                finish();
            }
        });

        Button cancelButton = findViewById(R.id.settings_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String readPlayerName() {
        return SharedPreferencesHandler.getInstance().readPlayerName(this);
    }

    private void savePlayerName(String inputName) {
        SharedPreferencesHandler.getInstance().savePlayerName(this, inputName);
    }
}

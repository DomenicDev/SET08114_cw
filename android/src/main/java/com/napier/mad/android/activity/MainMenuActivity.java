package com.napier.mad.android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jme.game.R;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button startGameButton = findViewById(R.id.main_menu_start_game_button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AndroidGameLauncherActivity.class);
            }
        });

        Button highScoreButton = findViewById(R.id.high_score_button);
        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ScoresActivity.class);
            }
        });

        Button exitButton = findViewById(R.id.main_menu_exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button creditsButton = findViewById(R.id.main_menu_credits_button);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreditsActivity.class);
            }
        });

        Button settingsButton = findViewById(R.id.main_menu_options_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingsActivity.class);
            }
        });

        Button helpButton = findViewById(R.id.main_menu_help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                View alertView = getLayoutInflater().inflate(R.layout.help_dialog, null);
                builder.setView(alertView);
                final AlertDialog dialog = builder.create();
                Button okButton = alertView.findViewById(R.id.help_dialog_ok_button);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

     private void startActivity(Class<? extends Activity> activityClass) {
        startActivity(new Intent(MainMenuActivity.this, activityClass));
    }

}

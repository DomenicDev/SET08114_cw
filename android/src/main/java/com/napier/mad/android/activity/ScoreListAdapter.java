package com.napier.mad.android.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jme.game.R;
import com.napier.mad.android.persistence.GameResult;

import java.util.List;

public class ScoreListAdapter extends ArrayAdapter<GameResult> {

    public ScoreListAdapter(@NonNull Context context, List<GameResult> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GameResult result = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_item_list, parent, false);
        }
        if (result == null) {
            return convertView;
        }
        // get text views
        TextView positionView = convertView.findViewById(R.id.score_item_position);
        TextView playerNameView = convertView.findViewById(R.id.score_item_player_name);
        TextView scoreView = convertView.findViewById(R.id.score_item_score);

        // write data to views
        positionView.setText(String.valueOf(position+1));
        playerNameView.setText(String.valueOf(result.getPlayerName()));
        scoreView.setText(String.valueOf(result.getScore()));

        // return view
        return convertView;
    }
}

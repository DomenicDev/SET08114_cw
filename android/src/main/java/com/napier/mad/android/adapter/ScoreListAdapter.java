package com.napier.mad.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jme.game.R;
import com.napier.mad.android.persistence.GameResult;

import java.util.List;

public class ScoreListAdapter extends ArrayAdapter<GameResult> {

    private Context context;

    public ScoreListAdapter(@NonNull Context context, List<GameResult> items) {
        super(context, 0, items);
        this.context = context;
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

        // set gold, silver and bronze color for top 3
        if (position == 0) {
            convertView.setBackgroundColor(Color.parseColor("#FFDF00"));
        } else if (position == 1) {
            convertView.setBackgroundColor(Color.parseColor("#C0C0C0"));
        } else if (position == 2) {
            convertView.setBackgroundColor(Color.parseColor("#cd7f32"));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        // we want the top 3 to be sized bigger
        if (position < 3) {
            convertView.setMinimumHeight(80);
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

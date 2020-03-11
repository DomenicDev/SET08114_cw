package com.napier.mad;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.jme.game.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScoresActivity extends Activity {

    private ArrayAdapter<GameResult> resultArrayAdapter;
    private List<GameResult> resultsToShow = new ArrayList<>();
    private PlayerStatsSQLiteDBHelper localDB;
    private FirebaseGameScoreHandler globalDb;

    private Button localButton;
    private Button globalButton;

    private boolean showGlobal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        // create instances for accessing game results later
        this.localDB = new PlayerStatsSQLiteDBHelper(this);
        this.globalDb = new FirebaseGameScoreHandler(this);

        // we use are own adapter type for this list
        this.resultArrayAdapter = new ScoreListAdapter(this, resultsToShow);

        ListView listView = findViewById(R.id.score_list_view);
        listView.setAdapter(resultArrayAdapter);

        // create button instances
        initButtons();

        // show local results on activity startup
        loadAndShowLocalResults();
    }

    private void initButtons() {
        Button homeButton = findViewById(R.id.score_activity_home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        localButton = findViewById(R.id.score_activity_local_button);
        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAndShowLocalResults();
            }
        });

        globalButton = findViewById(R.id.score_activity_global_button);
        globalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAndShowGlobalResults();
            }
        });
    }

    private void loadAndShowGlobalResults() {
        if (showGlobal) {
            // already shown, no need to show them again
            return;
        }
        globalDb.getTop100(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<FirebaseScoreEntry> results = queryDocumentSnapshots.toObjects(FirebaseScoreEntry.class);
                List<GameResult> gameResults = convertToGameResults(results);
                setResultsToShow(gameResults, true);
            }
        });
    }

    private List<GameResult> convertToGameResults(List<FirebaseScoreEntry> firebaseEntries) {
        List<GameResult> results = new ArrayList<>();
        for (FirebaseScoreEntry entry : firebaseEntries) {
            String playerName = entry.getName();
            Long value = entry.getScore();
            GameResult gameResult = new GameResult(playerName, value);
            results.add(gameResult);
        }
        return results;
    }

    public void loadAndShowLocalResults() {
        List<GameResult> results = localDB.getResults();
        setResultsToShow(results, false);
    }

    private void setResultsToShow(Collection<GameResult> results, boolean showGlobal) {
        this.showGlobal = showGlobal;
        this.resultsToShow.clear();
        this.resultsToShow.addAll(results);

        resultArrayAdapter.clear();
        resultArrayAdapter.addAll(results);

        updateButtons();
    }

    private void updateButtons() {
        localButton.setHovered(!showGlobal);
        globalButton.setHovered(showGlobal);
    }


}

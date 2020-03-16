package com.napier.mad.android.persistence;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirebaseGameScoreHandler {

    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_SCORE = "score";

    private final Context context;

    public FirebaseGameScoreHandler(Context context) {
        this.context = context;
    }

    public void uploadScore(String player, int score) {
        if (player == null || player.isEmpty() || score <= 0) {
            return;
        }
        // get firebase reference
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // create map containing game results
        Map<String, Object> values = new HashMap<>();
        values.put(ATTRIBUTE_NAME, player);
        values.put(ATTRIBUTE_SCORE, score);

        // upload game results
        db.collection("/scores/").add(values);
    }

    public void getTop100(OnSuccessListener<QuerySnapshot> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference rf = db.collection("scores");
        Query query = rf.orderBy("score", Query.Direction.DESCENDING).limit(100);
        query.get().addOnSuccessListener(listener);
    }

}

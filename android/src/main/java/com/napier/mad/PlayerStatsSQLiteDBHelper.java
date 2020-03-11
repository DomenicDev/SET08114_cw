package com.napier.mad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatsSQLiteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PLAYER_STATS";
    private static final int DATABASE_VERSION = 1;

    private static final String RESULT_TABLE_NAME = "result";
    private static final String RESULT_COLUMN_ID = "id";
    private static final String RESULT_COLUMN_NAME = "name";
    private static final String RESULT_COLUMN_SCORE = "score";


    public PlayerStatsSQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RESULT_TABLE_NAME + " ( " +
                RESULT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RESULT_COLUMN_NAME + " TEXT, " +
                RESULT_COLUMN_SCORE + " LONG " + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RESULT_TABLE_NAME);
        onCreate(db);
    }

    public void insertResult(String name, int score) {
        ContentValues values = new ContentValues();
        values.put(RESULT_COLUMN_NAME, name);
        values.put(RESULT_COLUMN_SCORE, score);
        getWritableDatabase().insert(RESULT_TABLE_NAME, null, values);
    }

    public List<GameResult> getResults() {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT " + RESULT_COLUMN_NAME + ", " + RESULT_COLUMN_SCORE +
                " FROM " + RESULT_TABLE_NAME +
                " ORDER BY " + RESULT_COLUMN_SCORE + " DESC", null);

        List<GameResult> results = new ArrayList<>();
        while (cursor.moveToNext()) {
            String playerName = cursor.getString(0);
            Long score = cursor.getLong(1);
            GameResult gameResult = new GameResult(playerName, score);
            results.add(gameResult);
        }
        cursor.close();
        return results;
    }
}

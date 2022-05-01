package edu.andrews.cptr252.nathanfernandez.quiz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.andrews.cptr252.nathanfernandez.quiz.database.QuestionCursorWrapper;
import edu.andrews.cptr252.nathanfernandez.quiz.database.QuestionDbHelper;
import edu.andrews.cptr252.nathanfernandez.quiz.database.QuestionDbSchema.QuestionTable;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/** Mange list of questions. This is a singleton
 * class. Only one instance of this class can
 * be created.
 */

public class QuestionList {
    /** Instance Variable for question list */
    private static QuestionList sOurInstance;

    /** List of Questions */
    //private ArrayList<Question> mQuestions;
    /** SQLite DB where questions are stored */
    private SQLiteDatabase mDatabase;

    /**
     * Pack bug information into a ContentValues object
     * @param question to pack
     * @return resulting contentValues object
     */
    public static ContentValues getContentValues(Question question) {
        ContentValues values = new ContentValues();
        values.put(QuestionTable.Cols.UUID, question.getId().toString());
        values.put(QuestionTable.Cols.QUESTION, question.getQuestion());
        values.put(QuestionTable.Cols.TRUE, question.isTrue() ? 1 : 0);

        return values;
    }

    /**
     * Build a query for Question DB.
     * @param whereClause defines the where clause of a SQL query
     * @param whereArgs defines where arguments for a SQL query
     * @return Object defining a SQL query
     */
    private QuestionCursorWrapper queryQuestions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                QuestionTable.NAME,
                null,     // Columns - null selects all columns
                whereClause,
                whereArgs,
                null,     // groupBy
                null,     // having
                null      // orderBy
        );
        return new QuestionCursorWrapper(cursor);
    }

    /** Reference to information about app environment */
    private Context mAppContext;

    /** Tag for message log */
    private static final String TAG = "QuestionList";
    /** name of JSON file containing list of questions */
    private static final String FILENAME = "questions.json";
    /** Reference to JSON serializer for a list of questions */
    private QuestionJSONSerializer mSerializer;


    /** Private Constructor */
    private QuestionList(Context appContext){
       // mAppContext = appContext;
        mAppContext = appContext.getApplicationContext();

        // open DB file or create it if itdoes not already exist
        // if the DB is older version, onUpgrade will be called
        mDatabase = new QuestionDbHelper(mAppContext).getWritableDatabase();
    }
    /**
     * Return one and only instance of the bug list.
     * (If it does not exist, create it).
     * @param c is the Application context
     * @return Reference to the bug list
     */
    public static QuestionList getInstance(Context c) {
        if (sOurInstance == null){
            sOurInstance = new QuestionList(c.getApplicationContext());
        }
        return sOurInstance;
    }
    /**
     * Return List of Questions
     * @return Array of Question Objects
     */
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        QuestionCursorWrapper cursor = queryQuestions(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                questions.add(cursor.getQuestion());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return questions;
    }

    /**
     * Return the question with a given id.
     * @param id Unique id for the question
     * @return The Question object or null if not found
     */
    public Question getQuestion(UUID id) {
        QuestionCursorWrapper cursor = queryQuestions(QuestionTable.Cols.UUID + " = ? ",
                new String[] {id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getQuestion();
        } finally {
            cursor.close();
        }
    }
    /**
     * Add a question to the list
     * @param question is the question to add.
     */
    public void addQuestion(Question question) {
        ContentValues values = getContentValues(question);
        mDatabase.insert(QuestionTable.NAME, null, values);
    }

    /**
     * Delete a given question from the list of questions
     */
    public void deleteQuestion(Question question) {
        String uuidString = question.getId().toString();
        mDatabase.delete(QuestionTable.NAME,
                QuestionTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
    }
    /**
     * Update information for a given bug.
     * @param question contains the latest information for the bug.
     */
    public void updateQuestion(Question question) {
        String uuidString = question.getId().toString();
        ContentValues values = getContentValues(question);

        mDatabase.update(QuestionTable.NAME, values,
                QuestionTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
    }
}

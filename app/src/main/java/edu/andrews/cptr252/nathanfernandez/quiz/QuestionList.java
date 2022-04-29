package edu.andrews.cptr252.nathanfernandez.quiz;

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
    private ArrayList<Question> mQuestions;

    /** Reference to information about app environment */
    private Context mAppContext;

    /** Tag for message log */
    private static final String TAG = "QuestionList";
    /** name of JSON file containing list of questions */
    private static final String FILENAME = "questions.json";
    /** Reference to JSON serializer for a list of questions */
    private QuestionJSONSerializer mSerializer;

    /**
     * Write question list to JSON file.
     * @return true if successful, false otherwise
     */
    public boolean saveQuestions() {
        try {
            mSerializer.saveQuestions(mQuestions);
            Log.d(TAG, "Questions saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving questions: " + e);
            return false;
        }
    }

    /** Private Constructor */
    private QuestionList(Context appContext){
        mAppContext = appContext;

        // create our serializer to load and save questions
        mSerializer = new QuestionJSONSerializer(mAppContext, FILENAME);

        try {
            // load questions from JSON File
            mQuestions = mSerializer.loadQuestions();
        } catch (Exception e) {
            // unable to load from file, so create empty question list
            // either file does not exist (that's fine)
            // or file containts error (not ideal)
            mQuestions = new ArrayList<>();
            Log.e(TAG, "Error loading questions: " + e);
        }
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
    public ArrayList<Question> getQuestions() {return mQuestions;}

    /**
     * Return the question with a given id.
     * @param id Unique id for the question
     * @return The Question object or null if not found
     */
    public Question getQuestion(UUID id) {
        for (Question question : mQuestions) {
            if (question.getId().equals(id))
                return question;
        }
        return null;
    }
    /**
     * Add a question to the list
     * @param question is the question to add.
     */
    public void addQuestion(Question question) {
        mQuestions.add(question);
        saveQuestions();
    }
}

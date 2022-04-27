package edu.andrews.cptr252.nathanfernandez.quiz;

import android.content.Context;
import java.util.ArrayList;

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

    /** Private Constructor */
    private QuestionList(Context appContext){
        mAppContext = appContext;
        mQuestions = new ArrayList<>();
        for (int i=0; i < 10; i++) {
            Question question = new Question();
            question.setQuestion("Question #" + (i+1));
            mQuestions.add(question);
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
}

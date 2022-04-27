package edu.andrews.cptr252.nathanfernandez.quiz;

import java.util.UUID;

/**
*   Manage information for any specified quiz question
*/
public class Question {
    //Unique ID for question
    private UUID mId;

    //The question that the user will enter
    private String mQuestion;

    /** Is the answer true or false? */
    private boolean mAnswer;

    /**
     * Create and initialize new question
     */
    public void Question() {
        //Generate unique id for new question
        mId = UUID.randomUUID();
    }

    //setters and getters for question answer
    public boolean isAnswer() { return mAnswer; }
    public void setAnswer(boolean answer) { mAnswer = answer; }

    /**
     * Return unique Id for the question
     * @return Question Id;
     */
    public UUID getId(){
        return mId;
    }

    /**
     * Return the question
     * @return the question
     */
    public String getQuestion() {
        return mQuestion;
    }

    /**
     * Provide the question
     * @param question New Question
     */
    public void setQuestion(String question){
        mQuestion = question;
    }

}

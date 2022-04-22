package edu.andrews.cptr252.nathanfernandez.quiz;

import java.util.UUID;

/**
*   Manage information for any specified quiz question
*/
public class QuizEditor {
    //Unique ID for question
    private UUID mId;

    //The question that the user will enter
    private String mQuestion;

    /**
     * Create and initialize new question
     */
    public void Question() {
        //Generate unique id for new question
        mId = UUID.randomUUID();
    }

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

    //TODO: Set up a method for the question answers (true/false)
}

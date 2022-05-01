package edu.andrews.cptr252.nathanfernandez.quiz;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
*   Manage information for any specified quiz question
*/
public class Question {
    /** Unique ID for question */
    private UUID mId;

    /**The question that the user will enter */
    private String mQuestion;

    /** Is the answer true? */
    private boolean mTrue;

    /** JSON attribute for question id */
    private static final String JSON_ID = "id";
    /** JSON attribute for question title */
    private static final String JSON_QUESTION = "question";
    /** JSON attribute for question true status */
    private static final String JSON_TRUE = "true";

    /**
     * Initialize a new question from a JSON object
     * @param json is the JSON object for a question
     * @throws JSONException
     */
    public Question(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mQuestion = json.optString(JSON_QUESTION);
        mTrue = json.getBoolean(JSON_TRUE);
    }

    /**
     * Write the question to a JSON object
     * @return JSON object containing the question info
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_ID, mId.toString());
        jsonObject.put(JSON_QUESTION, mQuestion);
        jsonObject.put(JSON_TRUE, mTrue);

        return jsonObject;
    }

    public Question(UUID id) {
        mId = id;
    }
    /**
     * Create and initialize new question
     */
    public Question() {
        this(UUID.randomUUID());
    }

    //setters and getters for question answer
    public boolean isTrue() { return mTrue; }
    public void setTrue(boolean True) { mTrue = True; }

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

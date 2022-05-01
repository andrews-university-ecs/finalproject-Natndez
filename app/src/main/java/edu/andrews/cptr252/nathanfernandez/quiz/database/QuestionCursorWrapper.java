package edu.andrews.cptr252.nathanfernandez.quiz.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import edu.andrews.cptr252.nathanfernandez.quiz.Question;
import edu.andrews.cptr252.nathanfernandez.quiz.database.QuestionDbSchema.QuestionTable;

/**
 * Handle results from DB queries
 */
public class QuestionCursorWrapper extends CursorWrapper {
    public QuestionCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    /**
     * Extract question information from a query.
     * @return Question that resulted from the query
     */
    public Question getQuestion() {
        String uuidString = getString(getColumnIndex(QuestionTable.Cols.UUID));
        String question1 = getString(getColumnIndex(QuestionTable.Cols.QUESTION));
        int isTrue = getInt(getColumnIndex(QuestionTable.Cols.TRUE));

        Question question = new Question(UUID.fromString(uuidString));
        question.setQuestion(question1);
        question.setTrue(isTrue != 0);
        return question;
    }

}

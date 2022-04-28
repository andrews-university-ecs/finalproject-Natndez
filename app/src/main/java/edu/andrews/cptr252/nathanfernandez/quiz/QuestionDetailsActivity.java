package edu.andrews.cptr252.nathanfernandez.quiz;

import androidx.fragment.app.Fragment;

import java.util.UUID;

/**
 * Activity that hosts QuestionDetailsFragment
 */


public class QuestionDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        //QuestionListFragment now launches QuestionDetailsActivity with a specific
        //qyestion id. Get the intent sent to this activity from the QuestionListFragment
        UUID questionId = (UUID)getIntent().getSerializableExtra(QuestionDetailsFragment.EXTRA_QUESTION_ID);

        //Create a new instance of the QuestionDetailsFragment
        //with the Question id as an argument
        return QuestionDetailsFragment.newInstance(questionId);
    }

}
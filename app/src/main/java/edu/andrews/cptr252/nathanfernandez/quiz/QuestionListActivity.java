package edu.andrews.cptr252.nathanfernandez.quiz;

import androidx.fragment.app.Fragment;

/**
 * Activity for displaying question list
 */
public class QuestionListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new QuestionListFragment();
    }

}

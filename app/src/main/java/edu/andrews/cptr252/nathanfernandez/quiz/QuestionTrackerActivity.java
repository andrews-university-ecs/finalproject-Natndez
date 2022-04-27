package edu.andrews.cptr252.nathanfernandez.quiz;

import androidx.fragment.app.Fragment;

public class QuestionTrackerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new QuestionDetailsFragment();
    }

}
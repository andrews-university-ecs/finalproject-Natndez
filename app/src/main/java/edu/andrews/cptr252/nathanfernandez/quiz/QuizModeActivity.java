package edu.andrews.cptr252.nathanfernandez.quiz;

import androidx.fragment.app.Fragment;

public class QuizModeActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new QuizModeFragment();
    }
}

package edu.andrews.cptr252.nathanfernandez.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class QuizTrackerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new QuizDetailsFragment();
    }

}
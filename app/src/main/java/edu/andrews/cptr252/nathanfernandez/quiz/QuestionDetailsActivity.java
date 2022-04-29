package edu.andrews.cptr252.nathanfernandez.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Activity that hosts QuestionDetailsFragment
 */


public class QuestionDetailsActivity extends FragmentActivity {
    /** ViewPager component that allows users to browse questions by swiping */
    private ViewPager mViewPager;

    /** Array of question */
    private ArrayList<Question> mQuestions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the ViewPager
        mViewPager = new ViewPager(this);
        // ViewPager needs a resource Id
        mViewPager.setId(R.id.viewPager);
        // Set the view for this activity to be the ViewPager
        // (Previously, it used the activity_fragment layout)
        setContentView(mViewPager);

        // get the llist of questions
        mQuestions = QuestionList.getInstance(this).getQuestions();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            // Create a new QuestionDetailsFragment for question at given position in list
            @Override
            public Fragment getItem(int i) {
                Question question = mQuestions.get(i);

                // Create a new instance of the QuestionDetailsFragment
                // with the question id as an argument
                return QuestionDetailsFragment.newInstance(question.getId());
            }
            @Override
            public int getCount() {
                return mQuestions.size();
            }
        });

        // QuestionListFragment now launches QuestionDetailsActivity with a specific question id
        // Get the intent sent to this activity from the QuestionListFragment
        UUID questionId = (UUID) getIntent().getSerializableExtra(QuestionDetailsFragment.EXTRA_QUESTION_ID);

        // Search through the list of questions until we find the question
        // with the same id as the one extracted from the intent
        for (int i = 0; i < mQuestions.size(); i++) {
            if (mQuestions.get(i).getId().equals(questionId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        // If there is an App Bar (AKA ActionBar)
        // display the title of the current question there
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Question question = mQuestions.get(i);
                if (question.getQuestion() != null) {
                    setTitle(question.getQuestion());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
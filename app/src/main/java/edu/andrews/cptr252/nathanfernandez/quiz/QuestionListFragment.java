package edu.andrews.cptr252.nathanfernandez.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Fragment to display list of questions in the editor
 */
public class QuestionListFragment extends Fragment {
    /**Tag for message log */
    private static final String TAG = "QuestionListFragment";

    /**Reference to the list of question in the display */
    private ArrayList<Question> mQuestions;

    /** RecyclerView that displays list of questions */
    private RecyclerView mRecyclerView;

    /** Adapter tha generates/reuses view to display questions */
    private QuestionAdapter mQuestionAdapter;

    public QuestionListFragment(){
        //Empty public constructor is required

    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.quizEditor_label);
        mQuestions = QuestionList.getInstance(getActivity()).getQuestions();

        // use our custom question adapter for generating views for each question
        mQuestionAdapter = new QuestionAdapter(mQuestions, getActivity());

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_list, container, false);

        mRecyclerView = v.findViewById(R.id.question_list_recyclerView);
        // RecyclerView will use our QuestionAdapter to create views for questions
        mRecyclerView.setAdapter(mQuestionAdapter);
        // Use a liner layout when displaying questions
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create and attach our new touch helper for question swipes
        QuestionSwiper questionSwiper = new QuestionSwiper(mQuestionAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(questionSwiper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        return v;
    }

    /**
     * Question list fragment was paused (User was likely editing a question)
     * Notify adapter that the data set (Question List) may have changed.
     * The adapter should update the views
     */
    @Override
    public void onResume() {
        super.onResume(); // first execute parent's onResume method
        mQuestionAdapter.refreshQuestionListDisplay();
    }

    /**
     * Create a new question, add it to the list and launch the question editor
     */
    private void addQuestion() {
        // Create a new question
        Question question = new Question();
        // add a question to the list
        QuestionList.getInstance(getActivity()).addQuestion(question);
        // create an intent to send to QuestionDetailsActivity
        // add the question Id as an extra so QuestionDetailsFragment can edit it.
        Intent intent = new Intent(getActivity(), QuestionDetailsActivity.class);
        intent.putExtra(QuestionDetailsFragment.EXTRA_QUESTION_ID, question.getId());
        // launch QuestionDetailsActivity which will launch QuestionDetailsFragment
        startActivityForResult(intent, 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_question_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_question:
                // new question icon clicked
                addQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

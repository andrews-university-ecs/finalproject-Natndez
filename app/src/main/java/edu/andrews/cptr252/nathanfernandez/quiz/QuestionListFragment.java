package edu.andrews.cptr252.nathanfernandez.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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
        getActivity().setTitle(R.string.quizEditor_label);
        mQuestions = QuestionList.getInstance(getActivity()).getQuestions();

        // use our custom question adapter for generating views for each question
        mQuestionAdapter = new QuestionAdapter(mQuestions);

        //for now, we'll be listing questions in log
        for(Question quiz: mQuestions){
            Log.d(TAG, quiz.getQuestion());
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_list, container, false);

        mRecyclerView = v.findViewById(R.id.question_list_recyclerView);
        // RecyclerView will use our QuestionAdapter to create views for questions
        mRecyclerView.setAdapter(mQuestionAdapter);
        // Use a liner layout when displaying questions
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
}

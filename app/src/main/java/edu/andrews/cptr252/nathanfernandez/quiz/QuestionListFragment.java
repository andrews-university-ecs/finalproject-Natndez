package edu.andrews.cptr252.nathanfernandez.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Fragment to display list of questions in the editor
 */
public class QuestionListFragment extends Fragment {
    /**Tag for message log */
    private static final String TAG = "QuestionListFragment";

    /**Reference to the list of question in the display */
    private ArrayList<QuizEditor> mQuestions;

    public QuestionListFragment(){
        //Empty public constructor is required
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.quizEditor_label);
        mQuestions = QuestionList.getInstance(getActivity()).getQuestions();

        //for now, we'll be listing questions in log
        for(QuizEditor quiz: mQuestions){
            Log.d(TAG, quiz.getQuestion());
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_list, container, false);

        return v;
    }
}

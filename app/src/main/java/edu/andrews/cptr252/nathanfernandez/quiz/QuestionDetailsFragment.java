package edu.andrews.cptr252.nathanfernandez.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CheckBox;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionDetailsFragment extends Fragment {
    /**  key used to pass the id of a question */
    public static final String EXTRA_QUESTION_ID
            = "edu.andrews.cptr252.nathanfernandez.quiz.question_id";
    /**
     * Tag for logging fragment messages
     */
    public static final String TAG = "QuestionDetailsFragment";
    /**
     * Quiz Question that is being viewed or edited
     */
    private Question mQuestion;
    /**
     * Reference to title field for quiz question
     */
    private EditText mQuestionField;
    /** Reference to Question is True Check Box */
    private CheckBox mTrueCheckBox;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new QuestionDetailsFragment with a given Question ID as an argument
     * @param questionId
     * @return A reference to the new QuestionDetailsFragment
     */
    public static QuestionDetailsFragment newInstance(UUID questionId) {
        // Create new instance of QuestionDetailsFragment
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();

        // Create a new argument for the Bundle object
        //Add the question id as an argument
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_QUESTION_ID, questionId);

        //Pass the bundle (containing question id) to the fragment
        // The bundle will be unpacked in the fragments onCreate(Bundle) method
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Extract Question id from Bundle
        UUID questionId = (UUID)getArguments().getSerializable(QuestionAdapter.EXTRA_QUESTION_ID);

        // Get the question with the id from the Bundle
        //This will be the question that the fragment displays.
        mQuestion = QuestionList.getInstance(getActivity()).getQuestion(questionId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_details, container, false);

        //get reference to EditText box for the question
        mQuestionField = v.findViewById(R.id.question);
        mQuestionField.setText(mQuestion.getQuestion());
        mQuestionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // user typed text, updates the question
                mQuestion.setQuestion(s.toString());
                // Write the new question to the message log for debugging
                Log.d(TAG, "Question changed to " + mQuestion.getQuestion());

            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }



        });

        //Get reference to true check box
        mTrueCheckBox = v.findViewById(R.id.question_true);
        mTrueCheckBox.setChecked(mQuestion.isTrue());
        //Toggle question true status when check box is tapped
        mTrueCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //set the question's solved property
                mQuestion.setTrue(isChecked);
                Log.d(TAG, "Set true statos to " + isChecked);
            }
        });

        return v;

    }

    /**
     * Save the question list to a JSON file when app is paused
     */
    @Override
    public void onPause() {
        super.onPause();
        QuestionList.getInstance(getActivity()).saveQuestions();
    }

}
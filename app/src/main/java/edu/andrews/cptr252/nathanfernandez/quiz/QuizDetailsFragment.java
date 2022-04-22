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
import android.widget.RadioButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizDetailsFragment extends Fragment {
    /** Tag for logging fragment messages */
    public static final String TAG = "QuizDetailsFragment";
    /** Quiz Question that is being viewed or edited */
    private Quiz mQuiz;
    /** Reference to title field for quiz question */
    private EditText mQuestionField;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizDetailsFragment newInstance(String param1, String param2) {
        QuizDetailsFragment fragment = new QuizDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mQuiz = new Quiz(); //Creates new quiz question
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz_details, container, false);

        //get reference to EditText box for the question
        mQuestionField = v.findViewById(R.id.question);
        mQuestionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // user typed text, updates the question
                mQuiz.setQuestion(s.toString());
                // Write the new question to the message log for debugging
                Log.d(TAG, "Question changed to " + mQuiz.getQuestion());

            }

            @Override
            public void afterTextChanged(Editable s) {
                //intentionally left blank
            }
        });

        return v;

    }

    public void onRadioButtonClicked(View view) {
        //Is the button checked?
        boolean checked = ((RadioButton) view).isChecked();

        //Check which radio button was clicked
        switch(view.getId()) {
            case R.id.true_button:
                if(checked)
                    break;
            case R.id.false_button:
                if(checked)
                    break;
        }
    }
}
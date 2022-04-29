package edu.andrews.cptr252.nathanfernandez.quiz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * Adapter responsible for getting the view for the question
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private static final String TAG="QuestionAdapter";
    public static final String EXTRA_QUESTION_ID="edu.andrews.cptr252.nathanfernandez.quiz.question_id";

    /** Used to store reference to list of questions */
    private ArrayList<Question> mQuestions;
    /** Activity hosting the list fragment */
    private Activity mActivity;

    /**
     * Constructor for BugAdapter. Initialize adapter with given list of questions.
     * @param questions list of questions to display
     */
    public QuestionAdapter(ArrayList<Question> questions, Activity activity) {
        mQuestions = questions;
        mActivity = activity;
    }

    /** return reference to activity hosting the question list fragment */
    public Context getActivity() {
        return mActivity;
    }
    /**
     * Put deleted question back into the list
     * @param question to restore
     * @param position in list where question will go
     */
    public void restoreQuestion(Question question, int position) {
        QuestionList.getInstance(mActivity).addQuestion(position, question);
        notifyItemInserted(position);
    }
    /**
     * Create a snackbar with ability to undo question deletion
     */
    private void showUndoSnackbar(final Question question, final int position) {
        // get root view for activity hosting question list fragment
        View view = mActivity.findViewById(android.R.id.content);
        // build message stating which question was deleted
        String questionDeletedText = mActivity.getString(R.string.question_deleted_msg, question.getQuestion());

        // create the snackbar
        Snackbar snackbar = Snackbar.make(view, questionDeletedText, Snackbar.LENGTH_LONG);
        // add an Undo option to the snackbar
        snackbar.setAction(R.string.undo_option, new View.OnClickListener() {
            @Override
                    public void onClick(View view) {
                // undo is selected, restore the deleted item
            restoreQuestion(question, position);
            }
        });
        // Text for UNDO will be yello
        snackbar.setActionTextColor(Color.YELLOW);
        // display snackbar
        snackbar.show();
    }

    /**
     * Remove question from list
     * @param position index of question to remove
     */
    public void deleteQuestion(int position) {
        // save deleted question so we can undo if needed.
        final Question question = mQuestions.get(position);
        // delete question from list
        QuestionList.getInstance(mActivity).deleteQuestion(position);
        // ipdate list of questions in recyclerview
        notifyItemRemoved(position);
        // display snackbar so user may undo delete
        showUndoSnackbar(question, position);
    }

    /**
     * Class to hold references to widgets on a given view.
     */
    public class ViewHolder extends RecyclerView.ViewHolder
                            implements View.OnClickListener {
        /** Textview that displays question */
        public TextView questionTextView;

        /** CheckBpx that displays whether the question is true or not */
        public CheckBox questionTrueCheckBox;

        /** Context hosting the view */
        public Context mContext;


        /** Create a new view holder for a given view item in the bug list */
        public ViewHolder(View itemView) {
            super(itemView);

            //Stores reference to the widget on the view item
            questionTextView = itemView.findViewById(R.id.question_list_item_titleTextView);
            questionTrueCheckBox = itemView.findViewById(R.id.question_list_item_trueCheckBox);

            //Get the context of the view. This will be the activity hosting the view
            mContext = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        /**
         * OnClick listener for the question in the question list.
         * Triggered when the user clicks on one of the questions in the list.
         * @param v is the view for the question that was clicked.
         */
        @Override
        public void onClick(View v) {
            //Get index of the question that was clicked
            int position = getAdapterPosition();
            //For now just display the question... will be able to open question in the future
            if (position != RecyclerView.NO_POSITION) {
                Question question = mQuestions.get(position);

                // start an instance of QuestionDetailsFragment
                Intent i = new Intent(mContext, QuestionDetailsActivity.class);
                //pass the id of the question as an intent
                i.putExtra(QuestionDetailsFragment.EXTRA_QUESTION_ID, question.getId());
                mContext.startActivity(i);
            }
        }
    }

    /**
     * Create a new view to display a question.
     * Return the ViewHolder that stores references to the widgets on the new view.
     */
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Get the layout inflater from parent that contains the question view item
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //inflate the layout to display a question in the list
        View questionView = inflater.inflate(R.layout.list_item_question, parent, false);

        // Create a view holder to store references to the widgets on the new view.
        ViewHolder viewHolder = new ViewHolder(questionView);

        return viewHolder;
    }

    /**
     * Display given question in the view referenced by the given ViewHolder.
     * @param viewHolder Contains references to widgets used to display question.
     * @param position Index of the question in the list
     */
    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder viewHolder, int position) {
        //Get question for given question in the question list
        Question question = mQuestions.get(position);

        //Get reference to widgets stored in ViewHolder
        TextView questionTextView = viewHolder.questionTextView;
        CheckBox questionTrueCheckBox = viewHolder.questionTrueCheckBox;

        // Update widgets on view with bug details
        questionTextView.setText(question.getQuestion());
        questionTrueCheckBox.setChecked(question.isTrue());
    }

    /**
     * Get number of questions in question list.
     */
    @Override
    public int getItemCount() {
        return mQuestions.size();
    }
}

package com.example.shi.quizcheatactivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    // variable name string for whether the answer to the question is true or false
    public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    // variable name string for the status of having shown the answer on savedInstantState issuance
    public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
    // variable name string for the status of whether the user cheated
    public static final String KEY_CHEATER = "cheater";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private Boolean mIsCheater;

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {// if there was a savedInstanceState issuance
            setAnswerShownResult(savedInstanceState.getBoolean(KEY_CHEATER, false));// was the answer displayed before savedInstanceState if so forward that on
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER);// did the user cheat before savedInstanceState
            mAnswerIsTrue = savedInstanceState.getBoolean(EXTRA_ANSWER_IS_TRUE, false);// was the answer to the ? cheated on true
            mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
            if ((mAnswerIsTrue == true) && (mIsCheater == true)) {// if the answer was true, and it was viewed(cheated)
                mAnswerTextView.setText(R.string.true_button);// then display it again
            } else if ((mAnswerIsTrue == false) && (mIsCheater == true)) {//if the answer was false, and it was viewed(cheated)
                mAnswerTextView.setText(R.string.false_button);// display it again
            }

        } else {// no savedInstanceState
            mIsCheater = false;// they haven't cheated yet
            setAnswerShownResult(false);// they haven't seen the answer yet
        }


        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);// the answer was seen
                mIsCheater = true;// the user cheated
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);// save the cheat status of the user onSaveInstanceState issuance
        savedInstanceState.putBoolean(EXTRA_ANSWER_IS_TRUE, mAnswerIsTrue);// get whether ? was True/false onSaveInstanceState issuance
    }
}
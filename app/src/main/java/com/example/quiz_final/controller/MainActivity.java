package com.example.quiz_final.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz_final.R;
import com.example.quiz_final.model.Question;

public class MainActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_CURRENT_INDEX = "currentIndex";
    public static final String EXTRA_KEY_IS_ANSWER_TRUE = "com.example.quiz_final.isAnswerTrue";
    private static final int REQUEST_CODE_CHEAT_ACTIVITY = 0;
    public static final String logTag = "MainActivity";
    private Button mButtonTrue;
    private Button mButtonFalse;
    private TextView mQuestionTextView;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private boolean mIsCheated = false;
    private Button mButtonCheat;
    private int mCurrentIndex = 0;
    private final Question[] mQuestionsBank ={
        new Question(R.string.question_australia, false),
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, true),
        new Question(R.string.question_americas, false),
        new Question(R.string.question_asia, false),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX);
        }

        setContentView(R.layout.activity_main);
        findAllViews();
        setClickListener();
        updateQuestion();

    }
    private void findAllViews(){
        mButtonTrue = findViewById(R.id.button_true);
        mButtonFalse = findViewById(R.id.button_false);
        mButtonNext = findViewById(R.id.button_next);
        mButtonPrevious = findViewById(R.id.button_previous);
        mQuestionTextView = findViewById(R.id.text_view_question);
        mButtonCheat = findViewById(R.id.button_go_to_cheat);
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK || data == null){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT_ACTIVITY){
            mIsCheated = data.getBooleanExtra(CheatActivity.EXTRA_IS_CHEATED, false);
        }
    }
    private void updateQuestion(){
        Question mCurrentQuestion = mQuestionsBank[mCurrentIndex];
        mQuestionTextView.setText(mCurrentQuestion.getTextResId());
    }

    private void checkAnswer(boolean userPressed){
        if(mIsCheated){
            Toast.makeText(this, R.string.cheat_judgment, Toast.LENGTH_SHORT).show();
            mIsCheated = false;
        }else{
            if(mQuestionsBank[mCurrentIndex].isAnswerTrue() == userPressed) {
                Toast toast = Toast.makeText(this, R.string.toast_correct, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this, R.string.toast_incorrect, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    private void setClickListener(){
        mButtonTrue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });
        mButtonFalse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });
        mButtonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = ++mCurrentIndex % mQuestionsBank.length;
                updateQuestion();

            }
        });
        mButtonPrevious.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (--mCurrentIndex + mQuestionsBank.length) % mQuestionsBank.length;
                updateQuestion();
//                disableButtons();
            }
        });
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = ++mCurrentIndex % mQuestionsBank.length;
                updateQuestion();
            }
        });
        if(mButtonCheat != null){
            mButtonCheat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startCheatActivity();
                }
            });
        }
    }
    private void startCheatActivity(){

        boolean isAnswerTrue = mQuestionsBank[mCurrentIndex].isAnswerTrue();
        Intent intent = new Intent(MainActivity.this, CheatActivity.class);
        intent.putExtra(EXTRA_KEY_IS_ANSWER_TRUE, isAnswerTrue);
        startActivityForResult(intent,REQUEST_CODE_CHEAT_ACTIVITY);
    }
}
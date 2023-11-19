package com.example.quiz_final.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz_final.R;

public class CheatActivity extends AppCompatActivity {
    public static final String EXTRA_IS_CHEATED = "com.example.quiz_final.isCheated";
    private Button mButtonCheat;
    private TextView mTextViewAnswer;
    private boolean mIsAnswerTrue;
    private Button mButtonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        extractAnswer();
        findAllViews();
        setClickListeners();
    }
    private void findAllViews() {
        mButtonCheat = findViewById(R.id.button_cheat);
        mButtonBack = findViewById(R.id.button_back);
        mTextViewAnswer = findViewById(R.id.is_answer_true);
    }
    private void extractAnswer() {
        Intent intent = getIntent();
        mIsAnswerTrue = intent.getBooleanExtra(MainActivity.EXTRA_KEY_IS_ANSWER_TRUE, false);
    }
    private void setClickListeners() {
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIsAnswerTrue){
                    mTextViewAnswer.setText(R.string.true_button);
                }else{
                    mTextViewAnswer.setText(R.string.false_button);
                }
                saveResult(true);
            }
        });
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void saveResult(boolean isCheated){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEATED, isCheated);
        setResult(RESULT_OK,intent);
    }

}
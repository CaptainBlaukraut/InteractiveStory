package com.maximilianobpacher.interactivestory.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximilianobpacher.interactivestory.R;
import com.maximilianobpacher.interactivestory.model.Page;
import com.maximilianobpacher.interactivestory.model.Story;


public class StoryActivity extends Activity {

    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story mStory = new Story();

    private ImageView mImageView;
    private TextView mTextView;
    private Button mButton1;
    private Button mButton2;
    private String mName;
    private Page mCurrentPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));

        if(mName == null){
            mName = "Friend";
        }
        Log.d(TAG,mName);

        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mButton1 = (Button) findViewById(R.id.choiceButton1);
        mButton2 = (Button) findViewById(R.id.choiceButton2);

        loadPage(0);
    }

    private void loadPage(int choice){

        mCurrentPage = mStory.getPage(choice);

        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId());
        mImageView.setImageDrawable(drawable);

        String pageText = mCurrentPage.getText();
        // Add the name if placeholder included, won't add if no placeholder
        pageText = String.format(pageText, mName);

        mTextView.setText(pageText);

        if(mCurrentPage.isFinal()){
            mButton1.setVisibility(View.INVISIBLE);
            mButton2.setText("PLAY AGAIN");
            mButton2.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        else {
            mButton1.setText(mCurrentPage.getChoice1().getText());
            mButton2.setText(mCurrentPage.getChoice2().getText());

            mButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }

    }

}

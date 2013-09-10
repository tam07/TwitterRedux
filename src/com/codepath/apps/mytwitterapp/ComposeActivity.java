package com.codepath.apps.mytwitterapp;

import org.json.JSONObject;

import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends Activity {
	
	Button cancelButton;
	Button tweetButton;
	EditText tweetTextField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setupViews();
		setTitle("Compose Activity");
	}

	public void setupViews() {
	cancelButton = (Button)findViewById(R.id.cancel_button_id);
	tweetButton = (Button)findViewById(R.id.tweet_button_id);
	tweetTextField = (EditText)findViewById(R.id.tweet_text_id);
		
	}
	
	public void onTweet(View v) {
		/* 1.  send POST request using TwitterClient with tweetText
		 * 2.  create intent to go to TimelineActivity.  When it goes to TimelineActivity,
		 *     onCreate will be called which pulls all the timeline tweets, including the one you 
		 *     just posted.  This step should just call onCancel because they do the same thing
		 */
		String tweetText = tweetTextField.getText().toString();
		MyTwitterApp.getRestClient().postTweet(tweetText, new JsonHttpResponseHandler() {
		@Override
			public void onSuccess(JSONObject jsonUser) {
			    //post done, do the same thing as cancelling, reload timeline
			    onCancel(null);
			}
		});
	}
	
	// startActivity or startActivityWithResult
	public void onCancel(View v) {
		// Prepare data intent 
		  Intent data = new Intent();
		  data.putExtra("crapKey", "crapValue");
		  setResult(RESULT_OK, data); 
		  // go back to TimelineActivity
		  finish(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

}

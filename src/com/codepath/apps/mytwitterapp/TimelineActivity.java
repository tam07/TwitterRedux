package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

/* Refresh button should trigger onCreate call again
 * Compose button should trigger startActivity to ComposeActivity
 * 
 */
public class TimelineActivity extends Activity {

	String currentUsername;
	Button refreshButton;
	Button composeButton;
	
	static final int REQUEST_CODE=100;
	
	public void setupViews() {
		refreshButton = (Button)findViewById(R.id.cancel_button_id);
		composeButton = (Button)findViewById(R.id.tweet_button_id);
	}
	
	// take you to compose screen(activity)
	public void onComposeClick(MenuItem mi) {
        Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra("mode", 2); // pass arbitrary data to launched activity
		// REQUEST_CODE can be any value we like, used to determine the result later
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
		 try {
			 /* sometimes there is a delay with the post and we get here too fast not 
			  * showing your post, add a delay
			  */
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     getAndDisplayTimeline();
	  }
	} 
	
	public void onRefreshClick(MenuItem mi) {
	    getAndDisplayTimeline();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		setupViews();
		SharedPreferences sessionData = getApplicationContext().getSharedPreferences("MyPref", 0);
		String logged_in_person = sessionData.getString("loggedInUser", "default since real not found!");
		setTitle("@" + logged_in_person);
		//getActionBar().setTitle(title);
		getAndDisplayTimeline();
	}
	
	
	/* getHomeTimeline submits GET request.  We pass in a defined handler which is the same
	 * as a listener.  Once we get a response back we enter onSuccess.  Assume jsonTweets
	 * has the tweets we want and use them to fill our Timeline view using the listview and
	 * custom adapter
	 */
	public void getAndDisplayTimeline() {
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// from json to arraylist of tweets that comprise timeline
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
				TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
				// use custom array adapter TweetsAdapter to fill lvTweets ListView
				lvTweets.setAdapter(adapter);
				// Log.d("DEBUG", jsonTweets.toString());
			}
		});
		
	}
	
	
	public void getCurrentUser() {
		MyTwitterApp.getRestClient().getCurrentCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				User currentUser = User.fromJson(jsonUser);
				currentUsername = currentUser.getScreenName();
				Toast.makeText(getApplicationContext(), currentUser.getScreenName(), 
						       Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}

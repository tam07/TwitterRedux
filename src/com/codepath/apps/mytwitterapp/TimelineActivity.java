package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.mytwitterapp.fragments.MentionsFragment;
import com.codepath.apps.mytwitterapp.fragments.TweetsListFragment;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;


/* Refresh button should trigger onCreate call again
 * Compose button should trigger startActivity to ComposeActivity
 * 
 */
public class TimelineActivity extends FragmentActivity implements TabListener {

	String currentUsername;
	Button refreshButton;
	Button composeButton;
	//TweetsListFragment fragmentTweets;
	
	static final int REQUEST_CODE=100;
	
	SharedPreferences sp;
	Editor e;
	
	public void setupViews() {
		refreshButton = (Button)findViewById(R.id.cancel_button_id);
		composeButton = (Button)findViewById(R.id.tweet_button_id);
	}
	
	// take you to compose screen(activity)
	public void onComposeClick(MenuItem mi) {
		sp = getApplicationContext().getSharedPreferences("MyPref", 0);
		e = sp.edit();
		
		MyTwitterApp.getRestClient().getCurrentCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject user) {
				User currentUser = User.fromJson(user);
				String profileImgLink = currentUser.getProfileImageUrl();
				e.putString("profileImgUrl", profileImgLink);
				e.commit();
			}
		});
		
        Intent i = new Intent(this, ComposeActivity.class);
		i.putExtra("mode", 2); // pass arbitrary data to launched activity
       
		// REQUEST_CODE can be any value we like, used to determine the result later
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
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
		
		setupNavigationTabs();
		getCurrentUser();
	}
	
	
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimelineFragment")
				     .setIcon(R.drawable.ic_home).setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsTimelineFragment")
				      .setIcon(R.drawable.ic_mentions).setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
		
		
	}

	/* getHomeTimeline submits GET request.  We pass in a defined handler which is the same
	 * as a listener.  Once we get a response back we enter onSuccess.  Assume jsonTweets
	 * has the tweets we want and use them to fill our Timeline view using the listview and
	 * custom adapter
	 */
	public void getAndDisplayTimeline() {
		//fragmentTweets = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
		
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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

	/* This activity's layout, activity_timeline.xml is just a frame layout called frame_conainer.  It is a placeholder whose
	 * contents are filled dynamically upon one of 2 tabs being selected. */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag() == "HomeTimelineFragment") {
			// set the fragment in the framelayout to home timeline
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		}
		else {
			// set the fragment in the framelayout to mentions timeline
			fts.replace(R.id.frame_container, new MentionsFragment());
		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	// LAUNCHES PROFILE ACTIVITY FROM THIS ACTIVITY, TimelineActivity
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("userToDisplay", currentUsername);
		startActivity(i);
	}
	

}

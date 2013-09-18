package com.codepath.apps.mytwitterapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends FragmentActivity {
	
	String userToDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		// screenname of user whose profile we want to view
		userToDisplay = getIntent().getStringExtra("userToDisplay");
        loadProfileInfo();
        
	}

	// GETs current user info and displays header
	private void loadProfileInfo() {
		MyTwitterApp.getRestClient().getAnyUser(userToDisplay, new 
		JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonUsers) {
				/* in the request you can specify multiple screen_names, so it returns
				 * an array.  Get the jsonObject of the user I specified via indexing.
				 * Then use model to deserialize into User object.  Finally use
				 * User object to fill in the view data */
				JSONObject jsonUser;
				try {
					jsonUser = jsonUsers.getJSONObject(0);
					User u = User.fromJson(jsonUser);
					getActionBar().setTitle("@" + u.getScreenName());
					populateProfileHeader(u);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable arg0, JSONArray arg1) {
				// TODO Auto-generated method stub
				//super.onFailure(arg0, arg1);
				Toast.makeText(getApplicationContext(), "https://api.twitter.com/1.1/" +
				"users/lookup.json?screen_name=" + userToDisplay, Toast.LENGTH_LONG).show();
			}
		});
	}

	
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}

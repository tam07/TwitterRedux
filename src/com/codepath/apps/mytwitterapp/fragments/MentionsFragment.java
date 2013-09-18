package com.codepath.apps.mytwitterapp.fragments;

import org.json.JSONArray;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;

public class MentionsFragment extends TweetsListFragment {
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	// REMEMBER, JsonHttpResponseHandler is a SUBCLASS of AsyncHttpResponseHandler
	MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(JSONArray jsonTweets) {
			getAdapter().addAll(Tweet.fromJson(jsonTweets));
		}
	});
}
}

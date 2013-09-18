package com.codepath.apps.mytwitterapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	String username = getActivity().getIntent().getStringExtra("userToDisplay");
    	MyTwitterApp.getRestClient().getUserTimeline(username, new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(JSONArray jsonTweets) {
    			getAdapter().addAll(Tweet.fromJson(jsonTweets));
    		}
    		
            @Override
            public void onFailure(Throwable arg0, JSONArray arg1) {
            	// TODO Auto-generated method stub
            	//super.onFailure(arg0, arg1);
    			Toast.makeText(getActivity(), "GET user timeline failed!", Toast.LENGTH_LONG);
            }
    	});
    }
}

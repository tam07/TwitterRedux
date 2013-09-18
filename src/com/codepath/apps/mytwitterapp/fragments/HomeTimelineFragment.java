package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.widget.Toast;

public class HomeTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// calling parent's onCreate.  TweetsListFragment doesn't define onCreate, so we're using Fragment's
		super.onCreate(savedInstanceState);
	
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				// from jsonarray of all tweets to arraylist of tweets so we can use adapter's addAll
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
			    getAdapter().addAll(tweets);
			}
			
	       @Override
	       public void onFailure(Throwable arg0, JSONArray arg1) {
		   // TODO Auto-generated method stub
		   //super.onFailure(arg0, arg1);
	    	   Toast.makeText(getActivity(), "GET request failed, JSONArray onFailure override", Toast.LENGTH_LONG).show();
	       }
	       
	       @Override
	       public void onFailure(Throwable arg0, JSONObject arg1) {
	    	// TODO Auto-generated method stub
	    	//super.onFailure(arg0, arg1);
	    	   Toast.makeText(getActivity(), "GET request failed, JSONObject onFailure override", Toast.LENGTH_LONG).show();
	    }
		});
	}
}

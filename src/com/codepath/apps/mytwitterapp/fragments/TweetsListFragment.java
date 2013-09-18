package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TweetsListFragment extends Fragment {
    private TweetsAdapter adapter;

	@Override
    public View onCreateView(LayoutInflater inf, ViewGroup parent,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	//return super.onCreateView(inflater, container, savedInstanceState);
    	return inf.inflate(R.layout.fragment_tweets_list, parent, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	    adapter = new TweetsAdapter(getActivity(), tweets);
	    ListView lv = (ListView)getActivity().findViewById(R.id.lvTweets);
	    lv.setAdapter(adapter);
    	// getActivity().
    }
    
    public TweetsAdapter getAdapter() {
    	return adapter;
    }
}

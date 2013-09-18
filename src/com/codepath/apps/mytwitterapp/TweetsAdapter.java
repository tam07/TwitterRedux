package com.codepath.apps.mytwitterapp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {
    SharedPreferences pref;
    Editor edit;
    String username;
    
	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	// fill out a tweet "row" and return this row
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}

		/* get the model instance everything is based off of -
		 * image, name, tweet text.  first row is at position 0
		 */
		Tweet tweet = getItem(position);
		username = tweet.getUser().getScreenName();
        ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
        imageView.setTag(username);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// getContext() here returns the activity this tweet is in
				Activity currentActivity = (Activity)getContext();
				String currentActivityName = currentActivity.getClass().getName();
				if(!currentActivityName.endsWith("ProfileActivity"))
				{	
				    Intent i = new Intent(getContext(), ProfileActivity.class);
				    i.putExtra("userToDisplay", v.getTag().toString());	
					getContext().startActivity(i);
				}
			}
		});

		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + " <small><font color='#777777'>@" +
				tweet.getUser().getScreenName() + "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));

		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		
		TextView tsView = (TextView) view.findViewById(R.id.tvTimestamp);
		tsView.setText(Html.fromHtml(tweet.getTimestamp()));
		
		/*pref = getApplicationContext().getSharedPreferences("MyPref", 0);// 0 - for private mode
    	editor = pref.edit();*/

		return view;
	}
	
}
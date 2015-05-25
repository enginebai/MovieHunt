package com.moviebomber.utils;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.moviebomber.R;

/**
 * Created by engine on 15/5/25.
 */
public class GAApplication extends Application {
	public static GoogleAnalytics analytics;
	public static Tracker tracker;

	@Override
	public void onCreate() {
		analytics = GoogleAnalytics.getInstance(this);
		analytics.setLocalDispatchPeriod(1800);

		tracker = analytics.newTracker("UA-48608391-3"); // Replace with actual tracker/property Id
		tracker.enableExceptionReporting(true);
		tracker.enableAdvertisingIdCollection(true);
		tracker.enableAutoActivityTracking(true);
	}

	public static Tracker getTracker(Context context) {
		if (tracker == null) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
			tracker = analytics.newTracker(R.xml.ga_tracker);
		}
		return tracker;
	}
}

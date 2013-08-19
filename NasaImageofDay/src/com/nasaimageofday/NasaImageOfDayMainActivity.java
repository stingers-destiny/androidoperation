package com.nasaimageofday;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * @ author : ammar
 */
public class NasaImageOfDayMainActivity extends FragmentActivity {
	private static final String URL = "http://www.nasa.gov/rss/image_of_the_day.rss";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nasa_image_of_day_main);
		setVisibilityForMoreOrLessLink(false);
		refresh();
	}

	public void refreshActivity(View view) {
		refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.nasa_image_of_day_main, menu);
		return true;
	}

	private void refresh() {
		if (isInternetAvailable()) {
			new UpdateRssAsyncTask().execute(URL);
		} else {
			DialogFragment fragment = new NoInternetConnectionDialogFragment();
			fragment.show(getSupportFragmentManager(), "");
		}
	}

	private boolean isInternetAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	private void setVisibilityForMoreOrLessLink(boolean b) {
		SpannableString ss = new SpannableString("Show More");
		ss.setSpan(new StyleSpan(Typeface.ITALIC), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new URLSpan("tel:4155551212"), 13, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		TextView view = (TextView) findViewById(R.id.imageDescriptionMoreOrLess);
		view.setMovementMethod(LinkMovementMethod.getInstance());

	}

	private class UpdateRssAsyncTask extends
			AsyncTask<String, Integer, RssHandler> {

		@Override
		protected RssHandler doInBackground(String... params) {
			RssHandler rssHandler = new RssHandler(params[0]);
			rssHandler.processFeed();
			return rssHandler;
		}

		public void onPostExecute(RssHandler rssHandler) {
			resetViewValues(rssHandler.getTitle(), rssHandler.getDate(),
					rssHandler.getImage(), rssHandler.getDescription());

		}

		private void resetViewValues(final String title, final String date,
				final Bitmap image, final StringBuffer description) {
			((TextView) findViewById(R.id.title)).setText(title);
			((TextView) findViewById(R.id.publishDate)).setText(date);
			((ImageView) findViewById(R.id.imageDisplay)).setImageBitmap(image);
			((TextView) findViewById(R.id.imageDescription)).setText(description);
			String smallDescription = (String)((description == null || description.length() < 20) ? description : description.substring(0, 20));
			((TextView) findViewById(R.id.imageDescriptionMoreOrLess)).setText(smallDescription);
		}
	}
}

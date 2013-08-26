package com.nasaimageofday;

import android.graphics.Bitmap;

public class ImageOfDayDetails {
	private Bitmap image;
	private String title;
	private StringBuffer fullDescription;
	private String pubDate;
	private boolean showLess;
	private int showMoreButton;
	
	public ImageOfDayDetails(Bitmap image, String title, StringBuffer fullDescription, String pubDate, boolean showLess, int showMoreButton){
		this.title = title;
		this.fullDescription = fullDescription;
		this.image = image;
		this.pubDate = pubDate;
		this.showLess = showLess;
		this.showMoreButton = showMoreButton;
	}

	public int getShowMoreButton() {
		return showMoreButton;
	}

	public Bitmap getImage() {
		return image;
	}

	public String getTitle() {
		return title;
	}

	public StringBuffer getFullDescription() {
		return fullDescription;
	}

	public String getPubDate() {
		return pubDate;
	}

	public boolean isShowLess() {
		return showLess;
	}

}

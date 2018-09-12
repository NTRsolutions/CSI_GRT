package com.growatt.shinephone.video;

import android.graphics.Bitmap;


public class ItemBean {String uriPath;
Bitmap bitmap;
String duration;
String size;
String title;
boolean checked;
boolean Show;
public String getUriPath() {
	return uriPath;
}
public void setUriPath(String uriPath) {
	this.uriPath = uriPath;
}
public Bitmap getBitmap() {
	return bitmap;
}
public void setBitmap(Bitmap bitmap) {
	this.bitmap = bitmap;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public String getSize() {
	return size;
}
public void setSize(String size) {
	this.size = size;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public boolean isChecked() {
	return checked;
}
public void setChecked(boolean checked) {
	this.checked = checked;
}
public boolean isShow() {
	return Show;
}
public void setShow(boolean show) {
	Show = show;
}
@Override
public String toString() {
	return "ItemBean [uriPath=" + uriPath + ", bitmap=" + bitmap
			+ ", duration=" + duration + ", size=" + size + ", title=" + title
			+ ", checked=" + checked + ", Show=" + Show + "]";
}
}

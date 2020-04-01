package com.example.lastfm;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ArtistItem{

	@SerializedName("image")
	private List<ImageItem> image;

	@SerializedName("mbid")
	private String mbid;

	@SerializedName("listeners")
	private String listeners;

	@SerializedName("streamable")
	private String streamable;

	@SerializedName("playcount")
	private String playcount;

	@SerializedName("name")
	private String name;

	@SerializedName("url")
	private String url;

	public void setImage(List<ImageItem> image){
		this.image = image;
	}

	public List<ImageItem> getImage(){
		return image;
	}

	public void setMbid(String mbid){
		this.mbid = mbid;
	}

	public String getMbid(){
		return mbid;
	}

	public void setListeners(String listeners){
		this.listeners = listeners;
	}

	public String getListeners(){
		return listeners;
	}

	public void setStreamable(String streamable){
		this.streamable = streamable;
	}

	public String getStreamable(){
		return streamable;
	}

	public void setPlaycount(String playcount){
		this.playcount = playcount;
	}

	public String getPlaycount(){
		return playcount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"ArtistItem{" + 
			"image = '" + image + '\'' + 
			",mbid = '" + mbid + '\'' + 
			",listeners = '" + listeners + '\'' + 
			",streamable = '" + streamable + '\'' + 
			",playcount = '" + playcount + '\'' + 
			",name = '" + name + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}
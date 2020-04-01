package com.example.lastfm;

import com.google.gson.annotations.SerializedName;

public class ArtistInfo{

	@SerializedName("artists")
	private Artists artists;

	public void setArtists(Artists artists){
		this.artists = artists;
	}

	public Artists getArtists(){
		return artists;
	}

	@Override
 	public String toString(){
		return 
			"ArtistInfo{" + 
			"artists = '" + artists + '\'' + 
			"}";
		}
}
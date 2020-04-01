package com.example.lastfm;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Artists{

	@SerializedName("@attr")
	private Attr attr;

	@SerializedName("artist")
	private List<ArtistItem> artist;

	public void setAttr(Attr attr){
		this.attr = attr;
	}

	public Attr getAttr(){
		return attr;
	}

	public void setArtist(List<ArtistItem> artist){
		this.artist = artist;
	}

	public List<ArtistItem> getArtist(){
		return artist;
	}

	@Override
 	public String toString(){
		return 
			"Artists{" + 
			"@attr = '" + attr + '\'' + 
			",artist = '" + artist + '\'' + 
			"}";
		}
}
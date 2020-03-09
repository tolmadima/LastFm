package com.example.lastfm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArtistParser {
    public ArtistParser(JSONArray artist) {
    }

    public List<ArtistsData> parseArtists(JSONObject artist) throws JSONException {
    List<ArtistsData> artists = new ArrayList<>();
    JSONObject artistJSON = artist.getJSONObject("artists");
    Iterator<?> keys = artistJSON.keys();
    while (keys.hasNext()) {
        Object value = artistJSON.get((String) keys.next());
        if (value != null && value instanceof JSONObject) {
            artistJSON = (JSONObject) value;
            String name = artistJSON.getString("name");
            String playcount = artistJSON.getString("playcount");
            String image = artistJSON.getString("image");
            artists.add(new ArtistsData(name, playcount, image));
        }
    }
    return artists;
}
}

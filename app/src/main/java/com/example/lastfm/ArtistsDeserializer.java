package com.example.lastfm;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import static com.example.lastfm.MainActivity.NUMBER_OF_ARTISTS;
import static com.example.lastfm.MainActivity.requestedArtists;

public class ArtistsDeserializer implements JsonDeserializer<Artist>
{
    @Override
    public Artist deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Log.e("Des","Working");
            JsonObject jsonObject = json.getAsJsonObject();
            Artist parsedArtist = new Artist();
        try{
                Gson gson = new Gson();
                JsonObject parsing = jsonObject.getAsJsonObject("artists");
                JsonArray artist = parsing.getAsJsonArray("artist");
                JSONArray artistJson = new JSONArray(gson.toJson(artist));
                for (int i = 0; i < NUMBER_OF_ARTISTS; i++) {
                    JSONObject data = artistJson.getJSONObject(i);
                    parsedArtist.setArtistName(data.getString("name"));
                    parsedArtist.setPlayCount(data.getString("playcount"));
                    requestedArtists.set(i, parsedArtist);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return parsedArtist;
        }
    }

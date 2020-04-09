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
import java.util.ArrayList;
import java.util.List;
public class ArtistsDeserializer implements JsonDeserializer<List<Artist>>
{
    private final String PARSER_PARAM = "artists";
    private final String ARTIST_PARAM = "artist";

    @Override
    public List<Artist> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
            JsonObject jsonObject = json.getAsJsonObject();

            List<Artist> parsedArtists = new ArrayList<>();
            Artist artistData;
        try{
                Gson gson = new Gson();
                JsonObject parsing = jsonObject.getAsJsonObject(PARSER_PARAM);
                JsonArray artist = parsing.getAsJsonArray(ARTIST_PARAM);
                JSONArray artistJson = new JSONArray(gson.toJson(artist));
                for (int i = 0; i < artist.size(); i++) {
                    artistData = gson.fromJson(artist.get(i), Artist.class);
                    parsedArtists.add(i, artistData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return parsedArtists;
        }
    }

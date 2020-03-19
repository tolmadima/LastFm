package com.example.lastfm;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ArtistsDeserializer implements JsonDeserializer<Artists>
{
    @Override
    public Artists deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();

        Artists artist = new Artists();
        artist.setArtistName(jsonObject.get("name").getAsString());

        artist.setPlayCount(jsonObject.get("playcount").getAsString());

        return artist;
    }
}

package com.example.lastfm;


import com.example.lastfm.artist_info.dto.ArtistDataDto;
import com.example.lastfm.artist_info.ArtistInfo;
import com.example.lastfm.artist_info.dto.ArtistInfoDto;
import com.example.lastfm.artist_info.dto.BioDto;
import com.example.lastfm.artist_info.dto.ImageDto;
import com.example.lastfm.artist_info.dto.StatsDto;

import static com.example.lastfm.Constants.PICTURE_SIZE;


public class ArtistMapper {


    public ArtistInfo map(ArtistInfoDto artistInfoDto){
        ArtistInfo choosenArtist = new ArtistInfo();
        ArtistDataDto artist = artistInfoDto.getArtist();
        choosenArtist.setName(artist.getName());
        BioDto artistBio = artist.getBio();
        StatsDto artistStat = artist.getStats();
        choosenArtist.setPlaycount(artistStat.getPlaycount());
        choosenArtist.setBio(artistBio.getContent());
        ImageDto url = artist.getImage().get(PICTURE_SIZE);
        choosenArtist.setImage(url.getText());
        return choosenArtist;
    }
}

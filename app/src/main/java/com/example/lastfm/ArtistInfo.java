package com.example.lastfm;

import java.util.ArrayList;

    class ArtistInfo {
        Artist ArtistObject;

        public Artist getArtist() {
            return ArtistObject;
        }

        public void setArtist(Artist artistObject) {
            this.ArtistObject = artistObject;
        }
    }

    class ArtistData {
        private String name;
        private String mbid;
        private String url;
        ArrayList<Object> image = new ArrayList<Object>();
        Stats StatsObject;
        Similar SimilarObject;
        Tags TagsObject;
        Bio BioObject;

        public String getName() {
            return name;
        }

        public String getMbid() {
            return mbid;
        }

        public String getUrl() {
            return url;
        }

        public Stats getStats() {
            return StatsObject;
        }

        public Similar getSimilar() {
            return SimilarObject;
        }

        public Tags getTags() {
            return TagsObject;
        }

        public Bio getBio() {
            return BioObject;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMbid(String mbid) {
            this.mbid = mbid;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setStats(Stats statsObject) {
            this.StatsObject = statsObject;
        }

        public void setSimilar(Similar similarObject) {
            this.SimilarObject = similarObject;
        }

        public void setTags(Tags tagsObject) {
            this.TagsObject = tagsObject;
        }

        public void setBio(Bio bioObject) {
            this.BioObject = bioObject;
        }
    }

    class Bio {
        Links LinksObject;
        private String published;
        private String summary;
        private String content;

        public Links getLinks() {
            return LinksObject;
        }

        public String getPublished() {
            return published;
        }

        public String getSummary() {
            return summary;
        }

        public String getContent() {
            return content;
        }

        // Setter Methods

        public void setLinks(Links linksObject) {
            this.LinksObject = linksObject;
        }

        public void setPublished(String published) {
            this.published = published;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    class Links {
        Link LinkObject;

        public Link getLink() {
            return LinkObject;
        }

        public void setLink(Link linkObject) {
            this.LinkObject = linkObject;
        }
    }

    class Link {
        private String text;
        private String rel;
        private String href;

        public String gettext() {
            return text;
        }

        public String getRel() {
            return rel;
        }

        public String getHref() {
            return href;
        }

        public void settext(String text) {
            this.text = text;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    class Tags {
        ArrayList<Object> tag = new ArrayList<Object>();

    }

    class Similar {
        ArrayList<Object> artist = new ArrayList<Object>();

    }

    class Stats {
        private String listeners;
        private String playcount;

        public String getListeners() {
            return listeners;
        }

        public String getPlaycount() {
            return playcount;
        }


        public void setListeners(String listeners) {
            this.listeners = listeners;
        }

        public void setPlaycount(String playcount) {
            this.playcount = playcount;
        }
    }
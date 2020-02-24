package com.example.lastfm;

public class NewsData {
    String Header;
    String Image;
    String Text;


    public NewsData(String Header, String Image, String Text){
        this.Header = Header;
        this.Image = Image;
        this.Text = Text;
    }

    public String getHeader(){return Header;}
    public String getImage(){return Image;}
    public String getText(){return Text;}
}

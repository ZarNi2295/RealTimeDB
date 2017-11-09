package app.znmsw.realtimedb.model;

/**
 * Created by WT on 10/11/2017.
 */
public class Hero {

    private String name;
    private String realname;
    private String firstappearance;
    private String createdby;
    private String publisher;
    private String imageurl;
    private String bio;
    private Team club;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Hero(String name, String realname, String firstappearance, String createdby, String publisher, String imageurl, String bio, Team club,String timestamp) {
        this.name = name;
        this.realname = realname;
        this.firstappearance = firstappearance;
        this.createdby = createdby;
        this.publisher = publisher;
        this.imageurl = imageurl;
        this.bio = bio;
        this.club = club;
        this.timestamp=timestamp;
    }

    public Team getClub() {
        return club;
    }

    public void setClub(Team club) {
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public String getRealname() {
        return realname;
    }


    public String getFirstappearance() {
        return firstappearance;
    }

    public String getCreatedby() {
        return createdby;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getBio() {
        return bio;
    }
}
package app.znmsw.realtimedb.model;

/**
 * Created by WT on 10/20/2017.
 */

public class Team {
    String team1;
    String team;
    String team2;

    public Team(String team1, String team, String team2) {
        this.team1 = team1;
        this.team = team;
        this.team2 = team2;
    }

    public Team() {
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }
}

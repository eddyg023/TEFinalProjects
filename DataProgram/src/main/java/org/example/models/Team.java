package org.example.models;

public class Team {
    private int teamId;
    private String name;
    private String city;
    private String state;
    private int coachId;

    public Team(int teamId, String name, String city, String state, int coachId) {
        this.teamId = teamId;
        this.name = name;
        this.city = city;
        this.state = state;
        this.coachId = coachId;
    }

    public Team() {}

    public int getTeamId() {return teamId;}

    public void setTeamId(int teamId) {this.teamId = teamId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getState() {return state;}

    public void setState(String state) {this.state = state;}

    public int getCoachId() {return coachId;}

    public void setCoachId(int coachId) {this.coachId = coachId;}
}

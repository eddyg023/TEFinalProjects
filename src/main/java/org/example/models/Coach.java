package org.example.models;

public class Coach {
    private int coachId;
    private String name;
    private int age;
    private int teamId;

    public Coach(int coachId, String name, int age, int teamId) {
        this.coachId = coachId;
        this.name = name;
        this.age = age;
        this.teamId = teamId;
    }

    public Coach() {}

    public int getCoachId() {return coachId;}

    public void setCoachId(int coachId) {this.coachId = coachId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

    public int getTeamId() {return teamId;}

    public void setTeamId(int teamId) {this.teamId = teamId;}
}

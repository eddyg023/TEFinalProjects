package org.example.models;

public class TeamPlayer {
    private int teamId;
    private int playerId;

    public TeamPlayer(int teamId, int playerId) {
        this.teamId = teamId;
        this.playerId = playerId;
    }

    public TeamPlayer() {}

    public int getTeamId() {return teamId;}

    public void setTeamId(int teamId) {this.teamId = teamId;}
    public int getPlayerId() {return playerId;}

    public void setPlayerId(int playerId) {this.playerId = playerId;}
}

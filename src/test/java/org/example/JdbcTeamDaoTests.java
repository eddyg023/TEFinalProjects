package org.example;

import org.example.daos.JdbcTeamDao;
import org.example.models.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JdbcTeamDaoTests extends BaseDaoTests {
    private final static Team TEAM_1 = new Team(1, "Team 1", "City 1", "S1", 1);
    private final static Team TEAM_2 = new Team(2, "Team 2", "City 2", "S2", 2);
    private final static Team TEAM_3 = new Team(3, "Team 3", "City 3", "S3", 3);
    private final static Team TEAM_4 = new Team(4, "Team 4", "City 4", "S4", 4);
    private JdbcTeamDao dao;
    @Before
    public void setup() {dao = new JdbcTeamDao(dataSource);}

    @Test
    public void getTeamById_with_valid_id_returns_correct_team() {
        Team team = dao.getTeamById(1);
        assertTeamMatch(TEAM_1, team);
    }
    public void getTeamById_with_invalid_id_returns_null_team() {
        Team team = dao.getTeamById(999);
        Assert.assertNull(team);
    }
    @Test
    public void getAllTeams_returns_all_teams() {
        List<Team> teams = dao.getTeams();
        Assert.assertEquals(4, teams.size());
    }
    @Test
    public void getTeamsByCoachId_with_valid_coach_id_returns_list_of_teams_for_coach() {
        List<Team> teams = dao.getTeamByCoachId(1);
        Assert.assertEquals(1, teams.size());
        assertTeamMatch(TEAM_1, teams.get(0));
    }
    @Test
    public void getTeamsByPlayerId_with_valid_player_id_returns_list_of_teams_for_player() {
        List<Team> teams = dao.getTeamByPlayerId(1);
        Assert.assertEquals(1, teams.size());
        assertTeamMatch(TEAM_1, teams.get(0));
    }
    @Test
    public void createTeam_creates_team() {
        Team newTeam = new Team();
        newTeam.setName("New Team");
        newTeam.setCity("City 5");
        newTeam.setState("S5");
        newTeam.setCoachId(5);

        Team createdTeam = dao.createTeam(newTeam);

        int newId = createdTeam.getTeamId();
        Team retrievedTeam = dao.getTeamById(newId);

        assertTeamMatch(createdTeam, retrievedTeam);
    }
    @Test
    public void updateTeam_updates_team() {
        Team teamToUpdate = dao.getTeamById(1);
        teamToUpdate.setName("Modified Team");
        teamToUpdate.setCity("Modified City 1");
        teamToUpdate.setState("Modified S1");
        teamToUpdate.setCoachId(7);

        dao.updateTeam(teamToUpdate);

        Team retrievedTeam = dao.getTeamById(1);
        assertTeamMatch(teamToUpdate, retrievedTeam);
    }
    @Test
    public void deleteTeam_deletes_team() {
        int rowsAffected = dao.deleteTeamById(1);
        Assert.assertEquals(1, rowsAffected);

        Team team = dao.getTeamById(1);
        Assert.assertNull(team);
    }

    private void assertTeamMatch(Team expected, Team actual) {
        Assert.assertEquals(expected.getTeamId(), actual.getTeamId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getCity(), actual.getCity());
        Assert.assertEquals(expected.getState(), actual.getState());
        Assert.assertEquals(expected.getCoachId(), actual.getCoachId());
    }

}

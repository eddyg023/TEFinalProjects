package org.example.daos;

import org.example.exception.DaoException;
import org.example.models.Team;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcTeamDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTeamDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}
    public Team getTeamById(int teamId) {
        Team team = null;
        String sql = "SELECT team_id, name, city, state, coach_id " +
                "FROM team " +
                "WHERE team_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, teamId);
            if (results.next()) {
                team = mapRowSetToTeam(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return team;
    }
    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM team;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Team team = mapRowSetToTeam(results);
                teams.add(team);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return teams;
    }
    public List<Team> getTeamByCoachId(int coachId) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT team_id, name, city, state, coach_id " +
                "FROM team " +
                "WHERE coach_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, coachId);
            while (results.next()) {
                Team team = mapRowSetToTeam(results);
                teams.add(team);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return teams;
    }
    public List<Team> getTeamByPlayerId(int playerId) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT team.team_id, name, city, state, coach_id " +
                "FROM team " +
                "JOIN team_player ON team.team_id = team_player.team_id " +
                "WHERE player_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playerId);
            while (results.next()) {
                Team team = mapRowSetToTeam(results);
                teams.add(team);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return teams;
    }
    public Team createTeam(Team newTeam) {
        int newId;
        String sql = "INSERT INTO team (name, city, state, coach_id) " +
                "VALUES (?, ?, ?, ?) RETURNING team_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newTeam.getName(),
                    newTeam.getCity(), newTeam.getState(), newTeam.getCoachId());
            newTeam.setTeamId(newId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTeam;
    }
    public Team updateTeam(Team updatedTeam) {
        Team updateTeam = null;
        String sql = "UPDATE team " +
                "SET name = ?, city = ?, state = ?, coach_id = ? " +
                "WHERE team_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedTeam.getName(), updatedTeam.getCity(),
                    updatedTeam.getState(), updatedTeam.getCoachId(), updatedTeam.getTeamId());
            if (rowsAffected > 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updateTeam = getTeamById(updatedTeam.getTeamId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updateTeam;
    }
    public int deleteTeamById(int teamId) {
        int numberOfRows = 0;
        try {
            String coachSql = "DELETE FROM coach WHERE team_id = ?;";
            numberOfRows = jdbcTemplate.update(coachSql, teamId);
            String teamPlayerSql = "DELETE FROM team_player WHERE team_id = ?;";
            numberOfRows = jdbcTemplate.update(teamPlayerSql, teamId);
            String teamSql = "DELETE FROM team WHERE team_id = ?;";
            numberOfRows = jdbcTemplate.update(teamSql, teamId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    private Team mapRowSetToTeam(SqlRowSet rowSet) {
        Team team = new Team();
        team.setTeamId(rowSet.getInt("team_id"));
        team.setName(rowSet.getString("name"));
        team.setCity(rowSet.getString("city"));
        team.setState(rowSet.getString("state"));
        team.setCoachId(rowSet.getInt("coach_id"));
        return team;
    }
}

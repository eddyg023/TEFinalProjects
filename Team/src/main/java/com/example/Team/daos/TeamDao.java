package com.example.Team.daos;

import com.example.Team.exception.DaoException;
import com.example.Team.models.Team;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeamDao {
    private final JdbcTemplate jdbcTemplate;
    /**
     * Creates a new product dao with the given JDBC template.
     *
     * @param dataSource The JDBC data source to use.
     */
    public TeamDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}
    /**
     * Get a team from the datastore that has the given id.
     * If the id is not found, return null.
     *
     * @param teamId The id of the team.
     * @return A filled out Team object, null if the teamId isn't in the database.
     */
    public Team getTeamById(int teamId) {
        Team team = null;
        String sql = "SELECT team_id, name, city, state " +
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

    /**
     * Get all teams from the datastore, ordered by team_id.
     *
     * @return All teams as Team objects in a List.
     */
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

    /**
     * Get all teams from the datastore that have the given player id.
     *
     * @param playerId The id of the player.
     * @return All teams as Team objects in a List.
     */
    public List<Team> getTeamByPlayerId(int playerId) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT team.team_id, name, city, state " +
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

    /**
     * Get a team from the datastore that has the given name.
     * If the name is not found, return null.
     *
     * @param name The name of the team.
     * @return A filled out Team object, null if the name isn't in the database.
     */
    public Team getTeamByName(String name) {
        Team team = null;
        String sql = "SELECT team_id, name, city, state " +
                "FROM team " +
                "WHERE name = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
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

    /**
     * Get all teams from the datastore that have the given city.
     *
     * @param city The city of the team.
     * @return All teams as Team objects in a List.
     */
    public List<Team> getTeamsByCity(String city) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT team_id, name, city, state " +
                "FROM team " +
                "WHERE city = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, city);
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

    /**
     * Get all teams from the datastore that have the given state.
     *
     * @param state The state of the team.
     * @return All teams as Team objects in a List.
     */
    public List<Team> getTeamsByState(String state) {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT team_id, name, city, state " +
                "FROM team " +
                "WHERE state = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, state);
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

    /**
     * Add a new team into the datastore.
     *
     * @param newTeam the Team object to add.
     * @return The added Team object with its new id filled in.
     */
    public Team createTeam(Team newTeam) {
        int newId;
        String sql = "INSERT INTO team (name, city, state) " +
                "VALUES (?, ?, ?) RETURNING team_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newTeam.getName(),
                    newTeam.getCity(), newTeam.getState());
            newTeam.setTeamId(newId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTeam;
    }

    /**
     * Update a team in the datastore. Only called on team that
     * are already in the datastore.
     *
     * @param updatedTeam The Team object to update.
     * @return The updated Team object.
     */
    public Team updateTeam(Team updatedTeam) {
        Team updateTeam = null;
        String sql = "UPDATE team " +
                "SET name = ?, city = ?, state = ? " +
                "WHERE team_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedTeam.getName(), updatedTeam.getCity(),
                    updatedTeam.getState(), updatedTeam.getTeamId());

            if (rowsAffected == 0) {
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

    /**
     * Delete a team from the datastore.
     *
     * @param teamId The id of the team to delete.
     * @return The number of rows affected.
     */
    public int deleteTeamById(int teamId) {
        int numberOfRows = 0;
        try {
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

    /**
     * Map the current row of the SqlRowSet to a Team object.
     *
     * @param rowSet The SqlRowSet to map.
     * @return A new Team object.
     */
    private Team mapRowSetToTeam(SqlRowSet rowSet) {
        Team team = new Team();
        team.setTeamId(rowSet.getInt("team_id"));
        team.setName(rowSet.getString("name"));
        team.setCity(rowSet.getString("city"));
        team.setState(rowSet.getString("state"));
        return team;
    }
}

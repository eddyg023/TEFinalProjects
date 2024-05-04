package org.example.daos;
import org.example.exception.DaoException;
import org.example.models.Coach;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCoachDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCoachDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    public Coach getCoachById(int coachId) {
        Coach coach = null;
        String sql = "SELECT coach_id, name, age, team_id " +
                "FROM coach " +
                "WHERE coach_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, coachId);
            if (results.next()) {
                coach = mapRowSetToCoach(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return coach;
    }
    public List<Coach> getCoaches() {
        List<Coach> coaches = new ArrayList<>();
        String sql = "SELECT * FROM coach;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Coach coach = mapRowSetToCoach(results);
                coaches.add(coach);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return coaches;
    }

    public Coach createCoach(Coach newCoach) {
        int newId;
        String sql = "INSERT INTO coach (name, age, team_id) " +
                "VALUES (?, ?, ?) RETURNING coach_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newCoach.getName(),
                    newCoach.getAge(), newCoach.getTeamId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getCoachById(newId);
    }
    public Coach updateCoach(Coach updatedCoach) {
        Coach updateCoach = null;
        String sql = "UPDATE coach " +
                "SET name = ?, age = ?, team_id = ? " +
                "WHERE coach_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedCoach.getName(), updatedCoach.getAge(),
                    updatedCoach.getTeamId(), updatedCoach.getCoachId());

            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updateCoach = getCoachById((updatedCoach.getCoachId()));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedCoach;
    }

    public int deleteCoachById(int coachId) {
        int numberOfRows = 0;

        try {
            String teamSql = "DELETE FROM team WHERE coach_id = ?;";
            numberOfRows = jdbcTemplate.update(teamSql, coachId);
            String coachSql = "DELETE FOM coach WHERE coach_id = ?;";
            numberOfRows = jdbcTemplate.update(coachSql, coachId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    private Coach mapRowSetToCoach(SqlRowSet rowSet) {
        Coach coach = new Coach();
        coach.setCoachId(rowSet.getInt("coach_id"));
        coach.setName(rowSet.getString("name"));
        coach.setAge(rowSet.getInt("age"));
        coach.setTeamId(rowSet.getInt("team_id"));
        return coach;
    }
}

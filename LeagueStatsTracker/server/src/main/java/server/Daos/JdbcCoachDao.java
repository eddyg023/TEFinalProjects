package server.Daos;

import org.springframework.stereotype.Component;
import server.Models.Coach;
import server.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCoachDao {
    private final JdbcTemplate jdbcTemplate;
    /**
     * Creates a new product dao with the given JDBC template.
     *
     * @param dataSource The JDBC data source to use.
     */
    public JdbcCoachDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    /**
     * Get a coach from the datastore that has the given id.
     * If the id is not found, return null.
     *
     * @param coachId The id of the coach.
     * @return A filled out Coach object, null if the coachId isn't in the database.
     */
    public Coach getCoachById(int coachId) {
        Coach coach = null;
        String sql = "SELECT coach_id, name, age, years_coached, team_id " +
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

    /**
     * Get all coaches from the datastore, ordered by coach_id.
     *
     * @return All coaches as Coach objects in a List.
     */
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

    /**
     * Get a coach from the datastore that has the given name.
     * If the name is not found, return null.
     *
     * @param name The name of the coach.
     * @return A filled out Coach object, null if the name isn't in the database.
     */
    public Coach getCoachByName(String name) {
        Coach coach = null;
        String sql = "SELECT coach_id, name, age, years_coached, team_id " +
                "FROM coach " +
                "WHERE name = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
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

    /**
     * Get all coaches associated with a team, ordered by coach_id.
     *
     * @param teamId The id of the team to get coaches from.
     * @return All coaches for a team as Coach objects in a List.
     */
    public List<Coach> getCoachByTeamId(int teamId) {
        List<Coach> coaches = new ArrayList<>();
        String sql = "SELECT coach_id, name, age, years_coached, team_id " +
                "FROM coach " +
                "WHERE team_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, teamId);
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

    /**
     * Add a new coach into the datastore.
     *
     * @param newCoach the Coach object to add.
     * @return The added Coach object with its new id filled in.
     */
    public Coach createCoach(Coach newCoach) {
        int newId;
        String sql = "INSERT INTO coach (name, age, years_coached, team_id) " +
                "VALUES (?, ?, ?, ?) RETURNING coach_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newCoach.getName(),
                    newCoach.getAge(), newCoach.getYears_coached(), newCoach.getTeamId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return getCoachById(newId);
    }

    /**
     * Update a coach in the datastore. Only called the coach that
     * are already in the datastore.
     *
     * @param updatedCoach The Coach object to update.
     * @return The updated Coach object.
     */
    public Coach updateCoach(Coach updatedCoach) {
        Coach updateCoach = null;
        String sql = "UPDATE coach " +
                "SET name = ?, age = ?, years_coached = ?, team_id = ? " +
                "WHERE coach_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedCoach.getName(), updatedCoach.getAge(),
                    updatedCoach.getYears_coached(), updatedCoach.getTeamId(), updatedCoach.getCoachId());

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
        return updateCoach;
    }

    /**
     * Delete a coach from the datastore.
     *
     * @param coachId The id of the coach to delete.
     * @return The number of rows affected.
     */
    public int deleteCoachById(int coachId) {
        int numberOfRows = 0;

        try {
            String coachSql = "DELETE FROM coach WHERE coach_id = ?;";
            numberOfRows = jdbcTemplate.update(coachSql, coachId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    /**
     * Map the current row of the given SqlRowSet to a Coach object.
     *
     * @param rowSet The SqlRowSet to map.
     * @return A new Coach object.
     */
    private Coach mapRowSetToCoach(SqlRowSet rowSet) {
        Coach coach = new Coach();
        coach.setCoachId(rowSet.getInt("coach_id"));
        coach.setName(rowSet.getString("name"));
        coach.setAge(rowSet.getInt("age"));
        coach.setYears_coached(rowSet.getInt("years_coached"));
        coach.setTeamId(rowSet.getInt("team_id"));
        return coach;
    }
}

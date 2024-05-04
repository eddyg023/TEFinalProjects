package org.example.daos;

import org.example.exception.DaoException;
import org.example.models.Player;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcPlayerDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPlayerDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}
    public Player getPlayerById(int playerId) {
        Player player = null;
        String sql = "SELECT player_id, name, number, age, height, weight, position " +
                "FROM player " +
                "WHERE player_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playerId);
            if (results.next()) {
                player = mapRowSetToPlayer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return player;
    }
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM player;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Player player = mapRowSetToPlayer(results);
                players.add(player);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return players;
    }
    public List<Player> getPlayersByTeamId(int teamId) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT player.player_id, name, number, age, height, weight, position " +
                "FROM player " +
                "JOIN team_player ON player.player_id = team_player.player_id " +
                "WHERE team_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, teamId);
            while (results.next()) {
                Player player = mapRowSetToPlayer(results);
                players.add(player);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return players;
    }
    public Player createPlayer(Player newPlayer) {
        int newId;
        String sql = "INSERT INTO player (name, number, age, height, weight, position) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING player_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newPlayer.getName(),
                    newPlayer.getNumber(), newPlayer.getAge(), newPlayer.getHeight(),
                    newPlayer.getWeight(), newPlayer.getPosition());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return getPlayerById(newId);
    }
    public Player updatePlayer(Player updatedPlayer) {
        Player updatePlayer = null;
        String sql = "UPDATE player " +
                "SET name = ?, number = ?, age = ?, height = ?, weight = ?, position = ? " +
                "WHERE player_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedPlayer.getName(), updatedPlayer.getNumber(),
                    updatedPlayer.getAge(), updatedPlayer.getHeight(), updatedPlayer.getWeight(),
                    updatedPlayer.getPosition(), updatedPlayer.getPlayerId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatePlayer = getPlayerById(updatedPlayer.getPlayerId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return updatePlayer;
    }
    public int deletePlayerById(int playerId) {
        int numberOfRows = 0;

        try {
            String teamPlayerSql = "DELETE FROM team_player WHERE player_id = ?;";
            numberOfRows = jdbcTemplate.update(teamPlayerSql, playerId);
            String playerSql = "DELETE FROM player WHERE player_id = ?;";
            numberOfRows = jdbcTemplate.update(playerSql, playerId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return numberOfRows;
    }
    private Player mapRowSetToPlayer(SqlRowSet rowSet) {
        Player player = new Player();
        player.setPlayerId(rowSet.getInt("player_id"));
        player.setName(rowSet.getString("name"));
        player.setNumber(rowSet.getInt("number"));
        player.setAge(rowSet.getInt("age"));
        player.setHeight(rowSet.getInt("height"));
        player.setWeight(rowSet.getInt("weight"));
        player.setPosition(rowSet.getString("position"));
        return player;
    }
}

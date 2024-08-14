package com.example.Team.daos;

import com.example.Team.exception.DaoException;
import com.example.Team.models.Player;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerDao {
    private final JdbcTemplate jdbcTemplate;
    /**
     * Creates a new product dao with the given JDBC template.
     *
     * @param dataSource The JDBC data source to use.
     */
    public PlayerDao(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    /**
     * Get a player from the datastore that has the given id.
     * If the id is not found, return null.
     *
     * @param playerId The id of the player.
     * @return A filled out Player object, null if the playerId isn't in the database.
     */
    public Player getPlayerById(int playerId) {
        Player player = null;
        String sql = "SELECT * " +
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

    /**
     * Get all players from the datastore, ordered by player_id.
     *
     * @return All players as Player objects in a List.
     */
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

    /**
     * Get all players associated with a team, ordered by player_id.
     *
     * @param teamId The id of the team to get players from.
     * @return All players for a team as Player objects in a List.
     */
    public List<Player> getPlayersByTeamId(int teamId) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT player.player_id, name, number, age, height, weight, player_position, " +
                "points_per_game, assists_per_game, rebounds_per_game, rebounds_per_game, blocks_per_game " +
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

    /**
     * Get all players from the datastore that have the given player position.
     *
     * @param playerPosition The player position to search for.
     * @return All players as Player objects in a List.
     */
    public List<Player> getPlayersByPlayerPosition(String playerPosition) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM player " +
                "WHERE player_position = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, playerPosition);
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

    /**
     * Get a player from the datastore that has the given name.
     * If the name is not found, return null.
     *
     * @param name The name of the player.
     * @return A filled out Player object, null if the name isn't in the database.
     */
    public Player getPlayerByName(String name) {
        Player player = null;
        String sql = "SELECT player_id, name, number, age, height, weight, player_position, " +
                "points_per_game, assists_per_game, rebounds_per_game, steals_per_game, blocks_per_game " +
                "FROM player " +
                "WHERE name = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
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

    /**
     * Add a new player into the datastore.
     *
     * @param newPlayer the Player object to add.
     * @return The added Player object with its new id filled in.
     */
    public Player createPlayer(Player newPlayer) {
        int newId;
        String sql = "INSERT INTO player (name, number, age, height, weight, player_position, " +
                "points_per_game, assists_per_game, rebounds_per_game, steals_per_game, blocks_per_game) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING player_id;";
        try {
            newId = jdbcTemplate.queryForObject(sql, int.class, newPlayer.getName(),
                    newPlayer.getNumber(), newPlayer.getAge(), newPlayer.getHeight(),
                    newPlayer.getWeight(), newPlayer.getPlayer_position(),
                    newPlayer.getPoints_per_game(), newPlayer.getAssists_per_game(),
                    newPlayer.getRebounds_per_game(), newPlayer.getSteals_per_game(),
                    newPlayer.getBlocks_per_game());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return getPlayerById(newId);
    }

    /**
     * Update a player in the datastore. Only called on player that
     * are already in the datastore.
     *
     * @param updatedPlayer The Player object to update.
     * @return The updated Player object.
     */
    public Player updatePlayer(Player updatedPlayer) {
        Player updatePlayer = null;
        String sql = "UPDATE player " +
                "SET name = ?, number = ?, age = ?, height = ?, weight = ?, player_position = ?, " +
                "points_per_game = ?, assists_per_game = ?, rebounds_per_game = ?, steals_per_game = ?, blocks_per_game = ? " +
                "WHERE player_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedPlayer.getName(), updatedPlayer.getNumber(),
                    updatedPlayer.getAge(), updatedPlayer.getHeight(), updatedPlayer.getWeight(), updatedPlayer.getPlayer_position(),
                    updatedPlayer.getPoints_per_game(), updatedPlayer.getAssists_per_game(), updatedPlayer.getRebounds_per_game(),
                    updatedPlayer.getSteals_per_game(), updatedPlayer.getBlocks_per_game(), updatedPlayer.getPlayerId());
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

    /**
     * Delete a player from the datastore.
     *
     * @param playerId The id of the player to delete.
     * @return The number of rows affected.
     */
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

    /**
     * Map the current row of the SqlRowSet to a Player object.
     *
     * @param rowSet The SqlRowSet to map.
     * @return The Player object.
     */
    private Player mapRowSetToPlayer(SqlRowSet rowSet) {
        Player player = new Player();
        player.setPlayerId(rowSet.getInt("player_id"));
        player.setName(rowSet.getString("name"));
        player.setNumber(rowSet.getInt("number"));
        player.setAge(rowSet.getInt("age"));
        player.setHeight(rowSet.getInt("height"));
        player.setWeight(rowSet.getInt("weight"));
        player.setPlayer_position(rowSet.getString("player_position"));
        player.setPoints_per_game(rowSet.getFloat("points_per_game"));
        player.setAssists_per_game(rowSet.getFloat("assists_per_game"));
        player.setRebounds_per_game(rowSet.getFloat("rebounds_per_game"));
        player.setSteals_per_game(rowSet.getFloat("steals_per_game"));
        player.setBlocks_per_game(rowSet.getFloat("blocks_per_game"));
        return player;
    }
}

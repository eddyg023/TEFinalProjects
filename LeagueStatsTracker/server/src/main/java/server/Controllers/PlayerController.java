package server.Controllers;

import server.Daos.JdbcPlayerDao;
import server.Models.Player;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import server.Models.Team;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private JdbcPlayerDao jdbcPlayerDao;

    /**
     * Constructor
     * @param jdbcPlayerDao
     */
    public PlayerController(JdbcPlayerDao jdbcPlayerDao) {this.jdbcPlayerDao = jdbcPlayerDao;}

    /**
     * Get all players
     * @return List of all players
     */
    @GetMapping
    public List<Player> getAllPlayers() {
        return jdbcPlayerDao.getPlayers();
    }

    /**
     * Get player by id
     * @param id
     * @return Player
     */
    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable int id) {
        return jdbcPlayerDao.getPlayerById(id);
    }

    /**
     * Get players by position
     * @param player_position
     * @return List of players
     */
    @GetMapping("/position/{player_position}")
    public List<Player> getPlayerByPosition(@PathVariable String player_position) {
        return jdbcPlayerDao.getPlayersByPlayerPosition(player_position);
    }

    /**
     * Get player by name
     * @param name
     * @return Player
     */
    @GetMapping("/name/{name}")
    public Player getPlayerByName(@PathVariable String name) {
        return jdbcPlayerDao.getPlayerByName(name);
    }

    @GetMapping("/team/{teamId}")
    public List<Player> getPlayersByTeam(@PathVariable int teamId) {
        return jdbcPlayerDao.getPlayersForTeams(teamId);
    }

    /**
     * Create a new player
     * @param player
     * @return Player
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return jdbcPlayerDao.createPlayer(player);
    }

    /**
     * Update a player
     * @param id
     * @param player
     * @return Player
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Player updatePlayer(@PathVariable int id, @RequestBody Player player) {
        player.setPlayerId(id);
        return jdbcPlayerDao.updatePlayer(player);
    }

    /**
     * Delete a player
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable int id) {
        jdbcPlayerDao.deletePlayerById(id);
    }
}


package com.example.Team.controllers;

import com.example.Team.daos.PlayerDao;
import com.example.Team.models.Player;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private PlayerDao playerDao;

    /**
     * Constructor
     * @param playerDao
     */
    public PlayerController(PlayerDao playerDao) {this.playerDao = playerDao;}

    /**
     * Get all players
     * @return List of all players
     */
    @GetMapping
    public List<Player> getAllPlayers() {
        return playerDao.getPlayers();
    }

    /**
     * Get player by id
     * @param id
     * @return Player
     */
    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable int id) {
        return playerDao.getPlayerById(id);
    }

    /**
     * Get players by position
     * @param player_position
     * @return List of players
     */
    @GetMapping("/position/{player_position}")
    public List<Player> getPlayerByPosition(@PathVariable String player_position) {
        return playerDao.getPlayersByPlayerPosition(player_position);
    }

    /**
     * Get player by name
     * @param name
     * @return Player
     */
    @GetMapping("/name/{name}")
    public Player getPlayerByName(@PathVariable String name) {
        return playerDao.getPlayerByName(name);
    }

    /**
     * Create a new player
     * @param player
     * @return Player
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('COMMISSIONER', 'COACH')")
    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playerDao.createPlayer(player);
    }

    /**
     * Update a player
     * @param id
     * @param player
     * @return Player
     */
    @PreAuthorize("hasAnyAuthority('COMMISSIONER', 'COACH')")
    @PutMapping("/{id}")
    public Player updatePlayer(@PathVariable int id, @RequestBody Player player) {
        player.setPlayerId(id);
        return playerDao.updatePlayer(player);
    }

    /**
     * Delete a player
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('COMMISSIONER', 'COACH')")
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable int id) {
        playerDao.deletePlayerById(id);
    }
}

package com.example.Team.controllers;


import com.example.Team.daos.TeamDao;
import com.example.Team.models.Team;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    private TeamDao teamDao;

    /**
     * Constructor
     * @param teamDao
     */
    public TeamController(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    /**
     * Get all teams
     * @return List of all teams
     */
    @GetMapping
    public List<Team> getAllTeams() {
        return teamDao.getTeams();
    }

    /**
     * Get team by id
     * @param id
     * @return Team
     */
    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable int id) {
        return teamDao.getTeamById(id);
    }

    /**
     * Get team by name
     * @param name
     * @return Team
     */
    @GetMapping("/name/{name}")
    public Team getTeamByName(@PathVariable String name) {
        return teamDao.getTeamByName(name);
    }

    /**
     * Get teams by city
     * @param city
     * @return List of teams
     */
    @GetMapping("/city/{city}")
    public List<Team> getTeamsByCity(@PathVariable String city) {
        return teamDao.getTeamsByCity(city);
    }

    /**
     * Get teams by state
     * @param state
     * @return List of teams
     */
    @GetMapping("/state/{state}")
    public List<Team> getTeamsByState(@PathVariable String state) {
        return teamDao.getTeamsByState(state);
    }

    /**
     * Create a new team
     * @param team
     * @return Team
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('COMMISSIONER')")
    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamDao.createTeam(team);
    }

    /**
     * Update a team
     * @param id
     * @param team
     * @return Team
     */
    @PreAuthorize("hasAuthority('COMMISSIONER')")
    @PutMapping("/{id}")
    public Team updateTeam(@PathVariable int id, @RequestBody Team team) {
        team.setTeamId(id);
        return teamDao.updateTeam(team);
    }

    /**
     * Delete a team
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('COMMISSIONER')")
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable int id) {
        teamDao.deleteTeamById(id);
    }
}

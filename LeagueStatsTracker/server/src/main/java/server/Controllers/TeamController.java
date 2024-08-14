package server.Controllers;

import server.Daos.JdbcTeamDao;
import server.Models.Team;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/team")
public class TeamController {
    private JdbcTeamDao jdbcTeamDao;

    /**
     * Constructor
     * @param jdbcTeamDao
     */
    public TeamController(JdbcTeamDao jdbcTeamDao) {
        this.jdbcTeamDao = jdbcTeamDao;
    }

    /**
     * Get all teams
     * @return List of all teams
     */
    @GetMapping
    public List<Team> getAllTeams() {
        return jdbcTeamDao.getTeams();
    }

    /**
     * Get team by id
     * @param id
     * @return Team
     */
    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable int id) {
        return jdbcTeamDao.getTeamById(id);
    }

    /**
     * Get team by name
     * @param name
     * @return Team
     */
    @GetMapping("/name/{name}")
    public Team getTeamByName(@PathVariable String name) {
        return jdbcTeamDao.getTeamByName(name);
    }

    /**
     * Get teams by city
     * @param city
     * @return List of teams
     */
    @GetMapping("/city/{city}")
    public List<Team> getTeamsByCity(@PathVariable String city) {
        return jdbcTeamDao.getTeamsByCity(city);
    }

    /**
     * Get teams by state
     * @param state
     * @return List of teams
     */
    @GetMapping("/state/{state}")
    public List<Team> getTeamsByState(@PathVariable String state) {
        return jdbcTeamDao.getTeamsByState(state);
    }

    /**
     * Create a new team
     * @param team
     * @return Team
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return jdbcTeamDao.createTeam(team);
    }

    /**
     * Update a team
     * @param id
     * @param team
     * @return Team
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Team updateTeam(@PathVariable int id, @RequestBody Team team) {
        team.setTeamId(id);
        return jdbcTeamDao.updateTeam(team);
    }

    /**
     * Delete a team
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable int id) {
        jdbcTeamDao.deleteTeamById(id);
    }
}

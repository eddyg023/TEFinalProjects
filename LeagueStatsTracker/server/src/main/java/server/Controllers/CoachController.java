package server.Controllers;

import server.Daos.JdbcCoachDao;
import server.Models.Coach;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/coach")
public class CoachController {
    private JdbcCoachDao jdbcCoachDao;

    /**
     * Constructor
     * @param jdbcCoachDao
     */
    public CoachController(JdbcCoachDao jdbcCoachDao) {
        this.jdbcCoachDao = jdbcCoachDao;
    }

    /**
     * Get all coaches
     * @return List of all coaches
     */
    @GetMapping
    public List<Coach> getAllCoaches() {
        return jdbcCoachDao.getCoaches();
    }

    /**
     * Get coach by id
     * @param id
     * @return Coach
     */
    @GetMapping("/{id}")
    public Coach getCoachById(@PathVariable int id) {
        return jdbcCoachDao.getCoachById(id);
    }

    /**
     * Get coach by name
     * @param name
     * @return Coach
     */
    @GetMapping("/name/{name}")
    public Coach getCoachByName(@PathVariable String name) {
        return jdbcCoachDao.getCoachByName(name);
    }

    /**
     * Create a new coach
     * @param coach
     * @return Coach
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST, path="")
    public Coach createCoach(@RequestBody Coach coach) {
        return jdbcCoachDao.createCoach(coach);
    }

    /**
     * Update a coach
     * @param id
     * @param coach
     * @return Coach
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Coach updateCoach(@PathVariable int id, @RequestBody Coach coach) {
        coach.setCoachId(id);
        return jdbcCoachDao.updateCoach(coach);
    }

    /**
     * Delete a coach
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCoach(@PathVariable int id) {
        jdbcCoachDao.deleteCoachById(id);
    }
}

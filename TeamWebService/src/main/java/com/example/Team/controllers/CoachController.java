package com.example.Team.controllers;

import com.example.Team.daos.CoachDao;
import com.example.Team.models.Coach;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachController {
    private CoachDao coachDao;

    /**
     * Constructor
     * @param coachDao
     */
    public CoachController(CoachDao coachDao) {
        this.coachDao = coachDao;
    }

    /**
     * Get all coaches
     * @return List of all coaches
     */
    @GetMapping
    public List<Coach> getAllCoaches() {
        return coachDao.getCoaches();
    }

    /**
     * Get coach by id
     * @param id
     * @return Coach
     */
    @GetMapping("/{id}")
    public Coach getCoachById(@PathVariable int id) {
        return coachDao.getCoachById(id);
    }

    /**
     * Get coach by name
     * @param name
     * @return Coach
     */
    @GetMapping("/name/{name}")
    public Coach getCoachByName(@PathVariable String name) {
        return coachDao.getCoachByName(name);
    }

    /**
     * Create a new coach
     * @param coach
     * @return Coach
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('COMMISSIONER')")
    @PostMapping
    public Coach createCoach(@RequestBody Coach coach) {
        return coachDao.createCoach(coach);
    }

    /**
     * Update a coach
     * @param id
     * @param coach
     * @return Coach
     */
    @PreAuthorize("hasAuthority('COMMISSIONER')")
    @PutMapping("/{id}")
    public Coach updateCoach(@PathVariable int id, @RequestBody Coach coach) {
        coach.setCoachId(id);
        return coachDao.updateCoach(coach);
    }

    /**
     * Delete a coach
     * @param id
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('COMMISSIONER')")
    @DeleteMapping("/{id}")
    public void deleteCoach(@PathVariable int id) {
        coachDao.deleteCoachById(id);
    }
}

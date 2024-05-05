package org.example;

import org.example.daos.JdbcCoachDao;
import org.example.daos.JdbcTeamDao;
import org.example.models.Coach;
import org.junit.Assert;
import org.junit.Before;

import java.util.List;

public class JdbcCoachDaoTests extends BaseDaoTests{
    private final static Coach COACH_1 = new Coach(1, "Coach 1", 45, 1);
    private final static Coach COACH_2 = new Coach(2, "Coach 2", 50, 2);
    private final static Coach COACH_3 = new Coach(3, "Coach 3", 55, 3);
    private final static Coach COACH_4 = new Coach(4, "Coach 4", 60, 4);
    private JdbcCoachDao dao;
    @Before
    public void setup() {dao = new JdbcCoachDao(dataSource);}
    public void getCoachById_with_valid_id_returns_correct_coach() {
        Coach coach = dao.getCoachById(1);
        assertCoachMatch(COACH_1, coach);
    }
    public void getCoachById_with_invalid_id_returns_null_coach() {
        Coach coach = dao.getCoachById(999);
        Assert.assertNull(coach);
    }
    public void getAllCoaches_returns_all_coaches() {
        List<Coach> coaches = dao.getCoaches();
        Assert.assertEquals(4, coaches.size());
    }
    public void getCoachesByTeamId_with_valid_team_id_returns_list_of_coaches_for_team() {
        List<Coach> coaches = dao.getCoachByTeamId(1);
        Assert.assertEquals(1, coaches.size());
        assertCoachMatch(COACH_1, coaches.get(0));
    }
    public void createCoach_creates_coach() {
        Coach newCoach = new Coach();
        newCoach.setName("New Coach");
        newCoach.setAge(65);
        newCoach.setTeamId(5);

        Coach createdCoach = dao.createCoach(newCoach);

        int newId = createdCoach.getCoachId();
        Coach retrievedCoach = dao.getCoachById(newId);

        assertCoachMatch(createdCoach, retrievedCoach);
    }
    public void updateCoach_updates_coach() {
        Coach coachToUpdate = dao.getCoachById(1);
        coachToUpdate.setName("Modified Coach");
        coachToUpdate.setAge(70);
        coachToUpdate.setTeamId(3);

        dao.updateCoach(coachToUpdate);

        Coach retrievedCoach = dao.getCoachById(1);
        assertCoachMatch(coachToUpdate, retrievedCoach);
    }
    public void deleteCoach_deletes_coach() {
        int rowsAffected = dao.deleteCoachById(1);
        Assert.assertEquals(1, rowsAffected);

        Coach coach = dao.getCoachById(1);
        Assert.assertNull(coach);
    }

    private void assertCoachMatch(Coach expected, Coach actual) {
        Assert.assertEquals(expected.getCoachId(), actual.getCoachId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getAge(), actual.getAge());
        Assert.assertEquals(expected.getTeamId(), actual.getTeamId());
    }
}

package org.example;

import org.example.daos.JdbcPlayerDao;
import org.example.models.Player;
import org.junit.Assert;
import org.junit.Before;

import java.util.List;

public class JdbcPlayerDaoTests extends BaseDaoTests{
    private final static Player PLAYER_1 = new Player(1, "Player 1", 25, 20, 80, 235, "Forward");
    private final static Player PLAYER_2 = new Player(2, "Player 2", 30, 25, 85, 240, "Guard");
    private final static Player PLAYER_3 = new Player(3, "Player 3", 35, 30, 90, 245, "Center");
    private final static Player PLAYER_4 = new Player(4, "Player 4", 40, 35, 85, 250, "Forward");
    private JdbcPlayerDao dao;
    @Before
    public void setup() {dao = new JdbcPlayerDao(dataSource);}
    public void getPlayerById_with_valid_id_returns_correct_player() {
        Player player = dao.getPlayerById(1);
        assertPlayerMatch(PLAYER_1, player);
    }
    public void getPlayerById_with_invalid_id_returns_null_player() {
        Player player = dao.getPlayerById(999);
        Assert.assertNull(player);
    }
    public void getAllPlayers_returns_all_players() {
        List<Player> players = dao.getPlayers();
        Assert.assertEquals(4, players.size());
    }
    public void getPlayersByTeamId_with_valid_team_id_returns_list_of_players_for_team() {
        List<Player> players = dao.getPlayersByTeamId(1);
        Assert.assertEquals(1, players.size());
        assertPlayerMatch(PLAYER_1, players.get(0));
    }
    public void createPlayer_creates_player() {
        Player newPlayer = new Player();
        newPlayer.setName("New Player");
        newPlayer.setAge(45);
        newPlayer.setHeight(40);
        newPlayer.setWeight(95);
        newPlayer.setPosition("Guard");

        Player createdPlayer = dao.createPlayer(newPlayer);

        int newId = createdPlayer.getPlayerId();
        Player retrievedPlayer = dao.getPlayerById(newId);

        assertPlayerMatch(createdPlayer, retrievedPlayer);
    }
    public void updatePlayer_updates_player() {
        Player playerToUpdate = dao.getPlayerById(1);
        playerToUpdate.setName("Modified Player");
        playerToUpdate.setAge(27);
        playerToUpdate.setHeight(75);
        playerToUpdate.setWeight(215);
        playerToUpdate.setPosition("Guard");

        dao.updatePlayer(playerToUpdate);

        Player retrievedPlayer = dao.getPlayerById(1);
        assertPlayerMatch(playerToUpdate, retrievedPlayer);
    }
    public void deletePlayer_deletes_player() {
        int rowsAffected = dao.deletePlayerById(1);
        Assert.assertEquals(1, rowsAffected);

        Player player = dao.getPlayerById(1);
        Assert.assertNull(player);
    }
    private void assertPlayerMatch(Player expected, Player actual) {
        Assert.assertEquals(expected.getPlayerId(), actual.getPlayerId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getAge(), actual.getAge());
        Assert.assertEquals(expected.getHeight(), actual.getHeight());
        Assert.assertEquals(expected.getWeight(), actual.getWeight());
        Assert.assertEquals(expected.getPosition(), actual.getPosition());
    }

}

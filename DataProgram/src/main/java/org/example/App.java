package org.example;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.daos.JdbcCoachDao;
import org.example.daos.JdbcPlayerDao;
import org.example.daos.JdbcTeamDao;
import org.example.models.Coach;
import org.example.models.Player;
import org.example.models.Team;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    private JdbcCoachDao jdbcCoachDao;
    private JdbcPlayerDao jdbcPlayerDao;
    private JdbcTeamDao jdbcTeamDao;
    private Scanner scanner;
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
    public void run() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/team");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        jdbcCoachDao = new JdbcCoachDao(dataSource);
        jdbcPlayerDao = new JdbcPlayerDao(dataSource);
        jdbcTeamDao = new JdbcTeamDao(dataSource);
        scanner = new Scanner(System.in);
        mainMenu();
    }
    public void mainMenu() {
        while (true) {
            System.out.println("1) Player Menu");
            System.out.println("2) Coach Menu");
            System.out.println("3) Team Menu");
            System.out.println("4) Exit");
            System.out.println("Please choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                playerMenu();
            } else if (choice.equals("2")) {
                coachMenu();
            } else if (choice.equals("3")) {
                teamMenu();
            } else if (choice.equals("4")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. PLease try again.");
            }
        }
    }
    public void playerMenu() {
        while (true) {
            System.out.println("1) View all players");
            System.out.println("2) View player details");
            System.out.println("3) Add player");
            System.out.println("4) Update player");
            System.out.println("5) Delete player");
            System.out.println("6) Return to main menu");
            System.out.println("Please choose an option: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.println("Players: ");
                List<Player> players = jdbcPlayerDao.getPlayers();
                if (!players.isEmpty()) {
                    for (Player player : players) {
                        System.out.println(player.getName());
                    }
                } else {
                    System.out.println("No players found.");
                }
            } else if (choice.equals("2")) {
                System.out.println("Enter player ID: ");
                int playerId = Integer.parseInt(scanner.nextLine());
                Player player = jdbcPlayerDao.getPlayerById(playerId);
                if (player != null) {
                    System.out.println("Name: " + player.getName());
                    System.out.println("Number: " + player.getNumber());
                    System.out.println("Age: " + player.getAge());
                    System.out.println("Height: " + player.getHeight());
                    System.out.println("Weight: " + player.getWeight());
                    System.out.println("Position: " + player.getPosition());
                } else {
                    System.out.println("Player not found.");
                }
            } else if (choice.equals("3")) {
                System.out.println("Enter player name: ");
                String name = scanner.nextLine();
                System.out.println("Enter player number: ");
                int number = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter player age: ");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter player height: ");
                int height = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter player weight: ");
                int weight = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter player position: ");
                String position = scanner.nextLine();
                Player newPlayer = new Player();
                newPlayer.setName(name);
                newPlayer.setNumber(number);
                newPlayer.setAge(age);
                newPlayer.setHeight(height);
                newPlayer.setWeight(weight);
                newPlayer.setPosition(position);
                jdbcPlayerDao.createPlayer(newPlayer);
            } else if (choice.equals("4")) {
                System.out.println("Enter player ID: ");
                int playerId = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new player name: ");
                String name = scanner.nextLine();
                System.out.println("Enter new player number: ");
                int number = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new player age: ");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new player height: ");
                int height = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new player weight: ");
                int weight = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new player position: ");
                String position = scanner.nextLine();
                Player updatedPlayer = new Player();
                updatedPlayer.setPlayerId(playerId);
                updatedPlayer.setName(name);
                updatedPlayer.setNumber(number);
                updatedPlayer.setAge(age);
                updatedPlayer.setHeight(height);
                updatedPlayer.setWeight(weight);
            } else if (choice.equals("5")) {
                System.out.println("Enter player ID: ");
                int playerId = Integer.parseInt(scanner.nextLine());
                jdbcPlayerDao.deletePlayerById(playerId);
            } else if (choice.equals("6")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void coachMenu() {
        while (true) {
            System.out.println("1) View all coaches");
            System.out.println("2) View coach details");
            System.out.println("3) Add coach");
            System.out.println("4) Update coach");
            System.out.println("5) Delete coach");
            System.out.println("6) Return to main menu");
            System.out.println("Please choose an option: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.println("Coaches: ");
                List<Coach> coaches = jdbcCoachDao.getCoaches();
                if (!coaches.isEmpty()) {
                    for (Coach coach : coaches) {
                        System.out.println(coach.getName());
                    }
                } else {
                    System.out.println("No coaches found.");
                }
            } else if (choice.equals("2")) {
                System.out.println("Enter coach ID: ");
                int coachId = Integer.parseInt(scanner.nextLine());
                Coach coach = jdbcCoachDao.getCoachById(coachId);
                if (coach != null) {
                    System.out.println("Name: " + coach.getName());
                    System.out.println("Age: " + coach.getAge());
                    System.out.println("Team ID: " + coach.getTeamId());
                } else {
                    System.out.println("Coach not found.");
                }
            } else if (choice.equals("3")) {
                System.out.println("Enter coach name: ");
                String name = scanner.nextLine();
                System.out.println("Enter coach age: ");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter team ID: ");
                int teamId = Integer.parseInt(scanner.nextLine());
                Coach newCoach = new Coach();
                newCoach.setName(name);
                newCoach.setAge(age);
                newCoach.setTeamId(teamId);
                jdbcCoachDao.createCoach(newCoach);
            } else if (choice.equals("4")) {
                System.out.println("Enter coach ID: ");
                int coachId = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new coach name: ");
                String name = scanner.nextLine();
                System.out.println("Enter new coach age: ");
                int age = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new team ID: ");
                int teamId = Integer.parseInt(scanner.nextLine());
                Coach updatedCoach = new Coach();
                updatedCoach.setCoachId(coachId);
                updatedCoach.setName(name);
                updatedCoach.setAge(age);
                updatedCoach.setTeamId(teamId);
                jdbcCoachDao.updateCoach(updatedCoach);
            } else if (choice.equals("5")) {
                System.out.println("Enter coach ID: ");
                int coachId = Integer.parseInt(scanner.nextLine());
                jdbcCoachDao.deleteCoachById(coachId);
            } else if (choice.equals("6")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void teamMenu() {
        while (true) {
            System.out.println("1) View all teams");
            System.out.println("2) View team details");
            System.out.println("3) Add team");
            System.out.println("4) Update team");
            System.out.println("5) Delete team");
            System.out.println("6) Return to main menu");
            System.out.println("Please choose an option: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.println("Teams: ");
                List<Team> teams = jdbcTeamDao.getTeams();
                if (!teams.isEmpty()) {
                    for (Team team : teams) {
                        System.out.println(team.getName());
                    }
                } else {
                    System.out.println("No teams found.");
                }
            } else if (choice.equals("2")) {
                System.out.println("Enter team ID: ");
                int teamId = Integer.parseInt(scanner.nextLine());
                Team team = jdbcTeamDao.getTeamById(teamId);
                if (team != null) {
                    System.out.println("Name: " + team.getName());
                    System.out.println("City: " + team.getCity());
                    System.out.println("State: " + team.getState());
                    System.out.println("Coach ID: " + team.getCoachId());
                } else {
                    System.out.println("Team not found.");
                }
            } else if (choice.equals("3")) {
                System.out.println("Enter team name: ");
                String name = scanner.nextLine();
                System.out.println("Enter team city: ");
                String city = scanner.nextLine();
                System.out.println("Enter team state: ");
                String state = scanner.nextLine();
                System.out.println("Enter coach ID: ");
                int coachId = Integer.parseInt(scanner.nextLine());
                Team newTeam = new Team();
                newTeam.setName(name);
                newTeam.setCity(city);
                newTeam.setState(state);
                newTeam.setCoachId(coachId);
                jdbcTeamDao.createTeam(newTeam);
            } else if (choice.equals("4")) {
                System.out.println("Enter team ID: ");
                int teamId = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new team name: ");
                String name = scanner.nextLine();
                System.out.println("Enter new team city: ");
                String city = scanner.nextLine();
                System.out.println("Enter new team state: ");
                String state = scanner.nextLine();
                System.out.println("Enter new coach ID: ");
                int coachId = Integer.parseInt(scanner.nextLine());
                Team updatedTeam = new Team();
                updatedTeam.setTeamId(teamId);
                updatedTeam.setName(name);
                updatedTeam.setCity(city);
                updatedTeam.setState(state);
                updatedTeam.setCoachId(coachId);
                jdbcTeamDao.updateTeam(updatedTeam);
            } else if (choice.equals("5")) {
                System.out.println("Enter team ID: ");
                int teamId = Integer.parseInt(scanner.nextLine());
                jdbcTeamDao.deleteTeamById(teamId);
            } else if (choice.equals("6")) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

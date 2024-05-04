
START TRANSACTION;

DROP TABLE IF EXISTS player, coach, team, team_player;

CREATE TABLE player (
    player_id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    number INT NOT NULL,
    age INT NOT NULL,
    height INT NOT NULL,
    weight INT NOT NULL,
    position VARCHAR(128) NOT NULL
);

CREATE TABLE team (
    team_id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    city VARCHAR(64) NOT NULL,
    state VARCHAR(2) NOT NULL,
    coach_id int NOT NULL
);

CREATE TABLE coach (
    coach_id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    age INT NOT NULL,
    team_id INT NOT NULL REFERENCES team(team_id)
);

CREATE TABLE team_player (
    team_id INT,
    player_id INT,
    PRIMARY KEY(team_id, player_id),
    FOREIGN KEY (player_id) REFERENCES player(player_id),
    FOREIGN KEY (team_id) REFERENCES team(team_id)
);
INSERT INTO player(player_id, name, number, age, height, weight, position)
VALUES (1, 'Jake Smith', 23, 28, 84, 210, 'Guard'),
(2, 'Sarah Johnson', 10, 25, 80, 187, 'Forward'),
(3, 'Michael Brown', 7, 31, 86, 232, 'Center'),
(4, 'Emily Martinez', 14, 27, 82, 159, 'Guard'),
(5, 'Ryan Thompson', 31, 29, 85, 217, 'Guard'),
(6, 'Samantha White', 22, 24, 78, 155, 'Guard'),
(7, 'Kevin Davis', 5, 30, 84, 213, 'Forward'),
(8, 'Jessica Rodriguez', 11, 26, 80, 188, 'Guard'),
(9, 'David Wilson', 3, 32, 88, 240, 'Center'),
(10, 'Lauren Taylor', 20, 28, 82, 210, 'Forward'),
(11, 'Daniel Anderson', 9, 27, 84, 202, 'Forward'),
(12, 'Ashley Thomas', 16, 25, 76, 150, 'Guard'),
(13, 'Matthew Garcia', 8, 29, 89, 222, 'Center'),
(14, 'Brittany Clark', 21, 26, 82, 195, 'Forward'),
(15, 'Jason Moore', 2, 33, 87, 255, 'Guard'),
(16, 'Amanda Lee', 13, 24, 75, 130, 'Guard'),
(17, 'Christopher Perez', 4, 31, 84, 217, 'Forward'),
(18, 'Megan King', 19, 27, 80, 195, 'Forward'),
(19, 'Joshua Scott', 12, 30, 86, 225, 'Center'),
(20, 'Taylor Hernandez', 6, 26, 78, 195, 'Forward'),
(21, 'Nicholas Ramirez', 17, 32, 88, 215, 'Center'),
(22, 'Rachel Adams', 15, 25, 80, 188, 'Forward'),
(23, 'Brandon Lewis', 1, 29, 85, 217, 'Guard'),
(24, 'Stephanie Hill', 18, 28, 82, 202, 'Forward'),
(25, 'Justin Green', 30, 27, 84, 203, 'Guard'),
(26, 'Nicole Cook', 24, 24, 76, 180, 'Forward'),
(27, 'Andrew Martinez', 33, 29, 89, 217, 'Guard'),
(28, 'Kayla Nelson', 29, 26, 82, 195, 'Guard'),
(29, 'Joseph Wright', 28, 33, 87, 247, 'Guard'),
(30, 'Olivia James', 27, 23, 75, 173, 'Guard'),
(31, 'Tyler Taylor', 26, 30, 84, 225, 'Forward'),
(32, 'Heather Adams', 32, 27, 80, 203, 'Guard'),
(33, 'Dylan Mitchell', 25, 31, 86, 232, 'Forward'),
(34, 'Kimberly Lopez', 36, 26, 78, 195, 'Center'),
(35, 'Eric Hill', 35, 32, 88, 240, 'Center'),
(36, 'Madison Martinez', 34, 24, 80, 180, 'Forward'),
(37, 'Austin Baker', 37, 28, 85, 213, 'Forward'),
(38, 'Emily Phillips', 38, 25, 82, 188, 'Guard'),
(39, 'Jacob Hall', 39, 27, 84, 203, 'Forward'),
(40, 'Samantha Wright', 40, 29, 75, 173, 'Guard'),
(41, 'Brandon Brown', 24, 28, 86, 210, 'Guard'),
(42, 'Christina Lee', 29, 26, 79, 195, 'Forward'),
(43, 'Tyler Adams', 22, 27, 83, 203, 'Guard'),
(44, 'Emma Wilson', 18, 29, 87, 225, 'Forward'),
(45, 'Jonathan Garcia', 33, 30, 88, 240, 'Center'),
(46, 'Olivia Smith', 30, 28, 85, 203, 'Forward'),
(47, 'Nathan Johnson', 25, 25, 81, 188, 'Guard'),
(48, 'Madison Brown', 27, 31, 89, 240, 'Center');

INSERT INTO team(team_id, name, city, state, coach_id)
VALUES (1, 'Eagles', 'Philadelphia', 'PA', 1),
(2, 'Lakers', 'Los Angeles', 'CA', 2),
(3, 'Bulls', 'Chicago', 'IL', 3),
(4, 'Celtics', 'Boston', 'MA', 4),
(5, 'Heat', 'Miami', 'FL', 5),
(6, 'Spurs', 'San Antonio', 'TX', 6);

INSERT INTO coach(coach_id, name, age, team_id)
VALUES (1, 'John Smith', 45, 1),
(2, 'Emily Johnson', 38, 2),
(3, 'Michael Brown', 50, 3),
(4, 'Jessica Rodriguez', 42, 4),
(5, 'David Wilson', 55, 5),
(6, 'Samantha White', 40, 6);

INSERT INTO team_player(team_id, player_id)
VALUES (1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(1, 7),
(2, 8),
(3, 9),
(4, 10),
(5, 11),
(6, 12),
(1, 13),
(2, 14),
(3, 15),
(4, 16),
(5, 17),
(6, 18),
(1, 19),
(2, 20),
(3, 21),
(4, 22),
(5, 23),
(6, 24),
(1, 25),
(2, 26),
(3, 27),
(4, 28),
(5, 29),
(6, 30),
(1, 31),
(2, 32),
(3, 33),
(4, 34),
(5, 35),
(6, 36),
(1, 37),
(2, 38),
(3, 39),
(4, 40),
(5, 41),
(6, 42),
(1, 43),
(2, 44),
(3, 45),
(4, 46),
(5, 47),
(6, 48);

COMMIT;
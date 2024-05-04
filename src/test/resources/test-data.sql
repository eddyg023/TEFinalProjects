
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


INSERT INTO team(team_id,name, city, state, coach_id)
VALUES (1, 'Eagles', 'Philadelphia', 'PA', 1),
(2, 'Lakers', 'Los Angeles', 'CA', 2),
(3, 'Bulls', 'Chicago', 'IL', 3),
(4, 'Celtics', 'Boston', 'MA', 4),

INSERT INTO coach(coach_id, name, age, team_id)
VALUES (1, 'John Smith', 45, 1),
(2, 'Emily Johnson', 38, 2),
(3, 'Michael Brown', 50, 3),
(4, 'Jessica Rodriguez', 42, 4),

INSERT INTO team_player(team_id, player_id)
VALUES (1, 1),
(2, 2),
(3, 3),
(4, 4),

COMMIT;
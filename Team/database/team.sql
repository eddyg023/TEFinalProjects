
BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, roles, player, coach, team, team_player;

CREATE TABLE users (
	username varchar(200) PRIMARY KEY,
	password varchar(200)
);

CREATE TABLE roles (
	username varchar(200) references users(username),
	rolename varchar(200)
);

CREATE TABLE team (
    team_id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    city VARCHAR(64) NOT NULL,
    state VARCHAR(2) NOT NULL
);

CREATE TABLE coach (
    coach_id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    age INT NOT NULL,
	years_coached INT,
    team_id INT,
    FOREIGN KEY (team_id) REFERENCES team(team_id)
);

CREATE TABLE player (
    player_id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    number INT NOT NULL,
    age INT NOT NULL,
    height INT NOT NULL,
    weight INT NOT NULL,
    player_position VARCHAR(128) NOT NULL,
	points_per_game FLOAT,
    assists_per_game FLOAT,
    rebounds_per_game FLOAT,
    steals_per_game FLOAT,
    blocks_per_game FLOAT
);

CREATE TABLE team_player (
    team_id INT,
    player_id INT,
    CONSTRAINT PK_team_player PRIMARY KEY (team_id, player_id),
    CONSTRAINT FK_team_player_team FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE,
    CONSTRAINT FK_team_player_player FOREIGN KEY (player_id) REFERENCES player(player_id) ON DELETE CASCADE
);

INSERT INTO users (username, password) VALUES ('commissioner', '$2a$10$cV.8tZthPtJ08EdM6lPtLuwnTya.Gm2DgNuFTTJS9uePjwYmmtWnW');
INSERT INTO roles (username, rolename) VALUES ('commissioner', 'COMMISSIONER');
INSERT INTO roles (username, rolename) VALUES ('commissioner', 'COACH');
INSERT INTO roles (username, rolename) VALUES ('commissioner', 'VIEWER');

INSERT INTO users (username, password) VALUES ('coach', '$2a$10$SBARYsdxlI75aIt5rjImL.8/y77Jg.00tXsKLNQsTpwa/AeOMeu4O');
INSERT INTO roles (username, rolename) VALUES ('coach', 'COACH');
INSERT INTO roles (username, rolename) VALUES ('coach', 'VIEWER');

INSERT INTO team (name, city, state) 
VALUES
('Eagles', 'Philadelphia', 'PA'),
('Lakers', 'Los Angeles', 'CA'),
('Bulls', 'Chicago', 'IL'),
('Celtics', 'Boston', 'MA'),
('Heat', 'Miami', 'FL'),
('Spurs', 'San Antonio', 'TX');

INSERT INTO coach (name, age, years_coached, team_id) 
VALUES
('John Smith', 45, 14, 1),
('Emily Johnson', 38, 12, 2),
('Michael Brown', 50, 18, 3),
('Jessica Rodriguez', 42, 20, 4),
('David Wilson', 55, 11, 5),
('Samantha White', 40, 17, 6);

INSERT INTO player (name, number, age, height, weight, player_position, points_per_game, assists_per_game, rebounds_per_game, steals_per_game, blocks_per_game) 
VALUES
('Jake Smith', 23, 28, 84, 210, 'Guard', 15.2, 4.1, 5.3, 1.7, 0.4),
('Sarah Johnson', 10, 25, 80, 187, 'Forward', 12.8, 3.6, 6.4, 1.3, 0.8),
('Michael Brown', 7, 31, 86, 232, 'Center', 18.4, 2.1, 9.8, 0.7, 1.5),
('Emily Martinez', 14, 27, 82, 159, 'Guard', 10.5, 5.2, 4.7, 2.3, 0.3),
('Ryan Thompson', 31, 29, 85, 217, 'Guard', 16.1, 4.4, 5.1, 1.8, 0.6),
('Samantha White', 22, 24, 78, 155, 'Guard', 8.9, 3.2, 3.5, 1.4, 0.2),
('Kevin Davis', 5, 30, 84, 213, 'Forward', 14.7, 3.9, 7.2, 1.0, 0.9),
('Jessica Rodriguez', 11, 26, 80, 188, 'Guard', 11.4, 4.0, 5.0, 1.6, 0.4),
('David Wilson', 3, 32, 88, 240, 'Center', 17.5, 2.2, 10.3, 0.8, 1.7),
('Lauren Taylor', 20, 28, 82, 210, 'Forward', 13.2, 3.7, 6.8, 1.1, 0.7),
('Daniel Anderson', 9, 27, 84, 202, 'Forward', 14.1, 3.4, 6.5, 1.2, 0.8),
('Ashley Thomas', 16, 25, 76, 150, 'Guard', 9.7, 3.0, 3.8, 1.5, 0.2),
('Matthew Garcia', 8, 29, 89, 222, 'Center', 16.8, 2.3, 9.5, 0.9, 1.3),
('Brittany Clark', 21, 26, 82, 195, 'Forward', 12.5, 3.5, 6.6, 1.1, 0.6),
('Jason Moore', 2, 33, 87, 255, 'Guard', 14.9, 4.5, 5.4, 1.7, 0.4),
('Amanda Lee', 13, 24, 75, 130, 'Guard', 7.8, 2.9, 3.3, 1.3, 0.1),
('Christopher Perez', 4, 31, 84, 217, 'Forward', 13.6, 3.8, 7.0, 1.0, 0.8),
('Megan King', 19, 27, 80, 195, 'Forward', 11.9, 3.6, 6.4, 1.2, 0.7),
('Joshua Scott', 12, 30, 86, 225, 'Center', 17.2, 2.3, 9.7, 0.9, 1.4),
('Taylor Hernandez', 6, 26, 78, 195, 'Forward', 12.2, 3.3, 6.1, 1.0, 0.5),
('Nicholas Ramirez', 17, 32, 88, 215, 'Center', 16.5, 2.1, 9.9, 0.7, 1.6),
('Rachel Adams', 15, 25, 80, 188, 'Forward', 11.4, 3.7, 6.0, 1.1, 0.8),
('Brandon Lewis', 1, 29, 85, 217, 'Guard', 15.5, 4.2, 5.2, 1.8, 0.5),
('Stephanie Hill', 18, 28, 82, 202, 'Forward', 13.1, 3.5, 6.7, 1.0, 0.7),
('Justin Green', 30, 27, 84, 203, 'Guard', 12.6, 4.0, 5.0, 1.4, 0.4),
('Nicole Cook', 24, 24, 76, 180, 'Forward', 10.2, 3.1, 5.8, 0.9, 0.6),
('Andrew Martinez', 33, 29, 89, 217, 'Guard', 16.7, 4.3, 5.6, 1.6, 0.6),
('Kayla Nelson', 29, 26, 82, 195, 'Guard', 11.3, 3.8, 5.4, 1.2, 0.4),
('Joseph Wright', 28, 33, 87, 247, 'Guard', 14.5, 4.5, 5.5, 1.5, 0.3),
('Olivia James', 27, 23, 75, 173, 'Guard', 8.5, 3.0, 3.8, 1.3, 0.2),
('Tyler Taylor', 26, 30, 84, 225, 'Forward', 14.9, 3.8, 7.1, 1.1, 0.9),
('Heather Adams', 32, 27, 80, 203, 'Guard', 10.8, 3.4, 5.0, 1.2, 0.3),
('Dylan Mitchell', 25, 31, 86, 232, 'Forward', 13.7, 3.5, 6.9, 1.0, 0.8),
('Kimberly Lopez', 36, 26, 78, 195, 'Center', 11.2, 2.0, 7.2, 0.6, 1.0),
('Eric Hill', 35, 32, 88, 240, 'Center', 16.9, 2.2, 10.0, 0.8, 1.5),
('Madison Martinez', 34, 24, 80, 180, 'Forward', 9.6, 3.1, 5.7, 1.0, 0.5),
('Austin Baker', 37, 28, 85, 213, 'Forward', 12.8, 3.6, 6.5, 1.1, 0.7),
('Emily Phillips', 38, 25, 82, 188, 'Guard', 11.9, 3.8, 5.6, 1.3, 0.4),
('Jacob Hall', 39, 27, 84, 203, 'Forward', 13.3, 3.4, 6.7, 1.1, 0.6),
('Samantha Wright', 40, 29, 75, 173, 'Guard', 9.0, 3.1, 3.9, 1.4, 0.2),
('Brandon Brown', 24, 28, 86, 210, 'Guard', 15.3, 4.1, 5.4, 1.7, 0.4),
('Christina Lee', 29, 26, 79, 195, 'Forward', 11.2, 3.5, 6.3, 1.0, 0.6),
('Tyler Adams', 22, 27, 83, 203, 'Guard', 12.7, 3.9, 5.1, 1.2, 0.5),
('Emma Wilson', 18, 29, 87, 225, 'Forward', 14.8, 3.7, 7.0, 1.1, 0.9),
('Jonathan Garcia', 33, 30, 88, 240, 'Center', 17.1, 2.4, 10.2, 0.9, 1.4),
('Olivia Smith', 30, 28, 85, 203, 'Forward', 12.3, 3.6, 6.2, 1.2, 0.5),
('Nathan Johnson', 25, 25, 81, 188, 'Guard', 15.4, 4.5, 6.0, 1.1, 0.3),
('Madison Brown', 27, 31, 89, 240, 'Center', 14.3, 5.6, 5.2, 1.4, 0.7);

INSERT INTO team_player(team_id, player_id) 
VALUES
(1, 1),
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
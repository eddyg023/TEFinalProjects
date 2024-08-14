package com.example.Team.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private int playerId;
    private String name;
    private int number;
    private int age;
    private int height;
    private int weight;
    private String player_position;
    private float points_per_game;
    private float assists_per_game;
    private float rebounds_per_game;
    private float steals_per_game;
    private float blocks_per_game;
}

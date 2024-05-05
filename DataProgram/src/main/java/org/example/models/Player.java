package org.example.models;

public class Player {
    private int playerId;
    private String name;
    private int number;
    private int age;
    private int height;
    private int weight;
    private String position;

    public Player(int playerId, String name, int number, int age, int height, int weight, String position) {
        this.playerId = playerId;
        this.name = name;
        this.number = number;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.position = position;
    }

    public Player() {}

    public int getPlayerId() {return playerId;}

    public void setPlayerId(int playerId) {this.playerId = playerId;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getNumber() {return number;}

    public void setNumber(int number) {this.number = number;}

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

    public int getHeight() {return height;}

    public void setHeight(int height) {this.height = height;}

    public int getWeight() {return weight;}

    public void setWeight(int weight) {this.weight = weight;}

    public String getPosition() {return position;}

    public void setPosition(String position) {this.position = position;}
}

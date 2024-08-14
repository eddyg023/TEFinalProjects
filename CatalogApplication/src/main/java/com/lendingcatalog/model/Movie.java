package com.lendingcatalog.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.*;
import java.util.*;

public class Movie implements CatalogItem{
    private String id;
    private String name;
    private String director;
    private LocalDate releaseDate;

    public Movie(String name, String director, LocalDate releaseDate) {
        this.name = name;
        this.director = director;
        this.releaseDate = releaseDate;
    }

    public String toString() {
        return "* " + this.name + System.lineSeparator()
                + " - Directed by: " + this.director + System.lineSeparator()
                + " - Released: " + this.releaseDate + System.lineSeparator()
                + " - Id: " + this.id;
    }

    @Override
    public boolean matchesName(String searchStr) {
        return this.name.toLowerCase().contains(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesCreator(String searchStr) {
        return this.director.toLowerCase().contains(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return this.releaseDate.getYear() == searchYear;
    }

    @Override
    public void registerItem() {
        this.id = UUID.randomUUID().toString();

        try (PrintWriter dataOutput = new PrintWriter(new
                FileOutputStream("src/main/resources/logs/movies.log", true))) {
            dataOutput.println(LocalDate.now() + " created new movie:");
            dataOutput.println(this);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open the log file for writing.");
        }
    }
}

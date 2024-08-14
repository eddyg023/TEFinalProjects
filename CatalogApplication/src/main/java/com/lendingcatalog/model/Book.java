package com.lendingcatalog.model;
import java.io.*;
import java.time.*;
import java.util.*;

public class Book implements CatalogItem{
    private String id;
    private String title;
    private String author;
    private LocalDate publishDate;

    public Book(String title, String author, LocalDate publishDate) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
    }

    public String toString() {
        return "* " + this.title + System.lineSeparator()
                + " - Written by: " + this.author + System.lineSeparator()
                + " - Published: " + this.publishDate + System.lineSeparator()
                + " - Id: " + this.id;
    }

    @Override
    public boolean matchesName(String searchStr) {
        return this.title.toLowerCase().contains(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesCreator(String searchStr) {
        return this.author.toLowerCase().contains(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return this.publishDate.getYear() == searchYear;
    }

    @Override
    public void registerItem() {
        this.id = UUID.randomUUID().toString();

        try (PrintWriter dataOutput = new PrintWriter(new
                FileOutputStream("src/main/resources/logs/books.log", true))) {
            dataOutput.println(LocalDate.now() + " created new book:");
            dataOutput.println(this);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open the log file for writing.");
        }
    }
}

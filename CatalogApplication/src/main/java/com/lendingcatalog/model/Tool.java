package com.lendingcatalog.model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

public class Tool implements CatalogItem{
    private String id;
    private String type;
    private String manufacturer;
    private int count;

    public Tool(String type, String manufacturer, int count) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.count = count;
    }

    public String toString() {
        return "* " + this.type + System.lineSeparator()
                + " - Manufactured by: " + this.manufacturer + System.lineSeparator()
                + " - Count: " + this.count + System.lineSeparator()
                + " - Id: " + this.id;
    }

    @Override
    public boolean matchesName(String searchStr) {
        return this.type.toLowerCase().contains(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesCreator(String searchStr) {
        return this.manufacturer.toLowerCase().contains(searchStr.toLowerCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return this.count == searchYear;
    }

    @Override
    public void registerItem() {
        this.id = UUID.randomUUID().toString();

        try (PrintWriter dataOutput = new PrintWriter(new
                FileOutputStream("src/main/resources/logs/tools.log", true))) {
            dataOutput.println(LocalDate.now() + " created new tool:");
            dataOutput.println(this);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot open the log file for writing.");
        }
    }
}

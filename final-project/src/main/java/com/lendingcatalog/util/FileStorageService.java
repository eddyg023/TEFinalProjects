package com.lendingcatalog.util;

import com.lendingcatalog.util.exception.FileStorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStorageService {

    // Requirement: File I/O
    public static void writeContentsToFile(String contents, String filename, boolean appendFile) throws FileStorageException {
        if (appendFile) {
            try (PrintWriter dataOutput = new PrintWriter(new
                    FileOutputStream(filename, true))) {
                dataOutput.print(contents);
            } catch (FileNotFoundException e) {
                throw new FileStorageException("Cannot open the file for writing");
            }
        } else {
            File dataFile = new File(filename);
            try (PrintWriter dataOutput = new PrintWriter(dataFile)) {
                dataOutput.println(contents);
            } catch (FileNotFoundException e) {
                throw new FileStorageException("Cannot open the file for writing.");
            }
        }
    }

    public static List<String> readContentsOfFile(String filename) throws FileStorageException {
        List<String> lines = new ArrayList<>();

        File dataFile = new File(filename);
        try (Scanner dataScanner = new Scanner(dataFile)) {
            while (dataScanner.hasNextLine()) {
                String currentLine = dataScanner.nextLine();
                lines.add(currentLine);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        return lines;
    }
}

package kruszywo.sa.computers.manage.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileCleaner {
    public static void cleanFileContent(String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
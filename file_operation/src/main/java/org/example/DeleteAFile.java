package org.example;

import java.io.File;

public class DeleteAFile {
    public void deleteAFile() {
        String filePath = "tmp/file.txt";
        File fileToDelete = new File(filePath);
        try {
            if (fileToDelete.exists()) {
                boolean isDeleted = fileToDelete.delete(); // Deletes the file
                if (isDeleted) {
                    System.out.println("File deleted successfully.");
                } else {
                    System.out.println("Unable to delete the file.");
                }
            } else {
                System.out.println("File does not exist at the specified path.");
            }
        } catch (SecurityException e) {
            System.out.println("SecurityException occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}

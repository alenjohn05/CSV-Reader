package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

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

    public void deleteDirectoryRecurrive(){
        Path directory = Path.of("tmp");
        try {
            if (!Files.exists(directory)) {
                System.out.println("Directory doesn't exist.");
                return;
            }

            Files.walkFileTree(directory, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    // Handle the case where file visit failed
                    System.err.println("Failed to visit file: " + file.toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
            System.out.println("Directory deleted successfully.");
        } catch (IOException e) {
            System.err.println("Failed to delete directory: " + e.getMessage());
        }
    }
}

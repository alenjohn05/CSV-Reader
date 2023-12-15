package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOperations {
    public void fileRenameMoveExample(){
        Path sourceFile = Paths.get("tmp/newfilename.txt");
        Path destinationFile = Paths.get("users/newfile.txt");
        try {
            // Renaming a file
            renameFile(sourceFile);
            // Moving a file
            moveFile(sourceFile, destinationFile);
            // Rename and Move the file
            renameAndMoveFile(sourceFile, destinationFile);
            System.out.println("File renamed and moved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to rename or move file: " + e.getMessage());
        }
    }
    private static void renameFile(Path sourceFile) throws IOException {
        if (!Files.exists(sourceFile)) {
            System.out.println("Source file doesn't exist.");
            return;
        }
        Path renamedFile = sourceFile.resolveSibling("newfilename.txt");
        Files.move(sourceFile, renamedFile);
    }
    private static void moveFile(Path sourceFile, Path destinationFile) throws IOException {
        if (!Files.exists(sourceFile)) {
            System.out.println("Source file doesn't exist.");
            return;
        }
        Files.move(sourceFile, destinationFile);
    }

    private static void renameAndMoveFile(Path sourceFile, Path destinationFile) throws IOException {
        if (!Files.exists(sourceFile)) {
            System.out.println("Source file doesn't exist.");
            return;
        }

        Path renamedFile = sourceFile.resolveSibling("newfilename.txt");
        Files.move(sourceFile, renamedFile);
        Files.move(renamedFile, destinationFile);
    }

    public void getSizeofTheFile(){
        String FILE_NAME = "users/sample.pdf";
        File file = new File(FILE_NAME);

        try {
            if (!file.exists() || !file.isFile()) {
                System.out.println("File doesn't exist or is not a regular file.");
                return;
            }

            System.out.println(getFileSizeBytes(file));
            System.out.println(getFileSizeKiloBytes(file));
            System.out.println(getFileSizeMegaBytes(file));
        } catch (SecurityException e) {
            System.err.println("Access to the file is denied: " + e.getMessage());
        }
    }

    private static String getFileSizeMegaBytes(File file) {
        return (double) file.length() / (1024 * 1024) + " mb";
    }

    private static String getFileSizeKiloBytes(File file) {
        return (double) file.length() / 1024 + "  kb";
    }

    private static String getFileSizeBytes(File file) {
        return file.length() + " bytes";
    }


    public void getFileExtension() {
        String filePath = "users/sample.pdf"; // Replace this with your file path
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            System.out.println("File extension: " + fileName.substring(dotIndex + 1));
        } else {
            System.out.println("File extension: " + " ");
        }
    }

    public void isFileGivenExists(){
        String filePath = "users/sample.pdf"; // Replace with your file path
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            System.out.println("File exists.");
        } else {
            System.out.println("File does not exist.");
        }
    }

    public 
}

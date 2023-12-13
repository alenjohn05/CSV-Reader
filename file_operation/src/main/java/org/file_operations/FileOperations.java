package org.example;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class FileOperations {
    public FileOperations() {
    }

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

    public void directoryCheckExample(){
        String filePath = "tmp"; // Replace with your directory path
        Path path = Paths.get(filePath);
        if (Files.exists(path) && Files.isDirectory(path)) {
            System.out.println("File is a directory.");
        } else {
            System.out.println("File is not a directory or does not exist.");
        }
    }

    public void lastModifiedDateExample(){
        String filePath = "users/sample.pdf"; // Replace with your file path
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try {
                long lastModified = Files.getLastModifiedTime(path).toMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = sdf.format(lastModified);
                System.out.println("Last modified date: " + formattedDate);
            } catch (Exception e) {
                System.out.println("Error getting last modified date: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public void fileFilterExample(){
        String directoryPath = "users"; // Replace with your directory path
        String fileExtension = ".txt"; // Replace with the file extension you want to filter
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            FilenameFilter filter = (dir, name) -> name.endsWith(fileExtension);
            File[] matchingFiles = directory.listFiles(filter);
            if (matchingFiles != null && matchingFiles.length > 0) {
                System.out.println("Files with extension '" + fileExtension + "':");
                for (File file : matchingFiles) {
                    System.out.println(file.getName());
                }
            } else {
                System.out.println("No files found with extension '" + fileExtension + "'.");
            }
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }
    }
    public void filePermissionsExample(){
        String filePath = "users/sample.pdf"; // Replace with your file path
        Path file = Paths.get(filePath);
        if (Files.exists(file)) {
            // Define permissions
            Set<PosixFilePermission> permissions = new HashSet<>();
            permissions.add(PosixFilePermission.OWNER_READ);
            permissions.add(PosixFilePermission.OWNER_WRITE);
            // Convert permissions to FileAttribute
            try {
                Files.setPosixFilePermissions(file, permissions);
                System.out.println("File permissions set successfully.");
            } catch (UnsupportedOperationException e) {
                System.out.println("Operation not supported on this platform: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error setting file permissions: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

}

package org.example;


public class Main {
    public static void main(String[] args) {
        new CreateNewFile().createNewFile();
        new CreateNewFile().allSeperators();

        //File Operations
        new FileOperations().fileRenameMoveExample();
        new FileOperations().getSizeofTheFile();
        new FileOperations().getFileExtension();
        new FileOperations().isFileGivenExists();
        new FileOperations().directoryCheckExample();
        new FileOperations().lastModifiedDateExample();
        new FileOperations().fileFilterExample();
        new FileOperations().filePermissionsExample();

//        // Delete A File and Directory
//        new DeleteAFile().deleteAFile();
//        new DeleteAFile().deleteDirectoryRecurrive();
    }
}

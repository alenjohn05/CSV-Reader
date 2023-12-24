package org.java_csv_reader;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        CsvReaderBean csvBeanReader = new CsvReaderBean();
        String csvFileName = "Book.csv";

        // Read all data from the original CSV file
        csvBeanReader.readCSVFile(csvFileName);

        // Add a new book
        csvBeanReader.addBook("0596527756", "Java Generics and Collections", "Naftali n & Philip Wadler", "O'Reilly Media", "10/24/2006", 27);

        // Get all books including the newly added one
        List<Book> updatedBooks = csvBeanReader.getBooks();

        // Write the updated list of books to a new CSV file
        csvBeanReader.writeCSVFile("netbook.csv", updatedBooks);

        // Perform partial reading
        csvBeanReader.partialReader(csvFileName);
    }
}

package org.java_csv_reader;

import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CsvReaderBean {
    private final List<Book> books = new ArrayList<>();

    public void readCSVFile(String csvFileName) {
        ICsvBeanReader beanReader = null;
        CellProcessor[] processors = new CellProcessor[]{
                new ISBNFormatProcessor(new NotNull()), // Custom processor for ISBN
                new NotNull(), // title
                new NotNull(), // author
                new NotNull(), // publisher
                new ParseDate("MM/dd/yyyy"), // published date
                new ParseDouble() // price
        };

        try {
            beanReader = new CsvBeanReader(new FileReader(csvFileName), CsvPreference.STANDARD_PREFERENCE);
            String[] header = beanReader.getHeader(true);
            Book bookBean;
            while ((bookBean = beanReader.read(Book.class, header, processors)) != null) {
                books.add(bookBean);
                System.out.printf("%s %-30s %-30s %-20s %tD $%.2f\n",
                        bookBean.getIsbn(), bookBean.getTitle(),
                        bookBean.getAuthor(), bookBean.getPublisher(),
                        bookBean.getPublished(), bookBean.getPrice());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Could not find the CSV file: " + ex);
        } catch (IOException ex) {
            System.err.println("Error reading the CSV file: " + ex);
        } finally {
            if (beanReader != null) {
                try {
                    beanReader.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the reader: " + ex);
                }
            }
        }
    }
    public void writeCSVFile(String csvFileName, List<Book> books) {
        ICsvBeanWriter beanWriter = null;

        try {
            beanWriter = new CsvBeanWriter(new FileWriter(csvFileName),
                    CsvPreference.STANDARD_PREFERENCE);

            // Define the CSV header
            final String[] header = new String[]{"isbn", "title", "author", "publisher", "published", "price"};

            // Write the header
            beanWriter.writeHeader(header);

            // Write each Book object to the CSV
            for (Book book : books) {
                beanWriter.write(book, header);
            }

        } catch (IOException ex) {
            System.err.println("Error writing to the CSV file: " + ex);
        } finally {
            if (beanWriter != null) {
                try {
                    beanWriter.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the writer: " + ex);
                }
            }
        }
    }

    public void addBook(String isbn, String title, String author, String publisher, String published, double price) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date publishedDate = null;
        try {
            publishedDate = dateFormat.parse(published);
        } catch (ParseException e) {
            System.err.println("Error closing the writer: " + e.getMessage());
        }
        Book newBook = new Book(isbn, title, author, publisher, publishedDate, price);
        books.add(newBook);
        System.out.println("New Book Added Successfully to the List");
    }

    public void partialReader(String csvFileName) {
        ICsvBeanReader beanReader = null;
        try {
            beanReader = new CsvBeanReader(new FileReader(csvFileName), CsvPreference.STANDARD_PREFERENCE);
            beanReader.getHeader(true);
            final String[] header = new String[]{"isbn", "title", "author", null, null, null};
            CellProcessor[] processors = new CellProcessor[]{
                    new NotNull(), // ISBN
                    new NotNull(), // title
                    new NotNull(), // author
                    null,
                    null,
                    null
            };
            Book bookBean;
            while ((bookBean = beanReader.read(Book.class, header, processors)) != null) {
                System.out.printf("isbn=%s, title=%s, Published=%s", bookBean.getIsbn(),
                        bookBean.getTitle(), bookBean.getPublished());
                System.out.println();
            }
        } catch (IOException ex) {
            System.err.println("Error reading the file: " + ex);
        } finally {
            if (beanReader != null) {
                try {
                    beanReader.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the reader: " + ex);
                }
            }
        }
    }

    public void readSpecificColumns(String csvFileName) {
        String[] columnsToRead = {"title", "author"};

        try {
            ICsvListReader listReader = new CsvListReader(new FileReader(csvFileName), CsvPreference.STANDARD_PREFERENCE);
            String[] header = listReader.getHeader(true);

            int[] columnIndex = new int[columnsToRead.length];
            for (int i = 0; i < columnsToRead.length; i++) {
                columnIndex[i] = findColumnIndex(header, columnsToRead[i]);
            }

            CellProcessor[] cellProcessors = new CellProcessor[header.length];
            for (int i = 0; i < header.length; i++) {
                if (Arrays.asList(columnsToRead).contains(header[i])) {
                    cellProcessors[i] = new NotNull();
                } else {
                    cellProcessors[i] = null;
                }
            }
            List<Object> rowData;
            while ((rowData = listReader.read(cellProcessors)) != null) {
                for (int index : columnIndex) {
                    System.out.print(rowData.get(index) + "\t");
                }
                System.out.println();
            }
            listReader.close();

        } catch (IOException ex) {
            System.err.println("Error reading the file: " + ex);
        }
    }

    private static int findColumnIndex(String @org.jetbrains.annotations.NotNull [] header, String columnName) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }


    public List<Book> getBooks() {
        return books;
    }

    public void readCSVFileAndIncreasePrice(String csvFileName) {
        ICsvBeanReader beanReader = null;
        CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // Custom processor for ISBN
                new NotNull(), // title
                new NotNull(), // author
                new NotNull(), // publisher
                new ParseDate("MM/dd/yyyy"), // published date
                new IncreasePriceProcessor(new ParseDouble()) // price
        };

        try {
            beanReader = new CsvBeanReader(new FileReader(csvFileName), CsvPreference.STANDARD_PREFERENCE);
            String[] header = beanReader.getHeader(true);
            Book bookBean;
            while ((bookBean = beanReader.read(Book.class, header, processors)) != null) {
                books.add(bookBean);
                System.out.printf("%s %-30s %-30s %-20s %tD $%.2f\n",
                        bookBean.getIsbn(), bookBean.getTitle(),
                        bookBean.getAuthor(), bookBean.getPublisher(),
                        bookBean.getPublished(), bookBean.getPrice());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Could not find the CSV file: " + ex);
        } catch (IOException ex) {
            System.err.println("Error reading the CSV file: " + ex);
        } finally {
            if (beanReader != null) {
                try {
                    beanReader.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the reader: " + ex);
                }
            }
        }
    }
}

package org.java_csv_reader;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.StringCellProcessor;
import org.supercsv.util.CsvContext;

import java.util.Random;

public class ISBNFormatProcessor extends CellProcessorAdaptor implements StringCellProcessor {
    public ISBNFormatProcessor() {
        super();
    }

    public ISBNFormatProcessor(StringCellProcessor next) {
        super(next);
    }

    @Override
    public Object execute(Object value, CsvContext context) {
        // For this demonstration, let's assume the input value is ignored
        // and we're generating a random ISBN
        String generatedISBN = generateRandomISBN();

        return next.execute(generatedISBN, context);
    }

    private String generateRandomISBN() {
        StringBuilder isbnBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 13; i++) {
            if (i == 3 || i == 5 || i == 9) {
                isbnBuilder.append('-');
            } else {
                isbnBuilder.append(random.nextInt(10));
            }
        }
        return isbnBuilder.toString();
    }
}

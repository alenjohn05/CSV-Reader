package org.java_csv_reader;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

public class IncreasePriceProcessor extends CellProcessorAdaptor {
    public IncreasePriceProcessor() {
        super();
    }
    public IncreasePriceProcessor(CellProcessor next) {
        super(next);
    }
    @Override
    public Object execute(Object value, CsvContext context) {
        if (value == null) {
            return next.execute(null, context);
        }
        if (value instanceof String) {
            double price = Double.parseDouble((String) value);
            Double increasedPrice = price + 50.0;
            return next.execute(increasedPrice, context);
        } else {
            return next.execute(value, context);
        }
    }
}

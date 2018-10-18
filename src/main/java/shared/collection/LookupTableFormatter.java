package shared.collection;

import util.shared.math.MathUtil;
import util.shared.primitive.Val;

import java.util.List;

public interface LookupTableFormatter {

    String format(LookupTable table);


    LookupTableFormatter CSV = table -> {
        int total = table.total;
        int columnCount = table.columnCount;
        List<Val> values = table.values;

        StringBuilder csv = new StringBuilder(total * 20);
        for (int i = 0; i < total; i++) {
            if (i > 0) {
                csv.append((i % columnCount) == 0 ? ",\n" : ",");
            }
            Val val = values.get(i);
            if (val.isDouble()) {
                csv.append(MathUtil.round(val.asDouble(), 6));
            }
            else {
                csv.append(val.toText());
            }
        }
        return csv.toString();
    };


}

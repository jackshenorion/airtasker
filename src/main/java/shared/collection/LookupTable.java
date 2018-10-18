package shared.collection;

import util.shared.math.MathUtil;
import util.shared.primitive.Val;
import util.shared.primitive.Vals;

import java.util.*;

public final class LookupTable {

    public enum LookupType {
        Exact,
        Range
    }

    int columnCount;
    int total;
    List<Val> values = Collections.emptyList();
    IndexColumn[] indexColumns;

    public LookupTable(int columnCount, Val[] values) {
        this(columnCount, Arrays.asList(values));
    }

    public LookupTable(int columnCount, List<Val> values) {
        this.columnCount = columnCount;
        this.values = values;
        this.total = values.size();
        index();
    }

    /**
     * @param returnValueColumn - starting from 1
     */
    public Val vlookup(Val lookupValue, int returnValueColumn, LookupType type) {
        return vlookup(lookupValue, returnValueColumn, type == LookupType.Exact ? 0 : 1, 1, LookupTableOffset.ZERO);
    }

    /**
     * @param returnValueColumn - starting from 1
     */
    public Val vlookup(Val lookupValue, int returnValueColumn, LookupType type, LookupTableOffset offset) {
        return vlookup(lookupValue, returnValueColumn, type == LookupType.Exact ? 0 : 1, 1, offset);
    }

    /**
     * @param returnValueColumn - starting from 1
     * @param indexColumn - starting from 1
     */
    public Val vlookup(Val lookupValue, int returnValueColumn, LookupType type, int indexColumn, LookupTableOffset offset) {
        return vlookup(lookupValue, returnValueColumn, type == LookupType.Exact ? 0 : 1, indexColumn, offset);
    }

    /**
     * @param returnValueColumn - starting from 1
     */
    public Val vlookup(Val lookupValue, int returnValueColumn, int type) {
        return vlookup(lookupValue, returnValueColumn, type, 1, LookupTableOffset.ZERO);
    }

    /**
     * @param returnValueColumn - starting from 1
     * @param indexColumn - starting from 1
     */
    public Val vlookup(Val lookupValue, int returnValueColumn, int type, int indexColumn, LookupTableOffset offset) {
        if (returnValueColumn < 1 || returnValueColumn > columnCount) {
            return Vals.ofNull();
        }
        IndexColumn indexCol = getIndexColumn(indexColumn - 1);
        if (indexCol == null) {
            return Vals.ofNull();
        }
        return type == 0 ?
            indexCol.lookupExact(lookupValue, returnValueColumn - 1, offset) :
            indexCol.lookupRange(lookupValue, returnValueColumn - 1, offset);
    }

    private void index() {
        for (int i = 0; i < total; i++) {
            if (values.get(i) == null) {
                values.set(i, Vals.ofNull());
            }
        }
        indexColumns = new IndexColumn[columnCount];
        getIndexColumn(0); // index the first column.
    }

    private IndexColumn getIndexColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnCount) {
            return null;
        }

        IndexColumn indexColumn = indexColumns[columnIndex];
        if (indexColumn == null) {
            List<Val> indexValues = new ArrayList<>();
            for (int i = columnIndex; i < total; i += columnCount) {
                indexValues.add(values.get(i));
            }
            indexColumn = new IndexColumn(indexValues);
            indexColumns[columnIndex] = indexColumn;
        }
        return indexColumn;
    }

    private Val getValue(int row, int column, LookupTableOffset offset) {
        row += offset.getRowOffset();
        column += offset.getColumnOffset();

        if (column < 0 || column >= columnCount) {
            return Vals.ofNull();
        }

        int pos = row * columnCount + column;
        return pos < 0 || pos >= total ? Vals.ofNull() : values.get(pos);
    }

    private class IndexColumn {

        private List<Val> indexValues;
        private Map<String, Integer> largeTableIndex;
        private boolean descending;

        public IndexColumn(List<Val> indexValues) {
            this.indexValues = indexValues;

            if (indexValues.size() > 16) {
                largeTableIndex = new HashMap<>();
                for (int i = 0, total = indexValues.size(); i < total; i++) {
                    Val v = indexValues.get(i);
                    largeTableIndex.put(buildLookupKey(v), i);
                }
            }

            // test if the column is in ascending or descending order.
            if (indexValues.size() >= 2) {
                if (indexValues.get(0).isGreaterThan(indexValues.get(indexValues.size() - 1))) {
                    descending = true;
                }
            }
        }

        public Val lookupExact(Val lookupValue, int returnColumn, LookupTableOffset offset) {
            if (largeTableIndex != null) {
                Integer row = largeTableIndex.get(buildLookupKey(lookupValue));
                return row == null ? Vals.ofNull() : getValue(row, returnColumn, offset);
            }
            else {
                for (int row = 0, total = indexValues.size(); row < total; row++) {
                    if (indexValues.get(row).isEqualTo(lookupValue)) {
                        return getValue(row, returnColumn, offset);
                    }
                }
                return Vals.ofNull();
            }
        }

        public Val lookupRange(Val lookupValue, int returnColumn, LookupTableOffset offset) {
            Comparator<Val> comparator = Val::comparesTo;
            if (descending) {
                comparator = Collections.reverseOrder(comparator);
            }

            int index = Collections.binarySearch(indexValues, lookupValue, comparator);
            if (index == -1) { // the criterion is smaller than the smallest value of the column
                return Vals.ofNull();
            }
            if (index < -1) { // no match and fail in range
                index = -(index + 1) - 1;
            }
            return getValue(index, returnColumn, offset);
        }

        private String buildLookupKey(Val v) {
            if (v.isDouble()) {
                return Double.toString(MathUtil.round(v.asDouble(), 6));
            }
            return v.toText();
        }
    }

}

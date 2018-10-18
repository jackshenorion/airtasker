package shared.collection;

public interface LookupTableOffset {

    LookupTableOffset ZERO = new Constant(0, 0);
    
    int getRowOffset();
    int getColumnOffset();

    static Constant by(int rowOffset, int columnOffset) {
        return new Constant(rowOffset, columnOffset);
    }

    static Constant byRows(int rowOffset) {
        return new Constant(rowOffset, 0);
    }

    static Constant byColumns(int columnOffset) {
        return new Constant(0, columnOffset);
    }

    final class Constant implements LookupTableOffset {
        private int rowOffset;

        private int columnOffset;

        private Constant(int rowOffset, int columnOffset) {
            this.rowOffset = rowOffset;
            this.columnOffset = columnOffset;
        }

        @Override
        public int getRowOffset() {
            return rowOffset;
        }

        @Override
        public int getColumnOffset() {
            return columnOffset;
        }



    }
}

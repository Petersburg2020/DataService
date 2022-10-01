package nx.peter.java.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Statistics {
    protected List<Number> data;

    public Statistics(Number... data) {
        setData(data);
    }

    public Statistics(List<Number> data) {
        setData(data);
    }

    public void setData(Number... data) {
        setData(Arrays.asList(data));
    }

    public void setData(List<Number> data) {
        this.data = new ArrayList<>();
        addData(data);
    }

    public void addData(Number... data) {
        addData(Arrays.asList(data));
    }

    public void addData(List<Number> data) {
        for (Number d : data)
            if (d != null)
                this.data.add(d);
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public boolean isOrderedData() {
        return getOrderedDataSize() == getDataSize();
    }

    public boolean isUnorderedData() {
        return !isOrderedData();
    }

    public Util.DataAnalyser.Data<Number> getOrderedData() {
        return getDataAnalyser().getSortedData().getData();
    }

    public int getDataSize() {
        return getRawData().size();
    }

    public int getOrderedDataSize() {
        return getOrderedData().size();
    }

    public int getFrequency(Number data) {
        return getRawData().getDataCount().getCount(data);
    }

    public Number getMode() {
        return getDataAnalyser().getMode();
    }

    public Number getMedian() {
        return getDataAnalyser().getMedian();
    }

    public Number getRange() {
        return getDataAnalyser().getRange();
    }

    public Util.MinMax<Number> getMinMax() {
        return getDataAnalyser().getMinMax();
    }

    public double getMean() {
        return Util.toNDecimalPlace(getDataAnalyser().getMean(), 2);
    }

    public double getStandardDeviation() {
        // SD = sqrt(Sum of (x - mean)^2 / N)  ==> N : number of data, SD : standard deviation
        double sumDiff = 0; // Sum of (x - mean)  ==> x : each data
        double mean = getMean();

        // Iterate values to get sumDiff
        for (Number value : data)
            sumDiff += Math.pow(Util.sum(value, mean).doubleValue(), 2);

        // Solve S.D.
        return Util.toNDecimalPlace(Math.sqrt(sumDiff / getDataSize()), 2);
    }

    public double getVariance() {
        // V = Sum of (x - mean)^2 / (N - 1) ==> N : number of data, V : variance
        double sumDiff = 0; // Sum of (x - mean)  ==> x : each data
        double mean = getMean();

        // Iterate values to get sumDiff
        for (Number value : data)
            sumDiff += Math.pow(Util.sum(value, mean).doubleValue(), 2);

        // Solve V
        return Util.toNDecimalPlace(sumDiff / (getDataSize() - 1), 2);
    }

    public Util.DataAnalyser.Data<Number> getRawData() {
        return getDataAnalyser().getRawData();
    }

    public Util.DataAnalyser.Data<Number> getSortedData() {
        return getDataAnalyser().getSortedData();
    }

    protected Util.DataAnalyser<Number> getDataAnalyser() {
        return new Util.DataAnalyser<>(data);
    }

    public Table getTable() {
        return new ITable(this);
    }



    public interface Table {
        Total getTotal();
        Heading getHeading();
        Column getColumn(int column);
        Column getColumn(String heading);
        SerialNumber getSerialNumber();
        Row getRow(int row);
        int getColumnCount();
        int getRowCount();
        Statistics getStatistics();

        interface Entity<E extends Entity, I> extends Iterable<I> {
            int getIndex();
            boolean isEmpty();
            boolean isNotEmpty();
            boolean contains(I data);
            boolean equals(E entity);
            Table getTable();
        }
    }

    public interface Row extends Table.Entity<Row, Number> {
        int getColumnCount();
        Number getData(int column);
        Column getColumn(int column);
    }

    public interface Heading extends Table.Entity<Heading, String> {
        String getData(int column);
        int getColumnCount();
        Column getColumn(int column);
    }

    public interface Total extends Row {
        int getTotalPopulation();
        Number getMedian();
        double getMean();
        Number getMode();
        double getVariance();
        double getStandardDeviation();
    }

    public interface SerialNumber extends Table.Entity<SerialNumber, String> {
        String getHeading();
        int getRowCount();
        String getTotal();
        int getNumber(int row);
    }

    public interface Column extends Table.Entity<Column, Number> {
        String getHeading();
        int getRowCount();
        Number getTotal();
        Number getData(int row);
        Row getRow(int row);
    }

    protected static class IRow implements Row {
        protected List<Number> data;
        protected Table table;
        protected int row, width;

        public IRow(int row) {
            this(row, new ArrayList<>());
        }

        public IRow(int row, List<Number> data) {
            this.row = row;
            this.data = data;
        }

        @Override
        public int getIndex() {
            return row;
        }

        @Override
        public int getColumnCount() {
            return data.size();
        }

        @Override
        public Number getData(int column) {
            return column > 0 && column < getColumnCount() ? data.get(column - 1) : null;
        }

        @Override
        public boolean isEmpty() {
            return data.isEmpty();
        }

        @Override
        public boolean isNotEmpty() {
            return !isEmpty();
        }

        @Override
        public boolean contains(Number data) {
            return this.data.contains(data);
        }

        @Override
        public boolean equals(Row row) {
            return row != null && ((IRow) row).data.equals(data);
        }

        @Override
        public Column getColumn(int column) {
            return table.getColumn(column);
        }

        @Override
        public Table getTable() {
            return table;
        }

        public IRow setTable(Table table) {
            this.table = table;
            return this;
        }

        public IRow setWidth(int width) {
            this.width = width;
            return this;
        }

        @Override
        public Iterator<Number> iterator() {
            return data.iterator();
        }

        @Override
        public String toString() {
            String row = "";
            int column = 2;
            for (Number d : data) {
                row += "| " + Util.centerLine(d.toString(), table.getHeading().getData(column).length()) + " ";
                column++;
            }
            int sn = table.getSerialNumber().getNumber(this.row);
            return "| " + (this instanceof Total ? table.getSerialNumber().getTotal() : Util.centerLine(((sn + "").length() % 2 == 0 ? sn + "" : sn + " "), "Total".length())) + " " + (row.isEmpty() ? "|" : row) + "|";
        }
    }

    protected static class IHeading implements Heading {
        protected List<String> headings;
        protected Table table;

        public IHeading(List<String> headings) {
            this.headings = headings;
        }

        @Override
        public int getIndex() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return headings.isEmpty();
        }

        @Override
        public boolean isNotEmpty() {
            return !isEmpty();
        }

        @Override
        public boolean contains(String data) {
            return headings.contains(data);
        }

        @Override
        public boolean equals(Heading entity) {
            return entity != null && ((IHeading) entity).headings.equals(headings);
        }

        @Override
        public Table getTable() {
            return table;
        }

        public IHeading setTable(Table table) {
            this.table = table;
            return this;
        }

        @Override
        public String getData(int column) {
            return headings.get(column - 1);
        }

        @Override
        public int getColumnCount() {
            return headings.size();
        }

        @Override
        public Column getColumn(int column) {
            return table.getColumn(column);
        }

        @Override
        public Iterator<String> iterator() {
            return headings.iterator();
        }

        @Override
        public String toString() {
            AtomicInteger index = new AtomicInteger();
            return (isEmpty() ? "" : "|") + headings.stream().map(s -> {
                index.getAndIncrement();
                return " " + (index.get() == 1 ? Util.centerLine(s, "Total".length()) : s) + " |";
            }).collect(Collectors.joining());
        }
    }

    protected static class ITotal extends IRow implements Total {

        public ITotal(int row, List<Number> data) {
            super(row, data);
        }

        @Override
        public int getTotalPopulation() {
            return table.getStatistics().getDataSize();
        }

        @Override
        public Number getMedian() {
            return table.getStatistics().getMedian();
        }

        @Override
        public double getMean() {
            return table.getStatistics().getMean();
        }

        @Override
        public Number getMode() {
            return table.getStatistics().getMode();
        }

        @Override
        public double getVariance() {
            return table.getStatistics().getVariance();
        }

        @Override
        public double getStandardDeviation() {
            return table.getStatistics().getStandardDeviation();
        }

    }

    protected static class IColumn implements Column {
        protected int column;
        protected String heading;
        protected List<Number> data;
        protected Table table;

        public IColumn(int column, String heading, List<Number> data) {
            this.column = column;
            this.heading = heading;
            this.data = data;
        }

        @Override
        public int getIndex() {
            return column;
        }

        @Override
        public String getHeading() {
            return heading;
        }

        @Override
        public int getRowCount() {
            return table.getRowCount();
        }

        @Override
        public Number getTotal() {
            if (isEmpty()) return null;
            Number total = data.get(0);
            for (int i = 1; i < getRowCount(); i++)
                total = Util.sum(total, data.get(i));
            return total;
        }

        @Override
        public boolean isEmpty() {
            return data.isEmpty();
        }

        @Override
        public boolean isNotEmpty() {
            return !isEmpty();
        }

        @Override
        public boolean equals(Column column) {
            return column != null && ((IColumn) column).data.equals(data);
        }

        @Override
        public boolean contains(Number data) {
            return this.data.contains(data);
        }

        @Override
        public Number getData(int row) {
            return getRow(row).getData(column);
        }

        @Override
        public Row getRow(int row) {
            return table.getRow(row);
        }

        @Override
        public Table getTable() {
            return table;
        }

        public IColumn setTable(Table table) {
            this.table = table;
            return this;
        }

        @Override
        public Iterator<Number> iterator() {
            return data.iterator();
        }

        @Override
        public String toString() {
            String column = "";
            for (Number d : data)
                column += Util.centerLine(d.toString(), heading.length()) + "\n";
            return heading + "\n" + column + Util.centerLine(getTotal().toString(), heading.length());
        }
    }

    protected static class ISerialNumber implements SerialNumber {
        protected List<String> numbers;
        protected List<Integer> nos;
        protected String heading;
        protected Table table;

        public ISerialNumber(String heading, List<Integer> numbers) {
            this.heading = heading;
            this.nos = numbers;
            this.numbers = new ArrayList<>();
            for (Integer i : numbers)
                this.numbers.add(i.toString());
        }

        @Override
        public boolean isEmpty() {
            return numbers.isEmpty();
        }

        @Override
        public boolean isNotEmpty() {
            return !isEmpty();
        }

        @Override
        public boolean contains(String data) {
            return numbers.contains(data);
        }

        @Override
        public boolean equals(SerialNumber entity) {
            return entity != null && ((ISerialNumber) entity).numbers.equals(numbers);
        }

        @Override
        public Table getTable() {
            return table;
        }

        public ISerialNumber setTable(Table table) {
            this.table = table;
            return this;
        }

        @Override
        public int getIndex() {
            return 1;
        }

        @Override
        public String getHeading() {
            return heading;
        }

        @Override
        public int getRowCount() {
            return numbers.size();
        }

        @Override
        public String getTotal() {
            return "Total";
        }

        @Override
        public int getNumber(int row) {
            return row > 0 && row <= getRowCount() ? nos.get(row - 1) : 0;
        }

        @Override
        public Iterator<String> iterator() {
            return numbers.iterator();
        }

        @Override
        public String toString() {
            String column = "";
            for (String d : numbers)
                column += d + "\n";
            return heading + "\n" + column + getTotal();
        }
    }

    protected static class ITable implements Table {
        protected Statistics statistics;
        protected Heading heading;
        protected Total total;
        protected List<Column> columns;
        protected SerialNumber serialNumber;

        public ITable(Statistics statistics) {
            this.statistics = statistics;
            columns = new ArrayList<>();

            String[] headings = {
                    " S/N ",
                    " Data (x) ",
                    "Frequency (f)",
                    " f(x) ",
                    " Mean (m) ",
                    " f(x) - m ",
                    "(f(x) - m)²"
            };

            heading = new IHeading(Arrays.asList(headings));

            // Update table
            for (int column = 1; column <= headings.length; column++) {
                switch (column) {
                    case 1 -> {
                        List<Integer> numbers = new ArrayList<>();
                        for (int i = 1; i <= statistics.getOrderedDataSize(); i++)
                            numbers.add(i);
                        serialNumber = new ISerialNumber(headings[column - 1], numbers).setTable(this);
                    }
                    case 2 -> {
                        List<Number> data = new ArrayList<>();
                        for (int i = 1; i <= statistics.getOrderedDataSize(); i++)
                            data.add(statistics.getOrderedData().get(i));
                        columns.add(new IColumn(column, headings[column - 1], data).setTable(this));
                    }
                    case 3 -> {
                        List<Number> frequency = new ArrayList<>();
                        for (int i = 1; i <= statistics.getOrderedDataSize(); i++)
                            frequency.add(statistics.getRawData().getDataCount().getCount(statistics.getOrderedData().get(i)));
                        columns.add(new IColumn(column, headings[2], frequency).setTable(this));
                    }
                    case 4 -> {
                        List<Number> fX = new ArrayList<>();
                        for (int i = 1; i <= statistics.getOrderedDataSize(); i++) {
                            Number val = statistics.getOrderedData().get(i);
                            fX.add(Util.product(val, statistics.getRawData().getDataCount().getCount(val)));
                        }
                        columns.add(new IColumn(column, headings[column - 1], fX).setTable(this));
                    }
                    case 5 -> {
                        List<Number> mean = new ArrayList<>();
                        for (int i = 1; i <= statistics.getOrderedDataSize(); i++)
                            mean.add(statistics.getMean());
                        columns.add(new IColumn(column, headings[column - 1], mean).setTable(this));
                    }
                    case 6 -> {
                        List<Number> fXMean = new ArrayList<>();
                        for (int i = 1; i <= statistics.getOrderedDataSize(); i++)
                            fXMean.add(Util.toNDecimalPlace(Util.diff(statistics.getOrderedData().get(i), statistics.getMean()).doubleValue(), 2));
                        columns.add(new IColumn(2, headings[1], fXMean).setTable(this));
                    }
                    case 7 -> {
                        List<Number> fXMeanSquare = new ArrayList<>();
                        for (int i = 1; i <= statistics.getOrderedDataSize(); i++)
                            fXMeanSquare.add(Util.toNDecimalPlace(Util.pow(Util.diff(statistics.getOrderedData().get(i), statistics.getMean()), 2), 2));
                        columns.add(new IColumn(2, headings[1], fXMeanSquare).setTable(this));
                    }
                }
            }

            List<Number> data = new ArrayList<>();
            int index = 1;
            for (Column column : columns) {
                data.add((index == 4 ? statistics.getMean() : column.getTotal()));
                index++;
            }
            total = (Total) new ITotal(getRowCount() + 2, data).setTable(this);
        }

        @Override
        public Total getTotal() {
            return total;
        }

        @Override
        public Heading getHeading() {
            return heading;
        }

        @Override
        public Column getColumn(int column) {
            return column > 0 && column < getColumnCount() ? columns.get(column) : null;
        }

        @Override
        public Column getColumn(String heading) {
            for (Column column : columns)
                if (column.getHeading().contentEquals(heading))
                    return column;
            return null;
        }

        @Override
        public SerialNumber getSerialNumber() {
            return serialNumber;
        }

        @Override
        public Row getRow(int row) {
            if (row < 1 || row > getRowCount()) return new IRow(row);
            List<Number> data = new ArrayList<>();
            for (Column column : columns)
                data.add(((IColumn) column).data.get(row - 1));
            return new IRow(row, data).setTable(this);
        }

        @Override
        public int getColumnCount() {
            return columns.size();
        }

        @Override
        public int getRowCount() {
            return statistics.getOrderedDataSize();
        }

        @Override
        public Statistics getStatistics() {
            return statistics;
        }

        @Override
        public String toString() {
            String table = heading + "\n";
            for (int row = 1; row <= getRowCount(); row++)
                table += getRow(row) + "\n";
            return table + total;
        }
    }
}

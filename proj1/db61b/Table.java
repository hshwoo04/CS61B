package db61b;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static db61b.Utils.*;

/** A single table in a database.
 *  @author P. N. Hilfinger
 */
class Table implements Iterable<Row> {
    /** A new Table whose columns are given by COLUMNTITLES, which may
     *  not contain dupliace names. */
    String[] columnTitles;
    /** Constructor. FIRSTCOLUMNTITLES */
    Table(String[] firstColumnTitles) {
        for (int i = firstColumnTitles.length - 1; i >= 1; i -= 1) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (firstColumnTitles[i].equals(firstColumnTitles[j])) {
                    throw error("duplicate column name: %s",
                                firstColumnTitles[i]);
                }
            }
        }

        this.columnTitles = columnTitles;
    }

    /** A new Table whose columns are give by SECONDCOLUMNTITLES. */
    Table(List<String> secondColumnTitles) {
        this(secondColumnTitles.toArray(new String[secondColumnTitles.size()]));
    }

    /** Return the number of columns in this table. */
    public int columns() {
        return this.columnTitles.length;
    }

    /** Return the title of the Kth column.  Requires 0 <= K < columns(). */
    public String getTitle(int k) {
        return this.columnTitles[k];
    }

    /** Return the number of the column whose title is TITLE, or -1 if
     *  there isn't one. */
    public int findColumn(String title) {
        for (int i = 0; i < this.columns(); i++) {
            if (this.columnTitles[i].equals(title)) {
                return i;
            }
        }
        return -1;
    }

    /** Return the number of Rows in this table. */
    public int size() {
        return _rows.size();
    }

    /** Returns an iterator that returns my rows in an unspecfied order. */
    @Override
    public Iterator<Row> iterator() {
        return _rows.iterator();
    }

    /** Add ROW to THIS if no equal row already exists.  Return true if anything
     *  was added, false otherwise. */
    public boolean add(Row row) {
        if (_rows.contains(row)) {
            return false;
        } else {
            _rows.add(row);
            return true;
        }
    }

    /** Read the contents of the file NAME.db, and return as a Table.
     *  Format errors in the .db file cause a DBException. */
    static Table readTable(String name) {
        BufferedReader input;
        Table table;
        int numRow;
        input = null;
        table = null;
        numRow = 0;
        try {
            input = new BufferedReader(new FileReader(name + ".db"));
            String header = input.readLine();
            if (header == null) {
                throw error("missing header in DB file");
            }
            String[] columnNames = header.split(",");
            table = new Table(columnNames);

            while (input.ready()) {
                String rowString = input.readLine();
                String[] rowInfo = rowString.split(",");
                Row addRow = new Row(rowInfo);
                table.add(addRow);
            }
        } catch (FileNotFoundException e) {
            throw error("could not find %s.db", name);
        } catch (IOException e) {
            throw error("problem reading from %s.db", name);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    /* Ignore IOException */
                }
            }
        }
        return table;
    }

    /** Write the contents of TABLE into the file NAME.db. Any I/O errors
     *  cause a DBException. */
    void writeTable(String name) {
        PrintStream output;
        output = null;
        try {
            String sep;
            sep = "";
            output = new PrintStream(name + ".db");
            for (String column : this.columnTitles) {
                if (Arrays.binarySearch(this.columnTitles, column)
                        == (this.columnTitles.length) - 1) {
                    output.append(column);
                } else {
                    output.append(column + ",");
                }
            }
            for (Row rowInstance : this._rows) {
                output.println();
                for (int i = 0; i < rowInstance.size(); i++) {
                    if (i == rowInstance.size() - 1) {
                        output.append(rowInstance.get(i));
                    } else {
                        output.append(rowInstance.get(i) + ",");
                    }
                }
            }
        } catch (IOException e) {
            throw error("trouble writing to %s.db", name);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /** Print my contents on the standard output. */
    void print() {
        for (Row rowInstance : this._rows) {
            for (int i = 0; i < rowInstance.size(); i++) {
                System.out.print(rowInstance.get(i) + " ");
            }
            System.out.println();
        }
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected from
     *  rows of this table that satisfy CONDITIONS. */
    Table select(List<String> columnNames, List<Condition> conditions) {
        Table result = new Table(columnNames);
        for (Row row : this._rows) {
            ArrayList<String> columns = new ArrayList<String>();
            if (Condition.test(conditions, row)) {
                for (String column : columnNames) {
                    columns.add(row.get(this.findColumn(column)));
                }
                String[] columnsArray =
                    columns.toArray(new String[columns.size()]);
                Row newRow = new Row(columnsArray);
                result.add(newRow);
            }
        }
        return result;
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected
     *  from pairs of rows from this table and from TABLE2 that match
     *  on all columns with identical names and satisfy CONDITIONS. */
    Table select(Table table2, List<String> columnNames,
                 List<Condition> conditions) {
        Table result = new Table(columnNames);
        ArrayList<String> tableColumns = allColumns(this);
        ArrayList<String> table2Columns = allColumns(table2);
        HashSet<String> combinedColumns = new HashSet<String>();
        ArrayList<String> commonColumns = new ArrayList<String>();
        ArrayList<String> combinedColumnsList = new ArrayList<String>();

        for (String string : tableColumns) {
            combinedColumns.add(string);
        }
        for (String string : table2Columns) {
            combinedColumns.add(string);
        }
        for (String string : combinedColumns) {
            combinedColumnsList.add(string);
        }
        Table beforeresult = new Table(combinedColumnsList);
        for (Row row : this._rows) {
            for (Row row2 : table2._rows) {
                for (String column : tableColumns) {
                    for (String column2 : table2Columns) {
                        ArrayList<Column> columnArray =
                            new ArrayList<Column>();
                        ArrayList<Column> columnArray2 =
                            new ArrayList<Column>();
                        Column firstColumn = new Column(column, this);
                        Column secondColumn = new Column(column2, table2);
                        columnArray.add(firstColumn);
                        columnArray2.add(secondColumn);
                        if (equijoin(columnArray, columnArray2, row, row2)) {
                            commonColumns.add(column);
                        }
                    }
                }
            }
        }
        String equalColumn = commonColumns.get(0);
        for (Row row : this._rows) {
            for (Row row2 : table2._rows) {
                int index = tableColumns.indexOf(equalColumn);
                int secondIndex = table2Columns.indexOf(equalColumn);
                if (row.get(index).equals(row2.get(secondIndex))) {
                    Row combinedRow = innerJoinRow(row, row2);
                    beforeresult.add(combinedRow);
                }
            }
        }
        result = beforeresult.select(columnNames, conditions);
        return result;
    }

    /** Joins two tables including duplicates. Not used.
    * TABLE1 TABLE2
    * @return Table*/

    public static Table innerJoinTable(Table table1, Table table2) {
        HashSet<String> columnsHash = new HashSet<String>();
        ArrayList<String> columns = new ArrayList<String>();
        for (int i = 0; i < table1.columns(); i++) {
            columns.add(table1.getTitle(i));
        }
        for (int i = 0; i < table2.columns(); i++) {
            columns.add(table2.getTitle(i));
        }
        for (String string : columnsHash) {
            columns.add(string);
        }
        Table result = new Table(columns);
        for (Row row1 : table1._rows) {
            for (Row row2 : table2._rows) {
                Row combinedRow  = innerJoinRow(row1, row2);
                result.add(combinedRow);
            }
        }
        return result;
    }

    /** Returns all the columns of a table.
    * @param table */
    public static ArrayList<String> allColumns(Table table) {
        ArrayList<String> columns = new ArrayList<String>();
        for (int i = 0; i < table.columns(); i++) {
            String string = table.getTitle(i);
            columns.add(string);
        }
        return columns;
    }

    /** Helper function for the second select method. Takes in two
     * Rows(each from table1 and table2) and combines their contents
     * to return a new Row with the combined content.
     * ROW1 ROW2*/

    public static Row innerJoinRow(Row row1, Row row2) {
        int row1Length = row1.size();
        int row2Length = row2.size();
        int totalLength = row1.size() + row2.size();
        String[] tempArray = new String[totalLength];
        for (int i = 0; i < row1Length; i++) {
            tempArray[i] = row1.get(i);
        }
        for (int j = row1Length; j < totalLength; j++) {
            tempArray[j] = row2.get(j - row1Length);
        }
        Row tempRow = new Row(tempArray);
        return tempRow;
    }

    /** Return true if the columns COMMON1 from ROW1 and COMMON2 from
     *  ROW2 all have identical values.  Assumes that COMMON1 and
     *  COMMON2 have the same number of elements and the same names,
     *  that the columns in COMMON1 apply to this table, those in
     *  COMMON2 to another, and that ROW1 and ROW2 come, respectively,
     *  from those tables. */
    private static boolean equijoin(List<Column> common1, List<Column> common2,
                                    Row row1, Row row2) {
        for (Column column : common1) {
            for (Column column2 : common2) {
                if (column.getName().equals(column2.getName())) {
                    if (!column.getFrom(row1).equals(column2.getFrom(row1))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /** My rows. */
    private HashSet<Row> _rows = new HashSet<>();
}


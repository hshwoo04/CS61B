package db61b;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/* Tests for Table class.*/

public class TableTest {
    @Test
    public void tableTest() {
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("col1");
        columns.add("col2");
        columns.add("col3");
        Table testTable =  new Table(columns);

        assertEquals(3, testTable.columns());
        assertEquals("col1", testTable.getTitle(0));
        assertEquals("col2", testTable.getTitle(1));
        assertEquals("col3", testTable.getTitle(2));

        assertEquals(0, testTable.findColumn("col1"));
        assertEquals(1, testTable.findColumn("col2"));
        assertEquals(2, testTable.findColumn("col3"));
        assertEquals(-1, testTable.findColumn("col4"));


        String[] contents = {"content1", "content2", "content3"};
        String[] contents1 = {"content4", "content5", "content6"};
        Row row1 = new Row(contents);
        Row row2 = new Row(contents1);
        Row row3 = new Row(contents);
        testTable.add(row1);
        testTable.add(row2);
        testTable.add(row3);
        testTable.print();
    }
    @Test
    public void selectTest() {
        System.out.println("selectTest");
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("col1");
        columns.add("col2");
        columns.add("col3");
        Table testTable =  new Table(columns);
        String[] contents = {"content1", "content1", "content3"};
        String[] contents1 = {"content2", "content2", "content2"};
        Row row1 = new Row(contents);
        Row row2 = new Row(contents1);
        testTable.add(row1);
        testTable.add(row2);
        Row[] rowArray = {row1, row2};

        ArrayList<Condition> conditions = new ArrayList<Condition>();
        Column col1 = new Column("col1", testTable);
        Column col2 = new Column("col2", testTable);
        Column col3 = new Column("col3", testTable);

        Condition condition1 = new Condition(col1, "=", col2);
        conditions.add(condition1);
        Table result = testTable.select(columns, conditions);
        result.print();

        Condition condition2 = new Condition(col2, "!=", col3);
        conditions.add(condition2);
        Table result1 = testTable.select(columns, conditions);
        result1.print();
    }
    @Test
    public void secondSelectTest() {
        System.out.println("secondSelectTest");
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("col1");
        columns.add("col2");
        columns.add("col3");
        Table testTable =  new Table(columns);
        String[] contents = {"content1", "content2", "content3"};
        String[] contents1 = {"content2", "content2", "content4"};
        Row row1 = new Row(contents);
        Row row2 = new Row(contents1);
        testTable.add(row1);
        testTable.add(row2);
        Row[] rowArray = {row1, row2};
        Row combinedRow = Table.innerJoinRow(row1, row2);
        for (int i = 0; i < combinedRow.size(); i++) {
            System.out.println(combinedRow.get(i));
        }
        Column col1 = new Column("col1", testTable);
        Column col2 = new Column("col2", testTable);
        Column col3 = new Column("col3", testTable);
        ArrayList<Condition> conditions = new ArrayList<Condition>();
        Condition condition1 = new Condition(col1, "=", col2);
        conditions.add(condition1);
        String[] contents2 = {"content5", "content6", "content7"};
        String[] contents3 = {"content8", "content9", "content10"};
        ArrayList<String> columns2 = new ArrayList<String>();
        columns2.add("col4");
        columns2.add("col5");
        columns2.add("col6");
        Table compareTable =  new Table(columns2);
        Row row3 = new Row(contents2);
        Row row4 = new Row(contents3);
        compareTable.add(row3);
        compareTable.add(row4);
        ArrayList<String> columnNames = new ArrayList<String>();
        columnNames.add("col3");
        columnNames.add("col4");
        Table resultTable =
        testTable.select(compareTable, columnNames, conditions);
        resultTable.print();
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TableTest.class));
    }
}

package db61b;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/* Tests for Row class. */

public class RowTest {

    @Test
    public void rowTest() {

        /* Tests for basic Row functions.*/

        Row testRow = new Row(new String[]{"col1", "col2", "col3"});
        assertEquals(3, testRow.size());
        assertEquals("col3", testRow.get(2));


        Row obj = new Row(new String[]{"col1", "col2", "col3"});
        Row obj3 = new Row(new String[]{"col4", "col5", "col6"});

        assertEquals(true, testRow.equals(obj));
        assertEquals(false, testRow.equals(obj3));
        assertEquals(obj.hashCode(), testRow.hashCode());
        assertEquals(false, obj3.hashCode() == testRow.hashCode());


    }

    @Test
    public void rowConstructorTest() {

        /* Tests for Row Constructor.*/

        Row testRow1 =
            new Row(new String[]{"content1", "content2", "content3"});
        Row testRow2 =
            new Row(new String[]{"content1", "content2", "content3"});
        Row testRow3 =
            new Row(new String[]{"content1", "content2", "content3"});

        String[] testColumns = {"col1", "col2", "col3"};
        Table testTable = new Table(testColumns);
        Column col1 = new Column("col1", testTable);
        Column col2 = new Column("col2", testTable);
        Column col3 = new Column("col3", testTable);
        ArrayList<Column> testColumns2 = new ArrayList<Column>();
        testColumns2.add(col1);
        testColumns2.add(col2);
        testColumns2.add(col3);
        Row[] testRows = {testRow1, testRow2, testRow3};

        Row result = new Row(testColumns2, testRows);
        System.out.println(result.get(0));
        System.out.println(result.get(1));
        System.out.println(result.get(2));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(RowTest.class));
    }
}

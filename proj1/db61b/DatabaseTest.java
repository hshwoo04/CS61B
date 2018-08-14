package db61b;

import org.junit.Test;
import static org.junit.Assert.*;

/* Tests for Table class. */

public class DatabaseTest {

    @Test
    public void databaseTest() {
        String[] contents1 = {"content1", "content2", "content3"};
        String[] contents2 = {"content4", "content5", "content6"};
        Table table1 = new Table(contents1);
        Table table2 = new Table(contents1);
        Table table3 = new Table(contents1);

        Database testDatabase = new Database();
        testDatabase.put("table1", table1);
        testDatabase.put("table2", table2);
        testDatabase.put("table3", table3);

        assertEquals(table1, testDatabase.get("table1"));
        assertEquals(table3, testDatabase.get("table3"));

        table3 = new Table(contents2);
        testDatabase.put("table3", table3);

        assertEquals("table3", table3);

    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TableTest.class));
    }
}

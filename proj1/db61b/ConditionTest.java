package db61b;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/* Tests for Condition class. */

public class ConditionTest {

    @Test
    public void conditionTest() {

        /* Tests for first Condition constructor.*/

        ArrayList<String> columns = new ArrayList<String>();
        columns.add("col1");
        columns.add("col2");
        columns.add("col3");
        Table testTable =  new Table(columns);
        String[] contents = {"1", "2", "3"};
        String[] contents1 = {"4", "5", "6"};
        Row row1 = new Row(contents);
        Row row2 = new Row(contents1);
        Row row3 = new Row(contents);
        testTable.add(row1);
        testTable.add(row2);
        testTable.add(row3);
        Column column1 = new Column("col1", testTable);
        Column column2 = new Column("col2", testTable);
        Column column3 = new Column("col3", testTable);

        Condition testCondition = new Condition(column1, "=", column2);
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(testCondition);
        Row[] set1 = {row1, row2};
        Row[] set2 = {row1, row3};
        assertEquals(false, Condition.test(conditions, set1));
        assertEquals(true, Condition.test(conditions, set2));

        Condition testCondition2 = new Condition(column2, "=", column3);
        conditions.add(testCondition2);
        assertEquals(false, Condition.test(conditions, set1));
        assertEquals(true, Condition.test(conditions, set2));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TableTest.class));
    }
}

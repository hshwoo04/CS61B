package db61b;
import java.util.ArrayList;

/* Tests for Table writeTable and readTable methods. */

public class ReadTableTest {

    public static void main(String[] args) {
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("col1");
        columns.add("col2");
        columns.add("col3");
        Table testTable =  new Table(columns);
        String[] contents = {"content1", "content2", "content3"};
        Row row1 = new Row(contents);
        testTable.add(row1);
        testTable.print();
        testTable.writeTable("testName");
        System.out.println(testTable.size());
        System.out.println("loaded");
        Table result = testTable.readTable("testName");
        result.print();
    }
}

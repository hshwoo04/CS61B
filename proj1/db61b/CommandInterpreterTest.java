package db61b;

import org.junit.Test;
import java.util.Scanner;
import static org.junit.Assert.*;

/* Tests for CommandInterpreter class. */

public class CommandInterpreterTest {

    @Test
    public void commandInterpreterTest() {
        Scanner s = new Scanner("load students;");
        CommandInterpreter interpreter = new CommandInterpreter(s, System.out);
        interpreter.statement();

        Scanner t = new Scanner("print students;");
        CommandInterpreter interpreter2 = new CommandInterpreter(t, System.out);
        interpreter.statement();


    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TableTest.class));
    }
}

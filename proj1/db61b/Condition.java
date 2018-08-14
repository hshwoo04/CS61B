package db61b;

import java.util.List;

/** Represents a single 'where' condition in a 'select' command.
 *  @author Harvey Woo */
class Condition {

    /** A Condition representing COL1 RELATION COL2, where COL1 and COL2
     *  are column designators. and RELATION is one of the
     *  strings "<", ">", "<=", ">=", "=", or "!=". */
    Condition(Column col1, String relation, Column col2) {
        _col1 = col1;
        _relation = relation;
        _col2 = col2;
    }

    /** A Condition representing COL1 RELATION 'VAL2', where COL1 is
     *  a column designator, VAL2 is a literal value (without the
     *  quotes), and RELATION is one of the strings "<", ">", "<=",
     *  ">=", "=", or "!=".
     */
    Condition(Column col1, String relation, String val2) {
        this(col1, relation, (Column) null);
        _val2 = val2;
    }

    /** Assuming that ROWS are rows from the respective tables from which
     *  my columns are selected, returns the result of performing the test I
     *  denote. */
    boolean test(Row... rows) {
        int result;
        if (this._col2 == null) {
            result = (_col1.getFrom(rows)).compareTo(_val2);
        } else {
            result = (_col1.getFrom(rows)).compareTo(_col2.getFrom(rows));
        }
        switch (_relation) {
        default:
            return true;
        case "<" :
            if (result < 0) {
                return true;
            }
            break;
        case ">" :
            if (result > 0) {
                return true;
            }
            break;
        case "<=" :
            if (result <= 0) {
                return true;
            }
            break;
        case ">=" :
            if (result >= 0) {
                return true;
            }
            break;
        case "=" :
            if (result == 0) {
                return true;
            }
            break;
        case "!=" :
            if (result != 0) {
                return true;
            }
            break;
        }
        return false;
    }

    /** Return true iff ROWS satisfies all CONDITIONS. */
    static boolean test(List<Condition> conditions, Row... rows) {
        if (conditions.size() == 0) {
            return true;
        }
        for (Condition cond : conditions) {
            if (!cond.test(rows)) {
                return false;
            }
        }
        return true;
    }

    /** The operands of this condition.  _col2 is null if the second operand
     *  is a literal. */
    private Column _col1, _col2;
    /** Second operand, if literal (otherwise null). */
    private String _val2;
    /** The relation string. */
    private String _relation;
}
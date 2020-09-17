/**
 * A NewExpression interface. Used for the bonus task.
 * @author Naveh Marchoom
 */
public interface NewExpression extends Expression {

    /**
     * For binary expressions, sort the values of the expression alphabeticly according to their .toString().
     * @return NewExpression
     */
    NewExpression sort();

    /**
     * Get the right value of a binary expression, or the only value of an unary Expression or base Expression.
     * @return NewExpression
     */
    NewExpression getValue1();

    /**
     * Get the left value of a binary Expression, or null for unary expression and base expression.
     * @return NewExpression
     */
    NewExpression getValue2();

    /**
     * Simplify the expression further than normal .simplify().
     * @return NewExpression
     */
    NewExpression newSimplify();

    /**
     * Will work only for Mult. Will return false for anything else.
     * @return false.
     */
    boolean isPoly();
}

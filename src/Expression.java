import java.util.Map;
import java.util.List;
/**
 * An expression interface.
 * @author Naveh Marchoom
 *
 */
public interface Expression {

    /**
     *  Evaluate the expression using the variable values provided in the assignment,
     *  and return the result. If the expression contains a variable which is not in the assignment,
     *  an exception is thrown.
     * @param assignment A map containing the value of each variable in the expression
     * @return A double.
     * @throws Exception in case a variable is left unmapped.
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return a double.
     * @throws Exception In case the expression DO contains variables.
     */
    double evaluate() throws Exception;

    /**
     * Returns a list of the variables in the expression.
     * @return a list of strings.
     */
    List<String> getVariables();

    /**
     * Returns a nice string representation of the expression.
     * @return a string.
     */
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression
     * (Does not modify the current expression).
     * @param var The variable should be replaced
     * @param expression the expression var should be replaced with.
     * @return a new expression.
     */
    NewExpression assign(String var, NewExpression expression);

    /**
     *  Returns the expression tree resulting from differentiating
     *  the current expression relative to variable `var`.
     * @param var String depicting a variable.
     * @return a new differentiated expression.
     */
    NewExpression differentiate(String var);

    /**
     * Return a simplified version of the current expression.
     * @return An Expression.
     * @throw Exception In case one the variableless expression contains illegal calculation (divide by zero, etc...)
     */
    NewExpression simplify();
}
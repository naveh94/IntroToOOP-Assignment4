import java.util.List;
import java.util.Map;

/**
 * A Number Expression Class.
 * @author Naveh Marchoom
 */
public class Num extends BaseExpression implements Expression, NewExpression {

    private double value;

    /**
     * Create a new Num expression using a double value.
     * @param v The value should be given to the num.
     */
    public Num(double v) {
        this.value = v;
    }

    /**
     * Return the Num's value.
     * @param assignment A map containing the value of each variable in the expression
     * @return A double.
     * @throws Exception no reason to throw exception.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.value;
    }

    /**
     * Returns a list of the variables in the expression.
     * @return a list of strings.
     */
    @Override
    public List<String> getVariables() {
        return new java.util.ArrayList<String>();
    }

    /**
     * Returns a nice string representation of the expression.
     * @return a string.
     */
    @Override
    public String toString() {
        return "" + this.value + "";
    }

    /**
     * Since a Num expression does not contains variables, returns this.
     * @param var The variable should be replaced
     * @param expression the expression var should be replaced with.
     * @return a new expression.
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression differentiate(String var) {
        return new Num(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression simplify() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression newSimplify() {
        return this.simplify();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression getValue1() {
        return this;
    }
}

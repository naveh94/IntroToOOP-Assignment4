import java.util.List;
import java.util.Map;
/**
 * Binary Expression Abstract Class.
 * @author Naveh Marchoom
 *
 */
public abstract class BinaryExpression implements Expression, NewExpression {

    private NewExpression value1;
    private NewExpression value2;

    /**
     * Create a new BinaryExpression expression using 2 expressions as it's members.
     * @param v1 expression 1.
     * @param v2 expression 2.
     */
    public BinaryExpression(NewExpression v1, NewExpression v2) {
        this.value1 = v1;
        this.value2 = v2;
    }

    /**
     * Return value1 of this expression.
     * @return Expression.
     */
    public NewExpression getValue1() {
        return (NewExpression) this.value1;
    }

    /**
     * Return value2 of this expression.
     * @return Expression.
     */
    public NewExpression getValue2() {
        return (NewExpression) this.value2;
    }

    /**
     * {@inheritDoc}
     */
    public abstract double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * {@inheritDoc}
     */
    public double evaluate() throws Exception {
        return this.evaluate(new java.util.TreeMap<String, Double>());
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getVariables() {
        List<String> l = new java.util.ArrayList<String>();
        l.addAll(value1.getVariables());
        l.addAll(value2.getVariables());
        return l;
    }

    /**
     * {@inheritDoc}
     */
    public abstract String toString();

    /**
     * {@inheritDoc}
     */
    public abstract NewExpression assign(String var, NewExpression expression);

    /**
     * {@inheritDoc}
     */
    public abstract NewExpression differentiate(String var);

    /**
     * {@inheritDoc}
     */
    public abstract NewExpression simplify();

    /**
     * {@inheritDoc}
     */
    public abstract NewExpression newSimplify();

    /**
     * {@inheritDoc}
     */
    public abstract NewExpression sort();

    /**
     * {@inheritDoc}
     */
    public boolean isPoly() {
        return false;
    }
}
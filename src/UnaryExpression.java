import java.util.List;
import java.util.Map;
/**
 * Abstract class for unary expressions.
 * @author Naveh Marchoom
 *
 */
public abstract class UnaryExpression implements Expression, NewExpression {

    private NewExpression value;

    /**
     * Create a new Unary Expression using v as the value.
     * @param v Expression.
     */
    public UnaryExpression(NewExpression v) {
        this.value = v;
    }

    /**
     * Return the Expressions's inside value.
     * @return Expression.
     */
    protected NewExpression getValue() {
        return value;
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
        return value.getVariables();
    }

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
    public NewExpression sort() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public NewExpression getValue1() {
        return this.getValue();
    }

    /**
     * {@inheritDoc}
     */
    public NewExpression getValue2() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isPoly() {
        return false;
    }
}

import java.util.List;
import java.util.Map;
/**
 * Abstract class for basic expressions.
 * @author Naveh Marchoom
 *
 */
public abstract class BaseExpression implements Expression, NewExpression {

    /**
     * {@inheritDoc}
     */
    public abstract double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * {@inheritDoc}
     */
    public double evaluate() throws Exception {
        return evaluate(new java.util.TreeMap<String, Double>());
    }

    /**
     * {@inheritDoc}
     */
    public abstract List<String> getVariables();

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
    public abstract NewExpression getValue1();

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
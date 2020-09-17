import java.util.List;
import java.util.ArrayList;
import java.util.Map;
/**
 * A Variable Expression Class.
 * @author Naveh Marchoom
 *
 */
public class Var extends BaseExpression implements Expression, NewExpression {

    private String sign;

    /**
     * Create a new var using s as sign.
     * @param s the var sign.
     */
    public Var(String s) {
        this.sign = s;
    }

    /**
     * Return the value of this var on the map.
     * @param assignment A map containing the values of variables.
     * @return A double.
     * @throws Exception In case this sign doesn't exist in the assignment map.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        if (!assignment.containsKey(this.sign)) {
            throw new Exception("No value given for var '" + this.sign + "'.");
        }
        return assignment.get(this.sign);
    }

    /**
     * Return a list containing this.
     * @return a list of strings.
     */
    @Override
    public List<String> getVariables() {
        List<String> l = new ArrayList<String>();
        l.add(this.sign);
        return l;
    }

    /**
     * Returns the expression if var is this variable's sign. Else will return itself.
     * @param var a String depicting a variable.
     * @param expression the expression var should be replaced with.
     * @return a new expression.
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        if (var.equals(this.sign)) {
            return expression;
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return sign;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression differentiate(String var) {
        // dx/dx = 1:
        if (this.sign.equals(var)) {
            return new Num(1);
        }

        // dc/dx = 0:
        return new Num(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression simplify() {
        return this;
    }

    @Override
    public NewExpression newSimplify() {
        return this.simplify();
    }

    @Override
    public NewExpression getValue1() {
        return this;
    }
}

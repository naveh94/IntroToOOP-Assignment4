import java.util.Map;
/**
 * A Negative expression class.
 * @author Naveh Marchoom
 *
 */
public class Neg extends UnaryExpression implements Expression, NewExpression {

    /**
     * Create a new Negative expression using v expression as value.
     * @param v an expression.
     */
    public Neg(NewExpression v) {
        super(v);
    }

    /**
     * Create a new Negative expression using s variable as value.
     * @param s String depicting a variable.
     */
    public Neg(String s) {
        this(new Var(s));
    }

    /**
     * Create a new Negative expression using d double as value.
     * @param d double depicting a value.
     */
    public Neg(double d) {
        this(new Num(d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return super.getValue().evaluate(assignment) * (-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "(-" + super.getValue().toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        NewExpression v = super.getValue().assign(var, expression);
        return new Neg(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression differentiate(String var) {
        // d(-f) = -df:
        return new Neg(super.getValue().differentiate(var));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression simplify() {
        NewExpression v = super.getValue().simplify();
        return new Neg(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression newSimplify() {
        NewExpression simple = this.simplify();
        if (simple.getValue1() instanceof Neg) {
            // (-(-x)) = x :
            simple = this.getValue1().getValue1().newSimplify();
        }
        return simple;
    }
}
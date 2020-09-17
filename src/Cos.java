import java.util.Map;
/**
 * A Trigonometry Cosinus Expression Class.
 * @author Naveh Marchoom
 *
 */
public class Cos extends UnaryExpression implements Expression, NewExpression {

    /**
     * Create a new Cos expression using v expression as value.
     * @param v an expression.
     */
    public Cos(NewExpression v) {
        super(v);
    }

    /**
     * Create a new Cos expression using s variable as value.
     * @param s String depicting a variable.
     */
    public Cos(String s) {
        this(new Var(s));
    }

    /**
     * Create a new Cos expression using d double as value.
     * @param d double depicting a value.
     */
    public Cos(double d) {
        this(new Num(d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double d = super.getValue().evaluate(assignment);
        return Math.cos(Math.toRadians(d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "cos(" + super.getValue().toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        NewExpression v = super.getValue().assign(var, expression);
        return new Cos(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression differentiate(String var) {
        // dcos(f) = -sin(f) * df :
        return new Mult(new Neg(new Sin(super.getValue())), super.getValue().differentiate(var));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression simplify() {
        return new Cos(super.getValue().simplify());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression newSimplify() {
        return this.simplify();
    }
}
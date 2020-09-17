import java.util.Map;
/**
 * A Trigonometric Sinus Expression Class.
 * @author Naveh Marchoom
 *
 */
public class Sin extends UnaryExpression implements Expression, NewExpression {

    /**
     * Create a new Sin expression using v expression as value.
     * @param v an expression.
     */
    public Sin(NewExpression v) {
        super(v);
    }

    /**
     * Create a new Sin expression using s variable as value.
     * @param s String depciting a variable.
     */
    public Sin(String s) {
        this(new Var(s));
    }

    /**
     * Create a new Sin expression using d double as value.
     * @param d double depicting a value.
     */
    public Sin(double d) {
        this(new Num(d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return Math.sin(Math.toRadians(super.getValue().evaluate(assignment)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "sin(" + super.getValue().toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        NewExpression v = super.getValue().assign(var, expression);
        return new Sin(v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression differentiate(String var) {
        // dsin(f) = cos(f) * df :
        return new Mult(new Cos(super.getValue()), super.getValue().differentiate(var));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression simplify() {
        return new Sin(super.getValue().simplify());
    }

    @Override
    public NewExpression newSimplify() {
        return this.simplify();
    }

}

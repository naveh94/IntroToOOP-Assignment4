import java.util.Map;
/**
 * Minus Expression Class.
 * @author Naveh Marchoom
 *
 */
public class Minus extends BinaryExpression implements Expression, NewExpression {

    /**
     * Create a new Minus expression using 2 expressions as it's members.
     * @param v1 expression 1.
     * @param v2 expression 2.
     */
    public Minus(NewExpression v1, NewExpression v2) {
        super(v1, v2);
    }

    /**
     * Create a new Minus expression using an expression v, and variable s.
     * @param v Expression.
     * @param s String depicting a variable.
     */
    public Minus(NewExpression v, String s) {
        this(v, new Var(s));
    }

    /**
     * Create a new Minus expression using an expression v, and variable s.
     * @param v Expression.
     * @param s String depicting a variable.
     */
    public Minus(String s, NewExpression v) {
        this(new Var(s), v);
    }

    /**
     * Create a new Minus expression using two variables.
     * @param s1 String depicting a variable.
     * @param s2 String depicting a variable.
     */
    public Minus(String s1, String s2) {
        this(new Var(s1), new Var(s2));
    }

    /**
     * Create a new Minus expression using an expression v, and a double d.
     * @param v Expression.
     * @param d double.
     */
    public Minus(NewExpression v, double d) {
        this(v, new Num(d));
    }

    /**
     * Create a new Minus expression using an expression v, and a double d.
     * @param v Expression.
     * @param d double.
     */
    public Minus(double d, NewExpression v) {
        this(new Num(d), v);
    }

    /**
     * Create a new Minus expression using 2 doubles.
     * @param d1 double.
     * @param d2 double.
     */
    public Minus(double d1, double d2) {
        this(new Num(d1), new Num(d2));
    }

    /**
     * Create a new Minus expression using a double d, and a variable s.
     * @param d double.
     * @param s String depicting a variable.
     */
    public Minus(double d, String s) {
        this(new Num(d), new Var(s));
    }

    /**
     * Create a new Minus expression using a double d, and a variable s.
     * @param d double.
     * @param s String depicting a variable.
     */
    public Minus(String s, double d) {
        this(new Var(s), new Num(d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return super.getValue1().evaluate(assignment) - super.getValue2().evaluate(assignment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "(" + super.getValue1().toString() + " - " + super.getValue2().toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        NewExpression v1 = super.getValue1().assign(var, expression);
        NewExpression v2 = super.getValue2().assign(var, expression);
        return new Minus(v1, v2);
    }

    /**
     * {@inheritDoc}
     */
    public NewExpression differentiate(String var) {
        // d(f - g) = df - dg:
        return new Minus(super.getValue1().differentiate(var), super.getValue2().differentiate(var));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression simplify() {
        NewExpression v1 = super.getValue1(), v2 = super.getValue2(), vsum = this;

        // In case no variables at all : Evaluate the expression and return a number.
        if (this.getVariables().isEmpty()) {
            try {
                return new Num(this.evaluate());
            } catch (Exception e) {
                // If getVariables() is empty, won't throw exception.
                System.out.println("Error.");
            }
        }

        // Get simplified value1 (In case no variables, get a number):
        if (super.getValue1().getVariables().isEmpty()) {
            try {
                v1 = new Num(super.getValue1().evaluate());
            } catch (Exception e) {
                // If getVariables() is empty, won't throw exception.
                System.out.println("Error.");
            }
        } else {
            v1 = super.getValue1().simplify();
        }

        // Get simplified value2 (In case no variables, get a number):
        if (super.getValue2().getVariables().isEmpty()) {
            try {
                v2 = new Num(super.getValue2().evaluate());
            } catch (Exception e) {
                // If getVariables() is empty, won't throw exception.
                System.out.println("Error.");
            }
        } else {
            v2 = super.getValue2().simplify();
        }

        // Check for special cases:
        if (v1.toString().equals(new Num(0).toString())) {
            // O - x = -x :
            vsum = new Neg(v2);
        } else if (v2.toString().equals(new Num(0).toString())) {
            // x - 0 = x :
            vsum = v1;
        } else if (v1.toString().equals(v2.toString())) {
            // x - x = 0 :
            vsum = new Num(0);
        } else {
            // x - y :
            vsum = new Minus(v1, v2);
        }

        return vsum;
    }

    @Override
    public NewExpression newSimplify() {
        return this.simplify();
    }

    @Override
    public NewExpression sort() {
        return this;
    }
}
import java.util.Map;
/**
 * A Power Expression Class.
 * @author Naveh Marchoom
 *
 */
public class Pow extends BinaryExpression implements Expression, NewExpression {

    /**
     * Create a new Pow expression using 2 expressions as it's members.
     * @param v1 expression 1.
     * @param v2 expression 2.
     */
    public Pow(NewExpression v1, NewExpression v2) {
        super(v1, v2);
    }

    /**
     * Create a new Pow expression using an expression v, and variable s.
     * @param v Expression.
     * @param s String depicting a variable.
     */
    public Pow(NewExpression v, String s) {
        this(v, new Var(s));
    }

    /**
     * Create a new Pow expression using an expression v, and variable s.
     * @param v Expression.
     * @param s String depicting a variable.
     */
    public Pow(String s, NewExpression v) {
        this(new Var(s), v);
    }

    /**
     * Create a new Pow expression using two variables.
     * @param s1 String depicting a variable.
     * @param s2 String depicting a variable.
     */
    public Pow(String s1, String s2) {
        this(new Var(s1), new Var(s2));
    }

    /**
     * Create a new Pow expression using an expression v, and a double d.
     * @param v Expression.
     * @param d double.
     */
    public Pow(NewExpression v, double d) {
        this(v, new Num(d));
    }

    /**
     * Create a new Pow expression using an expression v, and a double d.
     * @param v Expression.
     * @param d double.
     */
    public Pow(double d, NewExpression v) {
        this(new Num(d), v);
    }

    /**
     * Create a new Pow expression using 2 doubles.
     * @param d1 double.
     * @param d2 double.
     */
    public Pow(double d1, double d2) {
        this(new Num(d1), new Num(d2));
    }

    /**
     * Create a new Pow expression using a double d, and a variable s.
     * @param d double.
     * @param s String depicting a variable.
     */
    public Pow(double d, String s) {
        this(new Num(d), new Var(s));
    }

    /**
     * Create a new Pow expression using a double d, and a variable s.
     * @param d double.
     * @param s String depicting a variable.
     */
    public Pow(String s, double d) {
        this(new Var(s), new Num(d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double d = super.getValue2().evaluate(assignment);
        if (d == 0) {
            return 1;
        }
        return Math.pow(super.getValue1().evaluate(assignment), d);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "(" + super.getValue1().toString() + "^" + super.getValue2().toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        NewExpression v1 = super.getValue1().assign(var, expression);
        NewExpression v2 = super.getValue2().assign(var, expression);
        return new Pow(v1, v2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression differentiate(String var) {
        // d(f^g) = f^g * (df * g/f + dg * ln(f)) :
        NewExpression fpowg = this;
        NewExpression df = super.getValue1().differentiate(var);
        NewExpression gdivf = new Div(super.getValue2(), super.getValue1());
        NewExpression dg = super.getValue2().differentiate(var);
        NewExpression lnf = new Log("e", super.getValue1());
        return new Mult(fpowg, new Plus(new Mult(df, gdivf), new Mult(dg, lnf)));
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
        if (v2.toString().equals(new Num(1).toString())) {
            // x ^ 1 = x :
            vsum = v1;
        } else {
            // x ^ y :
            vsum = new Pow(v1, v2);
        }

        return vsum;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public NewExpression newSimplify() {
        NewExpression simple = this.simplify();
        if (simple.getValue1() instanceof Pow) {
            simple = new Pow(simple.getValue1().getValue1().newSimplify(),
                    new Mult(simple.getValue1().getValue2().newSimplify(),
                            simple.getValue2().newSimplify()).newSimplify());
        }
        return simple.simplify();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression sort() {
        return this;
    }
}

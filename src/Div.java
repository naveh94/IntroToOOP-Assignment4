import java.util.Map;
/**
 * A Divide Expression Class.
 * @author Naveh Marchoom
 *
 */
public class Div extends BinaryExpression implements Expression, NewExpression {

    /**
     * Create a new Div expression using 2 expressions as it's members.
     * @param v1 expression 1.
     * @param v2 expression 2.
     */
    public Div(NewExpression v1, NewExpression v2) {
        super(v1, v2);
    }

    /**
     * Create a new Div expression using an expression v, and variable s.
     * @param v Expression.
     * @param s String depicting a variable.
     */
    public Div(NewExpression v, String s) {
        this(v, new Var(s));
    }

    /**
     * Create a new Div expression using an expression v, and variable s.
     * @param v Expression.
     * @param s String depicting a variable.
     */
    public Div(String s, NewExpression v) {
        this(new Var(s), v);
    }

    /**
     * Create a new Div expression using two variables.
     * @param s1 String depicting a variable.
     * @param s2 String depicting a variable.
     */
    public Div(String s1, String s2) {
        this(new Var(s1), new Var(s2));
    }

    /**
     * Create a new Div expression using an expression v, and a double d.
     * @param v Expression.
     * @param d double.
     */
    public Div(NewExpression v, double d) {
        this(v, new Num(d));
    }

    /**
     * Create a new Div expression using an expression v, and a double d.
     * @param v Expression.
     * @param d double.
     */
    public Div(double d, NewExpression v) {
        this(new Num(d), v);
    }

    /**
     * Create a new Div expression using 2 doubles.
     * @param d1 double.
     * @param d2 double.
     */
    public Div(double d1, double d2) {
        this(new Num(d1), new Num(d2));
    }

    /**
     * Create a new Div expression using a double d, and a variable s.
     * @param d double.
     * @param s String depicting a variable.
     */
    public Div(double d, String s) {
        this(new Num(d), new Var(s));
    }

    /**
     * Create a new Div expression using a double d, and a variable s.
     * @param d double.
     * @param s String depicting a variable.
     */
    public Div(String s, double d) {
        this(new Var(s), new Num(d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(Map<String, Double> assignment)  throws Exception {
        return super.getValue1().evaluate(assignment) / super.getValue2().evaluate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "(" + super.getValue1().toString() + " / " + super.getValue2().toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        NewExpression v1 = super.getValue1().assign(var, expression);
        NewExpression v2 = super.getValue2().assign(var, expression);
        return new Div(v1, v2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression differentiate(String var) {
        // d(f/g) = (dfg - fdg) / g^2
        NewExpression dfg = new Mult(super.getValue1().differentiate(var), super.getValue2());
        NewExpression fdg = new Mult(super.getValue1(), super.getValue2().differentiate(var));
        NewExpression g2 = new Pow(super.getValue2(), 2);
        return new Div(new Minus(dfg, fdg), g2);
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
                // If getVariables() is empty, it won't throw Exception.
                System.out.println("Error.");
            }
        }

        // Get simplified value1 (In case no variables, get a number):
        if (super.getValue1().getVariables().isEmpty()) {
            try {
                v1 = new Num(super.getValue1().evaluate());
            } catch (Exception e) {
                // If getVariables() is empty, it won't throw Exception.
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
                // If getVariables() is empty, it won't throw Exception.
                System.out.println("Error.");
            }
        } else {
            v2 = super.getValue2().simplify();
        }

        // Check for special cases:
        if (v1.toString().equals(new Num(0).toString())) {
            // 0 / x = 0 :
            vsum = new Num(0);
        } else if (v2.toString().equals(new Num(1).toString())) {
            // x / 1 = x :
            vsum = v1;
        } else if (v1.toString().equals(v2.toString())) {
            // x / x = 1 :
            vsum = new Num(1);
        } else {
            // x * y :
            vsum = new Div(v1, v2);
        }

        return vsum;
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public NewExpression newSimplify() {
        NewExpression simple = this.simplify();
        simple = new Div(simple.getValue1().newSimplify(), simple.getValue2().newSimplify());
        simple = simple.simplify();
        return simple;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression sort() {
        return this;
    }
}

import java.util.Map;
/**
 * A Multiplication Expression Class.
 * @author Naveh Marchoom
 *
 */
public class Mult extends BinaryExpression implements Expression, NewExpression {

    /**
     * Create a new Mult expression using 2 expressions as it's members.
     * @param v1 expression 1.
     * @param v2 expression 2.
     */
    public Mult(NewExpression v1, NewExpression v2) {
        super(v1, v2);
    }

    /**
     * Create a new Mult expression using an expression v, and variable s.
     * @param v Expression.
     * @param s String depicting a variable.
     */
    public Mult(NewExpression v, String s) {
        this(v, new Var(s));
    }

    /**
     * Create a new Mult expression using an expression v, and variable s.
     * @param v Expression.
     * @param s String depicting a variable.
     */
    public Mult(String s, NewExpression v) {
        this(new Var(s), v);
    }

    /**
     * Create a new Mult expression using two variables.
     * @param s1 String depicting a variable.
     * @param s2 String depicting a variable.
     */
    public Mult(String s1, String s2) {
        this(new Var(s1), new Var(s2));
    }

    /**
     * Create a new Mult expression using an expression v, and a double d.
     * @param v Expression.
     * @param d double.
     */
    public Mult(NewExpression v, double d) {
        this(v, new Num(d));
    }

    /**
     * Create a new Mult expression using an expression v, and a double d.
     * @param v Expression.
     * @param d double.
     */
    public Mult(double d, NewExpression v) {
        this(new Num(d), v);
    }

    /**
     * Create a new Mult expression using 2 doubles.
     * @param d1 double.
     * @param d2 double.
     */
    public Mult(double d1, double d2) {
        this(new Num(d1), new Num(d2));
    }

    /**
     * Create a new Mult expression using a double d, and a variable s.
     * @param d double.
     * @param s String depicting a variable.
     */
    public Mult(double d, String s) {
        this(new Num(d), new Var(s));
    }

    /**
     * Create a new Mult expression using a double d, and a variable s.
     * @param d double.
     * @param s String depicting a variable.
     */
    public Mult(String s, double d) {
        this(new Var(s), new Num(d));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return super.getValue1().evaluate(assignment) * super.getValue2().evaluate(assignment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "(" + super.getValue1().toString() + " * " + super.getValue2().toString() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression assign(String var, NewExpression expression) {
        NewExpression v1 = super.getValue1().assign(var, expression);
        NewExpression v2 = super.getValue2().assign(var, expression);
        return new Mult(v1, v2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression differentiate(String var) {
        // d(fg) = (df)g + f(dg)
        NewExpression dfg = new Mult(super.getValue1().differentiate(var), super.getValue2());
        NewExpression fdg = new Mult(super.getValue1(), super.getValue2().differentiate(var));
        return new Plus(dfg, fdg);
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
        if (v1.toString().equals(new Num(0).toString()) || v2.toString().equals(new Num(0).toString())) {
            // 0 * x = 0 || x * 0 = 0 :
            vsum = new Num(0);
        } else if (v1.toString().equals(new Num(1).toString())) {
            // 1 * x = x :
            vsum = v2;
        } else if (v2.toString().equals(new Num(1).toString())) {
            // x * 1 = x :
            vsum = v1;
        } else if (v1.toString().equals(new Num(-1).toString())) {
            // -1 * x = -x :
            vsum = new Neg(v2);
        } else if (v2.toString().equals(new Num(-1).toString())) {
            // x * -1 = -x :
            vsum = new Neg(v1);
        } else {
            // x * y :
            vsum = new Mult(v1, v2);
        }

        return vsum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression newSimplify() {
        NewExpression simple = this.simplify();
        simple = simple.sort();
        if (simple.getValue1() instanceof Div) {
            // (x / y) * y = x :
            if (simple.getValue1().getValue2().toString().equals(simple.getValue2().toString())) {
                return simple.getValue1().getValue1().newSimplify();
            }
            //  (x / y) * (y^c) =  x * y^(c - 1)
            if (simple.getValue2() instanceof Pow
                    && simple.getValue2().getValue1().toString().equals(simple.getValue1().getValue2().toString())) {
                return new Mult(simple.getValue1().getValue1(),
                        new Pow(simple.getValue2().getValue1(),
                                new Minus(simple.getValue2().getValue2(), 1).simplify()));
            }
        }
        if (simple.getValue2() instanceof Div) {
            // y * (x / y) = x :
            if (simple.getValue2().getValue2().toString().equals(simple.getValue1().toString())) {
                return simple.getValue2().getValue1().newSimplify();
            }
            // (y^c) * (x /y) = x * y^(c - 1)
            if (simple.getValue1() instanceof Pow
                    && simple.getValue1().getValue1().toString().equals(simple.getValue2().getValue2().toString())) {
                return new Mult(simple.getValue2().getValue1(),
                        new Pow(simple.getValue1().getValue1(),
                                new Minus(simple.getValue1().getValue2(), 1).simplify()));
            }
        }
        return simple;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NewExpression sort() {
        NewExpression sorted = this.simplify();
        sorted = new Mult(sorted.getValue1().sort(), sorted.getValue2().sort());
        for (int i = 0; i < sorted.getValue1().toString().length() && i < sorted.getValue2().toString().length(); i++) {
            if (sorted.getValue1().toString().charAt(i) < sorted.getValue2().toString().charAt(i)) {
                return new Mult(this.getValue1(), sorted.getValue2());
            }
            if (sorted.getValue2().toString().charAt(i) < sorted.getValue1().toString().charAt(i)) {
                return new Mult(sorted.getValue2(), sorted.getValue1());
            }
        }
        if (sorted.getValue1().toString().length() < sorted.getValue2().toString().length()) {
            return new Mult(sorted.getValue1(), sorted.getValue2());
        }
        return new Mult(sorted.getValue2(), sorted.getValue1());
    }

    /**
     * Check if the multiplication is of this structure: c*x (c = Num , x = Var).
     * @return True if right, else will return false.
     */
    @Override
    public boolean isPoly() {
        if (this.getValue1().simplify() instanceof Num && this.getValue2().simplify() instanceof Var) {
            return true;
        }
        return false;
    }
}
/**
 * Expression Test.
 * @author Naveh Marchoom
 */
public class ExpressionTest {
    /**
     * Do some Expression tests using the expression: (2x) + (sin(4y)) + (e^x).
     * @param args unused.
     */
    public static void main(String[] args) {
        Expression e = new Plus(new Plus(new Mult(2, "x"), new Sin(new Mult(4, "y"))), new Pow("e", "x"));
        System.out.println(e.toString());

        java.util.Map<String, Double> m = new java.util.TreeMap<String, Double>();
        m.put("x", 2.0);
        m.put("y", 0.25);
        m.put("e", 2.71);

        try {
            System.out.println(e.evaluate(m));
        } catch (Exception e1) {
            // All variables are assigned. Won't throw exception.
            System.out.println("Error.");
        }

        Expression de = e.differentiate("x");
        System.out.println(de.toString());
        System.out.println(de.simplify().toString());
    }
}

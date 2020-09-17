/**
 * Simplification Demonstration File.
 * @author Naveh Marchoom
 */
public class SimlificationDemo {

	/**
	 * Demonstrating few simplifications added in Task 4 of the assignment.
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		NewExpression e1 = new Pow("x",4);
		NewExpression de1 = e1.differentiate("x");
		System.out.println(e1.toString());
		System.out.println(de1.toString());
		System.out.println(de1.newSimplify().toString());
		
		NewExpression e2 = new Neg(new Mult(-1, "x"));
		System.out.println(e2.toString());
		System.out.println(e2.newSimplify().toString());

	}

}

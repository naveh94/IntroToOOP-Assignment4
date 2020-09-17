import java.util.Map;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
		NewExpression d = new Pow("x",4);
		System.out.println(d.toString());
		d = d.differentiate("x");
		NewExpression dns = d.newSimplify(), ds = d.simplify();
		System.out.println(ds.toString());
		System.out.println(dns.toString());
	}
}

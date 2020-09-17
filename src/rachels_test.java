import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import Math.E;


public class rachels_test {
	public static void main(String[] args){
		// (2x) + (sin(4y)) + (e^x)
		Expression e = new Plus(
							new Mult(new Num(2), new Var("x")),
							new Plus(
									new Sin(new Mult(new Num(4), new Var("y"))),
									new Pow(new Var("e"), new Var("x"))));

		Integer corr_equation=-1;
		corr_equation=e.toString().toLowerCase().equals("((2 * x) + (sin((4 * y)) + (e^x)))")? 0:corr_equation;
		corr_equation=e.toString().toLowerCase().equals("((2.0 * x) + (sin((4.0 * y)) + (e^x)))")? 0:corr_equation;
				
		Integer corr_equation_ns=-1;
		corr_equation_ns=e.toString().toLowerCase().replace(" ","").equals("((2*x)+(sin((4*y))+(e^x)))")? 0:corr_equation_ns;
		corr_equation_ns=e.toString().toLowerCase().replace(" ","").equals("((2.0*x)+(sin((4.0*y))+(e^x)))")? 0:corr_equation_ns;

		Integer spaces_problem=0;
		if (corr_equation==-1 && corr_equation_ns==0){
			spaces_problem=-1;
			System.out.println("incorrect spacing ");
		}
		
		
		Map<String, Double> assignment = new TreeMap<String, Double>();
		assignment.put("x", 2.0);
		assignment.put("y", 0.25);
		assignment.put("e", 2.71);
		Integer corr_assignment=-1;
		Integer assign_crash=0;
		try{
			corr_assignment=Math.abs(e.evaluate(assignment)-12.185)<0.1 || Math.abs(e.evaluate(assignment)-11.361)<0.1? 0:-1;
			if (corr_assignment==-1) {
				System.out.println("incorrect assignment to expression 0 ");
		}
			
		}
		catch(Exception ex){
			assign_crash=-1;
			System.out.println("crashed at assignment to expression 0 ");
		}
		Expression[] expressions=new Expression[4];
		expressions[0]=e;
		expressions[1]=new Mult(new Pow(new Var("x"),new Num(2)), new Plus(new Plus(new Num(1),new Pow(new Num(2),new Var("y"))),new Pow(new Num(2),new Var("x")))); // x^2*(1+2^y+2^x)
		expressions[2]=new Mult(new Sin(new Var("x")),new Plus(new Cos(new Var("x")),new Cos(new Var("y"))));//sin(x)*(cos(x) +cos(y))
		expressions[3]=new Minus(new Log(new Num(4),new Plus(new Plus(new Pow(new Var("x"),new Num(2)),new Pow(new Var("x"),new Num(4))),new Num(4))),new Var("x"));//log((x^2+x^4+4),4)-x

		Map<String, Double> assign;
		double[] x_values={2.0,1.0,0,2};
		double[] y_values={0,1.0,0,0};
		double[] solutions_X={9.344,11.386,2,0.082};
		double[] solutions_Y={4,1.386,0.0,0};
		int[] corr_X={-1,-1,-1,-1};
		int[] corr_Y={-1,-1,-1,-1};
		int[] crashed={0,0,0,0};
		for (int i=0; i<expressions.length;i++){
			try{
				assign = new TreeMap<String, Double>();
				assign.put("x", x_values[i]);
				assign.put("y", y_values[i]);
				assign.put("e", Math.E);
				double sol_X=expressions[i].differentiate("x").evaluate(assign);
				double sol_Y=expressions[i].differentiate("y").evaluate(assign);
				corr_X[i]=Math.abs(sol_X-solutions_X[i])<0.1?0:-1;
				corr_Y[i]=Math.abs(sol_Y-solutions_Y[i])<0.1?0:-1;
				if (corr_X[i]==-1 || corr_Y[i]==-1){
					System.out.println("incorrect diffrentiate or assignment of expression #"+i);
				}
			}
			catch (Exception ex){
				crashed[i]=-1;
				System.out.println("diffrentiation and assignment crashed at expression #"+i);
				//ex.printStackTrace();		
			}
		}


		Expression[] simp_expressions=new Expression[3];
		simp_expressions[0]=expressions[0].differentiate("x");
		simp_expressions[1]=new Mult(new Log(new Mult(new Num(9),new Var("x")),new Mult(new Num(9),new Var("x"))),new Mult(new Num(2),new Var("y")));//(log(9x, 9x)*2y) => 2y
		simp_expressions[2]=new Plus(new Mult(new Plus(new Num(3),new Num(6)),new Var("x")),new Mult(new Mult(new Num(4),new Var("x")),new Sin(new Num(0))));//((3+6)*x + (4x * sin(0))) => 9x.
		int[] corr_simp={-1,-1,-1};
		String[][] simplify_options={{"2+e^x", "2.0+e^x", "e^x+2.0", "e^x+2","2.0+2.718281828459045^x","2.0+2.71828^x"},{"2*y","y*2","2.0*y","y*2.0"},{"9*x","x*9","9.0*x","x*9.0"}};
		int[] crashed_simp={0,0,0};
		for (int i=0; i<simp_expressions.length;i++){
			try{
				String simplified_diff_ns_nb=simp_expressions[i].simplify().toString().replace(" ","").replace("(","").replace(")","");
				for (String s:simplify_options[i]){
					if (s.equals(simplified_diff_ns_nb)){
						corr_simp[i]=0;
					}
				}
				if (corr_simp[i]==-1){
					System.out.println("incorrect simplify of expression #"+(4+i));
				}
			}
			catch (Exception ex){
				crashed_simp[i]=-1;
				//ex.printStackTrace();	
				System.out.println("crashed at simplify of expression #"+(4+i));	
			}
		}

		Expression[] expressions_compare=new Expression[3];
		expressions_compare[0]=new Neg( new Minus( new Minus( new Plus( new Var("z") , new Neg(new Num(0) ) ) ,  new Var("w") ) , new Neg( new Div(  new Var("x") ,  new Var("y")) ) ) );
		
		expressions_compare[1]=new Plus( new Neg( new Mult( new Div(new Var("y") , new Neg( new Div( new Var("y") , new Var("x") ) ) ) , new Num(0) ) ) , new Neg( new Plus( new Neg( new Neg( new Minus( new Minus( new Neg( new Num(2) ) , new Num(0) ) , new Num(4) ) ) ) , new Var("x") ) ) );

		expressions_compare[2]=new Neg( new Mult( new Neg( new Var("y") ) , new Div( new Mult( new Num(1) , new Neg( new Num(4) ) ) , new Num(2)) ) );
		Map<String, Double> assign_c;
		assign_c = new TreeMap<String, Double>();
		assign_c.put("x", 2.5);
		assign_c.put("y", 25.0);
		assign_c.put("w", 2.5);
		assign_c.put("z", 0.3);
		int count_bad_comparisons=0;
		int comparison_crashes=0;
		for (int i=0; i<expressions_compare.length;i++){
			try{
				
				double result=expressions_compare[i].differentiate("x").evaluate(assign_c);
				double result_of_simplified=expressions_compare[i].simplify().differentiate("x").evaluate(assign_c);
				if (Math.abs(result-result_of_simplified)>0.1){
					System.out.println("result of diff assignment not equal to result of simplified diff assignment in expression #"+(7+i));	
					count_bad_comparisons--;
				}
			}
			catch (Exception ex){
				System.out.println("comparison crashed at expression #"+(7+i));
				comparison_crashes--;
				ex.printStackTrace();		
			}
		}

		
		/*System.out.println(corr_X[0]+" "+corr_X[1]+" "+corr_X[2]+" "+corr_X[3]);
		System.out.println(corr_Y[0]+" "+corr_Y[1]+" "+corr_Y[2]+" "+corr_Y[3]);
		System.out.println(crashed[0]+" "+crashed[1]+" "+crashed[2]+" "+crashed[3]);
		System.out.println(corr_simp[0]+" "+corr_simp[1]+" "+corr_simp[2]);
		System.out.println(crashed_simp[0]+" "+crashed_simp[1]+" "+crashed_simp[2]);
		System.out.println(count_bad_comparisons);
		System.out.println(comparison_crashes);
*/
		try(FileWriter fw = new FileWriter("../results.txt", true);
    		    BufferedWriter bw = new BufferedWriter(fw);
    		    PrintWriter out = new PrintWriter(bw)
		   )
		{
    			out.print("\t"+spaces_problem+"\t"+corr_assignment+"\t"+corr_X[0]+"\t"+corr_X[1]+"\t"+corr_X[2]+"\t"+corr_X[3]+"\t"+corr_Y[0]+"\t"+corr_Y[1]+"\t"+corr_Y[2]+"\t"+corr_Y[3]+"\t"+corr_simp[0]+"\t"+corr_simp[1]+"\t"+corr_simp[2]+"\t"+count_bad_comparisons+"\t"+crashed[0]+"\t"+crashed[1]+"\t"+crashed[2]+"\t"+crashed[3]+"\t"+assign_crash+"\t"+crashed_simp[0]+"\t"+crashed_simp[1]+"\t"+crashed_simp[2]+"\t"+comparison_crashes);
		} catch (IOException exception) {
    			System.out.println(" didn't write");
		}


		}
		}
